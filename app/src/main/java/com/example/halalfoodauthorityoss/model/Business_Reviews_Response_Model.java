package com.example.halalfoodauthorityoss.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Business_Reviews_Response_Model {

    @SerializedName("success")
    public String success;
    @SerializedName("response_msg")
    public String response_msg;
    @SerializedName("business_id")
    public String business_id;
    @SerializedName("business_name")
    public String business_name;
    @SerializedName("business_address")
    public String business_address;
    @SerializedName("average_rating")
    public String average_rating;
    @SerializedName("total_ratings")
    public String total_ratings;
    @SerializedName("latitude")
    public String latitude;
    @SerializedName("longitude")
    public String longitude;
    @SerializedName("ratings_arr")
    public List<Model> ratings_arr;
}
