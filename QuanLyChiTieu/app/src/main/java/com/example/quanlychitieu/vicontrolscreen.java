package com.example.quanlychitieu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class vicontrolscreen extends AppCompatActivity {

    private ImageButton backbnt;
    private ImageButton donebnt;
    private EditText editssodu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vicontrolscreen);


        initview();

        final Intent intent = getIntent();
        String giatrisodu = intent.getStringExtra("SODU");
        editssodu.setText(giatrisodu);
        donebnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sodu = editssodu.getText().toString();
                intent.putExtra("SODU", sodu);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        backbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, intent);
                vicontrolscreen.super.finish();
            }
        });
    }

    private void initview(){
        backbnt = (ImageButton) findViewById(R.id.back_button);
        donebnt = (ImageButton) findViewById(R.id.done_button);
        editssodu = (EditText) findViewById((R.id.et_sodu));
    }
}
