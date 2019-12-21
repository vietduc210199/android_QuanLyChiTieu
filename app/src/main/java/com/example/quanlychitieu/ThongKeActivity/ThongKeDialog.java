package com.example.quanlychitieu.ThongKeActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlychitieu.GhiChuDialog;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.chitieuitems;
import com.example.quanlychitieu.itemsAdapter;

import java.util.ArrayList;

public class ThongKeDialog extends DialogFragment implements itemsAdapter.SelectedItem {

    private RecyclerView recyclerView;
    private ArrayList<chitieuitems> arrayList = new ArrayList<>();

    public ThongKeDialog(ArrayList<chitieuitems> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.thong_ke_dialog, null);

        recyclerView = view.findViewById(R.id.rv_thong_ke_items);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        itemsAdapter itemsadapter = new itemsAdapter(arrayList, getContext(), this);
        recyclerView.setAdapter(itemsadapter);//Hiển thị lên màn hình

        builder.setView(view)
                .setTitle("Danh sách giao dịch")
                .setNegativeButton("Trở Về", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }


    @Override
    public void selectedItem(chitieuitems items) {

    }
}
