package com.example.halalfoodauthorityoss.complaint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.model.Model;

import java.io.Serializable;

public class Complaint_Status extends AppCompatActivity {

    TextView BusinessName,txtInvestigation,compDate,compRemarks,compStatus,btnViewDetails;
    ImageView statusImage;
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint__status);

        Initialization();

        Bundle bundle = getIntent().getExtras();
        model = (Model) bundle.getSerializable("Model");
        /*Ctitle.setText(model.ComplaintTitle);
        district.setText(model.district);
        address.setText(model.Address);
        details.setText(model.ComplaintDescription);*/
        BusinessName.setText(model.comp_buss_name);
        String firstWord = model.create_date;
        if(firstWord.contains(" ")){
            firstWord= firstWord.substring(0, firstWord.indexOf(" "));
            compDate.setText("Date: "+firstWord);
        }
        compStatus.setText(model.status);
        if (model.status.toLowerCase().equals("close")) {
            statusImage.setImageResource(R.drawable.ic_status_all_tick);
            compRemarks.setText("Complete");
        }

        btnViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("Model", (Serializable) model);
                Intent intent = new Intent(Complaint_Status.this, Complaint_Details.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void Initialization() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        BusinessName=findViewById(R.id.BusinessName);
        txtInvestigation=findViewById(R.id.txtInvestigation);
        compDate=findViewById(R.id.compDate);
        compRemarks=findViewById(R.id.compRemarks);
        compStatus=findViewById(R.id.compStatus);
        btnViewDetails=findViewById(R.id.btnViewDetails);
        statusImage=findViewById(R.id.statusImage);
    }
}