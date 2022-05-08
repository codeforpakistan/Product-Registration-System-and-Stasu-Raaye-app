package com.example.halalfoodauthorityoss.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessNameForSearch {
        @SerializedName("success")
        public String success;
        @SerializedName("businesses")
        public List<Model> businesses;
        @SerializedName("response_msg")
        public String response_msg;

        public List<Model> getBusinesses() {
            return businesses;
        }

}
