package com.example.quanlychitieu.ThongKeActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.quanlychitieu.chitieuitems;
import com.example.quanlychitieu.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ThongKeActivity extends AppCompatActivity {

    private EditText et_loai;
    private EditText et_muc;
    private EditText et_ngay;
    private EditText et_thang;
    private EditText et_nam;

    private Button bnt_thong_ke;

    private Spinner sp_loai;
    private Spinner sp_muc;

    public DatabaseReference mDatabase;
    private String userID;
    private ArrayList<String> muc_chi_tieu_List = new ArrayList<String>();
    private ArrayList<String> muc_thu_nhap_List = new ArrayList<String>();
    private ArrayList<String> mucList = new ArrayList<String>();
    private ArrayList<chitieuitems> arrayList = new ArrayList<chitieuitems>();
    private ArrayList<chitieuitems> arrayList_thong_ke = new ArrayList<chitieuitems>();

    private ImageButton bnt_danh_sach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        initview();

        sp_loai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                et_loai.setText(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        et_loai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if(text.equals("Tất cả")) {
                    Log.i("Check text change", text);
                    ArrayAdapter adapterMuc = new ArrayAdapter<String>(ThongKeActivity.this, R.layout.support_simple_spinner_dropdown_item, mucList);
                    adapterMuc.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    sp_muc.setAdapter(adapterMuc);
                } else if(text.equals("Khoản Chi")) {
                    Log.i("Check text change", text);
                    ArrayAdapter adapterMuc = new ArrayAdapter<String>(ThongKeActivity.this, R.layout.support_simple_spinner_dropdown_item, muc_chi_tieu_List);
                    adapterMuc.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    sp_muc.setAdapter(adapterMuc);
                }
                else if(text.equals("Khoản Thu")) {
                    Log.i("Check text change", text);
                    ArrayAdapter adapterMuc = new ArrayAdapter<String>(ThongKeActivity.this, R.layout.support_simple_spinner_dropdown_item, muc_thu_nhap_List);
                    adapterMuc.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    sp_muc.setAdapter(adapterMuc);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sp_muc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                et_muc.setText(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bnt_thong_ke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initdata();
                arrayList_thong_ke.clear();

                arrayList_thong_ke.addAll(arrayList);
                if(!et_loai.getText().toString().equals("Tất cả")){
                    int i = 1;
                    while(i <= arrayList_thong_ke.size()){
                        chitieuitems items = arrayList_thong_ke.get(i-1);
                        if(!items.getLoaigiaodich().toString().equals(et_loai.getText().toString())) {
                            Log.i("Check", items.getLoaigiaodich());
                            arrayList_thong_ke.remove(items);
                        }
                        else i++;
                    }
                }

                if(!et_muc.getText().toString().equals("Tất cả")) {
                    int i = 1;
                    while(i <= arrayList_thong_ke.size()){
                        chitieuitems items = arrayList_thong_ke.get(i-1);
                        if(!items.getLoaichitieu().toString().equals(et_muc.getText().toString())) {
                            arrayList_thong_ke.remove(items);
                        }
                        else i++;
                    }
                }

                if(!et_ngay.getText().toString().isEmpty()) {
                    int i = 1;
                    while(i <= arrayList_thong_ke.size()){
                        chitieuitems items = arrayList_thong_ke.get(i-1);

                        try {
                            Date date=new SimpleDateFormat("dd/MM/yyyy").parse(items.getThoigian());

                            Log.i("check day", String.valueOf(date.getDate()));
                            if(date.getDate() != Integer.parseInt(et_ngay.getText().toString())) arrayList_thong_ke.remove(items);
                            else i++;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if(!et_thang.getText().toString().isEmpty()) {
                    int i = 1;
                    while(i <= arrayList_thong_ke.size()){
                        chitieuitems items = arrayList_thong_ke.get(i-1);

                        try {
                            Date date=new SimpleDateFormat("dd/MM/yyyy").parse(items.getThoigian());

                            Log.i("check month", String.valueOf(date.getMonth()));
                            if(date.getMonth() != Integer.parseInt(et_thang.getText().toString())-1) arrayList_thong_ke.remove(items);
                            else i++;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if(!et_nam.getText().toString().isEmpty()) {
                    int i = 1;
                    while(i <= arrayList_thong_ke.size()){
                        chitieuitems items = arrayList_thong_ke.get(i-1);

                        try {
                            Date date=new SimpleDateFormat("dd/MM/yyyy").parse(items.getThoigian());

                            Log.i("check year", String.valueOf(date.getYear()));
                            if(date.getYear() != Integer.parseInt(et_nam.getText().toString()) - 2000 + 100) arrayList_thong_ke.remove(items);
                            else i++;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        bnt_danh_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("Check List", arrayList_thong_ke.get(0).getGiatri().toString());
                ThongKeDialog thongKeDialogDialog = new ThongKeDialog(arrayList_thong_ke);
                thongKeDialogDialog.show(getSupportFragmentManager(), "thống kê dialog");
            }
        });
    }

    private void initview() {
        Intent intent = getIntent();
        userID = intent.getStringExtra("ID");
        mDatabase = FirebaseDatabase.getInstance().getReference().child(userID);

        mDatabase.child("Mục Chi Tiêu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer i = 0;
                muc_chi_tieu_List.add("Tất cả");

                while(dataSnapshot.child(i.toString()).getValue() != null)
                {
                    muc_chi_tieu_List.add(dataSnapshot.child(i.toString()).getValue().toString());
                    mucList.add(dataSnapshot.child(i.toString()).getValue().toString());
                    i++;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase.child("Mục Thu Nhập").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer i = 0;
                muc_thu_nhap_List.add("Tất cả");

                while(dataSnapshot.child(i.toString()).getValue() != null)
                {
                    muc_thu_nhap_List.add(dataSnapshot.child(i.toString()).getValue().toString());
                    mucList.add(dataSnapshot.child(i.toString()).getValue().toString());
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mucList.add("Tất cả");

        et_loai = (EditText) findViewById(R.id.et_tk_loai);
        et_loai.setText("Tất cả");
        et_muc = (EditText) findViewById(R.id.et_tk_muc);
        et_muc.setText("Tất cả");
        et_ngay = (EditText) findViewById(R.id.et_tk_ngay);
        et_thang = (EditText) findViewById(R.id.et_tk_thang);
        et_nam = (EditText) findViewById(R.id.et_tk_nam);

        bnt_thong_ke = (Button) findViewById(R.id.bnt_thong_ke);

        ArrayList<String> loaiList = new ArrayList<String>();
        loaiList.add("Tất cả");
        loaiList.add("Khoản Chi");
        loaiList.add("Khoản Thu");

        ArrayAdapter adapterLoai = new ArrayAdapter<String>(ThongKeActivity.this, R.layout.support_simple_spinner_dropdown_item, loaiList);
        adapterLoai.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sp_loai = (Spinner) findViewById(R.id.sp_tk_loai);
        sp_loai.setAdapter(adapterLoai);
        sp_muc = (Spinner) findViewById(R.id.sp_tk_muc);

        bnt_danh_sach = (ImageButton) findViewById(R.id.bnt_danhsach_items_tk);
    }

    private void initdata() {
        mDatabase.child("Danh sách giao dịch").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer i = 0;

                arrayList.clear();
                while(dataSnapshot.child(i.toString()).getValue() != null)
                {
                    chitieuitems items = dataSnapshot.child(i.toString()).getValue(chitieuitems.class);
                    try {
                        Date date=new SimpleDateFormat("dd/MM/yyyy").parse(items.getThoigian());

                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    arrayList.add(items);
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        initdata();
    }
}
