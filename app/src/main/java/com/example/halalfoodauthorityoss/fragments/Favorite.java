package com.example.halalfoodauthorityoss.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.adapter.FavoriteAdapter;
import com.example.halalfoodauthorityoss.adapter.Search_Business_Adapter;
import com.example.halalfoodauthorityoss.complaint.Complaint;
import com.example.halalfoodauthorityoss.model.AppData;
import com.example.halalfoodauthorityoss.model.FavoriteModel;
import com.example.halalfoodauthorityoss.model.Model;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Favorite extends Fragment {

    RecyclerView recyclerView;
    FavoriteAdapter favoriteAdapter;
    List<Model> modelList = new ArrayList<>();

    public Favorite() {
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
        View view = inflater.inflate(R.layout.favorite, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        Call<FavoriteModel> call = BaseClass
                .getInstance()
                .getApi()
                .Get_to_Favorite(AppData.id);

        call.enqueue(new Callback<FavoriteModel>() {
            @Override
            public void onResponse(Call<FavoriteModel> call, Response<FavoriteModel> response) {
                FavoriteModel favoriteModel = response.body();
                List<Model> list = favoriteModel.getFavourite_list();
                if (response.isSuccessful()) {
                    if (favoriteModel.success.equals("1")) {
                        int size = list.size();
                        modelList.clear();
                        for (int i = 0; i < size; i++) {
                            modelList.add(new Model(list.get(i).business_id, list.get(i).business_name, list.get(i).timestamp,
                            list.get(i).average_rating,list.get(i).district_name,list.get(i).favourite_id,list.get(i).business_type));
                        }
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                        favoriteAdapter = new FavoriteAdapter(modelList, getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(favoriteAdapter);
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2 ));
                        favoriteAdapter.notifyDataSetChanged();
                    } else {
                    }
                } else {
                    Toast.makeText(getActivity(), "Not Successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FavoriteModel> call, Throwable t) {
                Toast.makeText(getActivity(), "No Response", Toast.LENGTH_SHORT).show();
                Log.d("aaaaaa", call.request().toString());
            }
        });

        return view;
    }
}