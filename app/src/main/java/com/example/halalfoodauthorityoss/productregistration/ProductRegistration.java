package com.example.halalfoodauthorityoss.productregistration;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.model.AppData;
import com.example.halalfoodauthorityoss.model.Model;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRegistration extends AppCompatActivity {

    TextInputEditText edtLicNo, edtProductName;
    TextView txtBusinessName, Verify, btnRegister,txtInfo;
    int business_id;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_registration);

        initialization();

        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                Call<Model> call = BaseClass
                        .getInstance()
                        .getApi()
                        .LicNoChecking(AppData.id, edtLicNo.getText().toString().trim());

                call.enqueue(new Callback<Model>() {
                    @Override
                    public void onResponse(Call<Model> call, Response<Model> response) {
                        progressDialog.dismiss();
                        Model model = response.body();
                        if (response.isSuccessful()) {
                            if (model.getSuccess().equals("1")) {
                                Dialog permissionDialogue = new Dialog(ProductRegistration.this);
                                permissionDialogue.setContentView(R.layout.permission_dialogue);
                                permissionDialogue.setCancelable(false);
                                TextView txtText = permissionDialogue.findViewById(R.id.txtText);
                                txtText.setText("Do you want to register your product with business name: "+ model.business_name +"?");
                                TextView yes = permissionDialogue.findViewById(R.id.Yes);
                                TextView no = permissionDialogue.findViewById(R.id.No);
                                yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        txtInfo.setVisibility(View.VISIBLE);
                                        txtBusinessName.setVisibility(View.VISIBLE);
                                        txtBusinessName.setText(model.business_name);
                                        business_id = Integer.parseInt(model.business_id);
                                        permissionDialogue.dismiss();
                                    }
                                });
                                no.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        txtInfo.setVisibility(View.GONE);
                                        txtBusinessName.setVisibility(View.GONE);
                                        permissionDialogue.dismiss();
                                        txtBusinessName.setText(" ");
                                        business_id = 000000000;
                                    }
                                });
                                permissionDialogue.show();
                            } else {
                                Toast.makeText(ProductRegistration.this, "" + model.response_msg, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ProductRegistration.this, "Not Successful", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(ProductRegistration.this, "No Response", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Model> call = BaseClass
                        .getInstance()
                        .getApi()
                        .Product_Registration(AppData.id, business_id, edtProductName.getText().toString().trim());

                call.enqueue(new Callback<Model>() {
                    @Override
                    public void onResponse(Call<Model> call, Response<Model> response) {
                        Model model = response.body();
                        if (response.isSuccessful()) {
                            if (model.getSuccess().equals("1")) {
                                DialogBOX(model.r_application_id);
                            } else {
                                Toast.makeText(ProductRegistration.this, "" + model.response_msg, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {
                        Toast.makeText(ProductRegistration.this, "No Response", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initialization() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        edtLicNo = findViewById(R.id.edtLicNo);
        edtProductName = findViewById(R.id.edtProductName);
        txtBusinessName = findViewById(R.id.txtBusinessName);
        Verify = findViewById(R.id.Verify);
        btnRegister = findViewById(R.id.btnRegister);
        txtInfo = findViewById(R.id.txtInfo);
    }

    private void DialogBOX(String r_app_no) {
        Dialog dialoguebox = new Dialog(ProductRegistration.this);
        dialoguebox.setContentView(R.layout.dialogue_box);
        dialoguebox.setCancelable(true);
        TextView txtalert = dialoguebox.findViewById(R.id.txtalert);
        TextView message = dialoguebox.findViewById(R.id.txtmessage);
        TextView ok = dialoguebox.findViewById(R.id.ok);

        txtalert.setText("Congratulations!");
        message.setText("Your application submitted successfully for Product Registration.Your applicaiton number is : " + r_app_no);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialoguebox.dismiss();
            }
        });
        dialoguebox.show();
    }
}