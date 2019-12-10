package com.example.shoponline.model;

public class infoUserMD {
    private int id;
    private String username;
    private String phone;
    private String email;
    private String location;
    private String success;

    public infoUserMD(int id, String username, String phone, String email, String location, String success) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.success = success;
    }

    public infoUserMD() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
