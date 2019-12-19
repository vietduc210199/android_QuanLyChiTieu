package com.example.quanlychitieu.ChiTieuActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.quanlychitieu.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GhiChuActivity extends AppCompatActivity {

    private ImageButton bnt_back;
    private EditText et_ghi_chu;
    private FloatingActionButton fab;

    public static final String GHICHU = "GHICHU";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghi_chu);

        final Intent intent = getIntent();

        bnt_back = (ImageButton) findViewById(R.id.back_button);
        et_ghi_chu = (EditText) findViewById(R.id.et_ghi_chu);
        fab = (FloatingActionButton) findViewById(R.id.fab_done);

        if(intent.hasExtra("GHICHU")) {
            et_ghi_chu.setText(intent.getExtras().getString("GHICHU"));
        }

        bnt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GhiChuActivity.super.finish();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = getIntent();
                intent.putExtra(GHICHU,et_ghi_chu.getText().toString());
                Log.i("check 2", et_ghi_chu.getText().toString());
                setResult(RESULT_OK, intent);
                GhiChuActivity.super.finish();
            }
        });
    }
}
