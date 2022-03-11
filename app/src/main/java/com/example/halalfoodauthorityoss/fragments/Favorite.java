package com.example.halalfoodauthorityoss.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.adapter.Search_Business_Adapter;
import com.example.halalfoodauthorityoss.model.Model;
import com.example.halalfoodauthorityoss.model.SearchResponseModel;
import com.example.halalfoodauthorityoss.searchresult.SearchResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Favorite extends Fragment {

    RecyclerView recyclerView;
    Search_Business_Adapter search_business_adapter;
    List<Model> modelList = new ArrayList<>();
    ProgressDialog progressDialog;

    public Favorite() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Call<SearchResponseModel> call = BaseClass
                .getInstance()
                .getApi()
                .SearchResult();

        call.enqueue(new Callback<SearchResponseModel>() {
            @Override
            public void onResponse(Call<SearchResponseModel> call, Response<SearchResponseModel> response) {
                SearchResponseModel searchResponseModel = response.body();
                List<Model> list = searchResponseModel.getBusinesses();
                int size = list.size();
                if (response.isSuccessful()) {
                    if (searchResponseModel.success.equals("1")) {
                        for (int i = 0; i < size; i++) {
                            modelList.add(new Model(list.get(i).business_id, list.get(i).business_name, list.get(i).Address,
                                    list.get(i).Register_Date, list.get(i).distric_name,list.get(i).AverageRating
                            ));
                        }
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                        search_business_adapter = new Search_Business_Adapter(modelList, getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(search_business_adapter);
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "No Record Found", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Not Successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(), "No Response", Toast.LENGTH_SHORT).show();
                Log.d("aaaaaa", call.request().toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.favorite, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
//            progressDialog.show();

        return view;
    }
}