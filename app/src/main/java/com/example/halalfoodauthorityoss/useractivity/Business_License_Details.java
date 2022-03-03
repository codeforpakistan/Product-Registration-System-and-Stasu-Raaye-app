package com.example.halalfoodauthorityoss.useractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.model.Model;

public class Business_License_Details extends AppCompatActivity {

    TextView edtBusinessName,edtOwnerName,edtFatherName,edtCnic,
            edtBusinessDistrict,edtLicenseNumber,edtBusinessType,edtExpiryDate,edtNumber;
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business__license__details);

        Initialization();

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
        if (model.LicNo==null)
        {
            edtLicenseNumber.setText("LicNo Pending");
        }else {
            edtLicenseNumber.setText(model.LicNo);
        }
    }

    private void Initialization() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        edtBusinessName=findViewById(R.id.edtBusinessName);
        edtOwnerName=findViewById(R.id.edtOwnerName);
        edtFatherName=findViewById(R.id.edtFatherName);
        edtCnic=findViewById(R.id.edtCnic);
        edtBusinessDistrict=findViewById(R.id.edtBusinessDistrict);
        edtLicenseNumber=findViewById(R.id.edtLicenseNumber);
        edtBusinessType=findViewById(R.id.edtBusinessType);
        edtExpiryDate=findViewById(R.id.edtExpiryDate);
        edtNumber=findViewById(R.id.edtNumber);
    }
}