package com.example.quanlychitieu.ChiTieuActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quanlychitieu.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ChiTieuActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemClickListener {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;


    private String MucChiTieu;
    private String GiaTri;
    private String ThoiGian;
    private String LoaiGiaoDich = "Khoản Chi";
    private Calendar calendar;
    private String GhiChu = new String();


    public DatabaseReference mDatabase;

    public static final String MUCHITIEU = "MUCCHITIEU";
    public static final String GIATRI = "GIATRI";
    public static final String THOIGIAN = "THOIGIAN";
    public static final String LOAIGIAODICH = "LOAIGIAODICH";
    public static final String GHICHU = "GHICHU";
    public static final String BUNDLE = "BUNDLE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tieu);

        tabLayout = (TabLayout) findViewById((R.id.tablayout_id));
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        ViewPaperAdapter adapter = new ViewPaperAdapter(getSupportFragmentManager());
        adapter.AddFragment(new fragment_khoan_chi(), "Khoản Chi");
        adapter.AddFragment(new fragment_khoan_thu(), "Khoản Thu");

        calendar = Calendar.getInstance();

        viewPager.setAdapter(adapter);
        //tabLayout.setupWithViewPager(viewPager);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                selectTabIndex(0);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0) {
                    LoaiGiaoDich = "Khoản Chi";

                    final Intent intent = getIntent();
                    String userID = intent.getStringExtra("ID");

                    mDatabase = FirebaseDatabase.getInstance().getReference().child(userID);

                    final EditText et_MucChiTieu_chi = (EditText) findViewById(R.id.et_mucChiTieu_chi);
                    final EditText et_GiaTri_chi = (EditText) findViewById(R.id.et_GiaTri_chi);
                    final EditText et_ThoiGian_chi = (EditText) findViewById(R.id.et_ThoiGian_chi);
                    final ImageButton bnt_ThoiGian_chi = (ImageButton) findViewById(R.id.bnt_ThoiGian_chi);
                    final Spinner sp_mucChiTieu_chi = (Spinner) findViewById(R.id.sp_mucChiTieu_chi);
                    final EditText et_GhiChu_chi = (EditText) findViewById(R.id.et_GhiChu_chi);
                    final ImageButton bnt_GhiChu_chi = (ImageButton) findViewById(R.id.bnt_GhiChu_chi);

                    bnt_GhiChu_chi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ChiTieuActivity.this, GhiChuActivity.class);
                            if(et_GhiChu_chi.getText().toString()!=null) {
                                intent.putExtra("GHICHU", et_GhiChu_chi.getText().toString());
                            }
                            startActivityForResult(intent, 1);
                        }
                    });

                    if(!GhiChu.isEmpty())et_GhiChu_chi.setText(GhiChu);

                    final ArrayList<String> arrayList = new ArrayList<String>();

                    mDatabase.child("Mục Chi Tiêu").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Integer i = 0;

                            while(dataSnapshot.child(i.toString()).getValue() != null)
                            {
                                arrayList.add(dataSnapshot.child(i.toString()).getValue().toString());
                                i++;
                            }
                            ArrayAdapter adapter = new ArrayAdapter<String>(ChiTieuActivity.this, R.layout.support_simple_spinner_dropdown_item, arrayList);
                            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            sp_mucChiTieu_chi.setAdapter(adapter);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    sp_mucChiTieu_chi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String text = parent.getItemAtPosition(position).toString();
                            et_MucChiTieu_chi.setText(text);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                    et_ThoiGian_chi.setText(DateFormat.getDateInstance().format(calendar.getTime()));

                    bnt_ThoiGian_chi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogFragment datePicker = new datePickerFragment();
                            datePicker.show(getSupportFragmentManager(), "date picker");
                        }
                    });

                    FloatingActionButton fab_chi = findViewById(R.id.fab_chi);
                    fab_chi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolean check = false;

                            for (Integer i = 0; i< arrayList.size(); i++) {
                                Log.i("Check" + i.toString(), arrayList.get(i).toString());

                                if(et_MucChiTieu_chi.getText().toString().toUpperCase().equals(arrayList.get(i).toString().toUpperCase())){
                                    et_MucChiTieu_chi.setText(arrayList.get(i).toString());
                                    check = true;
                                    break;
                                }
                            }

                            if(check == false)
                            {
                                arrayList.add(et_MucChiTieu_chi.getText().toString());
                                mDatabase.child("Mục Chi Tiêu").setValue(arrayList);
                            }



                            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                            ThoiGian = format.format(calendar.getTime());
                            et_ThoiGian_chi.setText(ThoiGian);

                            MucChiTieu = et_MucChiTieu_chi.getText().toString();
                            GiaTri = et_GiaTri_chi.getText().toString();
                            ThoiGian = et_ThoiGian_chi.getText().toString();
                            GhiChu = et_GhiChu_chi.getText().toString();

                            Bundle bundle = new Bundle();//Tạo Bundle để truyền dữ liệu về

                            //Đưa các giá trị vào bundle
                            bundle.putString(MUCHITIEU, MucChiTieu);//
                            bundle.putString(GIATRI, GiaTri);
                            bundle.putString(THOIGIAN, ThoiGian);
                            bundle.putString(LOAIGIAODICH, LoaiGiaoDich);
                            bundle.putString(GHICHU,GhiChu);

                            if(GiaTri.isEmpty()) {
                                Toast.makeText(ChiTieuActivity.this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();
                            } else{
                                intent.putExtra(BUNDLE, bundle);//Gửi bundle vào intent
                                setResult(RESULT_OK, intent);//Truyền intent về với RESULT_OK
                                ChiTieuActivity.super.finish();
                            }
                        }
                    });
                }else {
                    LoaiGiaoDich = "Khoản Thu";
                    final Intent intent = getIntent();
                    String userID = intent.getStringExtra("ID");

                    mDatabase = FirebaseDatabase.getInstance().getReference().child(userID);

                    final EditText et_MucChiTieu_thu = (EditText) findViewById(R.id.et_mucChiTieu_thu);
                    final EditText et_GiaTri_thu = (EditText) findViewById(R.id.et_GiaTri_thu);
                    final EditText et_ThoiGian_thu = (EditText) findViewById(R.id.et_ThoiGian_thu);
                    final ImageButton bnt_ThoiGian_thu = (ImageButton) findViewById(R.id.bnt_ThoiGian_thu);
                    final Spinner sp_mucChiTieu_thu = (Spinner) findViewById(R.id.sp_mucChiTieu_thu);
                    final ImageButton bnt_GhiChu_thu = (ImageButton) findViewById(R.id.bnt_GhiChu_thu);
                    final EditText et_GhiChu_thu = (EditText) findViewById(R.id.et_GhiChu_thu);
                    final ArrayList<String> arrayList = new ArrayList<String>();

                    bnt_GhiChu_thu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ChiTieuActivity.this, GhiChuActivity.class);
                            if(et_GhiChu_thu.getText().toString()!=null) {
                                intent.putExtra("GHICHU", et_GhiChu_thu.getText().toString());
                            }
                            startActivityForResult(intent, 2);
                        }
                    });

                    if(!GhiChu.isEmpty()) et_GhiChu_thu.setText(GhiChu);

                    mDatabase.child("Mục Thu Nhập").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Integer i = 0;


                            while(dataSnapshot.child(i.toString()).getValue() != null)
                            {
                                arrayList.add(dataSnapshot.child(i.toString()).getValue().toString());
                                i++;
                            }
                            ArrayAdapter adapter = new ArrayAdapter<String>(ChiTieuActivity.this, R.layout.support_simple_spinner_dropdown_item, arrayList);
                            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            sp_mucChiTieu_thu.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    sp_mucChiTieu_thu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String text = parent.getItemAtPosition(position).toString();
                            et_MucChiTieu_thu.setText(text);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    et_ThoiGian_thu.setText(DateFormat.getDateInstance().format(calendar.getTime()));

                    bnt_ThoiGian_thu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogFragment datePicker = new datePickerFragment();
                            datePicker.show(getSupportFragmentManager(), "date picker");
                        }
                    });

                    FloatingActionButton fab_thu = findViewById(R.id.fab_thu);
                    fab_thu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolean check = false;

                            for (Integer i = 0; i< arrayList.size(); i++) {
                                Log.i("Check" + i.toString(), arrayList.get(i).toString());

                                if(et_MucChiTieu_thu.getText().toString().toUpperCase().equals(arrayList.get(i).toString().toUpperCase())){
                                    et_MucChiTieu_thu.setText(arrayList.get(i).toString());
                                    check = true;
                                    break;
                                }
                            }

                            if(check == false)
                            {
                                arrayList.add(et_MucChiTieu_thu.getText().toString());
                                mDatabase.child("Mục Thu Nhập").setValue(arrayList);
                            }

                            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                            ThoiGian = format.format(calendar.getTime());
                            et_ThoiGian_thu.setText(ThoiGian);

                            MucChiTieu = et_MucChiTieu_thu.getText().toString();
                            GiaTri = et_GiaTri_thu.getText().toString();
                            ThoiGian = et_ThoiGian_thu.getText().toString();
                            GhiChu = et_GhiChu_thu.getText().toString();

                            Bundle bundle = new Bundle();//Tạo Bundle để truyền dữ liệu về

                            //Đưa các giá trị vào bundle
                            bundle.putString(MUCHITIEU, MucChiTieu);//
                            bundle.putString(GIATRI, GiaTri);
                            bundle.putString(THOIGIAN, ThoiGian);
                            bundle.putString(LOAIGIAODICH, LoaiGiaoDich);
                            bundle.putString(GHICHU, GhiChu);

                            if(GiaTri.isEmpty()) {
                                Toast.makeText(ChiTieuActivity.this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();
                            } else{
                                intent.putExtra(BUNDLE, bundle);//Gửi bundle vào intent
                                setResult(RESULT_OK, intent);//Truyền intent về với RESULT_OK
                                ChiTieuActivity.super.finish();
                            }
                        }
                    });
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }


        });
    }

    @Override
    protected void onActivityResult (int requestcode, int resultcode , Intent data) {
        super.onActivityResult(requestcode, resultcode, data);

        if(resultcode == RESULT_OK && requestcode == 1){
            if(data.hasExtra("GHICHU")){
                GhiChu = data.getExtras().getString("GHICHU");

                tabLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        tabLayout.setupWithViewPager(viewPager);
                        selectTabIndex(0);

                    }
                });
            }
        }

        if(resultcode == RESULT_OK && requestcode == 2){
            if(data.hasExtra("GHICHU")){
                GhiChu = data.getExtras().getString("GHICHU");

                tabLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        tabLayout.setupWithViewPager(viewPager);
                        selectTabIndex(1);

                    }
                });
            }
        }
    }

    private void selectTabIndex(final int index){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tabLayout.setScrollPosition(index, 0, true);
                viewPager.setCurrentItem(index);
                // or
                // tabLayout.getTabAt(index).select();
            }
        },100);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        final EditText et_ThoiGian_chi = (EditText) findViewById(R.id.et_ThoiGian_chi);
        et_ThoiGian_chi.setText(DateFormat.getDateInstance().format(calendar.getTime()));

        final EditText et_ThoiGian_thu = (EditText) findViewById(R.id.et_ThoiGian_thu);
        et_ThoiGian_thu.setText(DateFormat.getDateInstance().format(calendar.getTime()));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
