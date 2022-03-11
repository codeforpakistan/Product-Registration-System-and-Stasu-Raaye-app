package com.example.halalfoodauthorityoss.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedBack_Attachements {
    @SerializedName("success")
    public String success;
    @SerializedName("response_msg")
    public String response_msg;
    @SerializedName("attachments")
    public List<Model> attachments;

    public String getSuccess() {
        return success;
    }

    public String getResponse_msg() {
        return response_msg;
    }

    public List<Model> getAttachments() {
        return attachments;
    }
}
