package com.example.halalfoodauthorityoss.complaint;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.halalfoodauthorityoss.BaseClass;
import com.example.halalfoodauthorityoss.CoreActivity;
import com.example.halalfoodauthorityoss.R;
import com.example.halalfoodauthorityoss.adapter.ImagesAdapter;
import com.example.halalfoodauthorityoss.loginsignupforgot.Forgot_Passoword;
import com.example.halalfoodauthorityoss.loginsignupforgot.Login;
import com.example.halalfoodauthorityoss.model.AppData;
import com.example.halalfoodauthorityoss.model.Model;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Complaint extends AppCompatActivity {

    TextInputEditText edtBusinessName, edtBusinessAddress, edtComplaint;
    TextView btnSubmit;
    Spinner categorySpinner, districtSpinner;
    ImageView ic_back;
    ProgressDialog progressDialog;
    List<String> ListDistrictName;
    List<String> ListDistrictID;
    String districtID;
    String districtName;
    ImageView addimage;
    ArrayList<Uri> imagesUriArrayList = new ArrayList<>();
    ArrayList<File> imagesNameArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    ImagesAdapter adapter;

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        initialization();
        displayDistrict();

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Complaint.this, CoreActivity.class));
                finish();
            }
        });

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String BusinessName = edtBusinessName.getText().toString().trim();
                String BusinessAddress = edtBusinessAddress.getText().toString().trim();
                String Complaint = edtComplaint.getText().toString().trim();
                String Category = String.valueOf(categorySpinner.getSelectedItem());

                if (Category.equals("Select Category")) {
                    Toast.makeText(Complaint.this, "Select Complaint Category", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (districtID.equals("0")) {
                    Toast.makeText(Complaint.this, "Select District", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (BusinessName.equals("") || BusinessAddress.equals("") || Complaint.equals("")) {
                    Toast.makeText(Complaint.this, "Field Cann't be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.show();
                MultipartBody.Part[] multipartTypedOutput = new MultipartBody.Part[imagesNameArrayList.size()];
                for (int index = 0; index < imagesNameArrayList.size(); index++) {
                    File file = new File(imagesNameArrayList.get(index).getAbsolutePath());
                    RequestBody reqBody = RequestBody.create(MediaType.parse("image"), file);
                    multipartTypedOutput[index] = MultipartBody.Part.createFormData("userfile[]", file.getName(), reqBody);
                }

                Call<Model> call = BaseClass
                        .getInstance()
                        .getApi()
                        .Complaint(Category, Complaint, AppData.id, BusinessAddress, districtName, BusinessName, multipartTypedOutput);

                call.enqueue(new Callback<Model>() {
                    @Override
                    public void onResponse(Call<Model> call, Response<Model> response) {
                        Model model = response.body();
                        if (response.isSuccessful()) {
                            if (model.success.equals("1")) {
                                progressDialog.dismiss();
                                DialogBOX();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(Complaint.this, "Complaint no submitted", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(Complaint.this, "Not Successful", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(Complaint.this, "No Response", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                districtID = ListDistrictID.get(i);
                districtName = ListDistrictName.get(i);
                Toast.makeText(Complaint.this, "districtname" + districtName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
        edtBusinessName = findViewById(R.id.edtBusinessName);
        districtSpinner = findViewById(R.id.districtSpinner);
        edtBusinessAddress = findViewById(R.id.edtBusinessAddress);
        edtComplaint = findViewById(R.id.edtComplaint);
        categorySpinner = findViewById(R.id.complaintSpinner);
        btnSubmit = findViewById(R.id.btnSubmit);
        ic_back = findViewById(R.id.ic_back);
        addimage = findViewById(R.id.addimage);
        ListDistrictName = new ArrayList<String>();
        ListDistrictID = new ArrayList<String>();
        ListDistrictName.add("Select District");
        ListDistrictID.add("0");
        recyclerView = findViewById(R.id.recyclerview);
    }

    private void DialogBOX() {
        Dialog dialoguebox = new Dialog(Complaint.this);
        dialoguebox.setContentView(R.layout.dialogue_box);
        dialoguebox.setCancelable(true);
        TextView txtalert = dialoguebox.findViewById(R.id.txtalert);
        TextView message = dialoguebox.findViewById(R.id.txtmessage);
        TextView ok = dialoguebox.findViewById(R.id.ok);

        txtalert.setText("Complaint!");
        message.setText("Your Complaint has been submitted.");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialoguebox.dismiss();
                Intent intent = new Intent(Complaint.this, MyComplaints.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        dialoguebox.show();
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Complaint.this, android.R.layout.simple_spinner_item, ListDistrictName);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        districtSpinner.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(Complaint.this, "Empty Category", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                Toast.makeText(Complaint.this, "No Response", Toast.LENGTH_SHORT).show();
                Log.d("errror2", call.request().toString());

            }
        });
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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Complaint.this, RecyclerView.VERTICAL, false);
        adapter = new ImagesAdapter(imagesUriArrayList, Complaint.this,"complaint");
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(Complaint.this, 4));
        adapter.notifyDataSetChanged();
    }

    private void selectImage() {
        final CharSequence[] options = {"Take from Camera", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Complaint.this);
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
}