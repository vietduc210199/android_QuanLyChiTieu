package com.example.quanlychitieu.AccountActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlychitieu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etRePassword;
    private Button bntRegister;
    private TextView textView;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initview();

        mAuth = FirebaseAuth.getInstance();

        bntRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etPassword.getText().toString().equals(etRePassword.getText().toString())) {
                    Toast.makeText(SignupActivity.this, "Mật Khẩu Nhập Vào Không Trùng Khớp", Toast.LENGTH_SHORT).show();
                }else registration(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });
    }

    private void initview() {
        etUsername = (EditText) findViewById(R.id.username_register);
        etPassword = (EditText) findViewById(R.id.password_register);
        etRePassword = (EditText) findViewById(R.id.password_check);
        bntRegister = (Button)findViewById(R.id.register_signup);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        textView = (TextView) findViewById(R.id.test);

    }

    private void registration(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.e("check",task.getResult().getUser().getUid().toString());
                            mDatabase.child(task.getResult().getUser().getUid()).child("Giá trị số dư").setValue(0);
                            mDatabase.child(task.getResult().getUser().getUid()).child("Thu Nhập").setValue(0);
                            mDatabase.child(task.getResult().getUser().getUid()).child("Chi Tiêu").setValue(0);
                            mDatabase.child(task.getResult().getUser().getUid()).child("Dao Động Số Dư").setValue(0);
                            mDatabase.child(task.getResult().getUser().getUid()).child("Danh sách giao dịch").setValue("   ");
                            mDatabase.child(task.getResult().getUser().getUid()).child("Mục Chi Tiêu").setValue("   ");
                            mDatabase.child(task.getResult().getUser().getUid()).child("Mục Thu Nhập").setValue("   ");
                            SignupActivity.super.finish();
                        }else {
                            Toast.makeText(SignupActivity.this, "Thông Tin Tài Khoản Không Hợp Lệ!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    }
                );
    }
}


