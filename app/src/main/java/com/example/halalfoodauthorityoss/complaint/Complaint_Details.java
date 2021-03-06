package com.example.halalfoodauthorityoss.complaint;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.adapter.ImagesAdapter;
import com.example.halalfoodauthorityoss.model.Model;
import com.example.halalfoodauthorityoss.model.UserResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Complaint_Details extends AppCompatActivity {

    TextView Ctitle, district, address, details, bName, status, btnfeedback,remarks;
    Model model;
    RecyclerView recyclerView;
    ImagesAdapter imagesAdapter;
    ArrayList<Uri> imagesUriArrayList = new ArrayList<>();
    LinearLayout remarkslayout;
    View view;
    int rating_value = 0;
    int satisfaction = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint__details);

        Initialization();

        ImageView ic_back=findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        model = (Model) bundle.getSerializable("Model");
        Ctitle.setText(model.ComplaintTitle);
        district.setText(model.district);
        address.setText(model.Address);
        details.setText(model.ComplaintDescription);
        bName.setText(model.comp_buss_name);
        status.setText(model.status);

        if (model.status.toLowerCase().equals("close")) {
            remarks.setText(model.eng_comment);
            remarkslayout.setVisibility(View.VISIBLE);
            btnfeedback.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
        }

        btnfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog rankDialog = new Dialog(Complaint_Details.this);
                rankDialog.setContentView(R.layout.rating_dialogue);
                rankDialog.setCancelable(true);
                RatingBar ratingBar = rankDialog.findViewById(R.id.dialog_ratingbar);
                TextView feedback = rankDialog.findViewById(R.id.feedback);
                RadioGroup radioGroup = rankDialog.findViewById(R.id.radioGroup);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.no:
                                satisfaction = 0;
                                break;
                            case R.id.yes:
                                satisfaction = 1;
                                break;
                        }
                    }
                });
                TextView cancel = rankDialog.findViewById(R.id.cancel);
                TextView submit = rankDialog.findViewById(R.id.submit);

                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating,
                                                boolean fromUser) {
                        rating_value = (int) rating;
                    }

                });

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String Feedback = feedback.getText().toString().trim();
                        if (satisfaction == 111 || rating_value == 0) {
                            Toast.makeText(Complaint_Details.this, "Rating cann't be null!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        int cust_id = Integer.parseInt(model.cust_id);
                        int cid = Integer.parseInt(model.cid);
                        Call<Model> call = BaseClass
                                .getInstance()
                                .getApi()
                                .ComplaintRating(rating_value, cust_id, cid, Feedback, satisfaction);
                        call.enqueue(new Callback<Model>() {
                            @Override
                            public void onResponse(Call<Model> call, Response<Model> response) {
                                Model model = response.body();
                                if (response.isSuccessful()) {
                                    if (model.getSuccess().equals("1")) {
                                        int rating_value = 0;
                                        int satisfaction = 111;
                                        rankDialog.dismiss();
                                        Toast.makeText(Complaint_Details.this, "Feeback Submitted", Toast.LENGTH_LONG).show();
                                    } else {
                                        rankDialog.dismiss();
                                        Toast.makeText(Complaint_Details.this, ""+model.response_msg, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Model> call, Throwable t) {
                                rankDialog.dismiss();
                                Toast.makeText(Complaint_Details.this, "No Response", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rankDialog.dismiss();
                    }
                });
                rankDialog.show();
            }
        });

        int complaint_id = Integer.parseInt(model.cid);

        Call<UserResponseModel> call = BaseClass
                .getInstance()
                .getApi()
                .GetImages(complaint_id);
        call.enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(Call<UserResponseModel> call, Response<UserResponseModel> response) {
                if (response.isSuccessful()) {
                    UserResponseModel userResponseModel = response.body();
                    if (userResponseModel.success.equals("1")) {
                        List<Model> list = userResponseModel.getMessage();
                        int size = list.size();
                        for (int i = 0; i < size; i++) {
                            imagesUriArrayList.add(Uri.parse(list.get(i).Path));
                        }
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Complaint_Details.this, RecyclerView.VERTICAL, false);
                        imagesAdapter = new ImagesAdapter(imagesUriArrayList, Complaint_Details.this, "complaintDetail");
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(imagesAdapter);
                        recyclerView.setLayoutManager(new GridLayoutManager(Complaint_Details.this, 4));
                        imagesAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(Complaint_Details.this, "Cann't Get Images", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Complaint_Details.this, "Not Successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponseModel> call, Throwable t) {
                Toast.makeText(Complaint_Details.this, "No Response", Toast.LENGTH_SHORT).show();
                Log.d("aaaaaa", call.request().toString());
            }
        });
    }

    private void Initialization() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Ctitle = findViewById(R.id.Ctitle);
        district = findViewById(R.id.district);
        address = findViewById(R.id.address);
        details = findViewById(R.id.details);
        bName = findViewById(R.id.bName);
        status = findViewById(R.id.status);
        btnfeedback = findViewById(R.id.btnfeedback);
        remarkslayout = findViewById(R.id.remarkslayout);
        view = findViewById(R.id.view);
        recyclerView = findViewById(R.id.recyclerview);
        remarks = findViewById(R.id.remarks);
    }
}