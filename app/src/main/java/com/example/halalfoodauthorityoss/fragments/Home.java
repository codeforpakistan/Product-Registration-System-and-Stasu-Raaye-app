package com.example.halalfoodauthorityoss.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.ViewPager;

import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.Splash_Screen;
import com.example.halalfoodauthorityoss.adapter.QuickSearchAdapter;
import com.example.halalfoodauthorityoss.adapter.SliderAdapter;
import com.example.halalfoodauthorityoss.businesslicense.Personal_Detail;
import com.example.halalfoodauthorityoss.complaint.Complaint;
import com.example.halalfoodauthorityoss.productregistration.ProductRegistration;
import com.example.halalfoodauthorityoss.searchresult.SearchResult;

import java.util.Timer;
import java.util.TimerTask;

public class Home extends Fragment {

    public static int category_id = 0;
    public static int district_id = 0;
    public static String name = " ";
    EditText edtSearch;
    ImageView icSearch, icFilter;
    LinearLayout businessregister, productregister, complaint, training;
    ViewPager viewPager;
    RecyclerView recyclerView;
    QuickSearchAdapter quickSearchAdapter;
    int currentPage = 0;
    Timer timer;

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

        View view = inflater.inflate(R.layout.home, container, false);
        edtSearch = view.findViewById(R.id.edtSearch);
        icSearch = view.findViewById(R.id.icSearch);
        icFilter = view.findViewById(R.id.icFilter);

        businessregister = view.findViewById(R.id.businessregister);
        productregister = view.findViewById(R.id.productregister);
        complaint = view.findViewById(R.id.complaint);
        training = view.findViewById(R.id.training);
        recyclerView = view.findViewById(R.id.recyclerview);
        viewPager = view.findViewById(R.id.viewPager);

