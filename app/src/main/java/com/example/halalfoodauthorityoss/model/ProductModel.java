package com.example.halalfoodauthorityoss.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductModel {
    @SerializedName("success")
    public String success;
    @SerializedName("response_msg")
    public String response_msg;
    @SerializedName("products")
    public List<Model> products;
    @SerializedName("product_detail")
    public Model product_detail;

    public Model getProduct_detail() {
        return product_detail;
    }

    public List<Model> getProducts() {
        return products;
    }
}
