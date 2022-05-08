package com.example.halalfoodauthorityoss.productregistration;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.model.AppData;
import com.example.halalfoodauthorityoss.model.Model;
import com.example.halalfoodauthorityoss.model.Product_Detail_Model;
import com.example.halalfoodauthorityoss.searchresult.Feedback_Reviews;
import com.example.halalfoodauthorityoss.searchresult.User_Rating;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product_Details extends AppCompatActivity {

    TextView edtBusinessName, edtproductname, edtBusinessDistrict, edtLicenseNumber, edtExpiryDate, btnRenew;
    Model model;
    ImageView ic_back;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__details);

        Initialization();

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Call<Product_Detail_Model> call = BaseClass
                .getInstance()
                .getApi()
                .Get_Products_Details(AppData.id, Integer.parseInt(model.r_application_id));

        call.enqueue(new Callback<Product_Detail_Model>() {
            @Override
            public void onResponse(Call<Product_Detail_Model> call, Response<Product_Detail_Model> response) {
                Product_Detail_Model productModel = response.body();
                if (response.isSuccessful()) {
                    if (productModel.success.equals("1")) {
                        edtBusinessName.setText(productModel.product_detail.business_name);
                        edtproductname.setText(productModel.product_detail.product_name);
                        edtBusinessDistrict.setText(productModel.product_detail.district_name);
                        edtLicenseNumber.setText(productModel.product_detail.product_license_no);
                        if (!productModel.product_detail.expiry_days_remaining.equals("false")) {
                            int days = Integer.parseInt(productModel.product_detail.expiry_days_remaining);
                            if (days <= 45) {
                                btnRenew.setVisibility(View.VISIBLE);
                            }
                        }
                        edtExpiryDate.setText(productModel.product_detail.product_license_expiry);
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Product_Details.this, "" + productModel.response_msg, Toast.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(Product_Details.this, "Not Successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product_Detail_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Product_Details.this, "No Response", Toast.LENGTH_SHORT).show();
            }
        });

        btnRenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog RenewBusinessDialogue = new Dialog(Product_Details.this);
                RenewBusinessDialogue.setContentView(R.layout.permission_dialogue);
                RenewBusinessDialogue.setCancelable(false);
                TextView txtText = RenewBusinessDialogue.findViewById(R.id.txtText);
                txtText.setText("Do you want to apply for product renewal?");
                TextView yes = RenewBusinessDialogue.findViewById(R.id.Yes);
                TextView no = RenewBusinessDialogue.findViewById(R.id.No);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RenewBusinessDialogue.dismiss();
                        RenewBusiness();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RenewBusinessDialogue.dismiss();
                    }
                });
                RenewBusinessDialogue.show();
            }
        });
    }

    private void RenewBusiness() {

        Call<Model> call = BaseClass
                .getInstance()
                .getApi()
                .Prodcut_Renewal(AppData.id, Integer.parseInt(model.r_application_id));

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Model model = response.body();
                if (response.isSuccessful()) {
                    if (model.getSuccess().equals("1")) {
                        DialogBOX(model.r_application_id);
                    } else {
                        Toast.makeText(Product_Details.this, "" + model.response_msg, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(Product_Details.this, "No Response", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void DialogBOX(String r_app_no) {
        Dialog dialoguebox = new Dialog(Product_Details.this);
        dialoguebox.setContentView(R.layout.dialogue_box);
        dialoguebox.setCancelable(false);
        TextView txtalert = dialoguebox.findViewById(R.id.txtalert);
        TextView message = dialoguebox.findViewById(R.id.txtmessage);
        TextView ok = dialoguebox.findViewById(R.id.ok);

        txtalert.setText("Congratulations!");
        message.setText("Your application submitted successfully for product renewal.Your applicaiton number is : " + r_app_no);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialoguebox.dismiss();
                MyProducts.fa.finish();
                startActivity(new Intent(Product_Details.this,MyProducts.class));
                finish();
            }
        });
        dialoguebox.show();
    }

    private void Initialization() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Bundle bundle = getIntent().getExtras();
        model = (Model) bundle.getSerializable("ProductModel");
        edtBusinessName = findViewById(R.id.edtBusinessName);
        edtproductname = findViewById(R.id.edtproductname);
        edtBusinessDistrict = findViewById(R.id.edtBusinessDistrict);
        edtLicenseNumber = findViewById(R.id.edtLicenseNumber);
        edtExpiryDate = findViewById(R.id.edtExpiryDate);
        ic_back = findViewById(R.id.ic_back);
        btnRenew = findViewById(R.id.btnRenew);
    }
}