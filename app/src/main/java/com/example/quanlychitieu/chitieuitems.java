package com.example.quanlychitieu;

public class chitieuitems {
    private String Loaichitieu;
    private String giatri;

    public chitieuitems() {
        //Dùng để lấy dữ liệu về từ firebase
    }

    public chitieuitems(String loaichitieu, String giatri) {
        Loaichitieu = loaichitieu;
        this.giatri = giatri;
    }

    public void setLoaichitieu(String loaichitieu) {
        Loaichitieu = loaichitieu;
    }

    public void setGiatri(String giatri) {
        this.giatri = giatri;
    }

    public String getLoaichitieu() {
        return Loaichitieu;
    }

    public String getGiatri() {
        return giatri;
    }
}
