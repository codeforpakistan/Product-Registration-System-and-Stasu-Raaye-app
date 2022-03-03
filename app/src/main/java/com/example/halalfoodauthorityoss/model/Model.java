package com.example.halalfoodauthorityoss.model;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.Serializable;

public class Model implements Serializable {
    @SerializedName("name")
    public String name;
    @SerializedName("cnic")
    public String cnic;
    @SerializedName("c_mobile")
    public String c_mobile;
    @SerializedName("cpass")
    public String cpass;
    @SerializedName("response_msg")
    public String response_msg;
    @SerializedName("district_id")
    public int district_id;
    @SerializedName("userID")
    public String userID;
    @SerializedName("UserId")
    public String UserId;
    @SerializedName("success")
    public String success;
    @SerializedName("Name")
    public String cName;
    @SerializedName("Id")
    public String ID;
    @SerializedName("message")
    public String message;
    @SerializedName("password")
    public String password;
    @SerializedName("business_id")
    public String business_id;
    @SerializedName("application_id")
    public String application_id;

    /*BusinessDetailResponse*/
    @SerializedName("owner_name")
    public String owner_name;
    @SerializedName("FName")
    public String FName;
    @SerializedName("CNIC")
    public String CNIC;
    @SerializedName("Mobile")
    public String Mobile;
    @SerializedName("DistrictId")
    public String DistrictId;
    @SerializedName("bussnie_name")
    public String bussnie_name;
    @SerializedName("Register_Date")
    public String Register_Date;
    @SerializedName("Type")
    public String Type;
    @SerializedName("StartDate")
    public String StartDate;
    @SerializedName("LicNo")
    public String LicNo;
    @SerializedName("ExpireDate")
    public String ExpireDate;
    @SerializedName("IssueDate")
    public String IssueDate;
    @SerializedName("distric_name")
    public String distric_name;
    @SerializedName("image_name")
    public String image_name;

    /*Complaint Response*/
    @SerializedName("cid")
    public String cid;
    @SerializedName("cust_id")
    public String cust_id;
    @SerializedName("district")
    public String district;
    @SerializedName("create_date")
    public String create_date;
    @SerializedName("status")
    public String status;
    @SerializedName("comp_buss_name")
    public String comp_buss_name;
    @SerializedName("ComplaintTitle")
    public String ComplaintTitle;
    @SerializedName("ComplaintDescription")
    public String ComplaintDescription;
    @SerializedName("Address")
    public String Address;

    /*Images Response*/
    @SerializedName("complaintId")
    public String complaintId;
    @SerializedName("Path")
    public String Path;
    @SerializedName("DOC")
    public String DOC;



    public String getPath() {
        return Path;
    }

    public String getAddress() {
        return Address;
    }

    public String getName() {
        return name;
    }

    public String getCnic() {
        return cnic;
    }

    public String getUser_id() {
        return userID;
    }

    public String getSuccess() {
        return success;
    }

    public String getC_mobile() {
        return c_mobile;
    }

    public String getCpass() {
        return cpass;
    }

    public String getID() {
        return ID;
    }

    public String getcName() {
        return cName;
    }

    public String getMessage() {
        return message;
    }

    public String getUserId() {
        return UserId;
    }

    public String getPassword() {
        return password;
    }


    public Model(String cid, String cust_id, String district, String create_date, String status, String comp_buss_name, String complaintTitle, String complaintDescription, String address) {
        this.cid = cid;
        this.cust_id = cust_id;
        this.district = district;
        this.create_date = create_date;
        this.status = status;
        this.comp_buss_name = comp_buss_name;
        ComplaintTitle = complaintTitle;
        ComplaintDescription = complaintDescription;
        Address = address;
    }

    public Model(String userId, String owner_name, String FName, String CNIC, String mobile, String address, String bussnie_name, String register_Date, String type, String startDate, String licNo, String expireDate, String issueDate, String distric_name) {
        UserId = userId;
        this.owner_name = owner_name;
        this.FName = FName;
        this.CNIC = CNIC;
        Mobile = mobile;
        Address = address;
        this.bussnie_name = bussnie_name;
        Register_Date = register_Date;
        Type = type;
        StartDate = startDate;
        LicNo = licNo;
        ExpireDate = expireDate;
        IssueDate = issueDate;
        this.distric_name = distric_name;
    }

    public Model(String path) {
        Path = path;
    }
}
