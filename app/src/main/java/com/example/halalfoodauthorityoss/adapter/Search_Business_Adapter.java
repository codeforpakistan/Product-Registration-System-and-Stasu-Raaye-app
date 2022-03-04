package com.example.halalfoodauthorityoss.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.complaint.Complaint_Details;
import com.example.halalfoodauthorityoss.model.Model;
import com.example.halalfoodauthorityoss.searchresult.SearchResult;
import com.example.halalfoodauthorityoss.useractivity.Business_License_Details;
import com.example.halalfoodauthorityoss.useractivity.MyBusinesses;
import com.example.halalfoodauthorityoss.useractivity.MyProducts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Search_Business_Adapter extends RecyclerView.Adapter<Search_Business_Adapter.ViewHolder> {
    List<Model> list=new ArrayList<>();
    Context context;

    public Search_Business_Adapter(List<Model> list, SearchResult context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.search_businesslayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Model model = list.get(position);

        holder.txtBussName.setText(model.bussnie_name);
        holder.txtDistrict.setText(model.distric_name);
        holder.txtAddress.setText(model.Address);
       /* holder.mlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("ModelBusiness", (Serializable) model);
                Intent intent = new Intent(context, Business_License_Details.class);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtBussName,txtDistrict,txtAddress;
        LinearLayout mlayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtBussName=itemView.findViewById(R.id.txtBussName);
            txtDistrict=itemView.findViewById(R.id.txtDistrict);
            txtAddress=itemView.findViewById(R.id.txtAddress);
            mlayout=itemView.findViewById(R.id.mlayout);
        }
    }
}
