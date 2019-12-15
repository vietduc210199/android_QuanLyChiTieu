package com.example.quanlychitieu.ChiTieuActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.khoanchiScreen;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.util.Calendar;

public class ChiTieuActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener{

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    private String MucChiTieu;
    private String GiaTri;
    private String ThoiGian;
    private String LoaiGiaoDich = "Khoản Chi";
    private Calendar calendar;


    public static final String MUCHITIEU = "MUCCHITIEU";
    public static final String GIATRI = "GIATRI";
    public static final String THOIGIAN = "THOIGIAN";
    public static final String LOAIGIAODICH = "LOAIGIAODICH";
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

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0) {
                    LoaiGiaoDich = "Khoản Chi";

                    final Intent intent = getIntent();

                    final EditText et_MucChiTieu_chi = (EditText) findViewById(R.id.et_mucChiTieu_chi);
                    final EditText et_GiaTri_chi = (EditText) findViewById(R.id.et_GiaTri_chi);
                    final EditText et_ThoiGian_chi = (EditText) findViewById(R.id.et_ThoiGian_chi);
                    final ImageButton bnt_ThoiGian_chi = (ImageButton) findViewById(R.id.bnt_ThoiGian_chi);

                    calendar = Calendar.getInstance();
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
                            MucChiTieu = et_MucChiTieu_chi.getText().toString();
                            GiaTri = et_GiaTri_chi.getText().toString();
                            ThoiGian = et_ThoiGian_chi.getText().toString();

                            Bundle bundle = new Bundle();//Tạo Bundle để truyền dữ liệu về

                            //Đưa các giá trị vào bundle
                            bundle.putString(MUCHITIEU, MucChiTieu);//
                            bundle.putString(GIATRI, GiaTri);
                            bundle.putString(THOIGIAN, ThoiGian);
                            bundle.putString(LOAIGIAODICH, LoaiGiaoDich);

                            intent.putExtra(BUNDLE, bundle);//Gửi bundle vào intent
                            setResult(RESULT_OK, intent);//Truyền intent về với RESULT_OK
                            ChiTieuActivity.super.finish();
                        }
                    });
                }else {
                    LoaiGiaoDich = "Khoản Thu";
                    final Intent intent = getIntent();

                    final EditText et_MucChiTieu_thu = (EditText) findViewById(R.id.et_mucChiTieu_thu);
                    final EditText et_GiaTri_thu = (EditText) findViewById(R.id.et_GiaTri_thu);
                    final EditText et_ThoiGian_thu = (EditText) findViewById(R.id.et_ThoiGian_thu);
                    final ImageButton bnt_ThoiGian_thu = (ImageButton) findViewById(R.id.bnt_ThoiGian_thu);

                    calendar = Calendar.getInstance();
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
                            MucChiTieu = et_MucChiTieu_thu.getText().toString();
                            GiaTri = et_GiaTri_thu.getText().toString();
                            ThoiGian = et_ThoiGian_thu.getText().toString();

                            Bundle bundle = new Bundle();//Tạo Bundle để truyền dữ liệu về

                            //Đưa các giá trị vào bundle
                            bundle.putString(MUCHITIEU, MucChiTieu);//
                            bundle.putString(GIATRI, GiaTri);
                            bundle.putString(THOIGIAN, ThoiGian);
                            bundle.putString(LOAIGIAODICH, LoaiGiaoDich);

                            intent.putExtra(BUNDLE, bundle);//Gửi bundle vào intent
                            setResult(RESULT_OK, intent);//Truyền intent về với RESULT_OK
                            ChiTieuActivity.super.finish();
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
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        final EditText et_ThoiGian_chi = (EditText) findViewById(R.id.et_ThoiGian_chi);
        et_ThoiGian_chi.setText(DateFormat.getDateInstance().format(calendar.getTime()));

        final EditText et_ThoiGian_thu = (EditText) findViewById(R.id.et_ThoiGian_thu);
        et_ThoiGian_thu.setText(DateFormat.getDateInstance().format(calendar.getTime()));
    }
}
