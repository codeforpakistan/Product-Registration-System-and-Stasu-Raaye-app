package com.example.halalfoodauthorityoss.businesslicense;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.CoreActivity;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.model.AppData;
import com.example.halalfoodauthorityoss.model.Model;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile_Images extends AppCompatActivity {

    ImageView cnicImage, profileImage;
    Button btnNext;
    EditText edtCNIC;
    File profileFile, cnicFile;
    String imageone = "", imagetwo = "", Checking = "";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_images);

        initialization();

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                Checking = "Profile";
            }
        });

        cnicImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage1();
                Checking = "Cnic";
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });
    }

    private void initialization() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        profileImage = findViewById(R.id.profileImage);
        cnicImage = findViewById(R.id.imgcnic);
        btnNext = findViewById(R.id.btnNext);
        edtCNIC = findViewById(R.id.edtcnic);
    }

    private void selectImage() {
        final CharSequence[] options = {"Take from Camera", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile_Images.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take from Camera")) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickPhoto.setType("image/*");
                    startActivityForResult(pickPhoto, 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void selectImage1() {
        final CharSequence[] options = {"Take from Camera", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile_Images.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take from Camera")) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 2);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickPhoto.setType("image/*");
                    startActivityForResult(pickPhoto, 3);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0) {
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
        } else if (resultCode == RESULT_OK && requestCode == 1) {
            Uri uri = data.getData();
            if (uri != null) {
                startCrop(uri);
            }
        } else if (resultCode == RESULT_OK && requestCode == 2) {
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
        } else if (resultCode == RESULT_OK && requestCode == 3) {
            Uri uri = data.getData();
            if (uri != null) {
                startCrop(uri);
            }
        } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP && Checking.equals("Profile")) {
            final Uri resultUri = UCrop.getOutput(data);
            Uri uri = Uri.parse(resultUri.getPath());
            if (uri != null) {
                profileFile = new File(String.valueOf(uri));
                profileImage.setImageURI(resultUri);
                imageone = "one";
            }
        } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP && Checking.equals("Cnic")) {
            final Uri resultUri = UCrop.getOutput(data);
            Uri uri = Uri.parse(resultUri.getPath());
            if (uri != null) {
                cnicFile = new File(String.valueOf(uri));
                cnicImage.setImageURI(resultUri);
                imagetwo = "two";
            }
        }
    }

    private void uploadFile() {

        if (!imageone.equals("") && !imagetwo.equals("")) {
            progressDialog.show();
            File PROFILEImage = new File(profileFile.getAbsolutePath());
            File CNICImage = new File(cnicFile.getAbsolutePath());
            RequestBody requestBodyPROFILE = RequestBody.create(MediaType.parse("multipart/form-data"), PROFILEImage);
            RequestBody requestBodyCNIC = RequestBody.create(MediaType.parse("multipart/form-data"), CNICImage);
            MultipartBody.Part profileimageFile = MultipartBody.Part.createFormData("profile_image", PROFILEImage.getName(), requestBodyPROFILE);
            MultipartBody.Part cnicimageFile = MultipartBody.Part.createFormData("cnic_image", CNICImage.getName(), requestBodyCNIC);

            int BID = Integer.parseInt(getIntent().getStringExtra("categoryid"));
            int DID = Integer.parseInt(getIntent().getStringExtra("districtid"));
            float Lat = Float.parseFloat(getIntent().getStringExtra("latitude"));
            float Long = Float.parseFloat(getIntent().getStringExtra("longitude"));
            /*double Lat= Double.parseDouble(getIntent().getStringExtra("latitude"));
            double Long= Double.parseDouble(getIntent().getStringExtra("longitude"));*/

            Call<Model> call = BaseClass
                    .getInstance()
                    .getApi()
                    .Add_Owner(getIntent().getStringExtra("name"), getIntent().getStringExtra("fathername"), getIntent().getStringExtra("cnic"),
                            getIntent().getStringExtra("contact"), profileimageFile, getIntent().getStringExtra("businessaddress"),
                            getIntent().getStringExtra("businessname")
                            , BID, Lat, Long, cnicimageFile, DID, "male", "Android_user", AppData.id);
            call.enqueue(new Callback<Model>() {
                @Override
                public void onResponse(Call<Model> call, Response<Model> response) {
                    Model model = response.body();
                    if (response.isSuccessful()) {
                        if (model.success.equals("1")) {
                            progressDialog.dismiss();
                            DialogBOX(model.application_id);
                        } else {
                            Toast.makeText(Profile_Images.this, "" + model.response_msg, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } else {
                        Log.v("Response", response.toString());
                        Toast.makeText(Profile_Images.this, "Not Successful", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();

                    }
                }

                @Override
                public void onFailure(Call<Model> call, Throwable t) {
                    Log.d("errror", call.toString());
                    Log.d("errror1", call.request().toString());
                    Toast.makeText(Profile_Images.this, "No Response", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
        } else {
            Toast.makeText(this, "Please Upload Images", Toast.LENGTH_SHORT).show();
        }

    }

    private void DialogBOX(String application_id) {

        Dialog dialoguebox = new Dialog(Profile_Images.this);
        dialoguebox.setContentView(R.layout.dialogue_box);
        dialoguebox.setCancelable(false);
        TextView message = dialoguebox.findViewById(R.id.txtmessage);
        TextView ok = dialoguebox.findViewById(R.id.ok);
        message.setText("Your Business Has Been Registered! Your Registration No is " + application_id);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialoguebox.dismiss();
                Intent intent = new Intent(Profile_Images.this, CoreActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        dialoguebox.show();
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
        uCrop.start(Profile_Images.this);
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
}