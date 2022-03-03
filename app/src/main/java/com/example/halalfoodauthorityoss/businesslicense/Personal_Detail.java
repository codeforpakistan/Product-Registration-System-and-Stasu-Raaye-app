package com.example.halalfoodauthorityoss.businesslicense;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.CoreActivity;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.model.Model;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Personal_Detail extends AppCompatActivity {

    private static Pattern CNIC_PATTERN, AFG_CNIC_PATTERN;
    TextView btnNext;
    TextInputEditText edtname, edtcnic, edtcontact, edtfathername;
    CheckBox checkBox;
    String cnic = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        initialization();

        edtcnic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String cnicNo = edtcnic.getText().toString().trim();
                if (!checkBox.isChecked()) {
                    if (cnicNo.length() == 13) {
                        cnic = cnicNo.substring(0, 5) + "-" + cnicNo.substring(5, 12) + "-" + cnicNo.substring(12);
                        if (CNIC_PATTERN.matcher(cnic).matches()) {
                            CheckCNIC(cnic);
                            return;
                        } else {
                            edtcnic.setError("Invalid Cnic");
                        }
                    } else if (cnicNo.length() > 13) {
                        edtcnic.setError("Invalid Cnic");
                        return;
                    }
                }
                if (checkBox.isChecked()) {
                    if (cnicNo.length() == 13) {
                        cnic = cnicNo.substring(0, 4) + "-" + cnicNo.substring(4, 8) + "-" + cnicNo.substring(8, 13);
                        if (AFG_CNIC_PATTERN.matcher(cnic).matches()) {
                            CheckCNIC(cnic);
                            return;
                        } else {
                            edtcnic.setError("Invalid Cnic");
                        }
                    } else if (cnicNo.length() > 13) {
                        edtcnic.setError("Invalid Cnic");
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtname.getText().toString().trim();
                String fathername = edtfathername.getText().toString().trim();
                String contact = edtcontact.getText().toString().trim();
                String cnicNo = edtcnic.getText().toString().trim();

                if (cnicNo.length()<13 || cnicNo.length()>13 || cnicNo.equals(""))
                {
                    edtcnic.setError("Invalid CNIC");
                    return;
                }
                if (!checkBox.isChecked()) {
                    cnic = cnicNo.substring(0, 5) + "-" + cnicNo.substring(5, 12) + "-" + cnicNo.substring(12);
                } else {
                    cnic = cnicNo.substring(0, 4) + "-" + cnicNo.substring(4, 8) + "-" + cnicNo.substring(8, 13);
                }

                if (!checkBox.isChecked()) {
                    if (!CNIC_PATTERN.matcher(cnic).matches()) {
                        edtcnic.setError("Invalid CNIC Number");
                        return;
                    } else {
                        CheckCNIC(cnic);
                    }
                }
                if (checkBox.isChecked()) {
                    if (!AFG_CNIC_PATTERN.matcher(cnic).matches()) {
                        edtcnic.setError("Invalid CNIC Number");
                        return;
                    } else {
                        CheckCNIC(cnic);
                    }
                }

                if (name.equals("")) {
                    edtname.setError("Please Enter Name");
                    return;
                }
                if (fathername.equals("")) {
                    edtfathername.setError("Please Enter Father Name");
                    return;
                }
                if (contact.length() < 11 || contact.length() > 11) {
                    edtcontact.setError("Invalid Mobile Number");
                    return;
                }

                Intent intent = new Intent(Personal_Detail.this, Bussiness_Details.class);
                intent.putExtra("name", name);
                intent.putExtra("fathername", fathername);
                intent.putExtra("contact", contact);
                intent.putExtra("cnic", cnic);
                intent.putExtra("check", "0");

                startActivity(intent);
            }
        });

    }

    private void CheckCNIC(String cnic) {
        Call<Model> call = BaseClass
                .getInstance()
                .getApi()
                .GetCNIC(cnic);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Model model = response.body();
                if (response.isSuccessful()) {
                    if (model.getSuccess().equals("1")) {
                        String userid = model.getUserId();
                        AlertDialog alertDialog = new AlertDialog.Builder(Personal_Detail.this).create();
                        alertDialog.setTitle("Business License!");
                        alertDialog.setMessage("Owner details exist add business Information");
                        alertDialog.setCancelable(false);
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Personal_Detail.this, Bussiness_Details.class);
                                intent.putExtra("userid", userid);
                                intent.putExtra("check", "1");
                                startActivity(intent);
                            }
                        });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                }
                if (model.getSuccess().equals("0")) {
                    Toast.makeText(Personal_Detail.this, "cnic not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.d("errror3", call.request().toString());
                Toast.makeText(Personal_Detail.this, "No Response", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initialization() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btnNext = findViewById(R.id.btnNext);
        edtname = findViewById(R.id.edtname);
        edtfathername = findViewById(R.id.edtfathername);
        edtcontact = findViewById(R.id.edtcontact);
        edtcnic = findViewById(R.id.edtcnic);
        checkBox = findViewById(R.id.checkBox);

        CNIC_PATTERN = Pattern.compile("^" + "[0-9]{5}-[0-9]{7}-[0-9]{1}" + "$");
        AFG_CNIC_PATTERN = Pattern.compile("^" + "[0-9]{4}-[0-9]{4}-[0-9]{5}" + "$");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Personal_Detail.this, CoreActivity.class));
        finish();
    }
}
