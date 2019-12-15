package com.example.quanlychitieu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class itemsAdapter extends RecyclerView.Adapter<itemsAdapter.ViewHolder>{
    ArrayList<chitieuitems> items_chitieu;
    Context context;

    public itemsAdapter(ArrayList<chitieuitems> items_chitieu, Context context) {
        this.items_chitieu = items_chitieu;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.chi_tieu_items, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtLoaiChiTieu.setText(items_chitieu.get(getItemCount() - position - 1).getLoaichitieu());
        holder.txtGiaTri.setText(items_chitieu.get(getItemCount() - position - 1).getGiatri());
        holder.txtThoiGian.setText(items_chitieu.get(getItemCount() - position - 1).getThoigian());
        holder.txtLoaiGiaoDich.setText(items_chitieu.get(getItemCount() - position - 1).getLoaigiaodich());
    }

    @Override
    public int getItemCount() {
        return items_chitieu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtLoaiChiTieu;
        TextView txtGiaTri;
        TextView txtThoiGian;
        TextView txtLoaiGiaoDich;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtLoaiChiTieu = (TextView)itemView.findViewById(R.id.loai_chi_tieu);
            txtGiaTri = (TextView)itemView.findViewById(R.id.gia_tri_item);
            txtThoiGian = (TextView)itemView.findViewById(R.id.thoi_gian);
            txtLoaiGiaoDich = (TextView) itemView.findViewById(R.id.loai_giao_dich);
        }
    }
}
