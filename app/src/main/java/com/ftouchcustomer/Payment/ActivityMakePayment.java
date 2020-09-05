package com.ftouchcustomer.Payment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.ftouchcustomer.Complain.GetFilePathFromDevice;
import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.Global.ClsUserInfo;
import com.ftouchcustomer.Global.FileUploader;
import com.ftouchcustomer.Orders.ClsCustomerOrderDetail;
import com.ftouchcustomer.Orders.ClsGetMerchantPaymentMethodList;
import com.ftouchcustomer.PlaceOrder.AdapterPaymentMethodRadio;
import com.ftouchcustomer.R;
import com.ftouchcustomer.ViewModelClass.AddToItemViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ActivityMakePayment extends AppCompatActivity {

    private static final String TAG = ActivityMakePayment.class.getSimpleName();
    Button btn_send_bill;
    ImageView iv_add_img;
    private static final int PICK_FROM_FILE = 2;

    Bitmap bmp;
    String imagePath = "";
    String extension = "";
    String filename = "";
    String file = "";
    File finalFile = new File("");
    ImageView iv_doc_file;
    Boolean _Selected = false;
    Double total_amount = 0.0;
    TextView txt_merchant_name, txt_total_items, txt_total_amount, txt_payment_mode;
    EditText edt_ref_no;
    ClsCustomerOrderDetail.ClsDataOrderDetails clsOrderDetail;
    ClsUserInfo clsUserInfo;
    ImageView iv_payment_mode;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);

        Toolbar toolbar = findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addToItemViewModel = new
                ViewModelProvider(this).get(AddToItemViewModel.class);

        btn_send_bill = findViewById(R.id.btn_send_bill);
        iv_add_img = findViewById(R.id.iv_add_img);
        iv_doc_file = findViewById(R.id.iv_doc_file);
        txt_merchant_name = findViewById(R.id.txt_merchant_name);
        txt_total_items = findViewById(R.id.txt_total_items);
        txt_total_amount = findViewById(R.id.txt_total_amount);
        txt_payment_mode = findViewById(R.id.txt_payment_mode);
        iv_payment_mode = findViewById(R.id.iv_payment_mode);
        edt_ref_no = findViewById(R.id.edt_ref_no);

        clsUserInfo = ClsGlobal.getUserInfo(this);
        clsOrderDetail = (ClsCustomerOrderDetail.ClsDataOrderDetails) getIntent().getSerializableExtra("clsOrderDetail");
        total_amount = getIntent().getDoubleExtra("total_amount", 0.0);

        if (clsOrderDetail != null) {
            txt_merchant_name.setText(clsOrderDetail.getMerchantName().toUpperCase());
            txt_total_items.setText("TOTAL ITEMS: " + clsOrderDetail.getItemscount());
            txt_total_amount.setText("\u20B9 " + total_amount);

        }

        iv_add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseImage();
            }
        });

        btn_send_bill.setOnClickListener(v -> {

            if (iv_doc_file.getDrawable() != null) {
                areYouSureDialog();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Please select bill image!", Toast.LENGTH_SHORT)
                        .show();

            }
        });

        iv_payment_mode.setOnClickListener(v -> {

            if (lstClsGetMerchantPaymentMethodLists.size() > 0) {
                displayPaymentMethod(lstClsGetMerchantPaymentMethodLists);
            } else {
                Toast.makeText(ActivityMakePayment.this, "No other payment method found! please contact to store.",
                        Toast.LENGTH_LONG).show();
            }

        });
    }


    private void areYouSureDialog() {

        final Dialog dialog = new Dialog(ActivityMakePayment.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dilaog_cust_alert);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView content = dialog.findViewById(R.id.content);
        Button btn_ok = dialog.findViewById(R.id.btn_ok);
        btn_ok.setText("YES");
        Button btn_no = dialog.findViewById(R.id.btn_no);
        btn_no.setVisibility(View.VISIBLE);

        content.setText(getText(R.string.alert_make_payment));

        btn_ok.setOnClickListener(v -> {
            dialog.dismiss();

            if (ClsGlobal.isNetworkConnected(getApplicationContext())) {
                callMakePaymentAPI();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Please check your internet connection!", Toast.LENGTH_SHORT)
                        .show();
            }

        });

        btn_no.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }


    @SuppressLint("SetTextI18n")
    void displayPaymentMethod(List<ClsGetMerchantPaymentMethodList> lst) {

        Dialog dialog = new Dialog(Objects.requireNonNull(ActivityMakePayment.this));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_listview);
        dialog.setCancelable(true);
        TextView txt_title = dialog.findViewById(R.id.txt_title);
        txt_title.setText("Payment Method");

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(lp);

        ListView lv_lst = dialog.findViewById(R.id.lv_lst);

        AdapterPaymentMethodRadio adapterTimeList =
                new AdapterPaymentMethodRadio(ActivityMakePayment.this, lst);

        adapterTimeList.setOnRadioBtnClick(cls -> {
            dialog.dismiss();
            txt_payment_mode.setText(cls.getPaymentMethod());
        });

        lv_lst.setAdapter(adapterTimeList);

        dialog.show();
    }

    AddToItemViewModel addToItemViewModel;
    List<ClsGetMerchantPaymentMethodList> lstClsGetMerchantPaymentMethodLists = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        getMerchantPaymentMethod();
    }

    @SuppressLint("SetTextI18n")
    void getMerchantPaymentMethod() {

        addToItemViewModel.getMerchantPaymentMethod(clsOrderDetail.getMerchantCode()).observe(this,
                clsGetMerchantPaymentMethodResponse -> {

                    lstClsGetMerchantPaymentMethodLists = clsGetMerchantPaymentMethodResponse.getData();

                });
    }

    public void browseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        intent.setType("image/*");
        intent.putExtra("return-data", false);
        startActivityForResult(
                Intent.createChooser(intent, "Complete action using"),
                PICK_FROM_FILE);
    }

    @SuppressLint("StaticFieldLeak")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FROM_FILE && resultCode == RESULT_OK && data != null) {
            Uri _SelectedFileUri = data.getData();
            try {

                bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), _SelectedFileUri);
                imagePath = GetFilePathFromDevice.getPath(ActivityMakePayment.this, data.getData());

                if (imagePath != null) {
                    filename = imagePath.substring(imagePath.lastIndexOf("/") + 1);
                }
                if (filename.indexOf(".") > 0) {
                    file = filename.substring(0, filename.lastIndexOf("."));
                } else {
                    file = filename;
                }
                if (filename.lastIndexOf(".") > 0) {
                    extension = filename.substring(filename.lastIndexOf("."));
                }

                new AsyncTask<Void, Void, Void>() {

                    protected void onPreExecute() {
                        super.onPreExecute();
                        pd = ClsGlobal._prProgressDialog(ActivityMakePayment.this,
                                "Loading...", false);
                        pd.show();
                    }

                    @Override
                    protected Void doInBackground(Void... voids) {
                        storeImage(bmp);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        if (pd.isShowing()) {
                            pd.dismiss();
                        }
                        iv_doc_file.setImageBitmap(bmp);
                        _Selected = true;
                    }

                }.execute();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onLoadImageClick(View view) {
        startActivityForResult(getPickImageChooserIntent(), 200);
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


    private void storeImage(Bitmap image) {
        finalFile = getOutputMediaFile();
        Log.d(TAG,
                "pictureFile: " + finalFile.getAbsolutePath());// e.getMessage());
        if (finalFile == null) {
            Log.d(TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }

        try {
            FileOutputStream fos = new FileOutputStream(finalFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }

    /**
     * Create a File for saving an image or video
     */
    private File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(getExternalFilesDir(null).getAbsolutePath());

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        // Create a media file name
        File mediaFile;
        String mImageName = "Payment.jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
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
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
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

    private ProgressDialog pd;

    @SuppressLint("StaticFieldLeak")
    void callMakePaymentAPI() {

        pd = ClsGlobal._prProgressDialog(ActivityMakePayment.this,
                "please wait for payment process.", false);
        pd.show();

        FileUploader fileUploader = new FileUploader(ActivityMakePayment.this);
        File file = new File(ClsGlobal.getItemsTemp_Folder(ActivityMakePayment.this));
        if (!file.exists()) {
            file.mkdirs();
        }

        // MerchantID where is MerchantID.
        fileUploader.getMakePaymentAPI(finalFile, clsOrderDetail.getOrderID(),
                clsOrderDetail.getMerchantID(), clsOrderDetail.getMerchantCode(),
                edt_ref_no.getText().toString(), "Complete");

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

                    finish();
                } else {
                    Toast.makeText(ActivityMakePayment.this, "Failed to upload!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onProgressUpdate(int currentpercent, int totalpercent, String msg) {
                Log.e("--Test--", "msg: " + msg);
                Log.d("--steps--", "step 24: " + msg);

            }
        });
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

}
