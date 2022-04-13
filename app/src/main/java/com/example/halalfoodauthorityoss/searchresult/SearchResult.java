package com.example.halalfoodauthorityoss.searchresult;

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
import com.example.halalfoodauthorityoss.adapter.Search_Business_Adapter;
import com.example.halalfoodauthorityoss.fragments.Home;
import com.example.halalfoodauthorityoss.model.Model;
import com.example.halalfoodauthorityoss.model.SearchResponseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResult extends AppCompatActivity {

    RecyclerView recyclerView;
    Search_Business_Adapter search_business_adapter;
    List<Model> modelList = new ArrayList<>();
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Initilizaiton();

        Call<SearchResponseModel> call = BaseClass
                .getInstance()
                .getApi()
                .SearchResult(Home.category_id,Home.district_id, Home.name);

        call.enqueue(new Callback<SearchResponseModel>() {
            @Override
            public void onResponse(Call<SearchResponseModel> call, Response<SearchResponseModel> response) {
                if (response.isSuccessful()) {
                SearchResponseModel searchResponseModel = response.body();
                List<Model> list = searchResponseModel.getBusinesses();
                if (list!=null)
                {
                    int size = list.size();
                    if (searchResponseModel.success.equals("1")) {
                        Toast.makeText(SearchResult.this, ""+searchResponseModel.message, Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < size; i++) {
                            modelList.add(new Model(list.get(i).business_id, list.get(i).business_name, list.get(i).Address,
                                    list.get(i).Register_Date, list.get(i).distric_name,list.get(i).AverageRating
                            ));
                        }
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchResult.this, RecyclerView.VERTICAL, false);
                        search_business_adapter = new Search_Business_Adapter(modelList, SearchResult.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(search_business_adapter);
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(SearchResult.this, "No Record Found", Toast.LENGTH_LONG).show();
                    }
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(SearchResult.this, "No Record Found", Toast.LENGTH_LONG).show();
                }
                } else {
                    Toast.makeText(SearchResult.this, "Not Successful", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<SearchResponseModel> call, Throwable t) {
                Toast.makeText(SearchResult.this, "No Response", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Home.category_id=0;
        Home.district_id=0;
        Home.name=" ";
    }
}