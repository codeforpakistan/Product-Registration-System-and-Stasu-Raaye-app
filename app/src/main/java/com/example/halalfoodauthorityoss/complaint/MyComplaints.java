package com.example.halalfoodauthorityoss.complaint;

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
import com.example.halalfoodauthorityoss.adapter.ComplaintAdapter;
import com.example.halalfoodauthorityoss.model.AppData;
import com.example.halalfoodauthorityoss.model.UserResponseModel;
import com.example.halalfoodauthorityoss.model.Model;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyComplaints extends AppCompatActivity {

    RecyclerView recyclerView;
    ComplaintAdapter complaintAdapter;
    List<Model> modelList = new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_complaints);

        Initilizaiton();

        Call<UserResponseModel> call = BaseClass
                .getInstance()
                .getApi()
                .ComplaintDetails(AppData.id);

        call.enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(Call<UserResponseModel> call, Response<UserResponseModel> response) {
                UserResponseModel userResponseModel = response.body();
                List<Model> list = userResponseModel.getMessage();
                if (response.isSuccessful()) {
                    if (userResponseModel.success.equals("1")) {
                        int size = list.size();
                        for (int i = 0; i < size; i++) {
                            modelList.add(new Model(list.get(i).cid, list.get(i).cust_id, list.get(i).district,
                                    list.get(i).create_date, list.get(i).status, list.get(i).comp_buss_name,
                                    list.get(i).ComplaintTitle, list.get(i).ComplaintDescription, list.get(i).Address,list.get(i).eng_comment));
                        }
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyComplaints.this, RecyclerView.VERTICAL, false);
                        complaintAdapter = new ComplaintAdapter(modelList, MyComplaints.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(complaintAdapter);
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(MyComplaints.this, "Cann't Get Complaints", Toast.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MyComplaints.this, "Not Successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponseModel> call, Throwable t) {
                Toast.makeText(MyComplaints.this, "No Response", Toast.LENGTH_SHORT).show();
                Log.d("aaaaaa", call.request().toString());
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