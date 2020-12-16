package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class Post {

    private int userId;
    private int id;
    private  String title;

    @SerializedName("body")
    private String description;


    public Post(int userId, String title, String description) {
        this.userId = userId;
        this.title = title;
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
