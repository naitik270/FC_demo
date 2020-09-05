package com.ftouchcustomer.Complain;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;


import com.ftouchcustomer.Global.ApiClient;
import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.Global.ClsUserInfo;
import com.ftouchcustomer.Global.ConnectionDetector;
import com.ftouchcustomer.Global.FileUploader;
import com.ftouchcustomer.Interface.InterfaceCustomerComplain;
import com.ftouchcustomer.NavigationTabs.BottomNavigationActivity;
import com.ftouchcustomer.Payment.ActivityMakePayment;
import com.ftouchcustomer.R;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivitySelectComplain extends AppCompatActivity {

    String _complainName = "";

    List<ClsComplainList> lstClsComplainLists = new ArrayList<>();
    List<String> lstStringWithTags = new ArrayList<String>();

    //    Button btn_send_complain;
    Bitmap bmp;
    String imagePath = "";
    String extension = "";
    String filename = "";
    String file = "";
    File finalFile = new File("");
    Boolean _Selected = false;
    ImageView iv_doc_file;

    String mobile = "";
    String _code = "", mTempPhotoPath = "";
    EditText edt_remark;
    Toolbar toolbar;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    ImageView iv_add_img;
    ImageView img_clear;
    ImageView iv_expand_more;
    TextView txt_complain;
    EditText et_name;
    EditText et_mobile;
    ClsUserInfo clsUserInfo;
    TextView tv_save;
    private static final int CAMERA_REQUEST = 1;

    public static final int REQUEST_CODE_FOR_COMPLAIN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_complain);

        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        main();
    }

    private void main() {

        clsUserInfo = ClsGlobal.getUserInfo(this);

        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");
        _code = intent.getStringExtra("_code");

        Log.d("--GSON--", "_code: " + _code);

        edt_remark = findViewById(R.id.edt_remark);
        tv_save = findViewById(R.id.tv_save);
        txt_complain = findViewById(R.id.txt_complain);
        iv_add_img = findViewById(R.id.iv_add_img);
        iv_doc_file = findViewById(R.id.iv_doc_file);
        iv_expand_more = findViewById(R.id.iv_expand_more);

        et_name = findViewById(R.id.et_name);
        et_mobile = findViewById(R.id.et_mobile);

        et_mobile.setText(clsUserInfo.getRegisteredmobilenumber());
        et_name.setText(clsUserInfo.getName());

        iv_expand_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityDisplayComplainList.class);
                startActivityForResult(intent, REQUEST_CODE_FOR_COMPLAIN);
            }
        });

        iv_add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmp = null;
//                iv_doc_file.setImageBitmap(bmp);

                UploadDocumentDialog();


