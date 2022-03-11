package com.example.halalfoodauthorityoss.searchresult;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.adapter.Reviews_Adapter;
import com.example.halalfoodauthorityoss.adapter.Search_Business_Adapter;
import com.example.halalfoodauthorityoss.model.AppData;
import com.example.halalfoodauthorityoss.model.Business_Reviews_Response_Model;
import com.example.halalfoodauthorityoss.model.Model;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Feedback_Reviews extends AppCompatActivity {

    RecyclerView recyclerView;
    Reviews_Adapter reviews_adapter;
    List<Model> modelList = new ArrayList<>();
    ProgressDialog progressDialog;
    TextView btnfeedback, txtBussName, txtAddress, txtRatingCount;
    RatingBar ratingbar;
    LinearLayout layoutratingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback__reviews);

        Initilizaiton();
        GetReviews();

        /*txtBussName.setText(Search_Business_Adapter.business_Name);
        txtAddress.setText(Search_Business_Adapter.business_address);
        txtRatingCount.setText("Average Rating: "+Search_Business_Adapter.average_rating);
        ratingbar.setRating(Float.parseFloat(Search_Business_Adapter.average_rating));*/

        btnfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Feedback_Reviews.this, User_Rating.class);
                startActivity(intent);
            }
        });
    }

    private void GetReviews() {
        Call<Business_Reviews_Response_Model> call = BaseClass
                .getInstance()
                .getApi()
                .Business_Details(AppData.id, Search_Business_Adapter.business_id);

        call.enqueue(new Callback<Business_Reviews_Response_Model>() {
            @Override
            public void onResponse(Call<Business_Reviews_Response_Model> call, Response<Business_Reviews_Response_Model> response) {
                Business_Reviews_Response_Model business_reviews_response_model = response.body();
                List<Model> list = business_reviews_response_model.ratings_arr;
                int size = list.size();
                if (response.isSuccessful()) {
                    if (business_reviews_response_model.success.equals("1")) {
                        txtBussName.setText(business_reviews_response_model.business_name);
                        txtAddress.setText(business_reviews_response_model.business_address);
                        txtRatingCount.setText(business_reviews_response_model.average_rating+" Ratings"+"("+business_reviews_response_model.total_ratings+")");
                        ratingbar.setRating(Float.parseFloat(business_reviews_response_model.average_rating));
                        for (int i = 0; i < size; i++) {
                            modelList.add(new Model(list.get(i).business_rating_id,list.get(i).cust_id, list.get(i).customer_name, list.get(i).customer_photo,
                                    list.get(i).feedback, list.get(i).overall_rating, list.get(i).timestamp,
                                    list.get(i).premises_hygiene, list.get(i).equipment_hygiene, list.get(i).staff_personal_hygiene, list.get(i).food_hygiene, list.get(i).food_quality));
                        }
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Feedback_Reviews.this, RecyclerView.VERTICAL, false);
                        reviews_adapter = new Reviews_Adapter(modelList, Feedback_Reviews.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(reviews_adapter);
                        progressDialog.dismiss();
                        layoutratingbar.setVisibility(View.VISIBLE);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Feedback_Reviews.this, "Cann't Get Complaints", Toast.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(Feedback_Reviews.this, "Not Successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Business_Reviews_Response_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Feedback_Reviews.this, "out", Toast.LENGTH_SHORT).show();
                Log.d("aaaaaa", call.request().toString());
            }
        });
    }

    private void Initilizaiton() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerView = findViewById(R.id.recyclerView);
        btnfeedback = findViewById(R.id.btnfeedback);
        txtBussName = findViewById(R.id.txtBussName);
        txtAddress = findViewById(R.id.txtAddress);
        txtRatingCount = findViewById(R.id.txtRatingCount);
        ratingbar = findViewById(R.id.ratingbar);
        layoutratingbar = findViewById(R.id.layoutratingbar);
        layoutratingbar.setVisibility(View.INVISIBLE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}