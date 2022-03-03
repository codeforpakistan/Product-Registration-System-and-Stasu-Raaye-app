package com.example.halalfoodauthorityoss;

import com.example.halalfoodauthorityoss.model.UserResponseModel;
import com.example.halalfoodauthorityoss.model.LoginResponse;
import com.example.halalfoodauthorityoss.model.Model;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Interface {

    @FormUrlEncoded
    @POST("sign_up")
    Call<Model> Sign_Up(
            @Field("cnic") String cnic,
            @Field("password") String password,
            @Field("name") String name,
            @Field("district_id") int district_id,
            @Field("c_mobile") String number

    );

    @Multipart
    @POST("add_owner_reg")
    Call<Model> Add_Owner(
            @Part("name") String name,
            @Part("fname") String fname,
            @Part("cnic_no") String cnic_no,
            @Part("contact_no") String contact_no,
            @Part MultipartBody.Part profileimage,
            @Part("bussiness_address") String bussiness_address,
            @Part("bussiness_name") String bussiness_name,
            @Part("bussiness_category") int bussiness_category,
            @Part("latitude") double latitude,
            @Part("longitude") double longitude,
            @Part MultipartBody.Part cnicimage,
            @Part("DistrictId") int DistrictId,
            @Part("Gender") String Gender,
            @Part("owner") String owner,
            @Part("cust_id") int cust_id
    );

    @FormUrlEncoded
    @POST("add_bussiness_r_App")
    Call<Model> Add_Business(
            @Field("bussiness_name") String bussiness_name,
            @Field("bussiness_category") int bussiness_category,
            @Field("DistrictId") int DistrictId,
            @Field("bussiness_address") String bussiness_address,
            @Field("latitude") float latitude,
            @Field("longitude") float longitude,
            @Field("user_id") int userid,
            @Field("cust_id") int cust_id
    );

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> Login(
            @Field("cnic") String cnic,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("forgot_password")
    Call<Model> Forgot(@Field("cnic") String cnic
    );

    @GET("bussiness_type_record")
    Call<List<Model>> getCategory();

    @GET("distric_record")
    Call<List<Model>> getDistrict();

    @FormUrlEncoded
    @POST("complain_records_by_post")
    Call<UserResponseModel> ComplaintDetails(@Field("cust_id") int cust_id
    );

    @FormUrlEncoded
    @POST("find_owner_cnic")
    Call<Model> GetCNIC(@Field("cnic") String cnic
    );

    @Multipart
    @POST("add_complain")
    Call<Model> Complaint(
            @Part("comp_title") String comp_title,
            @Part("comp_desc") String comp_desc,
            @Part("cust_id") int cust_id,
            @Part("address") String address,
            @Part("district") String district,
            @Part("Bussiness_name") String Bussiness_name,
            @Part MultipartBody.Part[] images
    );

    @FormUrlEncoded
    @POST("complain_records_by_post1")
    Call<UserResponseModel> GetImages(
            @Field("complaintId") int complaintId
    );

    @FormUrlEncoded
    @POST("owner_business_records")
    Call<UserResponseModel> GetBusiness(@Field("cust_id") int cust_id
    );

    @FormUrlEncoded
    @POST("add_complain_rating")
    Call<Model> ComplaintRating(
            @Field("rating") int rating,
            @Field("cust_id") int cust_id,
            @Field("cid") int cid,
            @Field("feedback") String feed,
            @Field("satisfaction") int satisfaction
    );

    @Multipart
    @POST("update_profile")
    Call<Model> UpdateProfile(
            @Part("cid") int cid,
            @Part("Name") String name,
            @Part("cnic") String cnic,
            @Part("c_mobile") String c_mobile,
            @Part MultipartBody.Part image,
            @Part("address") String address,
            @Part("cpass") String cpass
    );
}