package com.example.shoponline.model;

public class spinerMD {
    int id;
    String tenDM;
    String Hinhanh;

    public spinerMD(int id, String tenDM, String hinhanh) {
        this.id = id;
        this.tenDM = tenDM;
        Hinhanh = hinhanh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenDM() {
        return tenDM;
    }

    public void setTenDM(String tenDM) {
        this.tenDM = tenDM;
    }

    public String getHinhanh() {
        return Hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        Hinhanh = hinhanh;
    }
}
