package com.example.halalfoodauthorityoss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout navigationdrawer;
    LinearLayout navbusiness,navproducts,navcomplaints,navfeedback,navtraining,navdownloads,navaboutus;
    Button navlogout;
    ImageView menu;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

        navbusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

/*
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                //  navigationdrawer.closeDrawer(GravityCompat.START);
                switch (id) {
                    case R.id.navbusiness:
                        Toast.makeText(MainActivity.this, "Business clicked", Toast.LENGTH_SHORT).show();
                    case R.id.navproduct:
                        Toast.makeText(MainActivity.this, "Product clicked", Toast.LENGTH_SHORT).show();
                    case R.id.navcomplaints:
                        Toast.makeText(MainActivity.this, "Complaint clicked", Toast.LENGTH_SHORT).show();
                    default:
                        return true;
                }
            }
        });

*/

        /*menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer();
            }
        });*/
    }

    private void initialization() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        menu=findViewById(R.id.menu);
        navigationdrawer=findViewById(R.id.navigationdrawer);
        navigationView=findViewById(R.id.navigationview);
        navbusiness=navigationView.findViewById(R.id.navSOPs);

       /* navbusiness=findViewById(R.id.navbusiness);
        navproducts=findViewById(R.id.navproduct);
        navcomplaints=findViewById(R.id.navcomplaints);
        navfeedback=findViewById(R.id.navfeedback);
        navtraining=findViewById(R.id.navtraining);
        navdownloads=findViewById(R.id.navdownloads);
        navaboutus=findViewById(R.id.navaboutus);
        navlogout=findViewById(R.id.navlogout);*/

        /*navbusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BusinessRegistration.class));
            }
        });
        navproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProductRegistration.class));
            }
        });*/

    }

   /* @SuppressLint("WrongConstant")
    public void openDrawer() {
        navigationdrawer.openDrawer(Gravity.START);
    }*/
}