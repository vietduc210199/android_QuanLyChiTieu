package com.example.quanlychitieu;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class itemsAdapter extends RecyclerView.Adapter<itemsAdapter.ViewHolder> implements Filterable {
    private ArrayList<chitieuitems> arrayList;
    private ArrayList<chitieuitems> getItemsListFiltered;
    private Context context;
    private SelectedItem selectedItems;

    public itemsAdapter(ArrayList<chitieuitems> arrayList, Context context, SelectedItem selectedItems) {
        this.arrayList = arrayList;
        getItemsListFiltered = arrayList;
        this.context = context;
        this.selectedItems = selectedItems;
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
        holder.txtLoaiChiTieu.setText(arrayList.get(getItemCount() - position - 1).getLoaichitieu());
        holder.txtThoiGian.setText(arrayList.get(getItemCount() - position - 1).getThoigian());
        if(arrayList.get(getItemCount() - position - 1).getLoaigiaodich().equals("Khoản Chi")) {
            holder.imgLoaiGiaoDich.setImageResource(R.drawable.chi_asset);
            holder.txtGiaTri.setText("- " + arrayList.get(getItemCount() - position - 1).getGiatri() + " VND");
            holder.txtGiaTri.setTextColor(Color.argb(255, 233, 30, 99));
        } else {
            holder.imgLoaiGiaoDich.setImageResource(R.drawable.thu_asset);
            holder.txtGiaTri.setText("+ " + arrayList.get(getItemCount() - position - 1).getGiatri() +" VND");
            holder.txtGiaTri.setTextColor(Color.argb(255, 54, 243, 28));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if(constraint == null || constraint.length() == 0) {
                    filterResults.count = getItemsListFiltered.size();
                    filterResults.values = getItemsListFiltered;
                }else {
                    String searchChr = constraint.toString().toUpperCase();

                    ArrayList<chitieuitems> resultData = new ArrayList<>();

                    for(chitieuitems items: getItemsListFiltered) {
                        if(items.getLoaichitieu().toUpperCase().contains(searchChr)){
                            resultData.add(items);
                        }
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arrayList = (ArrayList<chitieuitems>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtLoaiChiTieu;
        TextView txtGiaTri;
        TextView txtThoiGian;
        ImageView imgLoaiGiaoDich;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtLoaiChiTieu = (TextView)itemView.findViewById(R.id.loai_chi_tieu);
            txtGiaTri = (TextView)itemView.findViewById(R.id.gia_tri_item);
            txtThoiGian = (TextView)itemView.findViewById(R.id.thoi_gian);
            imgLoaiGiaoDich = (ImageView) itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("check", arrayList.get(getAdapterPosition()).toString());
                    selectedItems.selectedItem(arrayList.get(getItemCount() - getAdapterPosition() - 1));
                }
            });
        }
    }

    public interface SelectedItem{

        void selectedItem(chitieuitems items);

    }
}
