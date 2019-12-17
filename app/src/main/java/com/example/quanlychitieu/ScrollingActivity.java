package com.example.quanlychitieu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.quanlychitieu.AccountActivity.LoginActivity;
import com.example.quanlychitieu.AccountActivity.SignupActivity;
import com.example.quanlychitieu.ChiTieuActivity.ChiTieuActivity;
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
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class ScrollingActivity extends AppCompatActivity implements itemsAdapter.SelectedItem{

    private ArrayList<chitieuitems> arrayList = new ArrayList<>(); // List Lưu trữ các khoản giao dịch

    private TextView tvSoDu;// Text hiển thị số dư
    private RecyclerView recyclerView; // Hiển thị List các giao dịch
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private TextView tvThuNhap;
    private TextView tvChiTieu;
    private TextView tvDaoDongSoDu;
    private SearchView searchView;

    private Integer sodu;// Khởi tạo số dư ban đầu
    private Integer thunhap = 100000;
    private Integer chitieu = 100000;
    private Integer daodongsodu = 100000;

    public DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private itemsAdapter itemsadapter;

    private String userID;

    @Override//Khởi chạy màn hình
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);



        initview();// Tạo các biến đối tượng  ban đầu


        //Vào màn hình tạo giao dịch khi ấn nút
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScrollingActivity.this, ChiTieuActivity.class);
                intent.putExtra("ID", userID);
                startActivityForResult(intent, 2);//Chạy màn hình giao dịch với code thực thi = 2
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                itemsadapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    public void initview(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        tvSoDu = (TextView) findViewById(R.id.so_du);
        tvSoDu.setText(sodu + " VND");

        tvThuNhap = (TextView) findViewById(R.id.tv_ThuNhap);
        tvThuNhap.setText("+ " + thunhap + " VND");

        tvChiTieu = (TextView) findViewById(R.id.tv_ChiTieu);
        tvChiTieu.setText("- " + chitieu + " VND");

        tvDaoDongSoDu = (TextView) findViewById(R.id.tv_DaoDongSoDu);
        tvDaoDongSoDu.setText(daodongsodu + " VND");

        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setMaxWidth(Integer.MAX_VALUE);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        itemsadapter = new itemsAdapter(arrayList, getApplicationContext(), this);
        recyclerView.setAdapter(itemsadapter);//Hiển thị lên màn hình

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
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            Intent intent = new Intent(ScrollingActivity.this, LoginActivity.class);
            startActivity(intent);
            super.finish();
        }

        if(id == R.id.xoa_lich_su) {
            mDatabase.child("Danh sách giao dịch").setValue(" ");
            arrayList.clear();
            itemsadapter = new itemsAdapter(arrayList, getApplicationContext(), this);
            recyclerView.setAdapter(itemsadapter);//Hiển thị lên màn hình
        }

        if(id == R.id.xoa_du_lieu) {
            mDatabase.child("Thu Nhập").setValue(0);
            mDatabase.child("Chi Tiêu").setValue(0);
            mDatabase.child("Dao Động Số Dư").setValue(0);
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
            Bundle bundle = data.getBundleExtra(ChiTieuActivity.BUNDLE);
            String mucchitieu = bundle.getString(ChiTieuActivity.MUCHITIEU);
            String giatri = bundle.getString(ChiTieuActivity.GIATRI);
            String thoigian = bundle.getString(ChiTieuActivity.THOIGIAN);
            String loaigiaodich = bundle.getString(ChiTieuActivity.LOAIGIAODICH);

            arrayList.add(new chitieuitems(mucchitieu, giatri + " VND", thoigian, loaigiaodich));//Add khoản giao dịch mới vào List

            itemsadapter = new itemsAdapter(arrayList, getApplicationContext(), this);

            recyclerView.setAdapter(itemsadapter);//Hiển thị lên màn hình


            mDatabase.child("Danh sách giao dịch").setValue(arrayList);

            if(loaigiaodich.equals("Khoản Chi") ){
                Log.i("check", loaigiaodich);
                sodu -= Integer.parseInt(giatri);//Điều chỉnh lại số dư sau giao dịch
                mDatabase.child("Giá trị số dư").setValue(sodu);
                chitieu += Integer.parseInt(giatri);
                mDatabase.child("Chi Tiêu").setValue(chitieu);

                daodongsodu = thunhap - chitieu;
                mDatabase.child("Dao Động Số Dư").setValue(daodongsodu);

            }
            else if (loaigiaodich.equals("Khoản Thu")){
                sodu += Integer.parseInt(giatri);
                thunhap += Integer.parseInt(giatri);
                mDatabase.child("Thu Nhập").setValue(thunhap);

                daodongsodu = thunhap - chitieu;
                mDatabase.child("Dao Động Số Dư").setValue(daodongsodu);
                mDatabase.child("Giá trị số dư").setValue(sodu);
            }
        }

    }

    public void onStart() {
        super.onStart();

        Intent intent = getIntent();

        userID = intent.getStringExtra("USERID");

        mDatabase = FirebaseDatabase.getInstance().getReference().child(userID);

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

        mDatabase.child("Thu Nhập").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                thunhap = Integer.parseInt(dataSnapshot.getValue().toString());
                tvThuNhap.setText("+ " + thunhap + " VND");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase.child("Chi Tiêu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chitieu = Integer.parseInt(dataSnapshot.getValue().toString());
                tvChiTieu.setText("- " + chitieu + " VND");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase.child("Dao Động Số Dư").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                daodongsodu = Integer.parseInt(dataSnapshot.getValue().toString());
                if(daodongsodu >= 0)
                {
                    tvDaoDongSoDu.setTextColor(Color.argb(255, 54, 243, 28));
                    tvDaoDongSoDu.setText("+ " + daodongsodu + " VND");
                }else {
                    tvDaoDongSoDu.setTextColor(Color.argb(255, 233, 30, 99));
                    tvDaoDongSoDu.setText(daodongsodu + " VND");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase.child("Danh sách giao dịch").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer i = 0;

                arrayList.clear();
                while(dataSnapshot.child(i.toString()).getValue() != null)
                {
                    chitieuitems items = dataSnapshot.child(i.toString()).getValue(chitieuitems.class);

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
    public void onBackPressed() {
        super.finish();
    }

    @Override
    public void selectedItem(chitieuitems items) {

        Log.i("Check Item", items.getLoaichitieu());
//        arrayList.remove(items);
//
//        itemsadapter = new itemsAdapter(arrayList, getApplicationContext(), this);
//        recyclerView.setAdapter(itemsadapter);//Hiển thị lên màn hình
//
//        mDatabase.child("Danh sách giao dịch").setValue(arrayList);

    }
}
