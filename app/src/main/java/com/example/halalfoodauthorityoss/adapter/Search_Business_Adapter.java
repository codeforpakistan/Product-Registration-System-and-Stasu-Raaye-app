package com.example.halalfoodauthorityoss.adapter;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.businesslicense.Bussiness_Details;
import com.example.halalfoodauthorityoss.businesslicense.Personal_Detail;
import com.example.halalfoodauthorityoss.model.AppData;
import com.example.halalfoodauthorityoss.model.Model;
import com.example.halalfoodauthorityoss.searchresult.Feedback_Reviews;
import com.example.halalfoodauthorityoss.searchresult.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class Search_Business_Adapter extends RecyclerView.Adapter<Search_Business_Adapter.ViewHolder> {
//    public static int business_id;
    List<Model> list=new ArrayList<>();
    Context context;

    public Search_Business_Adapter(List<Model> list, Context context) {
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

        holder.txtBussName.setText(model.business_name);
        holder.txtDistrict.setText(model.distric_name);
        holder.txtRating.setText(model.AverageRating);
        String firstWord = model.Register_Date;
        if(firstWord.contains(" ")){
            firstWord= firstWord.substring(0, firstWord.indexOf(" "));
            holder.regDate.setText("Registered Since: "+firstWord);
        }

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Feedback_Reviews.class);
                AppData.business_id= Integer.parseInt(model.business_id);
                AppData.activity= "SearchActivity";
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtBussName,txtDistrict,regDate,txtRating;
        View mLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtBussName=itemView.findViewById(R.id.txtBussName);
            txtDistrict=itemView.findViewById(R.id.txtDistrict);
            regDate=itemView.findViewById(R.id.regDate);
            txtRating=itemView.findViewById(R.id.txtRating);
            mLayout=itemView.findViewById(R.id.mLayout);
        }
    }
}
