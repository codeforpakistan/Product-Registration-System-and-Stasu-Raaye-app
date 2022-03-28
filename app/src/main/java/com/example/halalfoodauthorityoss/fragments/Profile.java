package com.example.halalfoodauthorityoss.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.halalfoodauthorityoss.loginsignupforgot.UpdateProfile;
import com.example.halalfoodauthorityoss.model.AppData;
import com.example.halalfoodauthorityoss.useractivity.MyBusinesses;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.complaint.MyComplaints;
import com.example.halalfoodauthorityoss.productregistration.MyProducts;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends Fragment {

    LinearLayout myBusinesses,myProducts,myComplaints;
    CircleImageView profilePic;
    ImageView btnUpdate;
    TextView txtName,txtAddress;

    public Profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.profile, container, false);
        myBusinesses=view.findViewById(R.id.myBusinesses);
        myComplaints=view.findViewById(R.id.myComplaints);
        myProducts=view.findViewById(R.id.myProducts);
        txtName=view.findViewById(R.id.txtName);
        txtAddress=view.findViewById(R.id.txtNumber);
        profilePic=view.findViewById(R.id.profilePic);
        btnUpdate=view.findViewById(R.id.btnUpdate);

        txtName.setText(AppData.name);

        if (!AppData.address.equals("0"))
        {
            txtAddress.setText(AppData.address);
        }
        if (AppData.photo != "0") {
            String path = "https://halalfoods.testportal.famzsolutions.com/assets/customer_images/" + AppData.photo;
            Glide.with(Profile.this).load(path).into(profilePic);
        }
        else {
            profilePic.setImageResource(R.drawable.ic_human);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UpdateProfile.class);
                startActivity(intent);
            }
        });

        myBusinesses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyBusinesses.class);
                startActivity(intent);
            }
        });
        myComplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyComplaints.class);
                startActivity(intent);
            }
        });
        myProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyProducts.class);
                startActivity(intent);
            }
        });

        return view;
    }
}