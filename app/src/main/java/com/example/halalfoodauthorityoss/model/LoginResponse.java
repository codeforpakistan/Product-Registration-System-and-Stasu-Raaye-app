package com.example.halalfoodauthorityoss.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse
{
    @SerializedName("success")
    public String success;
    @SerializedName("Message")
    public String Message;
    @SerializedName("user_data")
    public Model user_data;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return Message;
    }

    public Model getUser_data() {
        return user_data;
    }
}