//                browseImage();
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean val = validation();
                if (val) {
                    cd = new ConnectionDetector(getApplicationContext());
                    isInternetPresent = cd.isConnectingToInternet();

                    if (isInternetPresent) {
                        // Final code...
                        uploadComplainError();

//                        callUploadComplainAPI();

                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Please check your internet connection!", Toast.LENGTH_SHORT)
                                .show();
                    }
                }

            }
        });
    }


    private Boolean validation() {

        if (et_name.getText() == null || et_name.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Name.", Toast.LENGTH_SHORT).show();
            et_name.requestFocus();
            return false;
        }
        if (et_mobile.getText() == null || et_mobile.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Mobile.", Toast.LENGTH_SHORT).show();
            et_mobile.requestFocus();
            return false;
        }

        if (txt_complain.getText() == null || txt_complain.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Select complain type.", Toast.LENGTH_SHORT).show();
            txt_complain.requestFocus();
            return false;
        }

        if (edt_remark.getText() == null || edt_remark.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter remark.", Toast.LENGTH_SHORT).show();
            edt_remark.requestFocus();
            return false;
        }
        return true;
    }

    private static final int PICK_FROM_FILE = 2;
    String complain_name = "";

    public void browseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        intent.setType("image/*");
        intent.putExtra("return-data", false);
        startActivityForResult(
                Intent.createChooser(intent, "Complete action using"),
                PICK_FROM_FILE);
    }

    private File getOutputMediaFile() {

        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(getExternalFilesDir(null).getAbsolutePath());

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.e("Check", "mediaStorageDir:");
                return null;
            }
        }


        Log.e("Check", "getPath:" + mediaStorageDir.getPath());
        // Create a media file name
        File mediaFile;
        String mImageName = "Payment.jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        Log.e("Check", "mediaFile:" + mediaFile.getAbsolutePath());
        try {
            if (!finalFile.exists()) {
                finalFile.createNewFile();
            }
        } catch (Exception e) {
            Log.e("Check", "mediaFile:" + e.getMessage());
        }
        Log.e("Check", "mediaFile.exists:" + mediaFile.exists());


        return mediaFile;
    }


    private void saveImage(Bitmap bitmap, @NonNull String name) throws IOException {
        boolean saved;
        OutputStream fos;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver resolver = getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" + "new");
            Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            fos = resolver.openOutputStream(imageUri);
        } else {
//            String imagesDir = Environment.getExternalStoragePublicDirectory(
//                    Environment.DIRECTORY_DCIM).toString() + File.separator +  "new";

            String imagesDir = getExternalFilesDir(null).getAbsolutePath();

            File file = new File(imagesDir);

            if (!file.exists()) {
                file.mkdir();
            }

            File image = new File(imagesDir, name);
            fos = new FileOutputStream(image);

        }

        saved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.flush();
        fos.close();
    }

    private void UploadDocumentDialog() {
        final Dialog dialog = new Dialog(ActivitySelectComplain.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_cam_gallary);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);


        LinearLayout ll_gallery = dialog.findViewById(R.id.ll_gallery);
        LinearLayout ll_camera = dialog.findViewById(R.id.ll_camera);

        ll_gallery.setOnClickListener(v -> {
            dialog.dismiss();
            dialog.cancel();


            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, ClsGlobal.STORAGE_PERMISSIONS,
                        1);
            } else {

                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                intent.setType("image/*");
                intent.putExtra("return-data", false);
                startActivityForResult(
                        Intent.createChooser(intent, "Complete action using"),
                        PICK_FROM_FILE);

            }


        });

        ll_camera.setOnClickListener(view -> {
            dialog.dismiss();
            dialog.cancel();

//            String extension = ".jpg";
//            String filename = item + "_".concat(ClsGlobal.getRandom());

//            startActivityForResult(getPickImageChooserIntent(), 200);


            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, ClsGlobal.askStorage_and_camera,
                        1);
            } else {

                launchCamera("Cam_".concat(ClsGlobal.getRandom()));

            }


