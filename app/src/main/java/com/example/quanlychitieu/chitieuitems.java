package com.example.quanlychitieu;

import java.util.Date;

public class chitieuitems {
    private String Loaichitieu;
    private String giatri;
    private String thoigian;

    public chitieuitems() {
        //Dùng để lấy dữ liệu về từ firebase
    }

    public chitieuitems(String loaichitieu, String giatri, String thoigian) {
        this.Loaichitieu = loaichitieu;
        this.giatri = giatri;
        this.thoigian = thoigian;
    }

    public void setLoaichitieu(String loaichitieu) {
        this.Loaichitieu = loaichitieu;
    }

    public void setGiatri(String giatri) {
        this.giatri = giatri;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public String getLoaichitieu() {
        return Loaichitieu;
    }

    public String getGiatri() {
        return giatri;
    }

    public String getThoigian() {
        return thoigian;
    }
}
