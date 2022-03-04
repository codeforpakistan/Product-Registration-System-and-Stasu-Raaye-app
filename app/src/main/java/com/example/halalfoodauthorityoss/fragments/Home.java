package com.example.halalfoodauthorityoss.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.adapter.ComplaintAdapter;
import com.example.halalfoodauthorityoss.complaint.MyComplaints;
import com.example.halalfoodauthorityoss.model.Model;
import com.example.halalfoodauthorityoss.model.SearchResponseModel;
import com.example.halalfoodauthorityoss.searchresult.SearchResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {

    EditText edtSearch;
    ImageView icSearch,icFilter;
    public Home() {
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

        View view= inflater.inflate(R.layout.home, container, false);
        edtSearch=view.findViewById(R.id.edtSearch);
        icSearch=view.findViewById(R.id.icSearch);
        icFilter=view.findViewById(R.id.icFilter);

        icSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SearchResult.class));
            }
        });
        return view;    }
}