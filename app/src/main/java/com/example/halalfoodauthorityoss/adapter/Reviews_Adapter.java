package com.example.halalfoodauthorityoss.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.complaint.Complaint_Details;
import com.example.halalfoodauthorityoss.model.AppData;
import com.example.halalfoodauthorityoss.model.FeedBack_Attachements;
import com.example.halalfoodauthorityoss.model.Model;
import com.example.halalfoodauthorityoss.model.UserResponseModel;
import com.example.halalfoodauthorityoss.searchresult.Feedback_Reviews;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Reviews_Adapter extends RecyclerView.Adapter<Reviews_Adapter.ViewHolder> {
    List<Model> list = new ArrayList<>();
    Context context;
    boolean isTextViewClicked = false;
    ImagesAdapter imagesAdapter;
    ArrayList<Uri> imagesUriArrayList = new ArrayList<>();

    public Reviews_Adapter(List<Model> list, Feedback_Reviews context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reviews_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model model = list.get(position);
        holder.txtName.setText(model.customer_name);
        holder.txtRating.setText(model.overall_rating);
        holder.txtReviews.setText(model.feedback);
        holder.Premises_Hygiene.setRating(Float.parseFloat(model.premises_hygiene));
        holder.Equipment_Hygiene.setRating(Float.parseFloat(model.equipment_hygiene));
        holder.Staff_Personal_Hygiene.setRating(Float.parseFloat(model.staff_personal_hygiene));
        holder.Food_Hygiene.setRating(Float.parseFloat(model.food_hygiene));
        holder.Food_Quality.setRating(Float.parseFloat(model.food_quality));
        String firstWord = model.timestamp;
        if(firstWord.contains(" ")){
            firstWord= firstWord.substring(0, firstWord.indexOf(" "));
            holder.txtTime.setText(firstWord);
        }

        holder.txtReviews.setMaxLines(2);
        holder.ratingLayout.setVisibility(View.GONE);
        holder.imageRecycler.setVisibility(View.GONE);
        holder.txtSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagesUriArrayList.clear();
                if (isTextViewClicked) {
                    holder.txtReviews.setMaxLines(2);
                    isTextViewClicked = false;
                    holder.ratingLayout.setVisibility(View.GONE);
                    holder.imageRecycler.setVisibility(View.GONE);
                    holder.txtSeeMore.setText("See more");

                } else {
                    holder.txtReviews.setMaxLines(Integer.MAX_VALUE);
                    isTextViewClicked = true;
                    holder.ratingLayout.setVisibility(View.VISIBLE);
                    holder.imageRecycler.setVisibility(View.VISIBLE);
                    holder.txtSeeMore.setText("See less");

                }

                Call<FeedBack_Attachements> call = BaseClass
                        .getInstance()
                        .getApi()
                        .GetFeedbackAttachments(AppData.id, Integer.parseInt(model.business_rating_id));
                call.enqueue(new Callback<FeedBack_Attachements>() {
                    @Override
                    public void onResponse(Call<FeedBack_Attachements> call, Response<FeedBack_Attachements> response) {
                        FeedBack_Attachements feedBack_attachements = response.body();
                        List<Model> list = feedBack_attachements.getAttachments();
                        int size = list.size();
                        if (response.isSuccessful()) {
                            if (feedBack_attachements.success.equals("1")) {
                                for (int i = 0; i < size; i++) {
                                    imagesUriArrayList.add(Uri.parse(list.get(i).Path));
                                }
                                holder.imageRecycler.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                                imagesAdapter = new ImagesAdapter(imagesUriArrayList, context, "feedbackImages");
                                holder.imageRecycler.setLayoutManager(layoutManager);
                                holder.imageRecycler.setAdapter(imagesAdapter);
                                holder.imageRecycler.setLayoutManager(new GridLayoutManager(context, 4));
                                imagesAdapter.notifyDataSetChanged();
                      /*  recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
                        imagesAdapter.notifyDataSetChanged();*/
                            } else {
                                Toast.makeText(context, "Cann't Get Images", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(context, "Not Successful", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<FeedBack_Attachements> call, Throwable t) {
                        Toast.makeText(context, "No Response", Toast.LENGTH_SHORT).show();
                        Log.d("aaaaaa", call.request().toString());
                    }
                });
            }
        });

       /* Call<FeedBack_Attachements> call = BaseClass
                .getInstance()
                .getApi()
                .GetFeedbackAttachments(AppData.id, Integer.parseInt(model.business_rating_id));
        call.enqueue(new Callback<FeedBack_Attachements>() {
            @Override
            public void onResponse(Call<FeedBack_Attachements> call, Response<FeedBack_Attachements> response) {
                FeedBack_Attachements feedBack_attachements = response.body();
                List<Model> list = feedBack_attachements.getAttachments();
                int size = list.size();
                if (response.isSuccessful()) {
                    if (feedBack_attachements.success.equals("1")) {
                        for (int i = 0; i < size; i++) {
                            imagesUriArrayList.add(Uri.parse(list.get(i).Path));
                            Toast.makeText(context, ""+list.get(i).Path, Toast.LENGTH_SHORT).show();
                        }
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                        imagesAdapter = new ImagesAdapter(imagesUriArrayList, context, "feedbackImages");
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(imagesAdapter);
                      *//*  recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
                        imagesAdapter.notifyDataSetChanged();*//*
                    } else {
                        Toast.makeText(context, "Cann't Get Images", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "Not Successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FeedBack_Attachements> call, Throwable t) {
                Toast.makeText(context, "No Response", Toast.LENGTH_SHORT).show();
                Log.d("aaaaaa", call.request().toString());
            }
        });*/



     /*   holder.txtBussName.setText(model.business_name);
        holder.txtDistrict.setText(model.distric_name);
        holder.txtRating.setText(model.AverageRating);
        String firstWord = model.Register_Date;
        holder.regDate.setText("Registered Since: " + firstWord);*/


       /* holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               *//* Bundle bundle = new Bundle();
                bundle.putSerializable("ModelBusiness", (Serializable) model);*//*
                Intent intent = new Intent(context, Feedback_Reviews.class);
                //  intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtTime, txtReviews, txtRatingCount, txtSeeMore,txtRating;
        CircleImageView profilePic;
        ImageView ImageRtg;
        RatingBar Premises_Hygiene, Equipment_Hygiene, Staff_Personal_Hygiene, Food_Hygiene, Food_Quality;

        LinearLayout ratingLayout;
        RecyclerView imageRecycler;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtReviews = itemView.findViewById(R.id.txtReviews);
            txtRatingCount = itemView.findViewById(R.id.txtRatingCount);
            profilePic = itemView.findViewById(R.id.profilePic);
            ratingLayout = itemView.findViewById(R.id.ratingLayout);
            txtSeeMore = itemView.findViewById(R.id.txtSeeMore);
            Premises_Hygiene = itemView.findViewById(R.id.Premises_Hygiene);
            Equipment_Hygiene = itemView.findViewById(R.id.Equipment_Hygiene);
            Staff_Personal_Hygiene = itemView.findViewById(R.id.Staff_Personal_Hygiene);
            Food_Hygiene = itemView.findViewById(R.id.Food_Hygiene);
            Food_Quality = itemView.findViewById(R.id.Food_Quality);
            txtRating = itemView.findViewById(R.id.txtRating);
            imageRecycler = itemView.findViewById(R.id.imageRecycler);

        }
    }
}
