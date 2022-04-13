package com.example.halalfoodauthorityoss.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.CoreActivity;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.model.AppData;
import com.example.halalfoodauthorityoss.model.Model;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notifications_Adapter extends RecyclerView.Adapter<Notifications_Adapter.ViewHolder> {
    List<Model> list = new ArrayList<>();
    Context context;

    public Notifications_Adapter(List<Model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notificaiton_row, parent, false);
        return new Notifications_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Model model = list.get(position);
        holder.notification_title.setText(model.notification_title);
        holder.notification_type.setText(model.notification_type);
        if (model.notification_status.equals("unseen"))
        {
            holder.cardView.setBackgroundResource(R.color.lightBlue);
        }


        if (model.notification_type.equals("success")) {
            holder.image_status_background.setBackgroundResource(R.color.darkgreen);
            holder.image_status.setImageResource(R.drawable.ic_success);
        } else if (model.notification_type.equals("info")) {
            holder.image_status_background.setBackgroundResource(R.color.lightyellow);
            holder.image_status.setImageResource(R.drawable.ic_download);
        } else {
            holder.image_status_background.setBackgroundResource(R.color.darkred);
            holder.image_status.setImageResource(R.drawable.ic_error);
        }

        String firstWord = model.notification_timestamp;
        if (firstWord.contains(" ")) {
            firstWord = firstWord.substring(0, firstWord.indexOf(" "));
            holder.notification_timestamp.setText(firstWord);
        }

        holder.readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.notification_title.setText(model.notification_description);
                holder.readmore.setVisibility(View.GONE);

                Call<Model> call = BaseClass
                        .getInstance()
                        .getApi()
                        .GetNotificationsMark(AppData.id, Integer.parseInt(model.notification_id));

                call.enqueue(new Callback<Model>() {
                    @Override
                    public void onResponse(Call<Model> call, Response<Model> response) {
                        if (response.isSuccessful()) {
                            Model model = response.body();
                            if (model.success.equals("1")) {
                                holder.cardView.setBackgroundResource(R.color.white);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {
                        Toast.makeText(context, "No Response", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog favoriteDialogue = new Dialog(context);
                favoriteDialogue.setContentView(R.layout.permission_dialogue);
                favoriteDialogue.setCancelable(false);
                TextView txtText = favoriteDialogue.findViewById(R.id.txtText);
                TextView yes = favoriteDialogue.findViewById(R.id.Yes);
                TextView no = favoriteDialogue.findViewById(R.id.No);
                txtText.setText("Do you want to delete this Notification?");
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RemoveNotification(model,holder);
                        favoriteDialogue.dismiss();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        favoriteDialogue.dismiss();
                    }
                });
                favoriteDialogue.show();
            }
        });
    }

    private void RemoveNotification(Model model, ViewHolder holder) {
        Call<Model> call = BaseClass
                .getInstance()
                .getApi()
                .GetNotificationsDelete(AppData.id, Integer.parseInt(model.notification_id));

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response.isSuccessful()) {
                    Model model = response.body();
                    if (model.success.equals("1")) {
                        Toast.makeText(context, "Notification has been deleted", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(context, "No Response", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image_status;
        TextView notification_type, notification_title, notification_timestamp, readmore, delete;
        LinearLayout image_status_background;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_status = itemView.findViewById(R.id.image_status);
            image_status_background = itemView.findViewById(R.id.image_status_background);
            notification_type = itemView.findViewById(R.id.notification_type);
            notification_title = itemView.findViewById(R.id.notification_title);
            notification_timestamp = itemView.findViewById(R.id.notification_timestamp);
            readmore = itemView.findViewById(R.id.readmore);
            delete = itemView.findViewById(R.id.delete);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
