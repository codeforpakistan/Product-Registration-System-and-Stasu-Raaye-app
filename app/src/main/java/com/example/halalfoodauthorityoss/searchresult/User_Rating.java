package com.example.halalfoodauthorityoss.searchresult;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.FileUtils;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.adapter.ImageAdapterDisplay;
import com.example.halalfoodauthorityoss.model.AppData;
import com.example.halalfoodauthorityoss.model.Model;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Rating extends AppCompatActivity {

    public static boolean camera=false;
    RatingBar Premises_Hygiene, Equipment_Hygiene, Staff_Personal_Hygiene, Food_Hygiene, Food_Quality;
    TextInputEditText edtReviews;
    ImageView addimage;
    String premises_Hygiene = " ", equipment_Hygiene = " ", staff_Personal_Hygiene = " ", food_Hygiene = " ", food_Quality = " ";
    TextView btnShare;
    ImageAdapterDisplay adapter;
    ArrayList<Uri> imagesUriArrayList = new ArrayList<>();
    ArrayList<File> imagesNameArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    FileUtils fileUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__rating);

        Initialization();

        ImageView ic_back=findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera=false;
                finish();
            }
        });

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        Premises_Hygiene.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                premises_Hygiene = String.valueOf(rating);
            }

        });
        Equipment_Hygiene.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                equipment_Hygiene = String.valueOf(rating);
            }

        });
        Staff_Personal_Hygiene.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                staff_Personal_Hygiene = String.valueOf(rating);

            }

        });
        Food_Hygiene.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                food_Hygiene = String.valueOf(rating);
            }

        });
        Food_Quality.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                food_Quality = String.valueOf(rating);
            }

        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (premises_Hygiene.equals(" ") || equipment_Hygiene.equals(" ") || staff_Personal_Hygiene.equals(" ") || food_Hygiene.equals(" ") || food_Quality.equals(" ")) {
                    Toast.makeText(User_Rating.this, "Ratings cann't be null", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtReviews.getText().toString().equals("")) {
                    Toast.makeText(User_Rating.this, "Feedback cann't be null", Toast.LENGTH_SHORT).show();
                    return;
                }

                btnShare.setEnabled(false);
                progressDialog.show();

                MultipartBody.Part[] multipartTypedOutput = new MultipartBody.Part[imagesNameArrayList.size()];
                for (int index = 0; index < imagesNameArrayList.size(); index++) {
                    File file = new File(imagesNameArrayList.get(index).getAbsolutePath());
                    RequestBody reqBody = RequestBody.create(MediaType.parse("image"), file);
                    multipartTypedOutput[index] = MultipartBody.Part.createFormData("attachments[]", file.getName(), reqBody);
                }


                Call<Model> call = BaseClass
                        .getInstance()
                        .getApi()
                        .Add_Rating(AppData.id, AppData.business_id, edtReviews.getText().toString().trim(), premises_Hygiene, equipment_Hygiene, staff_Personal_Hygiene, food_Hygiene, food_Quality, multipartTypedOutput);

                call.enqueue(new Callback<Model>() {
                    @Override
                    public void onResponse(Call<Model> call, Response<Model> response) {
                        if (response.isSuccessful()) {
                            Model model = response.body();
                            if (model.success.equals("1")) {
                                progressDialog.dismiss();
                                DialogBOX();
                            } else {
                                Toast.makeText(User_Rating.this, "" + model.message, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                btnShare.setEnabled(true);

                            }
                        } else {
                            Toast.makeText(User_Rating.this, "Not Successful", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            btnShare.setEnabled(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {
                        progressDialog.dismiss();
                        btnShare.setEnabled(true);
                        Toast.makeText(User_Rating.this, "No Response", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void Initialization() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        Premises_Hygiene = findViewById(R.id.Premises_Hygiene);
        Equipment_Hygiene = findViewById(R.id.Equipment_Hygiene);
        Staff_Personal_Hygiene = findViewById(R.id.Staff_Personal_Hygiene);
        Food_Hygiene = findViewById(R.id.Food_Hygiene);
        Food_Quality = findViewById(R.id.Food_Quality);
        edtReviews = findViewById(R.id.edtReviews);
        addimage = findViewById(R.id.addimage);
        btnShare = findViewById(R.id.btnShare);
        recyclerView = findViewById(R.id.recyclerview);

    }

    private void DialogBOX() {
        Dialog dialoguebox = new Dialog(User_Rating.this);
        dialoguebox.setContentView(R.layout.dialogue_box);
        dialoguebox.setCancelable(false);
        TextView txtalert = dialoguebox.findViewById(R.id.txtalert);
        TextView message = dialoguebox.findViewById(R.id.txtmessage);
        TextView ok = dialoguebox.findViewById(R.id.ok);

        txtalert.setText("Thank You!");
        message.setText("Your feedback has been incorporated into the public ratings");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialoguebox.dismiss();
                Feedback_Reviews.fa.finish();
                Intent intent = new Intent(User_Rating.this, Feedback_Reviews.class);
                startActivity(intent);
                camera=false;
                finish();
            }
        });
        dialoguebox.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                camera = true;
                File file = null;
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                final int min = 200000;
                final int max = 800000;
                final int random = new Random().nextInt((max - min) + 1) + min;
                String name = String.valueOf(random);
                try {
                    file = File.createTempFile(
                            "PNG_",
                            ".png",
                            dir
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (file != null) {
                    FileOutputStream fout;
                    try {
                        fout = new FileOutputStream(file);
                        photo.compress(Bitmap.CompressFormat.PNG, 100, fout);
                        fout.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    imagesUriArrayList.clear();
                    imagesNameArrayList.clear();
                    imagesUriArrayList.add(Uri.parse(file.getAbsolutePath()));
                    imagesNameArrayList.add(new File(file.getAbsolutePath()));

                }
            }
            ShowImages();
        } else {
            Toast.makeText(this, "You have not selected image", Toast.LENGTH_SHORT).show();
        }
    }

    private void ShowImages() {
        Log.e("SIZE", imagesUriArrayList.size() + "");
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(User_Rating.this, RecyclerView.VERTICAL, false);
        adapter = new ImageAdapterDisplay(imagesUriArrayList, User_Rating.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(User_Rating.this, 4));
        adapter.notifyDataSetChanged();
    }

    private void selectImage() {
        final CharSequence[] options = {"Take from Camera", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(User_Rating.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take from Camera")) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    launchImageActivity.launch(Intent.createChooser(intent, "Pictures"));
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    ActivityResultLauncher<Intent> launchImageActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data.getClipData() != null) {
                            if (data.getClipData().getItemCount() > 4) {
                                Snackbar snackbar = Snackbar
                                        .make(findViewById(R.id.addimage), "You can not select more than 4 images", Snackbar.LENGTH_LONG)
                                        .setAction("RETRY", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                                intent.setType("image/*");
                                                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                                                launchImageActivity.launch(Intent.createChooser(intent, "Pictures"));
                                            }
                                        });
                                snackbar.setActionTextColor(Color.BLUE);
                                View sbView = snackbar.getView();
                                TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
                                textView.setTextColor(Color.RED);
                                snackbar.show();
                            } else {
                                imagesUriArrayList.clear();
                                imagesNameArrayList.clear();
                                int count = data.getClipData().getItemCount();
                                for (int i = 0; i < count; i++) {
                                    imagesUriArrayList.add(data.getClipData().getItemAt(i).getUri());
                                    String imgPath = fileUtils.getPath(User_Rating.this,
                                            data.getClipData().getItemAt(i).getUri());
                                    File imagefile = new File(imgPath);
                                    imagesNameArrayList.add(new File(imagefile.getAbsolutePath()));
                                    Log.d("imgPath", imgPath);

                                }
                            }
                        } else {
                            imagesUriArrayList.clear();
                            imagesNameArrayList.clear();
                            Uri image_uri = data.getData();
                            try {
                                String imgPath = fileUtils.getPath(User_Rating.this, image_uri);
                                Log.e("FILES", imgPath);

                                if (imgPath != null) {
                                    imagesNameArrayList.add(new File(imgPath));
                                    imagesUriArrayList.add(image_uri);

                                    Toast.makeText(User_Rating.this, "Image picked", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(User_Rating.this, "Image not picked",
                                            Toast.LENGTH_SHORT).show();
                                    //imageEdt.setText("Image");
                                }
                            } catch (Exception e) {
                                Log.i("TAG", "Some exception " + e);
                            }
                        }
                    }

                }
            });

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        camera=false;
        finish();
    }
}