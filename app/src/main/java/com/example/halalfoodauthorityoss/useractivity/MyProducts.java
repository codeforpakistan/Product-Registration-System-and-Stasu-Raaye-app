package com.example.halalfoodauthorityoss.useractivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.adapter.BusinessAdapter;
import com.example.halalfoodauthorityoss.adapter.ProductAdapter;
import com.example.halalfoodauthorityoss.model.Model;

import java.util.ArrayList;
import java.util.List;

public class MyProducts extends AppCompatActivity {

    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    List<Model> modelList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products);

        Initilizaiton();

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyProducts.this, RecyclerView.VERTICAL, false);
        productAdapter = new ProductAdapter(modelList, MyProducts.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(productAdapter);
    }

    private void Initilizaiton() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerView=findViewById(R.id.recyclerView);

    }
}