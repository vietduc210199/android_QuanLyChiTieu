package com.example.quanlychitieu.AccountActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.quanlychitieu.R;

public class LoginActivity extends AppCompatActivity {

    private Button bntLogin;
    private Button bntRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initview();

        bntRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initview() {
        bntLogin = (Button) findViewById(R.id.login);
        bntRegister = (Button) findViewById(R.id.register);
    }
}
