package com.example.halalfoodauthorityoss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.halalfoodauthorityoss.adapter.ComplaintAdapter;
import com.example.halalfoodauthorityoss.adapter.Notifications_Adapter;
import com.example.halalfoodauthorityoss.complaint.MyComplaints;
import com.example.halalfoodauthorityoss.model.AppData;
import com.example.halalfoodauthorityoss.model.Model;
import com.example.halalfoodauthorityoss.model.NotificationsModel;
import com.example.halalfoodauthorityoss.model.UserResponseModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notifications extends AppCompatActivity {

    RecyclerView recyclerView;
    Notifications_Adapter notifications_adapter;
    List<Model> modelList = new ArrayList<>();
    ProgressDialog progressDialog;
    ImageView ic_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        Initilizaiton();

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Notifications.this,CoreActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        Call<NotificationsModel> call = BaseClass
                .getInstance()
                .getApi()
                .GetNotifications(AppData.id);

        call.enqueue(new Callback<NotificationsModel>() {
            @Override
            public void onResponse(Call<NotificationsModel> call, Response<NotificationsModel> response) {
                NotificationsModel userResponseModel = response.body();
                List<Model> list = userResponseModel.getNotifications();
                if (response.isSuccessful()) {
                    if (userResponseModel.success.equals("1")) {
                        int size = list.size();
                        for (int i = 0; i < size; i++) {
                            modelList.add(new Model(list.get(i).notification_id, list.get(i).notification_title, list.get(i).notification_description,
                                    list.get(i).notification_link, list.get(i).notification_type, list.get(i).notification_status,list.get(i).notification_timestamp,"",""));
                        }
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Notifications.this, RecyclerView.VERTICAL, false);
                        notifications_adapter = new Notifications_Adapter(modelList, Notifications.this);
                        recyclerView.setLayoutManager(layoutManager);
                        Collections.reverse(modelList);
                        recyclerView.setAdapter(notifications_adapter);
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Notifications.this, "You don't have notifications", Toast.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(Notifications.this, "Not Successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationsModel> call, Throwable t) {
                Toast.makeText(Notifications.this, "No Response", Toast.LENGTH_SHORT).show();
                Log.d("aaaaaa", call.request().toString());
            }
        });
    }

    private void Initilizaiton() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerView = findViewById(R.id.recyclerView);
        ic_back = findViewById(R.id.ic_back);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Notifications.this,CoreActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}