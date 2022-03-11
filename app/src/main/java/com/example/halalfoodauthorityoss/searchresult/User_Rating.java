package com.example.halalfoodauthorityoss.searchresult;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.adapter.ImagesAdapter;
import com.example.halalfoodauthorityoss.adapter.Search_Business_Adapter;
import com.example.halalfoodauthorityoss.complaint.Complaint;
import com.example.halalfoodauthorityoss.loginsignupforgot.UpdateProfile;
import com.example.halalfoodauthorityoss.model.AppData;
import com.example.halalfoodauthorityoss.model.Model;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Rating extends AppCompatActivity {

    RatingBar Premises_Hygiene, Equipment_Hygiene, Staff_Personal_Hygiene, Food_Hygiene, Food_Quality;
    TextInputEditText edtReviews;
    ImageView addimage;
    String premises_Hygiene=" ", equipment_Hygiene=" ", staff_Personal_Hygiene=" ", food_Hygiene=" ", food_Quality=" ";
    TextView btnShare;
    ImagesAdapter adapter;
    ArrayList<Uri> imagesUriArrayList = new ArrayList<>();
    ArrayList<File> imagesNameArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__rating);

        Initialization();

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
                if (premises_Hygiene.equals(" ") || equipment_Hygiene.equals(" ") || staff_Personal_Hygiene.equals(" ") || food_Hygiene.equals(" ") || food_Quality.equals(" "))
                {
                    Toast.makeText(User_Rating.this, "Ratings cann't be null", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtReviews.getText().toString().equals(""))
                {
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
                        .Add_Rating(AppData.id,Search_Business_Adapter.business_id,edtReviews.getText().toString().trim(),premises_Hygiene,equipment_Hygiene,staff_Personal_Hygiene,food_Hygiene,food_Quality,multipartTypedOutput);

                call.enqueue(new Callback<Model>() {
                    @Override
                    public void onResponse(Call<Model> call, Response<Model> response) {
                       if (response.isSuccessful())
                       {
                           Model model=response.body();
                           if (model.success.equals("1")) {
                               progressDialog.dismiss();
                               DialogBOX();
                           }
                           else {
                               Toast.makeText(User_Rating.this, ""+model.message, Toast.LENGTH_SHORT).show();
                               progressDialog.dismiss();
                               btnShare.setEnabled(true);

                           }
                       }else {
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
        dialoguebox.setCancelable(true);
        TextView txtalert = dialoguebox.findViewById(R.id.txtalert);
        TextView message = dialoguebox.findViewById(R.id.txtmessage);
        TextView ok = dialoguebox.findViewById(R.id.ok);

        txtalert.setText("Thank You!");
        message.setText("Your feedback has been incorporated into the public ratings");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialoguebox.dismiss();
                Intent intent = new Intent(User_Rating.this, Feedback_Reviews.class);
              //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        dialoguebox.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                Log.e("++data", "" + data.getClipData().getItemCount());// Get count of image here.

                Log.e("++count", "" + data.getClipData().getItemCount());

                if (data.getClipData().getItemCount() > 4) {
                    adapter.notifyDataSetChanged();
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.addimage), "You can not select more than 4 images", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent();
                                    intent.setType("image/*");
                                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                                    intent.setAction(Intent.ACTION_PICK);
                                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 5);
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

                    for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                        File file;
                        Uri selectedImageUri = data.getClipData().getItemAt(i).getUri();
                        String selectedImagePath = getRealPathFromURIForGallery(selectedImageUri);
                        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                        Bitmap b = BitmapFactory.decodeFile(selectedImagePath);
                        Bitmap out = getResizedBitmap(b, 1536, selectedImagePath);
                        final int min = 200000;
                        final int max = 800000;
                        final int random = new Random().nextInt((max - min) + 1) + min;
                        String name = random + ".jpg";
                        file = new File(dir, name);
                        FileOutputStream fOut;
                        try {
                            fOut = new FileOutputStream(file);
                            out.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                            fOut.flush();
                            fOut.close();
                            b.recycle();
                            out.recycle();
                        } catch (Exception e) {
                        }

                        Log.d("nnnn", String.valueOf(selectedImageUri));
                        Log.d("mmmm", String.valueOf(file.getAbsolutePath()));
                        imagesUriArrayList.add(data.getClipData().getItemAt(i).getUri());
                        imagesNameArrayList.add(new File(file.getAbsolutePath()));
                    }
                }

                ShowImages();

                  /*  adapter = new Adapter(Complaint.this, imagesUriArrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();*/
            }
        }
        if (requestCode == 0) {
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
            } catch (IOException e) {
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

                    /*for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                        imagesUriArrayList.add(data.getClipData().getItemAt(i).getUri());
                    }*/
                imagesUriArrayList.add(Uri.parse(file.getAbsolutePath()));
                imagesNameArrayList.add(new File(file.getAbsolutePath()));

                ShowImages();


                // Toast.makeText(this, "" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "" + file.getName(), Toast.LENGTH_SHORT).show();
                // Toast.makeText(this, "path" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void ShowImages() {
        Log.e("SIZE", imagesUriArrayList.size() + "");
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(User_Rating.this, RecyclerView.VERTICAL, false);
        adapter = new ImagesAdapter(imagesUriArrayList, User_Rating.this,"complaint");
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
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_PICK);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public String getRealPathFromURIForGallery(Uri uri) {
        if (uri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(uri, projection, null,
                null, null);
        if (cursor != null) {
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        assert false;
        cursor.close();
        return uri.getPath();
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize, String photoPath) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        Bitmap resizedBitmap1 = RotateImage(photoPath, Bitmap.createScaledBitmap(image, width, height, true));
        return resizedBitmap1;
    }

    private Bitmap RotateImage(String photoPath, Bitmap bitmap) {
        Bitmap rotatedBitmap = null;
        try {
            ExifInterface ei = null;
            ei = new ExifInterface(photoPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmap;
            }
        } catch (Exception e) {
        }
        return rotatedBitmap;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

}