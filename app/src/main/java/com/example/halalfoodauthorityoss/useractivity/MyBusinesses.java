package com.example.halalfoodauthorityoss.useractivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.adapter.BusinessAdapter;
import com.example.halalfoodauthorityoss.model.AppData;
import com.example.halalfoodauthorityoss.model.Model;
import com.example.halalfoodauthorityoss.model.UserResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBusinesses extends AppCompatActivity {

    RecyclerView recyclerView;
    BusinessAdapter businessAdapter;
    List<Model> modelList = new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_businesses);

        Initilizaiton();

        Call<UserResponseModel> call = BaseClass
                .getInstance()
                .getApi()
                .GetBusiness(AppData.id);

        call.enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(Call<UserResponseModel> call, Response<UserResponseModel> response) {
                UserResponseModel userResponseModel = response.body();
                List<Model> list = userResponseModel.getMessage();
                if (response.isSuccessful()) {
                    if (userResponseModel.success.equals("1")) {
                        int size = list.size();
                        for (int i = 0; i < size; i++) {
                            modelList.add(new Model(list.get(i).UserId, list.get(i).owner_name, list.get(i).FName,
                                    list.get(i).CNIC, list.get(i).Mobile, list.get(i).Address, list.get(i).bussnie_name,
                                    list.get(i).Register_Date, list.get(i).Type, list.get(i).StartDate, list.get(i).LicNo,
                                    list.get(i).ExpireDate, list.get(i).IssueDate, list.get(i).distric_name,list.get(i).expiry_days_remaining,list.get(i).r_application_id));
                        }
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyBusinesses.this, RecyclerView.VERTICAL, false);
                        businessAdapter = new BusinessAdapter(modelList, MyBusinesses.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(businessAdapter);
                        progressDialog.dismiss();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(MyBusinesses.this, "Cann't Get Complaints", Toast.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MyBusinesses.this, "Not Successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponseModel> call, Throwable t) {
                Toast.makeText(MyBusinesses.this, "No Response", Toast.LENGTH_SHORT).show();
                Log.d("aaaaaa", call.request().toString());
                progressDialog.dismiss();

            }
        });
    }

    private void Initilizaiton() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerView = findViewById(R.id.recyclerView);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}