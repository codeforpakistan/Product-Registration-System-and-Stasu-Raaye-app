package com.example.halalfoodauthorityoss.productregistration;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.adapter.ProductAdapter;
import com.example.halalfoodauthorityoss.model.AppData;
import com.example.halalfoodauthorityoss.model.Model;
import com.example.halalfoodauthorityoss.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProducts extends AppCompatActivity {

    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    List<Model> modelList = new ArrayList<Model>();
    ProgressDialog progressDialog;
    ImageView ic_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products);

        Initilizaiton();

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Call<ProductModel> call = BaseClass
                .getInstance()
                .getApi()
                .Get_Products(AppData.id);

        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                ProductModel productModel = response.body();
                List<Model> list = productModel.getProducts();
                if (response.isSuccessful()) {
                    if (productModel.success.equals("1")) {
                        int size = list.size();
                        for (int i = 0; i < size; i++) {
                            modelList.add(new Model(list.get(i).ProductNameReq, list.get(i).ProductNameApp, list.get(i).ProcLvl,
                                    list.get(i).r_application_id, list.get(i).business_id, list.get(i).business_name, list.get(i).cust_id, list.get(i).ExpireDate
                                    ));
                        }
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyProducts.this, RecyclerView.VERTICAL, false);
                        productAdapter = new ProductAdapter(modelList, MyProducts.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(productAdapter);
                        progressDialog.dismiss();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(MyProducts.this, "" + productModel.response_msg, Toast.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MyProducts.this, "Not Successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MyProducts.this, "No Response", Toast.LENGTH_SHORT).show();
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
}