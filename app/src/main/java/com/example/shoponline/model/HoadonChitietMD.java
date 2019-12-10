package com.example.shoponline.model;

public class HoadonChitietMD {
    private int  id;
    private int  masanpham;
    private int  idkhachhang;
    private String tensanpham;
    private long giasanpham;
    private int soluong;
    private String hinhanh;

    public HoadonChitietMD() {
    }

    public HoadonChitietMD(int id, int masanpham, int idkhachhang, String tensanpham, long giasanpham, int soluong, String hinhanh) {
        this.id = id;
        this.masanpham = masanpham;
        this.idkhachhang = idkhachhang;
        this.tensanpham = tensanpham;
        this.giasanpham = giasanpham;
        this.soluong = soluong;
        this.hinhanh = hinhanh;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMasanpham() {
        return masanpham;
    }

    public void setMasanpham(int masanpham) {
        this.masanpham = masanpham;
    }

    public int getIdkhachhang() {
        return idkhachhang;
    }

    public void setIdkhachhang(int idkhachhang) {
        this.idkhachhang = idkhachhang;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public long getGiasanpham() {
        return giasanpham;
    }

    public void setGiasanpham(long giasanpham) {
        this.giasanpham = giasanpham;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
