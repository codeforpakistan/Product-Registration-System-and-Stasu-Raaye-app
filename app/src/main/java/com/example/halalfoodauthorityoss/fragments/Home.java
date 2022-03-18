package com.example.halalfoodauthorityoss.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.searchresult.SearchResult;

import java.util.ArrayList;

public class Home extends Fragment {

    public static int category_id=0;
    public static int district_id=0;
    public static String name=" ";
    EditText edtSearch;
    ImageView icSearch, icFilter;
    ArrayList<String> checkboxList = new ArrayList<>();

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

        icSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=edtSearch.getText().toString().trim();

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
                RadioGroup TypeGroup = searchDialogue.findViewById(R.id.radioGroupType);
                RadioGroup radioGroupDistrict = searchDialogue.findViewById(R.id.radioGroupDistrict);
                TypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.Resturant:
                                category_id = 2;
                                Toast.makeText(getActivity(), "" + category_id, Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.Hotel:
                                category_id = 1;
                                Toast.makeText(getActivity(), "" + category_id, Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.Bakery:
                                category_id = 17;
                                Toast.makeText(getActivity(), "" + category_id, Toast.LENGTH_SHORT).show();
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
                        name=edtSearch.getText().toString();
                        Intent intent = new Intent(getActivity(), SearchResult.class);
                        searchDialogue.dismiss();
                        startActivity(intent);
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
}