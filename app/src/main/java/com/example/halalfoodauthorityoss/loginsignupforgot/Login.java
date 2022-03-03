package com.example.halalfoodauthorityoss.loginsignupforgot;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.CoreActivity;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.complaint.Complaint;
import com.example.halalfoodauthorityoss.model.AppData;
import com.example.halalfoodauthorityoss.model.LoginResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.Constants;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private static Pattern CNIC_PATTERN, AFG_CNIC_PATTERN;
    TextView signup, forgot, btnlogin;
    TextInputEditText edtcnic, edtpassword;
    CheckBox checkBox;
    ConstraintLayout mainlayout;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor;
    String cnic="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialization();
        CheckInternet();

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

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cnicNo = edtcnic.getText().toString().trim();
                String password = edtpassword.getText().toString().trim();

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

                if (password.equals("")) {
                    edtpassword.setError("Please Enter Password");
                    return;
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
                LoginFunction(cnic, password, "0");
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Sign_Up.class));
                finish();
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Forgot_Passoword.class));
                finish();
            }
        });
    }

    private void CheckInternet() {
        if (isOnline()) {
            CheckPrefFOrLogin();
        }
        else {
            AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();
            alertDialog.setTitle("Info");
            alertDialog.setMessage("Internet not available, Check your internet connectivity and try again");
            alertDialog.setCancelable(false);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    AgainCheck();
                }
            });
            alertDialog.show();
        }
    }

    private void AgainCheck() {
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CheckInternet();
            }
        },7000);
    }

    private boolean isOnline() {
        boolean connected = false;
        ConnectivityManager connectivityManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
        }
        return connected;
    }


    private void CheckPrefFOrLogin() {
        progressDialog.show();
        sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
        final String CNIC = sharedPreferences.getString("CNIC", "Nothing");
        final String PASSWORD = sharedPreferences.getString("PASSWORD", "Nothing");
        if (!CNIC.equals("Nothing") && !PASSWORD.equals("Nothing")) {
            LoginFunction(CNIC, PASSWORD, "1");
        } else {
            progressDialog.dismiss();
            mainlayout.setVisibility(View.VISIBLE);
        }
    }

    private void LoginFunction(String cnic, String password, String check) {
        progressDialog.show();
        Call<LoginResponse> call = BaseClass
                .getInstance()
                .getApi()
                .Login(cnic, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (!loginResponse.equals(null)) {
                        if (!loginResponse.success.equals("0")) {
                            AppData.name = loginResponse.user_data.getName();
                            AppData.cnic = loginResponse.user_data.getCnic();
                            AppData.mobileNumber = loginResponse.user_data.getC_mobile();
                            if (loginResponse.user_data.getPath()!=null){
                                AppData.photo=loginResponse.user_data.getPath();
                            }
                            if (loginResponse.user_data.getAddress()!=null){
                                AppData.address = loginResponse.user_data.getAddress();
                            }
                            AppData.password = loginResponse.user_data.getCpass();
                            AppData.id = Integer.parseInt(loginResponse.user_data.getUser_id());
                            startActivity(new Intent(Login.this, CoreActivity.class));
                            if (check.equals("0")) {
                                SharedprefUser(cnic, password);
                            }
                            progressDialog.dismiss();
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "Invalid CNIC or Password", Toast.LENGTH_LONG).show();
                        }
                    }
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, "Not Successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Login.this, "out", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initialization() {
        mainlayout = findViewById(R.id.mainlayout);
        mainlayout.setVisibility(View.INVISIBLE);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        signup = findViewById(R.id.btnsignup);
        forgot = findViewById(R.id.forgot);
        edtcnic = findViewById(R.id.edtcnic);
        edtpassword = findViewById(R.id.edtpassword);
        btnlogin = findViewById(R.id.btnlogin);
        checkBox = findViewById(R.id.checkBox);

        CNIC_PATTERN = Pattern.compile("^" + "[0-9]{5}-[0-9]{7}-[0-9]{1}" + "$");
        AFG_CNIC_PATTERN = Pattern.compile("^" + "[0-9]{4}-[0-9]{4}-[0-9]{5}" + "$");
    }

    private void SharedprefUser(String cnic, String password) {
        sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("CNIC", cnic);
        editor.putString("PASSWORD", password);
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}