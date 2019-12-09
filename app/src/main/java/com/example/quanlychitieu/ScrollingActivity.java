package com.example.quanlychitieu;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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

public class ScrollingActivity extends AppCompatActivity {

    private ArrayList<chitieuitems> arrayList = new ArrayList<>(); // List Lưu trữ các khoản giao dịch
    private Integer sodu = 1000000;// Khởi tạo số dư ban đầu
    private TextView tvSoDu;// Text hiển thị số dư
    private RecyclerView recyclerView; // Hiển thị List các giao dịch
    private Toolbar toolbar;

    @Override//Khởi chạy màn hình
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        initview();// Tạo các biến đối tượng  ban đầu

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
        tvSoDu.setText(sodu.toString() + " VND");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //Add list giao dịch hiển thị demo
        arrayList.add(new chitieuitems("Mua sắm", "100.000 vnd"));
        arrayList.add(new chitieuitems("Di Chuyển", "20.000 vnd"));
        arrayList.add(new chitieuitems("Ăn Uống", "20.000 vnd"));
        arrayList.add(new chitieuitems("Trả Nợ", "20.000 vnd"));
        arrayList.add(new chitieuitems("Điện Nước", "100.000 vnd"));

        itemsAdapter itemsadapter = new itemsAdapter(arrayList, getApplicationContext());
        recyclerView.setAdapter(itemsadapter);
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
            String giatrisodu = tvSoDu.getText().toString();
            intent.putExtra("SODU",giatrisodu);
            startActivityForResult(intent, 1);//Chạy màn hình điều chỉnh số dư với code thực thi = 2
        }

        //Thoát
        if (id == R.id.thoat){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void addFragment (Fragment fragment) {
        FragmentManager fmgr = getSupportFragmentManager();
        FragmentTransaction ft = fmgr.beginTransaction();
        ft.add(R.id.thu_chi_items, fragment);
        ft.addToBackStack(fragment.getClass().getSimpleName());
        ft.commit();
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
                tvSoDu.setText(sodu.toString() + " VND");// hiển thị số dư mới lên màn hình
            }
        }

        if (resultcode == RESULT_OK && requestcode == 2)//Nếu kết quả trả về từ màn hình tạo giao dịch (code thực thi == 2) với RESULT_OK
        {
            Bundle bundle = data.getBundleExtra(khoanchiScreen.BUNDLE);
            String mucchitieu = bundle.getString(khoanchiScreen.MUCHITIEU);
            String giatri = bundle.getString(khoanchiScreen.GIATRI);

            arrayList.add(new chitieuitems(mucchitieu, giatri + " VND"));//Add khoản giao dịch mới vào List
            itemsAdapter itemsadapter = new itemsAdapter(arrayList, getApplicationContext());
            recyclerView.setAdapter(itemsadapter);//Hiển thị lên màn hình

            sodu -= Integer.parseInt(giatri);//Điều chỉnh lại số dư sau giao dịch

            tvSoDu.setText(sodu.toString() + " VND");
        }
    }
}
