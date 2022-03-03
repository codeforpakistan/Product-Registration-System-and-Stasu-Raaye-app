package com.example.halalfoodauthorityoss.loginsignupforgot;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.model.Model;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Forgot_Passoword extends AppCompatActivity {

    private static Pattern CNIC_PATTERN, AFG_CNIC_PATTERN;
    TextView btnverify;
    TextInputEditText edtcnic;
    CheckBox checkBox;
    ImageView ic_back;
    String cnic = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cnic__verification);

        initi();

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
                startActivity(new Intent(Forgot_Passoword.this, Login.class));
                finish();
            }
        });

        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                        Forgot();
                    }
                }
                if (checkBox.isChecked()) {
                    if (!AFG_CNIC_PATTERN.matcher(cnic).matches()) {
                        edtcnic.setError("Invalid CNIC Number");
                        return;
                    } else {
                        Forgot();
                    }
                }
            }
        });

    }

    private void initi() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btnverify = findViewById(R.id.btnverify);
        edtcnic = findViewById(R.id.edtcnic);
        checkBox = findViewById(R.id.checkBox);
        ic_back = findViewById(R.id.ic_back);

        CNIC_PATTERN = Pattern.compile("^" + "[0-9]{5}-[0-9]{7}-[0-9]{1}" + "$");
        AFG_CNIC_PATTERN = Pattern.compile("^" + "[0-9]{4}-[0-9]{4}-[0-9]{5}" + "$");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Forgot_Passoword.this, Login.class));
        finish();
    }

    private void DialogBOX() {
        AlertDialog alertDialog = new AlertDialog.Builder(Forgot_Passoword.this).create();
        alertDialog.setTitle("Alert!");
        alertDialog.setMessage("Password Has Been Sent On Your Number!");
        alertDialog.setCancelable(false);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Forgot_Passoword.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        alertDialog.show();
    }

    public void Forgot() {
        Call<Model> call = BaseClass
                .getInstance()
                .getApi()
                .Forgot(cnic);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Model model = response.body();
                if (!model.equals(null)) {
                    if (model.getSuccess().equals("Valid user")) {
                        Toast.makeText(Forgot_Passoword.this, "Mobile Number:" + model.getC_mobile(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(Forgot_Passoword.this, "Password:" + model.getCpass(), Toast.LENGTH_SHORT).show();
                        DialogBOX();
                    } else {
                        Toast.makeText(Forgot_Passoword.this, "CNIC Doesn't Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(Forgot_Passoword.this, "I am Out", Toast.LENGTH_SHORT).show();
            }
        });
    }
}