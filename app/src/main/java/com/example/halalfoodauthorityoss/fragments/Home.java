package com.example.halalfoodauthorityoss.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.adapter.ComplaintAdapter;
import com.example.halalfoodauthorityoss.complaint.Complaint_Details;
import com.example.halalfoodauthorityoss.complaint.MyComplaints;
import com.example.halalfoodauthorityoss.model.Model;
import com.example.halalfoodauthorityoss.model.SearchResponseModel;
import com.example.halalfoodauthorityoss.searchresult.SearchResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {

    EditText edtSearch;
    ImageView icSearch,icFilter;
    String category = " ";

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.home, container, false);
        edtSearch=view.findViewById(R.id.edtSearch);
        icSearch=view.findViewById(R.id.icSearch);
        icFilter=view.findViewById(R.id.icFilter);

        icSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SearchResult.class));
            }
        });

        icFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog searchDialogue = new Dialog(getActivity());
                searchDialogue.setContentView(R.layout.search_dialogue);
                EditText edtSearch = searchDialogue.findViewById(R.id.edtSearch);
                TextView btnSearch = searchDialogue.findViewById(R.id.btnSearch);
                RadioGroup radioGroup = searchDialogue.findViewById(R.id.radioGroup);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.Resturant:
                                category = "Resturant";
                                Toast.makeText(getActivity(), "" + category, Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.Milk:
                                category = "Milk";
                                Toast.makeText(getActivity(), "" + category, Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.Bakery:
                                category = "Bakery";
                                Toast.makeText(getActivity(), "" + category, Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.Meat:
                                category = "Meat";
                                Toast.makeText(getActivity(), "" + category, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
                searchDialogue.setCancelable(true);

                /*RatingBar ratingBar = rankDialog.findViewById(R.id.dialog_ratingbar);
                TextView feedback = rankDialog.findViewById(R.id.feedback);
                RadioGroup radioGroup = rankDialog.findViewById(R.id.radioGroup);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.no:
                                satisfaction = 0;
                                Toast.makeText(getActivity(), "" + satisfaction, Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.yes:
                                satisfaction = 1;
                                Toast.makeText(getActivity(), "" + satisfaction, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
                TextView cancel = rankDialog.findViewById(R.id.cancel);
                TextView submit = rankDialog.findViewById(R.id.submit);*/


                /*submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String Feedback = feedback.getText().toString().trim();
                        if (satisfaction == 111 || rating_value == 0) {
                            Toast.makeText(getActivity(), "Rating cann't be null!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        int cust_id = Integer.parseInt(model.cust_id);
                        int cid = Integer.parseInt(model.cid);
                        Call<Model> call = BaseClass
                                .getInstance()
                                .getApi()
                                .ComplaintRating(rating_value, cust_id, cid, Feedback, satisfaction);
                        call.enqueue(new Callback<Model>() {
                            @Override
                            public void onResponse(Call<Model> call, Response<Model> response) {
                                Model model = response.body();
                                if (response.isSuccessful()) {
                                    if (model.getSuccess().equals("1")) {
                                        int rating_value = 0;
                                        int satisfaction = 111;
                                        Toast.makeText(getActivity(), "Feeback Submit", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Not Submitted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Model> call, Throwable t) {
                                Toast.makeText(getActivity(), "out", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rankDialog.dismiss();
                    }
                });*/
                searchDialogue.show();
            }
        });

        return view;    }
}