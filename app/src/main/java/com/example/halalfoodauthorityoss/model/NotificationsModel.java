package com.example.halalfoodauthorityoss.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationsModel {
    @SerializedName("success")
    public String success;
    @SerializedName("response_msg")
    public String response_msg;
    @SerializedName("notifications")
    public List<Model> notifications;

    public List<Model> getNotifications() {
        return notifications;
    }
}
