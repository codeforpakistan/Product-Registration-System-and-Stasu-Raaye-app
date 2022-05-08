package com.example.halalfoodauthorityoss;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.halalfoodauthorityoss.adapter.PageAdapter;
import com.example.halalfoodauthorityoss.loginsignupforgot.Login;
import com.example.halalfoodauthorityoss.model.AppData;
import com.example.halalfoodauthorityoss.model.Model;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CoreActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor;

    DrawerLayout navigationdrawer;
    LinearLayout navSOPs, navActs, navReports, navAboutus;
    Button navlogout;
    ImageView menu, notification;
    NavigationView navigationView;
    CircleImageView profilePic;
    TextView txtName, txtNumber, notification_count;

    TabLayout layout;
    ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_home,
            R.drawable.ic_profile,
            R.drawable.ic_favorite
    };
    public static CoreActivity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);

        initialization();

        layout = findViewById(R.id.layout);
        viewPager = findViewById(R.id.show);

        for (int i = 0; i < 3; i++) {
            layout.getTabAt(i).setIcon(tabIcons[i]);
        }

        final PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), layout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0 || tab.getPosition() == 1 || tab.getPosition() == 2) {
                    pageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(layout));

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CoreActivity.this, Notifications.class));
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer();
            }
        });
        navSOPs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoreActivity.this, Web_View.class);
                intent.putExtra("url", "https://halal.kpfsa.gov.pk/index.php/sops/");
                startActivity(intent);
            }
        });
        navActs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoreActivity.this, Web_View.class);
                intent.putExtra("url", "https://halal.kpfsa.gov.pk/wp-content/uploads/2022/03/act.pdf");
                startActivity(intent);
            }
        });
        navReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoreActivity.this, Web_View.class);
                intent.putExtra("url", "https://halal.kpfsa.gov.pk/index.php/reports/");
                startActivity(intent);
            }
        });
        navAboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoreActivity.this, Web_View.class);
                intent.putExtra("url", "https://halal.kpfsa.gov.pk/index.php/overview-2/");
                startActivity(intent);
            }
        });
        navlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogOut();
            }
        });
    }

    private void LogOut() {
        sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        AppData.name = null;
        AppData.cnic = null;
        AppData.address = "0";
        AppData.photo = "0";
        AppData.password = null;
        AppData.mobileNumber = null;
        Intent intent = new Intent(CoreActivity.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void initialization() {

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fa = this;
        menu = findViewById(R.id.menu);
        notification = findViewById(R.id.notification);
        notification_count = findViewById(R.id.notification_count);
        Count_Notifications();
        navigationdrawer = findViewById(R.id.navigationdrawer);
        navigationView = findViewById(R.id.navigationview);
        navlogout = navigationView.findViewById(R.id.navlogout);
        navSOPs = navigationView.findViewById(R.id.navSOPs);
        navActs = navigationView.findViewById(R.id.navActs);
        navReports = navigationView.findViewById(R.id.navReports);
        navAboutus = navigationView.findViewById(R.id.navAboutus);
        profilePic = navigationView.findViewById(R.id.profilePic);
        txtName = navigationView.findViewById(R.id.txtName);
        txtNumber = navigationView.findViewById(R.id.txtNumber);

        if (AppData.photo != "0") {
            String path = "" + AppData.photo;
            Glide.with(CoreActivity.this).load(path).into(profilePic);
        } else {
            profilePic.setImageResource(R.drawable.ic_human);
        }
        txtName.setText(AppData.name);
        txtNumber.setText(AppData.mobileNumber);

    }

    private void Count_Notifications() {

        Call<Model> call = BaseClass
                .getInstance()
                .getApi()
                .GetNotificationsCount(AppData.id);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response.isSuccessful()) {
                    Model model = response.body();
                    if (model.success.equals("1")) {
                        if (!model.unseen_notifications.equals("0")) {
                            notification_count.setText(model.unseen_notifications);
                            notification_count.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(CoreActivity.this, "No Response", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

    @SuppressLint("WrongConstant")
    public void openDrawer() {
        navigationdrawer.openDrawer(Gravity.START);
    }
}