        recyclerView.setHasFixedSize(true);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        quickSearchAdapter = new QuickSearchAdapter(Splash_Screen.modelList, getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(quickSearchAdapter);

        SetSLiderImages();

        edtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                if (cs.length() >= 3) {
                    recyclerView.setVisibility(View.VISIBLE);
                    quickSearchAdapter.getFilter().filter(cs);
                } else {
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
        businessregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Personal_Detail.class));
            }
        });
        productregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProductRegistration.class));

            }
        });
        complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Complaint.class));
            }
        });
        training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog RenewBusinessDialogue = new Dialog(getActivity());
                RenewBusinessDialogue.setContentView(R.layout.permission_dialogue);
                RenewBusinessDialogue.setCancelable(false);
                TextView txtText = RenewBusinessDialogue.findViewById(R.id.txtText);
                txtText.setText("We are working on this feature.");
                TextView yes = RenewBusinessDialogue.findViewById(R.id.Yes);
                yes.setText("OK");
                TextView no = RenewBusinessDialogue.findViewById(R.id.No);
                no.setVisibility(View.GONE);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RenewBusinessDialogue.dismiss();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RenewBusinessDialogue.dismiss();
                    }
                });
                RenewBusinessDialogue.show();
            }
        });
        icSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edtSearch.getText().toString().trim();
                if (name.equals("")) {
                    Toast.makeText(getActivity(), "Search cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(getContext(), SearchResult.class));
                }
            }
        });
        icFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog searchDialogue = new Dialog(getActivity());
                searchDialogue.setContentView(R.layout.search_dialogue);
                EditText edtSearch = searchDialogue.findViewById(R.id.edtSearch);
                TextView btnSearch = searchDialogue.findViewById(R.id.btnSearch);
                RadioGroup TypeGroup = searchDialogue.findViewById(R.id.radioGroupType);
                RadioGroup radioGroupDistrict = searchDialogue.findViewById(R.id.radioGroupDistrict);
                TypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.Resturant:
                                category_id = 2;
                                break;
                            case R.id.Hotel:
                                category_id = 1;
                                break;
                            case R.id.Bakery:
                                category_id = 17;
                                break;
                        }
                    }
                });
                searchDialogue.setCancelable(true);
                /*districtRadioButton*/
                radioGroupDistrict.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.Abbottabad:
                                district_id = 1;
                                break;
                            case R.id.Bannu:
                                district_id = 3;
                                break;
                            case R.id.DI_Khan:
                                district_id = 4;
                                break;
                            case R.id.Kohat:
                                district_id = 5;
                                break;
                            case R.id.Mardan:
                                district_id = 6;
                                break;
                            case R.id.Peshawar:
                                district_id = 7;
                                break;
                            case R.id.Swat:
                                district_id = 8;
                                break;
                            case R.id.Battagram:
                                district_id = 9;
                                break;
                            case R.id.Buner:
                                district_id = 10;
                                break;
                            case R.id.Upper_Chitral:
                                district_id = 11;
                                break;
                            case R.id.Charsadda:
                                district_id = 12;
                                break;
                            case R.id.Dera_Ismail_Khan:
                                district_id = 13;
                                break;
                            case R.id.Hangu:
                                district_id = 14;
                                break;
                            case R.id.Haripur:
                                district_id = 15;
                                break;
                            case R.id.Karak:
                                district_id = 16;
                                break;
                            case R.id.Kolai_Pallas:
                                district_id = 17;
                                break;
                            case R.id.Upper_Kohistan:
                                district_id = 18;
                                break;
                            case R.id.Lower_Kohistan:
                                district_id = 19;
                                break;
                            case R.id.Lakki_Marwat:
                                district_id = 20;
                                break;
                            case R.id.Lower_Dir:
                                district_id = 21;
                                break;
                            case R.id.Malakand:
                                district_id = 22;
                                break;
                            case R.id.Mansehra:
                                district_id = 23;
                                break;
                            case R.id.Nowshera:
                                district_id = 24;
                                break;
                            case R.id.Shangla:
                                district_id = 25;
                                break;
                            case R.id.Swabi:
                                district_id = 26;
                                break;
                        }
                    }
                });

                btnSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        name = edtSearch.getText().toString();
                        if (!name.equals("")) {
                            if (category_id != 0 || district_id != 0) {
                                Intent intent = new Intent(getActivity(), SearchResult.class);
                                startActivity(intent);
                                searchDialogue.dismiss();
                                return;
                            } else {
                                Toast.makeText(getActivity(), "Select district OR Category", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        if (category_id == 0) {
                            Toast.makeText(getActivity(), "Select Category", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (district_id == 0) {
                            Toast.makeText(getActivity(), "Select District", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            Intent intent = new Intent(getActivity(), SearchResult.class);
                            startActivity(intent);
                            searchDialogue.dismiss();
                        }
                    }
                });

                /*CheckBoxes*/
                /*CheckBox Abbottabad = searchDialogue.findViewById(R.id.Abbottabad);

                CheckBox Bannu = searchDialogue.findViewById(R.id.Bannu);

                CheckBox DI_Khan = searchDialogue.findViewById(R.id.DI_Khan);

                CheckBox Kohat = searchDialogue.findViewById(R.id.Kohat);

                CheckBox Mardan = searchDialogue.findViewById(R.id.Mardan);

                CheckBox Peshawar = searchDialogue.findViewById(R.id.Peshawar);

                CheckBox Swat = searchDialogue.findViewById(R.id.Swat);

                CheckBox Battagram = searchDialogue.findViewById(R.id.Battagram);

                CheckBox Buner = searchDialogue.findViewById(R.id.Buner);

                CheckBox Upper_Chitral = searchDialogue.findViewById(R.id.Upper_Chitral);

                CheckBox Charsadda = searchDialogue.findViewById(R.id.Charsadda);

                CheckBox Dera_Ismail_Khan = searchDialogue.findViewById(R.id.Dera_Ismail_Khan);

                CheckBox Hangu = searchDialogue.findViewById(R.id.Hangu);

                CheckBox Haripur = searchDialogue.findViewById(R.id.Haripur);

                CheckBox Karak = searchDialogue.findViewById(R.id.Karak);

                CheckBox Kolai_Pallas = searchDialogue.findViewById(R.id.Kolai_Pallas);

                CheckBox Upper_Kohistan = searchDialogue.findViewById(R.id.Upper_Kohistan);

                CheckBox Lower_Kohistan = searchDialogue.findViewById(R.id.Lower_Kohistan);

                CheckBox Lakki_Marwat = searchDialogue.findViewById(R.id.Lakki_Marwat);

                CheckBox Lower_Dir = searchDialogue.findViewById(R.id.Lower_Dir);

                CheckBox Malakand = searchDialogue.findViewById(R.id.Malakand);

                CheckBox Mansehra = searchDialogue.findViewById(R.id.Mansehra);

                CheckBox Nowshera = searchDialogue.findViewById(R.id.Nowshera);

                CheckBox Shangla = searchDialogue.findViewById(R.id.Shangla);

                CheckBox Swabi = searchDialogue.findViewById(R.id.Swabi);*/
               /* btnSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkboxList.clear();
                        if (Abbottabad.isChecked()) {
                            checkboxList.add("1");
                        }
                        if (Bannu.isChecked()) {
                            checkboxList.add("3");
                        }
                        if (DI_Khan.isChecked()) {
                            checkboxList.add("4");
                        }
                        if (Kohat.isChecked()) {
                            checkboxList.add("5");
                        }
                        if (Mardan.isChecked()) {
                            checkboxList.add("6");
                        }
                        if (Peshawar.isChecked()) {
                            checkboxList.add("7");
                        }
                        if (Swat.isChecked()) {
                            checkboxList.add("8");
                        }
                        if (Battagram.isChecked()) {
                            checkboxList.add("9");
                        }
                        if (Buner.isChecked()) {
                            checkboxList.add("10");
                        }
                        if (Upper_Chitral.isChecked()) {
                            checkboxList.add("11");
                        }
                        if (Charsadda.isChecked()) {
                            checkboxList.add("12");
                        }
                        if (Dera_Ismail_Khan.isChecked()) {
                            checkboxList.add("13");
                        }
                        if (Hangu.isChecked()) {
                            checkboxList.add("14");
                        }
                        if (Haripur.isChecked()) {
                            checkboxList.add("15");
                        }
                        if (Karak.isChecked()) {
                            checkboxList.add("16");
                        }
                        if (Kolai_Pallas.isChecked()) {
                            checkboxList.add("17");
                        }
                        if (Upper_Kohistan.isChecked()) {
                            checkboxList.add("18");
                        }
                        if (Lower_Kohistan.isChecked()) {
                            checkboxList.add("19");
                        }
                        if (Lakki_Marwat.isChecked()) {
                            checkboxList.add("20");
                        }
                        if (Lower_Dir.isChecked()) {
                            checkboxList.add("21");
                        }
                        if (Malakand.isChecked()) {
                            checkboxList.add("22");
                        }
                        if (Mansehra.isChecked()) {
                            checkboxList.add("23");
                        }
                        if (Nowshera.isChecked()) {
                            checkboxList.add("24");
                        }
                        if (Shangla.isChecked()) {
                            checkboxList.add("25");
                        }
                        if (Swabi.isChecked()) {
                            checkboxList.add("26");
                        }

                        Intent intent = new Intent(getActivity(), SearchResult.class);
                        intent.putStringArrayListExtra("checkboxList", checkboxList);
                        intent.putExtra("category_id", category_id);
                        intent.putExtra("name", edtSearch.getText().toString());
                        startActivity(intent);

                    }
                });*/
                searchDialogue.show();
            }
        });

        return view;
    }

    private void SetSLiderImages() {
        SliderAdapter adapter = new SliderAdapter(getActivity());
        viewPager.setAdapter(adapter);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 4 - 1) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1000, 5000);
    }
}