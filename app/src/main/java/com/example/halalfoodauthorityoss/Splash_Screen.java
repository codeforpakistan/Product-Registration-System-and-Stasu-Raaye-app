package com.example.halalfoodauthorityoss;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halalfoodauthorityoss.adapter.Search_Business_Adapter;
import com.example.halalfoodauthorityoss.fragments.Home;
import com.example.halalfoodauthorityoss.loginsignupforgot.Login;
import com.example.halalfoodauthorityoss.model.AppData;
import com.example.halalfoodauthorityoss.model.LoginResponse;
import com.example.halalfoodauthorityoss.model.Model;
import com.example.halalfoodauthorityoss.model.RoomModel;
import com.example.halalfoodauthorityoss.model.BusinessNameForRoom;
import com.example.halalfoodauthorityoss.searchresult.SearchResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splash_Screen extends AppCompatActivity {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 7;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        getBusinessNames();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkAndroidVersion();
            }
        }, 2500);
    }

    private void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermissions();
        } else {
        }
    }

    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(Splash_Screen.this, Manifest.permission.CAMERA);
        int wtite = ContextCompat.checkSelfPermission(Splash_Screen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(Splash_Screen.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (wtite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(Splash_Screen.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        } else {
            CheckInternet();
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d("in fragment on request", "CAMERA & WRITE_EXTERNAL_STORAGE READ_EXTERNAL_STORAGE permission granted");
                        CheckInternet();
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Splash_Screen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(Splash_Screen.this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(Splash_Screen.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK("Camera and Storage Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            openAlertDialog();
                            Toast.makeText(Splash_Screen.this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Splash_Screen.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private void openAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Camera and Storage Permission required for this app!");
        alertDialogBuilder.setPositiveButton("Try again",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        checkAndRequestPermissions();
                    }
                });

        alertDialogBuilder.setNegativeButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                i.setData(Uri.parse("package:com.example.halalfoodauthorityoss"));
                startActivity(i);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void CheckInternet() {
        if (isOnline()) {
            CheckPrefFOrLogin();
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(Splash_Screen.this).create();
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
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CheckInternet();
            }
        }, 7000);
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
            LoginFunction(CNIC, PASSWORD);
        } else {
            progressDialog.dismiss();
            Intent intent = new Intent(Splash_Screen.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void LoginFunction(String cnic, String password) {
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
                        if (loginResponse.success.equals("1")) {
                            AppData.name = loginResponse.user_data.getName();
                            AppData.cnic = loginResponse.user_data.getCnic();
                            AppData.mobileNumber = loginResponse.user_data.getC_mobile();
                            if (loginResponse.user_data.getPath() != null) {
                                AppData.photo = loginResponse.user_data.getPath();
                            }
                            if (loginResponse.user_data.getAddress() != null) {
                                AppData.address = loginResponse.user_data.getAddress();
                            }
                            AppData.password = loginResponse.user_data.getCpass();
                            AppData.id = Integer.parseInt(loginResponse.user_data.getUser_id());
                            startActivity(new Intent(Splash_Screen.this, CoreActivity.class));
                            progressDialog.dismiss();
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Splash_Screen.this, "Invalid CNIC or Password", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(Splash_Screen.this, "Not Successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Splash_Screen.this, "No Response", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void getBusinessNames()
    {
        Call<BusinessNameForRoom> call = BaseClass
                .getInstance()
                .getApi()
                .BusinessNameSave("");

        call.enqueue(new Callback<BusinessNameForRoom>() {
            @Override
            public void onResponse(Call<BusinessNameForRoom> call, Response<BusinessNameForRoom> response) {
                if (response.isSuccessful()) {
                    BusinessNameForRoom businessNameForRoom = response.body();
                    List<Model> list = businessNameForRoom.getBusinesses();
                    if (list!=null)
                    {
                        DatabaseClass.getDatabase(getApplicationContext()).getDao().deleteData();
                        int size = list.size();
                        if (businessNameForRoom.success.equals("1")) {
                            for (int i = 0; i < size; i++) {
                                saveData(list.get(i).business_name);
                            }
                            progressDialog.dismiss();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Splash_Screen.this, "No Record Found", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(Splash_Screen.this, "No Record Found", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Splash_Screen.this, "Not Successful", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<BusinessNameForRoom> call, Throwable t) {
                Toast.makeText(Splash_Screen.this, "No Response", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Log.d("aaaaaa", call.request().toString());
            }
        });
    }

    private void saveData(String business_name) {
        RoomModel model = new RoomModel();
        model.setName(business_name);
        DatabaseClass.getDatabase(getApplicationContext()).getDao().insertAllData(model);
    }
}