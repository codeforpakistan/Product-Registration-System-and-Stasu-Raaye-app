package com.example.halalfoodauthorityoss.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessNameForRoom {
        @SerializedName("success")
        public String success;
        @SerializedName("businesses")
        public List<Model> businesses;
        @SerializedName("response_msg")
        public String response_msg;

        public String getSuccess() {
            return success;
        }

        public List<Model> getBusinesses() {
            return businesses;
        }

        public String getMessage() {
            return response_msg;
        }

}
