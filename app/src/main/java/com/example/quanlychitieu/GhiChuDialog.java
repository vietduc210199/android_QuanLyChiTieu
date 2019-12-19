package com.example.quanlychitieu;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class GhiChuDialog extends AppCompatDialogFragment {

    private TextView ghichu;
    private GhiChuDialogListener listener;
    private String getghichu;

    public GhiChuDialog(String ghichu){
        getghichu = ghichu;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.ghi_chu_dialog, null);

        ghichu = view.findViewById(R.id.tv_ghichu_dialog);

        ghichu.setText(getghichu);
        builder.setView(view)
                .setTitle("Ghi Chú")
                .setNegativeButton("Trở Về", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.sendDelete();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (GhiChuDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement GhiChuDialogListener");
        }
    }

    public interface GhiChuDialogListener{
        void sendDelete();
    }
}
