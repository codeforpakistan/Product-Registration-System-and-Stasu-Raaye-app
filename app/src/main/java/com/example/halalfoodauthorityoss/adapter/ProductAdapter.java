package com.example.halalfoodauthorityoss.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.model.Model;
import com.example.halalfoodauthorityoss.useractivity.MyProducts;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    List<Model> list=new ArrayList<>();
    Context context;

    public ProductAdapter(List<Model> list, MyProducts context) {
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

        holder.categ_Number.setText("Product Catergory");
        holder.categ_Number.setText("Product Name");

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView categ_Number,buss_Prod_Name,date,licenseDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categ_Number=itemView.findViewById(R.id.categ_Number);
            buss_Prod_Name=itemView.findViewById(R.id.buss_Prod_Name);
            date=itemView.findViewById(R.id.date);
            licenseDetail=itemView.findViewById(R.id.licenseDetail);
        }
    }
}
