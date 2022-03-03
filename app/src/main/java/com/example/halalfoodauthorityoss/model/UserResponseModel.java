package com.example.halalfoodauthorityoss.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponseModel {

    @SerializedName("success")
    public String success;
    @SerializedName("message")
    public List<Model> message;

    public String getSuccess() {
        return success;
    }

    public List<Model> getMessage() {
        return message;
    }
}
