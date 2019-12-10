package com.example.shoponline.model;

import android.content.Intent;

public class SanphamMD {
    private int id;
    private String tensanpham;
    private Integer Giasp;
    private String Motasanpham;
    private String Hinhanhsp;

    private int IDsanpham;
    private int soluong;
    public SanphamMD() {
    }

    public SanphamMD(int id, String tensanpham, Integer giasp, String motasanpham, String hinhanhsp, int IDsanpham, int soluong) {
        this.id = id;
        this.tensanpham = tensanpham;
        Giasp = giasp;
        Motasanpham = motasanpham;
        Hinhanhsp = hinhanhsp;
        this.IDsanpham = IDsanpham;
        this.soluong = soluong;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public Integer getGiasp() {
        return Giasp;
    }

    public void setGiasp(Integer giasp) {
        Giasp = giasp;
    }

    public String getMotasanpham() {
        return Motasanpham;
    }

    public void setMotasanpham(String motasanpham) {
        Motasanpham = motasanpham;
    }

    public String getHinhanhsp() {
        return Hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        Hinhanhsp = hinhanhsp;
    }

    public int getIDsanpham() {
        return IDsanpham;
    }

    public void setIDsanpham(int IDsanpham) {
        this.IDsanpham = IDsanpham;
    }
}
