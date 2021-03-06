package com.example.halalfoodauthorityoss.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.complaint.Complaint;
import com.example.halalfoodauthorityoss.searchresult.User_Rating;

import java.util.ArrayList;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {
    ArrayList<Uri> imagesList = new ArrayList<>();
    Context context;
    String action;

    public ImagesAdapter(ArrayList<Uri> imagesList, Context context, String action) {
        this.imagesList = imagesList;
        this.context = context;
        this.action = action;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_images, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Uri selectedImage = imagesList.get(position);

        if (action.equals("complaintDetail")) {
            String path = "" + selectedImage;
            Glide.with(context).load(path).into(holder.images);
            Log.d("Images", path);
            return;
        }

        if (action.equals("feedbackImages")) {
            String path = "" + selectedImage;
            Log.d("tag", String.valueOf(selectedImage));
            Glide.with(context).load(path).into(holder.images);
            Log.d("Images", path);
            return;
        }
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView images;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            images = itemView.findViewById(R.id.images);

        }
    }
}

