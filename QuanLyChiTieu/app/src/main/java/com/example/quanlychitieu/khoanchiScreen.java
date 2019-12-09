package com.example.quanlychitieu;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.ParcelUuid;
import android.view.View;
import android.widget.EditText;

public class khoanchiScreen extends AppCompatActivity {

    private String MucChiTieu;
    private String GiaTri;

    public static final String MUCHITIEU = "MUCCHITIEU";
    public static final String GIATRI = "GIATRI";
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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MucChiTieu = et_MucChiTieu.getText().toString();
                GiaTri = et_GiaTri.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString(MUCHITIEU, MucChiTieu);
                bundle.putString(GIATRI, GiaTri);

                intent.putExtra(BUNDLE, bundle);
                setResult(RESULT_OK, intent);
                khoanchiScreen.super.finish();
            }
        });
    }

}
