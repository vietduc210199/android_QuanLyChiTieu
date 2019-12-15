package com.example.quanlychitieu.ChiTieuActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlychitieu.R;

import java.util.Calendar;

public class fragment_khoan_chi extends Fragment {

    View view;


    public fragment_khoan_chi() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.khoan_chi_fragment, container, false);



        return view;
    }
}