//            if (!hasPermissions(AddDocumentsActivity.this,askStorage_and_camera)){
//                ClsPermission.askPermission(AddDocumentsActivity.this, 1,
//                        askStorage_and_camera);
//                return;
//            }


        });
        dialog.show();
    }


    private void storeImage() {
//        finalFile = getOutputMediaFile();
//        if (finalFile == null) {
//            Log.e("Check", "finalFile == null: ");
//            return;
//        }

        try {

//            FileOutputStream out = new FileOutputStream(filename);

//            bmp = Bitmap.createScaledBitmap(bmp, THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);
//            bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);

//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);

//            FileOutputStream fos = new FileOutputStream(filename);
//            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
//            fos.close();

//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            image.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
//
//
//            FileOutputStream fo = new FileOutputStream(finalFile);
//            fo.write(bytes.toByteArray());
//            fo.close();


            String imagesDir = getExternalFilesDir(null).getAbsolutePath();

            File file = new File(imagesDir);

            if (!file.exists()) {
                file.mkdir();
            }

            finalFile = new File(imagesDir, "Payment.jpg");
            FileOutputStream fos = new FileOutputStream(finalFile);

            if (bmp != null) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            }

            fos.flush();
            fos.close();


//            out.close();
        } catch (Exception e) {
            Log.e("Check", "Exception: " + e.getMessage());
        }

        saveBitmapToFile(finalFile);

    }


    public File saveBitmapToFile(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image


            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file;
        } catch (Exception e) {
            Log.e("Check", "Exception saveBitmapToFile: " + e.getMessage());
            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("--Code--", "1st");

        if (requestCode == PICK_FROM_FILE && resultCode == RESULT_OK && data != null) {
            Uri _SelectedFileUri = data.getData();
            try {


                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), _SelectedFileUri);
//                finalFile = new File(ClsGlobal.getRealPathFromURI(ActivitySelectComplain.this,
//                        _SelectedFileUri));

//                imagePath = GetFilePathFromDevice.getPath(ActivitySelectComplain.this, data.getData());
//                filename = imagePath.substring(imagePath.lastIndexOf("/") + 1);

//                if (filename.indexOf(".") > 0) {
//                    file = filename.substring(0, filename.lastIndexOf("."));
//                } else {
//                    file = filename;
//                }
//                if (filename.lastIndexOf(".") > 0) {
//                    extension = filename.substring(filename.lastIndexOf("."));
//                }

                Log.e("Check", "_SelectedFileUri: " + _SelectedFileUri);

                makeImageSizeSmall();


            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.d("--Code--", "1st");

        } else if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            try {
                bmp = ClsGlobal.resamplePic(this, mTempPhotoPath);
                makeImageSizeSmall();
//                iv_doc_file.setImageBitmap(bmp);
            } catch (Exception e) {
                Log.d("--Code--", "IOException");
                e.printStackTrace();
            }


        } else if (requestCode == REQUEST_CODE_FOR_COMPLAIN && resultCode == Activity.RESULT_OK) {

            Log.d("--Code--", "3rd");

            complain_name = data.getStringExtra("complain_name");
            txt_complain.setText(complain_name);
        }
    }

    @SuppressLint("StaticFieldLeak")
    void makeImageSizeSmall() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pd = ClsGlobal._prProgressDialog(ActivitySelectComplain.this,
                        "Loading...", false);
                pd.show();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    Thread.sleep(500);
                    try {
                        finalFile = new File(getExternalFilesDir(null).getAbsolutePath(),
                                "Payment.jpg");
                        finalFile.delete();
                    } catch (Exception e) {
                        Log.e("Check", "Exception: " + e.getMessage());
                    }


                    storeImage();

//                            saveImage(bmp,"Payment.jpg");

                } catch (Exception e) {

                    Log.e("Check", "Exception doInBackground: " + e.getMessage());
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (bmp != null) {

                    iv_doc_file.setImageBitmap(bmp);
                    _Selected = true;
                }
            }

        }.execute();
    }


    public void onLoadImageClick(View view) {
        startActivityForResult(getPickImageChooserIntent(), 200);
    }

    public Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent()
                    .getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));
        // bitmapUri=outputFileUri;
        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "pickImageResult.jpeg"));
        }
        // bitmapUri=outputFileUri;
        return outputFileUri;
    }

    private ProgressDialog pd;

