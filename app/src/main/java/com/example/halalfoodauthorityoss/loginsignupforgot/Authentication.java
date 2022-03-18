package com.example.halalfoodauthorityoss.loginsignupforgot;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.businesslicense.Bussiness_Details;
import com.example.halalfoodauthorityoss.model.Model;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authentication extends AppCompatActivity {

    TextInputLayout layoutOTP;
    TextInputEditText edtNumber, edtOTP;
    TextView btnSendOTP, btnResend, btnVerify;
    String code = "abcdef";
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        Initilization();

        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = edtNumber.getText().toString().trim();
                if (number.isEmpty()) {
                    Toast.makeText(Authentication.this, "Please enter number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (number.length() < 11 || number.length() > 11) {
                    edtNumber.setError("Invalid Mobile Number");
                    return;
                } else {
                    SendOTP(number);
                }
            }
        });
        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = edtNumber.getText().toString().trim();
                if (number.isEmpty()) {
                    Toast.makeText(Authentication.this, "Please enter number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (number.length() < 11 || number.length() > 11) {
                    edtNumber.setError("Invalid Mobile Number");
                    return;
                } else {
                    SendOTP(number);
                }
            }
        });
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtOTP.getText().toString().trim().equals(code)) {
                    Intent intent = new Intent(Authentication.this, Sign_Up.class);
                    intent.putExtra("number", number);
                    startActivity(intent)
                    ;
                } else {
                    Toast.makeText(Authentication.this, "Invalid Code", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void Initilization() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        layoutOTP = findViewById(R.id.layoutOTP);
        edtNumber = findViewById(R.id.edtNumber);
        edtOTP = findViewById(R.id.edtOTP);
        btnSendOTP = findViewById(R.id.btnSendOTP);
        btnResend = findViewById(R.id.btnResend);
        btnVerify = findViewById(R.id.btnVerify);
    }

    public void SendOTP(String number) {
        Call<Model> call = BaseClass
                .getInstance()
                .getApi()
                .Authentication(number);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Model model = response.body();
                if (response.isSuccessful()) {
                    if (model.getSuccess().equals("1")) {
                        DialogBOX(number);
                        Toast.makeText(Authentication.this, "" + model.message, Toast.LENGTH_LONG).show();
                        Toast.makeText(Authentication.this, "" + model.otp, Toast.LENGTH_LONG).show();
                        code = model.otp;
                        btnSendOTP.setVisibility(View.GONE);
                        layoutOTP.setVisibility(View.VISIBLE);
                        btnResend.setVisibility(View.VISIBLE);
                        btnVerify.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(Authentication.this, "" + model.message, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(Authentication.this, "No Response", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void DialogBOX(String number) {
        Dialog dialoguebox = new Dialog(Authentication.this);
        dialoguebox.setContentView(R.layout.dialogue_box);
        dialoguebox.setCancelable(true);
        TextView txtalert = dialoguebox.findViewById(R.id.txtalert);
        TextView message = dialoguebox.findViewById(R.id.txtmessage);
        TextView ok = dialoguebox.findViewById(R.id.ok);

        txtalert.setText("OTP Code!");
        message.setText("Code has been sent on your number: " + number);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialoguebox.dismiss();
            }
        });
        dialoguebox.show();
    }
}