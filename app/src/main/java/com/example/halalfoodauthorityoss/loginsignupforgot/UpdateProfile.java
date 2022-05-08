package com.example.halalfoodauthorityoss.loginsignupforgot;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.CoreActivity;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.model.AppData;
import com.example.halalfoodauthorityoss.model.Model;
import com.example.halalfoodauthorityoss.searchresult.Feedback_Reviews;
import com.example.halalfoodauthorityoss.searchresult.User_Rating;
import com.google.android.material.textfield.TextInputEditText;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfile extends AppCompatActivity {

    private final int ImageCropIntentG = 1;
    private final int ImageCropIntentC = 2;
    TextInputEditText edtname, edtcnic, edtnumber, edtpassword, edtAddress;
    TextView btnUpdate;
    LinearLayout layoutupdate;
    CircleImageView profilePic;
    File profileFile = null;
    ProgressDialog progressDialog;
    String name, cnic, number, password, Address;
    ImageView ic_back;

    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        initialization();

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        layoutupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                name = edtname.getText().toString().trim();
                cnic = edtcnic.getText().toString().trim();
                number = edtnumber.getText().toString().trim();
                password = edtpassword.getText().toString().trim();
                Address = edtAddress.getText().toString().trim();

                if (number.length() < 11 || number.length() > 11) {
                    progressDialog.dismiss();
                    edtnumber.setError("Invalid Mobile Number");
                    return;
                }
                if (name.equals("") || cnic.equals("") || number.equals("") || password.equals("") || Address.equals("")) {
                    progressDialog.dismiss();
                    Toast.makeText(UpdateProfile.this, "Field cann't be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                MultipartBody.Part profileimageFile = null;
                if (profileFile != null) {
                    File file = new File(profileFile.getAbsolutePath());
                    RequestBody requestBodyPROFILE = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    profileimageFile = MultipartBody.Part.createFormData("photo", profileFile.getName(), requestBodyPROFILE);
                }
                Call<Model> call = BaseClass
                        .getInstance()
                        .getApi()
                        .UpdateProfile(AppData.id, name, cnic, number, profileimageFile, Address, password);

                call.enqueue(new Callback<Model>() {
                    @Override
                    public void onResponse(Call<Model> call, Response<Model> response) {
                        Model model = response.body();
                        if (response.isSuccessful()) {
                            if (model.success.equals("1")) {
                                progressDialog.dismiss();
                                AppData.name = name;
                                AppData.cnic = cnic;
                                AppData.mobileNumber = number;
                                AppData.password = password;
                                AppData.address = Address;
                                if (profileFile != null) {
                                    AppData.photo = model.image_name;
                                }
                                SharedprefUser(cnic, password);
                                DialogBOX();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(UpdateProfile.this, "Not Success", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(UpdateProfile.this, "Not Successful", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(UpdateProfile.this, "No Response", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    private void selectImage() {
        final CharSequence[] options = {"Take from Camera", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take from Camera")) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, ImageCropIntentC);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_PICK);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), ImageCropIntentG);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void initialization() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        ic_back = findViewById(R.id.ic_back);
        edtname = findViewById(R.id.edtname);
        edtcnic = findViewById(R.id.edtcnic);
        edtnumber = findViewById(R.id.edtnumber);
        edtpassword = findViewById(R.id.edtpassword);
        btnUpdate = findViewById(R.id.btnUpdate);
        edtAddress = findViewById(R.id.edtDistrict);
        layoutupdate = findViewById(R.id.layoutupdate);
        profilePic = findViewById(R.id.profilePic);

        edtname.setText(AppData.name);
        edtcnic.setText(AppData.cnic);
        edtnumber.setText(AppData.mobileNumber);
        if (AppData.address != "0") {
            edtAddress.setText(AppData.address);
        }
        edtpassword.setText(AppData.password);
        if (AppData.photo != "0") {
            String path = "" + AppData.photo;
            Glide.with(UpdateProfile.this).load(path).into(profilePic);
        } else {
            profilePic.setImageResource(R.drawable.ic_human);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ImageCropIntentG) {
            Uri uri = data.getData();
            if (uri != null) {
                startCrop(uri);
            }
        }
        if (resultCode == RESULT_OK && requestCode == ImageCropIntentC) {
            File file = null;
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            try {
                file = File.createTempFile("JPEG_", ".JPEG", dir);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (file != null) {
                FileOutputStream fout;
                try {
                    fout = new FileOutputStream(file);
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, fout);
                    fout.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (file != null) {
                Uri uri = Uri.fromFile(file);
                startCrop(uri);
            }
        } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            Uri uri = Uri.parse(resultUri.getPath());
            if (uri != null) {
                profileFile = new File(String.valueOf(uri));
                profilePic.setImageURI(resultUri);
            }
        }
    }

    public void startCrop(Uri uri) {
        String destinationFileName = new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File file = new File(path, "/" + destinationFileName);
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(file));
        uCrop.withAspectRatio(1, 1);
        uCrop.withAspectRatio(3, 4);
        uCrop.useSourceImageAspectRatio();
        uCrop.withAspectRatio(2, 3);
        uCrop.withAspectRatio(16, 9);
        uCrop.withMaxResultSize(450, 450);
        uCrop.withOptions(getCropOptions());
        uCrop.start(UpdateProfile.this);
    }

    public UCrop.Options getCropOptions() {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(70);
        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(false);
        options.useSourceImageAspectRatio();
        options.setToolbarTitle("Crop Image");
        return options;
    }

    private void DialogBOX() {
        Dialog dialoguebox = new Dialog(UpdateProfile.this);
        dialoguebox.setContentView(R.layout.dialogue_box);
        dialoguebox.setCancelable(false);
        TextView txtalert = dialoguebox.findViewById(R.id.txtalert);
        TextView message = dialoguebox.findViewById(R.id.txtmessage);
        TextView ok = dialoguebox.findViewById(R.id.ok);

        txtalert.setText("Update!");
        message.setText("Your profile has been updated!");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialoguebox.dismiss();
                CoreActivity.fa.finish();
                startActivity(new Intent(UpdateProfile.this, CoreActivity.class));
                finish();
            }
        });
        dialoguebox.show();
    }

    private void SharedprefUser(String cnic, String password) {
        sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        editor.putString("CNIC", cnic);
        editor.putString("PASSWORD", password);
        editor.commit();
    }

}