/*

    @SuppressLint("StaticFieldLeak")
    void callUploadComplainAPI() {

        pd = ClsGlobal._prProgressDialog(ActivitySelectComplain.this,
                "please wait for upload complain.", false);
        pd.show();

        String remark = edt_remark.getText().toString().concat("-->")
                .concat(" ").concat("Name : ").concat(clsUserInfo.getName());

        String _jpgFromFile = filename;
        _jpgFromFile = _jpgFromFile.replace(".jpg", "");

        FileUploader fileUploader = new FileUploader(ActivitySelectComplain.this);
        File file = new File(ClsGlobal.getItemsTemp_Folder(ActivitySelectComplain.this));
        if (!file.exists()) {
            file.mkdirs();
        }

        fileUploader.getUploadComplainAPI(finalFile, clsUserInfo.getRegisteredmobilenumber(),
                "", txt_complain.getText().toString(), remark, ClsGlobal.AppName,
                _jpgFromFile, extension, ClsGlobal.ApplicationType);

        fileUploader.showUploadProgressBar(false);


        fileUploader.SetCallBack(new FileUploader.FileUploaderCallback() {

            @Override
            public void onError() {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
            }

            @Override
            public void onFinish(String responses) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (responses.equalsIgnoreCase("1")) {

                    new Thread(() -> finalFile.delete()).start();

                    Toast.makeText(ActivitySelectComplain.this,
                            "Feedback submitted successfully! Thank you!", Toast.LENGTH_SHORT).show();
                    finish();

                } else if (responses.equalsIgnoreCase("2")) {
                    Toast.makeText(ActivitySelectComplain.this,
                            "Feedback not submitted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivitySelectComplain.this,
                            "No response on Feedback!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onProgressUpdate(int currentpercent, int totalpercent, String msg) {
                pd.dismiss();

                Toast.makeText(getApplicationContext(),
                        "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
*/


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (ArrayUtils.contains(grantResults, -1)) {
                    ClsGlobal.permissionAlert(this, "Permission",
                            "Please allow required permissions to function the app.");
                }
            }


        }

    }


    void uploadComplainError() {

        try {

            String remark = edt_remark.getText().toString().concat("-->")
                    .concat(" ").concat("Name : ").concat(clsUserInfo.getName());

            String _jpgFromFile = filename;
            _jpgFromFile = _jpgFromFile.replace(".jpg", "");

            ClsCustomerComplainParams obj = new ClsCustomerComplainParams();
            obj.setMobileNumber(clsUserInfo.getRegisteredmobilenumber());
            obj.setCustomerCode("");
            obj.setRequestSubject(txt_complain.getText().toString());
            obj.setRequestRemark(remark);
            obj.setProductName(ClsGlobal.AppName);
            obj.setFileName(_jpgFromFile);
            obj.setFileExtension(extension);
            obj.setData(ClsGlobal.getBytes(finalFile));
            obj.setApplicationType(ClsGlobal.ApplicationType);

            Gson gson = new Gson();
            String jsonInString = gson.toJson(obj);
            Log.d("--GSON--", "uploadDocumentAPI- " + jsonInString);

            InterfaceCustomerComplain interfaceComplain =
                    ApiClient.getRetrofitInstance().create(InterfaceCustomerComplain.class);

            Call<ClsCustomerComplainParams> call
                    = interfaceComplain.postComplain(obj);

            ProgressDialog pd = ClsGlobal._prProgressDialog(ActivitySelectComplain.this,
                    "Working...", true);
            pd.show();

            call.enqueue(new Callback<ClsCustomerComplainParams>() {
                @Override
                public void onResponse(Call<ClsCustomerComplainParams> call,
                                       Response<ClsCustomerComplainParams> response) {

                    if (pd.isShowing()) {
                        pd.dismiss();
                    }

                    if (response.body() != null) {
                        String _response = response.body().getSuccess();
                        switch (_response) {
                            case "1":

                                new Thread(() -> {
                                    try {
                                        File TempPhoto = new File(mTempPhotoPath);

                                        TempPhoto.delete();

                                        finalFile.delete();
                                    } catch (Exception e) {
                                        Log.e("Check", "Exception: " + e.getMessage());
                                    }

                                }).start();

                                Toast.makeText(ActivitySelectComplain.this,
                                        "Feedback submitted successfully! Thank you!", Toast.LENGTH_SHORT).show();
                                finish();
                                break;
                            case "2":
                                Toast.makeText(ActivitySelectComplain.this,
                                        "Feedback not submitted!", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(ActivitySelectComplain.this,
                                        "No response on Feedback!", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Server error...!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ClsCustomerComplainParams> call, Throwable t) {
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }

                    Toast.makeText(getApplicationContext(),
                            "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            if (pd.isShowing()) {
                pd.dismiss();
            }
            Toast.makeText(getApplicationContext(),
                    "Failed to submit complain!", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Creates a temporary image file and captures a picture to store in it.
     */
    private void launchCamera(String imageFileName) {

        // Create the capture image intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the temporary File where the photo should go
            File photoFile = null;
            try {
                photoFile = ClsGlobal.createTempImageFile(this, imageFileName);
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                // Get the path of the temporary fileb
                mTempPhotoPath = photoFile.getAbsolutePath();

                // Get the content URI for the image file
                Uri photoURI = FileProvider.getUriForFile(this,
                        ClsGlobal.AUTHORITY,
                        photoFile);

                // Add the URI so the camera can store the image
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                // Launch the camera activity
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }


}
