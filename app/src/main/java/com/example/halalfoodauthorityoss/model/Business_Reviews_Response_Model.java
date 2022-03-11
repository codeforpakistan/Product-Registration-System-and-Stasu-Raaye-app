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

    public String getSuccess() {
        return success;
    }

    public String getResponse_msg() {
        return response_msg;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public String getBusiness_address() {
        return business_address;
    }

    public String getAverage_rating() {
        return average_rating;
    }

    public String getTotal_ratings() {
        return total_ratings;
    }

    public List<Model> getRatings_arr() {
        return ratings_arr;
    }
}
