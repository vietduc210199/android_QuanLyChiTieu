package com.example.quanlychitieu;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.quanlychitieu.ChiTieuActivity.datePickerFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.text.DateFormat;
import java.util.Calendar;

public class khoanchiScreen extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private String MucChiTieu;
    private String GiaTri;
    private String ThoiGian;
    private Calendar calendar;



    public static final String MUCHITIEU = "MUCCHITIEU";
    public static final String GIATRI = "GIATRI";
    public static final String THOIGIAN = "THOIGIAN";
    public static final String BUNDLE = "BUNDLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoanchi_screen);

        final Intent intent = getIntent();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText et_MucChiTieu = (EditText) findViewById(R.id.et_mucChiTieu);
        final EditText et_GiaTri = (EditText) findViewById(R.id.et_GiaTri);
        final EditText et_ThoiGian = (EditText) findViewById(R.id.et_ThoiGian);
        final ImageButton bnt_ThoiGian = (ImageButton) findViewById(R.id.bnt_ThoiGian);

        calendar = Calendar.getInstance();
        et_ThoiGian.setText(DateFormat.getDateInstance().format(calendar.getTime()));

        bnt_ThoiGian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new datePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MucChiTieu = et_MucChiTieu.getText().toString();
                GiaTri = et_GiaTri.getText().toString();
                ThoiGian = et_ThoiGian.getText().toString();

                Bundle bundle = new Bundle();//Tạo Bundle để truyền dữ liệu về

                //Đưa các giá trị vào bundle
                bundle.putString(MUCHITIEU, MucChiTieu);//
                bundle.putString(GIATRI, GiaTri);
                bundle.putString(THOIGIAN, ThoiGian);

                intent.putExtra(BUNDLE, bundle);//Gửi bundle vào intent
                setResult(RESULT_OK, intent);//Truyền intent về với RESULT_OK
                khoanchiScreen.super.finish();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        final EditText et_ThoiGian = (EditText) findViewById(R.id.et_ThoiGian);
        et_ThoiGian.setText(DateFormat.getDateInstance().format(calendar.getTime()));
    }
}
