package com.example.halalfoodauthorityoss.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponseModel {
    @SerializedName("success")
    public String success;
    @SerializedName("businesses")
    public List<Model> businesses;
    @SerializedName("message")
    public String message;

    public List<Model> getBusinesses() {
        return businesses;
    }

}
