package com.example.halalfoodauthorityoss.model;

import com.google.gson.annotations.SerializedName;

public class AppData {
    public static String name=null;
    public static String cnic=null;
    public static String address="0";
    public static String photo="0";
    public static String password=null;
    public static String mobileNumber=null;
    public static int id;

    @SerializedName("business_id")
    public String business_id;
    @SerializedName("business_name")
    public String business_name;
    @SerializedName("Address")
    public String Address;
    @SerializedName("Register_Date")
    public String Register_Date;
    @SerializedName("distric_name")
    public String distric_name;

    public AppData(String business_id, String business_name, String address, String register_Date, String distric_name) {
        this.business_id = business_id;
        this.business_name = business_name;
        Address = address;
        Register_Date = register_Date;
        this.distric_name = distric_name;
    }
}
