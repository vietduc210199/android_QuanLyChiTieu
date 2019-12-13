package com.example.quanlychitieu;

import android.content.Intent;
import android.os.Bundle;

import com.example.quanlychitieu.AccountActivity.LoginActivity;
import com.example.quanlychitieu.AccountActivity.SignupActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class ScrollingActivity extends AppCompatActivity {

    private ArrayList<chitieuitems> arrayList = new ArrayList<>(); // List Lưu trữ các khoản giao dịch
    private Integer sodu;// Khởi tạo số dư ban đầu
    private TextView tvSoDu;// Text hiển thị số dư
    private RecyclerView recyclerView; // Hiển thị List các giao dịch
    private Toolbar toolbar;
    private FloatingActionButton fab;

    public DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private String userID;

    @Override//Khởi chạy màn hình
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);


        initview();// Tạo các biến đối tượng  ban đầu

        if (user != null) {
            userID = user.getUid();
            mDatabase = mDatabase.child(userID);

        } else {
            Intent intent = new Intent(ScrollingActivity.this, LoginActivity.class);
            startActivityForResult(intent, 3);
        }

        dataChangeEvent();// Tạo các sự kiện khi data tại firebase thay đổi

        //Vào màn hình tạo giao dịch khi ấn nút
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScrollingActivity.this, khoanchiScreen.class);
                startActivityForResult(intent, 2);//Chạy màn hình giao dịch với code thực thi = 2
            }
        });

    }



    public void initview(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        tvSoDu = (TextView) findViewById(R.id.gia_tri_so_du);
        tvSoDu.setText(sodu + " VND");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    //Điều hướng các lựa chọn trong menu
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //Vào màn hình điều chỉnh số dư khi ấn nút
        if (id == R.id.dieu_chinh_so_du) {
            Intent intent = new Intent(ScrollingActivity.this, vicontrolscreen.class);

            intent.putExtra("SODU",sodu.toString());
            startActivityForResult(intent, 1);//Chạy màn hình điều chỉnh số dư với code thực thi = 2
        }

        //Thoát
        if (id == R.id.thoat){
            this.finish();
        }

        if (id == R.id.logout) {
            mAuth.signOut();
            Intent intent = new Intent(ScrollingActivity.this, LoginActivity.class);
            startActivityForResult(intent, 3);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    //Hàm nhận và xử lí dữ liệu được trả về từ các màn hình con
    protected void onActivityResult (int requestcode, int resultcode , Intent data){
        super.onActivityResult(requestcode,resultcode,data);
        Log.i("check","dang nhan result");
        if (resultcode == RESULT_OK && requestcode == 1)//Nếu kết quả trả về từ màn hình điều chỉnh(code thực thi == 1) số dư với RESULT_OK
        {
            if(data.hasExtra("SODU"))//Nếu giá trị trả về có key == "SODU"
            {

                sodu = Integer.parseInt(data.getExtras().getString("SODU")); //Lấy giá trị vào biến lưu trữ số dư

                mDatabase.child("Giá trị số dư").setValue(sodu, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if(databaseError == null) {
                            Toast.makeText(ScrollingActivity.this, "Điều chính số dư thành công!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ScrollingActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }

        if (resultcode == RESULT_OK && requestcode == 2)//Nếu kết quả trả về từ màn hình tạo giao dịch (code thực thi == 2) với RESULT_OK
        {
            Bundle bundle = data.getBundleExtra(khoanchiScreen.BUNDLE);
            String mucchitieu = bundle.getString(khoanchiScreen.MUCHITIEU);
            String giatri = bundle.getString(khoanchiScreen.GIATRI);
            String thoigian = bundle.getString(khoanchiScreen.THOIGIAN);

            arrayList.add(new chitieuitems(mucchitieu, giatri + " VND", thoigian));//Add khoản giao dịch mới vào List

            mDatabase.child("Danh sách giao dịch").setValue(arrayList);

            sodu -= Integer.parseInt(giatri);//Điều chỉnh lại số dư sau giao dịch
            mDatabase.child("Giá trị số dư").setValue(sodu);

        }

        if (resultcode == RESULT_OK && requestcode == 3)
        {
            user = mAuth.getCurrentUser();
            userID = user.getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference().child(userID);

            dataChangeEvent();
        }
    }

    private void dataChangeEvent(){


        mDatabase.child("Giá trị số dư").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sodu = Integer.parseInt(dataSnapshot.getValue().toString());
                tvSoDu.setText(sodu.toString() + " VND");// hiển thị số dư mới lên màn hình
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase.child("Danh sách giao dịch").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer i = 0;

                arrayList = new ArrayList<chitieuitems>();
                while(dataSnapshot.child(i.toString()).getValue() != null)
                {
                    chitieuitems items = dataSnapshot.child(i.toString()).getValue(chitieuitems.class);

                    arrayList.add(items);
                    i++;
                }

                itemsAdapter itemsadapter = new itemsAdapter(arrayList, getApplicationContext());
                recyclerView.setAdapter(itemsadapter);//Hiển thị lên màn hình
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
