package com.example.halalfoodauthorityoss.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.fragments.Home;
import com.example.halalfoodauthorityoss.model.Model;
import com.example.halalfoodauthorityoss.searchresult.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class QuickSearchAdapter extends RecyclerView.Adapter<QuickSearchAdapter.ViewHolder> implements Filterable {
    List<Model> list = new ArrayList<>();
    Context context;
    List<Model> filter;

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Model> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(filter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Model item : filter) {
                    if (item.business_name.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public QuickSearchAdapter(List<Model> list, Context context) {
        this.list = list;
        this.context = context;
        filter=new ArrayList<>(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Model model = list.get(position);
        holder.model.setText(model.business_name);
        holder.model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Home.name = model.business_name;
                context.startActivity(new Intent(context, SearchResult.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView model;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            model = itemView.findViewById(R.id.model);
        }
    }
}
