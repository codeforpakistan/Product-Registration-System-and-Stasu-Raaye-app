package com.example.halalfoodauthorityoss;

import com.example.halalfoodauthorityoss.fragments.Favorite;
import com.example.halalfoodauthorityoss.model.BusinessNameForRoom;
import com.example.halalfoodauthorityoss.model.Business_Reviews_Response_Model;
import com.example.halalfoodauthorityoss.model.FavoriteModel;
import com.example.halalfoodauthorityoss.model.FeedBack_Attachements;
import com.example.halalfoodauthorityoss.model.LoginResponse;
import com.example.halalfoodauthorityoss.model.Model;
import com.example.halalfoodauthorityoss.model.NotificationsModel;
import com.example.halalfoodauthorityoss.model.ProductModel;
import com.example.halalfoodauthorityoss.model.Product_Detail_Model;
import com.example.halalfoodauthorityoss.model.SearchResponseModel;
import com.example.halalfoodauthorityoss.model.UserResponseModel;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
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
    @FormUrlEncoded
    @POST("search_businesses")
    Call<SearchResponseModel> SearchResult(
            @Field("business_type") int business_type,
            @Field("district_id") int district_id,
            @Field("business_name") String business_name
    );

    @Multipart
    @POST("business_rating_add")
    Call<Model> Add_Rating(
            @Part("cust_id") int cust_id,
            @Part("business_id") int business_id,
            @Part("feedback") String feedback,
            @Part("premises_hygiene") String premises_hygiene,
            @Part("equipment_hygiene") String equipment_hygiene,
            @Part("staff_personal_hygiene") String staff_personal_hygiene,
            @Part("food_hygiene") String food_hygiene,
            @Part("food_quality") String food_quality,
            @Part MultipartBody.Part[] image
    );

    @FormUrlEncoded
    @POST("business_detail")
    Call<Business_Reviews_Response_Model> Business_Details(
            @Field("cust_id") int cust_id,
            @Field("business_id") int business_id
    );

    @FormUrlEncoded
    @POST("business_rating_attachments")
    Call<FeedBack_Attachements> GetFeedbackAttachments(
            @Field("cust_id") int cust_id,
            @Field("business_rating_id") int business_rating_id
    );
    @FormUrlEncoded
    @POST("customer_favourite_business_add")
    Call<Model> Add_to_Favorite(
            @Field("cust_id") int cust_id,
            @Field("business_id") int business_id
    );
    @FormUrlEncoded
    @POST("customer_favourite_business_get")
    Call<FavoriteModel> Get_to_Favorite(
            @Field("cust_id") int cust_id
    );
    @FormUrlEncoded
    @POST("customer_favourite_business_remove")
    Call<Model> RemoveBusiness(
            @Field("cust_id") int cust_id,
            @Field("favourite_id") int favourite_id
    );
    @FormUrlEncoded
    @POST("signup_phone_validation")
    Call<Model> Authentication(
            @Field("c_mobile") String c_mobile
    );
    @FormUrlEncoded
    @POST("business_license_renewal")
    Call<Model> Business_Renewal(
            @Field("cust_id") int cust_id,
            @Field("r_application_id") int r_application_id
    );
    @FormUrlEncoded
    @POST("search_business_license")
    Call<Model> LicNoChecking(
            @Field("cust_id") int cust_id,
            @Field("license_no") String license_no
    );
    @FormUrlEncoded
    @POST("product_registration")
    Call<Model> Product_Registration(
            @Field("cust_id") int cust_id,
            @Field("business_id") int business_id,
            @Field("product_name") String product_name
    );
    @FormUrlEncoded
    @POST("customer_products")
    Call<ProductModel> Get_Products(
            @Field("cust_id") int cust_id
    );
    @FormUrlEncoded
    @POST("product_detail")
    Call<Product_Detail_Model> Get_Products_Details(
            @Field("cust_id") int cust_id,
            @Field("r_application_id") int r_application_id
    );
    @FormUrlEncoded
    @POST("product_license_renewal")
    Call<Model> Prodcut_Renewal(
            @Field("cust_id") int cust_id,
            @Field("r_application_id") int r_application_id
    );

    @FormUrlEncoded
    @POST("business_name_get_all")
    Call<BusinessNameForRoom> BusinessNameSave(
            @Field("cust_id") String cust_id
    );
    @FormUrlEncoded
    @POST("customer_notifications_get")
    Call<NotificationsModel> GetNotifications(
            @Field("cust_id") int cust_id
    );
    @FormUrlEncoded
    @POST("customer_notifications_count")
    Call<Model> GetNotificationsCount(
            @Field("cust_id") int cust_id
    );
    @FormUrlEncoded
    @POST("customer_notification_mark_read")
    Call<Model> GetNotificationsMark(
            @Field("cust_id") int cust_id,
            @Field("notification_id") int notification_id
    );
    @FormUrlEncoded
    @POST("customer_notification_delete")
    Call<Model> GetNotificationsDelete(
            @Field("cust_id") int cust_id,
            @Field("notification_id") int notification_id
    );
}
