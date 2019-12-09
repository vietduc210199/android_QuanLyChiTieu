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

    private ArrayList<chitieuitems> arrayList = new ArrayList<>();
    private FrameLayout thuchi;
    private Integer sodu = 1000000;
    private TextView tvSoDu;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScrollingActivity.this, khoanchiScreen.class);
                startActivityForResult(intent, 2);
            }
        });
        Log.i("check","dang init");
        initview();


    }



    public void initview(){
        tvSoDu = (TextView) findViewById(R.id.gia_tri_so_du);
        tvSoDu.setText(sodu.toString() + "VND");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.dieu_chinh_so_du) {
            Intent intent = new Intent(ScrollingActivity.this, vicontrolscreen.class);
            String giatrisodu = tvSoDu.getText().toString();
            intent.putExtra("SODU",giatrisodu);
            startActivityForResult(intent, 1);
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
    protected void onActivityResult (int requestcode, int resultcode , Intent data){
        super.onActivityResult(requestcode,resultcode,data);
        Log.i("check","dang nhan result");
        if (resultcode == RESULT_OK && requestcode == 1)
        {
            if(data.hasExtra("SODU"))
            {

                sodu = Integer.parseInt(data.getExtras().getString("SODU"));
                tvSoDu.setText(sodu.toString() + " VND");
            }
        }

        if (resultcode == RESULT_OK && requestcode == 2)
        {
            Bundle bundle = data.getBundleExtra(khoanchiScreen.BUNDLE);
            String mucchitieu = bundle.getString(khoanchiScreen.MUCHITIEU);
            String giatri = bundle.getString(khoanchiScreen.GIATRI);

            arrayList.add(new chitieuitems(mucchitieu, giatri + " VND"));
            itemsAdapter itemsadapter = new itemsAdapter(arrayList, getApplicationContext());
            recyclerView.setAdapter(itemsadapter);

            sodu -= Integer.parseInt(giatri);

            tvSoDu.setText(sodu.toString() + " VND");

        }
    }
}
