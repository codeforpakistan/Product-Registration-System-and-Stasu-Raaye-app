package com.example.halalfoodauthorityoss.adapter;

import android.content.Context;
import android.net.Uri;
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

public class ImageAdapterDisplay extends RecyclerView.Adapter<ImageAdapterDisplay.ViewHolder> {
    ArrayList<Uri> imagesList = new ArrayList<>();
    Context context;

    public ImageAdapterDisplay(ArrayList<Uri> imagesList, Context context) {
        this.imagesList = imagesList;
        this.context = context;

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
        if (User_Rating.camera) {
            holder.images.setImageURI(selectedImage);
        } else if (Complaint.camera) {
            holder.images.setImageURI(selectedImage);
        } else {
            Glide.with(context).load(selectedImage).into(holder.images);
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
