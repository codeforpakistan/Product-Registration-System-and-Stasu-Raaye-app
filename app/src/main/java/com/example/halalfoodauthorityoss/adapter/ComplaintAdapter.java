package com.example.halalfoodauthorityoss.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.model.Model;
import com.example.halalfoodauthorityoss.complaint.Complaint_Details;
import com.example.halalfoodauthorityoss.complaint.MyComplaints;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ViewHolder> {
    List<Model> list=new ArrayList<>();
    Context context;

    public ComplaintAdapter(List<Model> list, MyComplaints context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.rowlayout_complaint,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Model model = list.get(position);

        holder.businessName.setText(model.comp_buss_name);
        holder.complaintType.setText(model.ComplaintTitle);
        int counting= position+1;
        String number= String.valueOf(counting);
        holder.count.setText(number);

        holder.mlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("Model", (Serializable) model);
                Intent intent = new Intent(context, Complaint_Details.class);
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

        TextView count,businessName,complaintType;
        LinearLayout mlayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mlayout=itemView.findViewById(R.id.Linearlayout);
            count=itemView.findViewById(R.id.count);
            businessName=itemView.findViewById(R.id.bussName);
            complaintType=itemView.findViewById(R.id.complaintType);
        }
    }
}
