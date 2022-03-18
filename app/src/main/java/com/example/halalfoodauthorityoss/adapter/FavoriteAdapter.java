package com.example.halalfoodauthorityoss.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.model.AppData;
import com.example.halalfoodauthorityoss.model.Model;
import com.example.halalfoodauthorityoss.complaint.Complaint_Details;
import com.example.halalfoodauthorityoss.complaint.MyComplaints;
import com.example.halalfoodauthorityoss.searchresult.Feedback_Reviews;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    List<Model> list=new ArrayList<>();
    Context context;

    public FavoriteAdapter(List<Model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.favorite_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Model model = list.get(position);

        holder.txtBusinessType.setText(model.business_type);
        holder.txtBussName.setText(model.business_name);
        holder.txtDistrict.setText(model.district_name);
        holder.txtRating.setText(model.average_rating);

        holder.mlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Feedback_Reviews.class);
                AppData.business_id= Integer.parseInt(model.business_id);
                AppData.favorite_id= Integer.parseInt(model.favourite_id);
                AppData.activity= "FavoriteActivity";
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtBussName,txtBusinessType,txtDistrict,txtRating;
        LinearLayout mlayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mlayout=itemView.findViewById(R.id.Linearlayout);
            txtBussName=itemView.findViewById(R.id.txtBussName);
            txtBusinessType=itemView.findViewById(R.id.txtBusinessType);
            txtDistrict=itemView.findViewById(R.id.txtDistrict);
            txtRating=itemView.findViewById(R.id.txtRating);
        }
    }
}
