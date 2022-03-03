package com.example.halalfoodauthorityoss.businesslicense;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.CoreActivity;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.model.AppData;
import com.example.halalfoodauthorityoss.model.Model;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Bussiness_Details extends FragmentActivity implements OnMapReadyCallback {

    Spinner districtSpinner, categorySpinner;
    TextView btnNext;
    TextInputEditText edtBusinessName, edtBusinessAdress;
    List<String> ListDistrictName;
    List<String> ListDistrictID;
    String districtID;
    String districtName;

    List<String> ListCategoryName;
    List<String> ListCategoryId;
    String categoryID;

    ImageView ic_back, currentLocation, icSearch;
    GoogleMap mMap;
    TextInputEditText edtlocation;
    double latitude, longitude;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_details);

        initialization();
        displayDistrict();
        displayCategory();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    locationManager = (LocationManager) getSystemService(Bussiness_Details.this.LOCATION_SERVICE);
                }
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //Write Function To enable gps
                    OnGPS();
                } else {
                    getLocation();
                }
            }
        });

        icSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Location = edtlocation.getText().toString().trim();
                Geocoder geocoder = new Geocoder(Bussiness_Details.this);
                try {
                    mMap.clear();
                    List<Address> AddressList = geocoder.getFromLocationName(Location, 1);
                    if (AddressList.size() != 0) {
                        Address Address = AddressList.get(0);
                        latitude = Address.getLatitude();
                        longitude = Address.getLongitude();
                        LatLng latLng = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(latLng).title(Location));
                        // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15), 2500, null);
                    } else {
                        Toast.makeText(Bussiness_Details.this, "Invalid Address Location", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Bussiness_Details.this, Personal_Detail.class));
            }
        });

        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                districtID = ListDistrictID.get(i);
                districtName = ListDistrictName.get(i);
                if (!districtName.equals("Select District")) {
                    Geocoder geocoder = new Geocoder(Bussiness_Details.this);
                    try {
                        mMap.clear();
                        List<Address> AddressList = geocoder.getFromLocationName(districtName, 1);
                        if (AddressList.size() != 0) {
                            Address Address = AddressList.get(0);
                            latitude = Address.getLatitude();
                            longitude = Address.getLongitude();
                            LatLng latLng = new LatLng(latitude, longitude);
                            mMap.addMarker(new MarkerOptions().position(latLng).title(districtName));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15), 2500, null);
                        } else {
                            Toast.makeText(Bussiness_Details.this, "Invalid Address Location", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoryID = ListCategoryId.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Businessname = edtBusinessName.getText().toString().trim();
                String BusinessAddress = edtBusinessAdress.getText().toString().trim();
                if (Businessname.equals("")) {
                    Toast.makeText(Bussiness_Details.this, "Please Enter Business Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (categoryID.equals("0") && districtID.equals("0")) {
                    Toast.makeText(Bussiness_Details.this, "Select Category and District", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (BusinessAddress.equals("")) {
                    Toast.makeText(Bussiness_Details.this, "Please Enter Business Adddress", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (getIntent().getStringExtra("check").equals("1")) {
                    String lat1 = String.valueOf(latitude);
                    String lng1 = String.valueOf(longitude);
                    float lat = Float.parseFloat(lat1);
                    float lng = Float.parseFloat(lng1);
                    int CategoryID = Integer.parseInt(categoryID);
                    int DistrictID = Integer.parseInt(districtID);
                    int userid = Integer.parseInt(getIntent().getStringExtra("userid"));
                    Call<Model> call = BaseClass
                            .getInstance()
                            .getApi()
                            .Add_Business(Businessname, CategoryID, DistrictID, BusinessAddress, lat, lng, userid, AppData.id);
                    call.enqueue(new Callback<Model>() {
                        @Override
                        public void onResponse(Call<Model> call, Response<Model> response) {
                            Model model = response.body();
                            if (response.isSuccessful()) {
                                if (model.success.equals("1")) {
                                    DialogBOX(model.application_id);
                                   }
                            } else {
                                Toast.makeText(Bussiness_Details.this, "Business Not Registered", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Model> call, Throwable t) {
                            Toast.makeText(Bussiness_Details.this, "out", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
                if (getIntent().getStringExtra("check").equals("0")) {
                    Intent intent = new Intent(Bussiness_Details.this, Profile_Images.class);
                    intent.putExtra("name", getIntent().getStringExtra("name"));
                    intent.putExtra("cnic", getIntent().getStringExtra("cnic"));
                    intent.putExtra("contact", getIntent().getStringExtra("contact"));
                    intent.putExtra("fathername", getIntent().getStringExtra("fathername"));
                    intent.putExtra("businessname", Businessname);
                    intent.putExtra("categoryid", categoryID);
                    intent.putExtra("districtid", districtID);
                    intent.putExtra("businessaddress", BusinessAddress);
                    String lat = String.valueOf(latitude);
                    String lng = String.valueOf(longitude);
                    intent.putExtra("latitude", lat);
                    intent.putExtra("longitude", lng);
                    startActivity(intent);
                }
            }
        });
    }

    private void initialization() {
        //  getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ic_back = findViewById(R.id.ic_back);
        btnNext = findViewById(R.id.btnNext);
        edtBusinessName = findViewById(R.id.edtBusinessName);
        edtBusinessAdress = findViewById(R.id.edtBusinessAddress);
        //  edtlatitude=findViewById(R.id.edtlatitude);
        //  edtlongitude=findViewById(R.id.edtlongitude);
        edtlocation = findViewById(R.id.edtlocation);
        currentLocation = findViewById(R.id.currentLocation);
        icSearch = findViewById(R.id.icSearch);

        districtSpinner = findViewById(R.id.districtSpinner);
        ListDistrictName = new ArrayList<String>();
        ListDistrictID = new ArrayList<String>();
        ListDistrictName.add("Select District");
        ListDistrictID.add("0");

        categorySpinner = findViewById(R.id.categorySpinner);
        ListCategoryName = new ArrayList<String>();
        ListCategoryId = new ArrayList<String>();
        ListCategoryName.add("Select Category");
        ListCategoryId.add("0");
    }

    private void displayDistrict() {
        Call<List<Model>> call = BaseClass
                .getInstance()
                .getApi()
                .getDistrict();
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                List<Model> model = response.body();
                int size = model.size();
                if (!model.equals(null)) {
                    for (int i = 0; i < size; i++) {
                        ListDistrictName.add(model.get(i).getcName());
                        ListDistrictID.add(model.get(i).getID());
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Bussiness_Details.this, android.R.layout.simple_spinner_item, ListDistrictName);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        districtSpinner.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(Bussiness_Details.this, "Empty Category", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                Toast.makeText(Bussiness_Details.this, "out", Toast.LENGTH_SHORT).show();
                Log.d("errror2", call.request().toString());

            }
        });
    }

    private void displayCategory() {
        Call<List<Model>> call = BaseClass
                .getInstance()
                .getApi()
                .getCategory();
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                List<Model> model = response.body();
                int size = model.size();
                if (!model.equals(null)) {
                    for (int i = 0; i < size; i++) {

                        ListCategoryName.add(model.get(i).getcName());
                        ListCategoryId.add(model.get(i).getID());
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Bussiness_Details.this, android.R.layout.simple_spinner_item, ListCategoryName);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        categorySpinner.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(Bussiness_Details.this, "Empty Category", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                Toast.makeText(Bussiness_Details.this, "out", Toast.LENGTH_SHORT).show();
                Log.d("errror1", call.request().toString());

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(34.0043, 71.5448);
        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true).title("Marker In Peshawar"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).draggable(true).title("Your Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.getUiSettings().setZoomControlsEnabled(true);
                Toast.makeText(Bussiness_Details.this, "" + latLng, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void OnGPS() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("You need to turn on your GPS for sharing current location!").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {

        //Check Permissions again

        if (ActivityCompat.checkSelfPermission(Bussiness_Details.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Bussiness_Details.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            Location LocationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps != null) {
                double lat = LocationGps.getLatitude();
                double longi = LocationGps.getLongitude();

                latitude = lat;
                longitude = longi;
                LatLngConversion(lat, longi);
                Toast.makeText(this, "First" + latitude + "," + longitude, Toast.LENGTH_SHORT).show();
                // showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
            } else if (LocationNetwork != null) {
                double lat = LocationNetwork.getLatitude();
                double longi = LocationNetwork.getLongitude();

                latitude = lat;
                longitude = longi;
                LatLngConversion(lat, longi);
                Toast.makeText(this, "Second" + latitude + "," + longitude, Toast.LENGTH_SHORT).show();


                //  showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
            } else if (LocationPassive != null) {
                double lat = LocationPassive.getLatitude();
                double longi = LocationPassive.getLongitude();

                latitude = lat;
                longitude = longi;
                LatLngConversion(lat, longi);
                Toast.makeText(this, "Third" + latitude + "," + longitude, Toast.LENGTH_SHORT).show();


                //  showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
            } else {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }

            //Thats All Run Your App
        }
    }

    private void LatLngConversion(double lat, double lng) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
            Toast.makeText(this, "Address:" + address, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void DialogBOX(String application_id) {
        AlertDialog alertDialog = new AlertDialog.Builder(Bussiness_Details.this).create();
        alertDialog.setTitle("Business Registration");
        alertDialog.setMessage("Your Business Has Been Registered! Your Registration No is "+application_id);
        alertDialog.setCancelable(false);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Bussiness_Details.this, CoreActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        alertDialog.show();
    }

}