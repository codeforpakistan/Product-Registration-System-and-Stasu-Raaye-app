package com.example.halalfoodauthorityoss.loginsignupforgot;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.model.Model;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sign_Up extends AppCompatActivity {

    private static Pattern CNIC_PATTERN, AFG_CNIC_PATTERN, PASSWORD_PATTERN;
    TextInputEditText edtname, edtcnic, edtnumber, edtpassword, edtconfirmpassword;
    TextView register;
    CheckBox checkBox;
    String name, cnic = "", number, password, confirmpassword;
    ImageView ic_back;
    String districtID;
    ProgressDialog progressDialog;
    TextInputLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

        initialization();

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkBox.isChecked())
                {
                    layout.setHint("AFG No(without-dashes)");
                }
                else {
                    layout.setHint("CNIC(without-dashes)");
                }
            }
        });


        edtnumber.setText(getIntent().getStringExtra("number"));

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

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Sign_Up.this, Login.class));
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edtname.getText().toString().trim();
                String cnicNo = edtcnic.getText().toString().trim();
                number = edtnumber.getText().toString().trim();
                password = edtpassword.getText().toString().trim();
                confirmpassword = edtconfirmpassword.getText().toString().trim();

                if (cnicNo.length() < 13 || cnicNo.length() > 13 || cnicNo.equals("")) {
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
                    }
                }
                if (checkBox.isChecked()) {
                    if (!AFG_CNIC_PATTERN.matcher(cnic).matches()) {
                        edtcnic.setError("Invalid CNIC Number");
                        return;
                    }
                }
                if (name.equals("")) {
                    edtname.setError("Please Enter Name");
                    return;
                }
                if (number.length() < 11 || number.length() > 11) {
                    edtnumber.setError("Invalid Mobile Number");
                    return;
                }
                if (districtID == "0") {
                    Toast.makeText(Sign_Up.this, "Select District", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!PASSWORD_PATTERN.matcher(password).matches()) {
                    edtpassword.setError("Minimum Length 6, Must Contain Latters and Digits");
                    return;
                }
                if (!confirmpassword.equals(password)) {
                    edtconfirmpassword.setError("Password Does Not Matched");
                    return;
                }
                progressDialog.show();

                Call<Model> call = BaseClass
                        .getInstance()
                        .getApi()
                        .Sign_Up(cnic, password, name, getIntent().getStringExtra("number"));

                call.enqueue(new Callback<Model>() {
                    @Override
                    public void onResponse(Call<Model> call, Response<Model> response) {
                        Model model = response.body();
                        if (response.isSuccessful()) {
                            if (model.getSuccess().equals("1")) {
                                progressDialog.dismiss();
                                DialogBOX();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(Sign_Up.this, "This User Already Registered", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(Sign_Up.this, "No Response", Toast.LENGTH_SHORT).show();
                        Log.d("errror", call.toString());
                        Log.d("errror1", call.request().toString());
                    }
                });
            }
        });

    }

    private void DialogBOX() {

        Dialog dialoguebox = new Dialog(Sign_Up.this);
        dialoguebox.setContentView(R.layout.dialogue_box);
        dialoguebox.setCancelable(true);
        TextView message = dialoguebox.findViewById(R.id.txtmessage);
        TextView ok = dialoguebox.findViewById(R.id.ok);
        message.setText("Your Profile has been Created.");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialoguebox.dismiss();
                Intent intent = new Intent(Sign_Up.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        dialoguebox.show();
    }

    private void initialization() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        edtname = findViewById(R.id.edtname);
        edtcnic = findViewById(R.id.edtcnic);
        edtnumber = findViewById(R.id.edtnumber);
        edtpassword = findViewById(R.id.edtpassword);
        edtconfirmpassword = findViewById(R.id.edtconfirmpassword);
        register = findViewById(R.id.btnregister);
        checkBox = findViewById(R.id.checkBox);
        layout = findViewById(R.id.layout);

        ic_back = findViewById(R.id.ic_back);

        PASSWORD_PATTERN =
                Pattern.compile("^" +
                        "(?=.*[0-9])" +         //at least 1 digit
                        "(?=.*[a-zA-Z])" +      //any letter
                        "(?=\\S+$)" +           //no white spaces
                        ".{6,}" +               //at least 6 characters
                        "$");
        CNIC_PATTERN = Pattern.compile("^" + "[0-9]{5}-[0-9]{7}-[0-9]{1}" + "$");
        AFG_CNIC_PATTERN = Pattern.compile("^" + "[0-9]{4}-[0-9]{4}-[0-9]{5}" + "$");
    }

}