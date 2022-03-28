package com.example.halalfoodauthorityoss.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.model.Model;
import com.example.halalfoodauthorityoss.useractivity.Business_License_Details;
import com.example.halalfoodauthorityoss.useractivity.MyBusinesses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.ViewHolder> {
    List<Model> list=new ArrayList<>();
    Context context;

    public BusinessAdapter(List<Model> list, MyBusinesses context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.rowlayout_business,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Model model = list.get(position);
        if (model.LicNo==null)
        {
            holder.name_Number.setText("LicNo Pending");
        }else {
            holder.name_Number.setText("LicNo "+model.LicNo);
        }
        holder.buss_Name.setText(model.bussnie_name);
        String firstWord = model.Register_Date;
        if(firstWord.contains(" ")){
            firstWord= firstWord.substring(0, firstWord.indexOf(" "));
            holder.date.setText("Reg Date: "+firstWord);
        }


        holder.mlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("ModelBusiness", (Serializable) model);
                Intent intent = new Intent(context, Business_License_Details.class);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name_Number,buss_Name,date,licenseDetail;
        ConstraintLayout mlayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name_Number=itemView.findViewById(R.id.name_Number);
            buss_Name=itemView.findViewById(R.id.buss_Name);
            date=itemView.findViewById(R.id.date);
          //  licenseDetail=itemView.findViewById(R.id.licenseDetail);
            mlayout=itemView.findViewById(R.id.Linearlayout);
        }
    }
}
