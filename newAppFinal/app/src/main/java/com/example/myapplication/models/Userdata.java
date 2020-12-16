package com.example.myapplication.models;

public class Userdata {
    String u_name;
    String u_age;
    String u_phone;
    String u_description;

    public Userdata() {
    }

    public Userdata(String u_name, String u_age, String u_phone, String u_description) {
        this.u_name = u_name;
        this.u_age = u_age;
        this.u_phone = u_phone;
        this.u_description = u_description;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_age() {
        return u_age;
    }

    public void setU_age(String u_age) {
        this.u_age = u_age;
    }

    public String getU_phone() {
        return u_phone;
    }

    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }

    public String getU_description() {
        return u_description;
    }

    public void setU_description(String u_description) {
        this.u_description = u_description;
    }
}
