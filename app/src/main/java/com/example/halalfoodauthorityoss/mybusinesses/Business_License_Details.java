package com.example.halalfoodauthorityoss.mybusinesses;

import android.app.Dialog;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Business_License_Details extends AppCompatActivity {

    TextView edtBusinessName, edtOwnerName, edtFatherName, edtCnic,
            edtBusinessDistrict, edtLicenseNumber, edtBusinessType, edtExpiryDate, edtNumber, btnRenew, edtAddress;
    Model model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business__license__details);

        Initialization();

        ImageView ic_back=findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        model = (Model) bundle.getSerializable("ModelBusiness");
        edtBusinessName.setText(model.bussnie_name);
        edtOwnerName.setText(model.owner_name);
        edtFatherName.setText(model.FName);
        edtCnic.setText(model.CNIC);
        edtBusinessDistrict.setText(model.distric_name);
        edtLicenseNumber.setText(model.LicNo);
        edtBusinessType.setText(model.Type);
        edtExpiryDate.setText(model.ExpireDate);
        edtNumber.setText(model.Mobile);
        edtAddress.setText(model.Address);
        if (model.LicNo == null) {
            edtLicenseNumber.setText("LicNo Pending");
        } else {
            edtLicenseNumber.setText(model.LicNo);
        }
        if (!model.expiry_days_remaining.equals("false")) {
            int days = Integer.parseInt(model.expiry_days_remaining);
            if (days <= 45 && model.renewal_application.equals("0")) {
                btnRenew.setVisibility(View.VISIBLE);
            }
        }

        btnRenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog RenewBusinessDialogue = new Dialog(Business_License_Details.this);
                RenewBusinessDialogue.setContentView(R.layout.permission_dialogue);
                RenewBusinessDialogue.setCancelable(false);
                TextView txtText = RenewBusinessDialogue.findViewById(R.id.txtText);
                txtText.setText("Do you want to apply for business renewal?");
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
                .Business_Renewal(AppData.id, Integer.parseInt(model.r_application_id));

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Model model = response.body();
                if (response.isSuccessful()) {
                    if (model.getSuccess().equals("1")) {
                        DialogBOX(model.r_application_id);
                    } else {
                        Toast.makeText(Business_License_Details.this, "" + model.response_msg, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(Business_License_Details.this, "No Response", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Initialization() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        edtBusinessName = findViewById(R.id.edtBusinessName);
        edtOwnerName = findViewById(R.id.edtOwnerName);
        edtFatherName = findViewById(R.id.edtFatherName);
        edtCnic = findViewById(R.id.edtCnic);
        edtBusinessDistrict = findViewById(R.id.edtBusinessDistrict);
        edtLicenseNumber = findViewById(R.id.edtLicenseNumber);
        edtBusinessType = findViewById(R.id.edtBusinessType);
        edtExpiryDate = findViewById(R.id.edtExpiryDate);
        edtNumber = findViewById(R.id.edtNumber);
        btnRenew = findViewById(R.id.btnRenew);
        edtAddress = findViewById(R.id.edtAddress);
    }

    private void DialogBOX(String r_app_no) {
        Dialog dialoguebox = new Dialog(Business_License_Details.this);
        dialoguebox.setContentView(R.layout.dialogue_box);
        dialoguebox.setCancelable(false);
        TextView txtalert = dialoguebox.findViewById(R.id.txtalert);
        TextView message = dialoguebox.findViewById(R.id.txtmessage);
        TextView ok = dialoguebox.findViewById(R.id.ok);

        txtalert.setText("Congratulations!");
        message.setText("Your application submitted successfully for business renewal.Your applicaiton number is : " + r_app_no);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialoguebox.dismiss();
                MyBusinesses.fa.finish();
                startActivity(new Intent(Business_License_Details.this,MyBusinesses.class));
                finish();
            }
        });
        dialoguebox.show();
    }
}