package com.example.halalfoodauthorityoss.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoriteModel {
    @SerializedName("success")
    public String success;
    @SerializedName("response_msg")
    public String response_msg;
    @SerializedName("favourite_list")
    public List<Model> favourite_list;

    public List<Model> getFavourite_list() {
        return favourite_list;
    }
}
