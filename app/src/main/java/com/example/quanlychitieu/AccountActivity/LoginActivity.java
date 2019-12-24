package com.example.quanlychitieu.AccountActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.ScrollingActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private Button bntLogin;
    private Button bntRegister;
    private EditText etUsername;
    private EditText etPassword;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    public final String USERID = "USERID";

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

        bntLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void initview() {

        bntLogin = (Button) findViewById(R.id.login);
        bntRegister = (Button) findViewById(R.id.register);
        etUsername = (EditText) findViewById(R.id.username_login);
        etPassword = (EditText) findViewById(R.id.password_login);

        mAuth = FirebaseAuth.getInstance();
    }

    private void login() {
        String email = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      if(task.isSuccessful()){
                          mAuth = FirebaseAuth.getInstance();
                          user = mAuth.getCurrentUser();
                          if(user!=null) updateUI(user);

                      } else {
                          Toast.makeText(LoginActivity.this, "Đăng nhập thất bại!!", Toast.LENGTH_SHORT).show();
                      }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if(user != null) updateUI(user);
    }

    private void updateUI(FirebaseUser currentUser) {
        Intent intent = new Intent(LoginActivity.this, ScrollingActivity.class);
        intent.putExtra(USERID, currentUser.getUid());
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
