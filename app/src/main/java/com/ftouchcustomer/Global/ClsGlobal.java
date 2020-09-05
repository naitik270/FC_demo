package com.ftouchcustomer.Global;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.system.ErrnoException;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ftouchcustomer.BuildConfig;
import com.ftouchcustomer.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.ftouchcustomer.Complain.GetFilePathFromDevice.getDataColumn;
import static com.ftouchcustomer.Complain.GetFilePathFromDevice.isDownloadsDocument;
import static com.ftouchcustomer.Complain.GetFilePathFromDevice.isExternalStorageDocument;
import static com.ftouchcustomer.Complain.GetFilePathFromDevice.isGooglePhotosUri;
import static com.ftouchcustomer.Complain.GetFilePathFromDevice.isMediaDocument;

public class ClsGlobal {

    public static Boolean isMoveToBack = false;
    public static final String AppName = "fTouch Customer";
    public static final String ApplicationType = "CustomerApp";
    public static boolean textAvailable = false;
    public static String imgPreviewMode = "";
    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".myfileprovider";
    public static String DataBaseName = "fTouchCustomerDB";

    public static int countryStateCityCode = 0; // 1 for county, 2 for State, 3 for City

    public static boolean clearMode = false;
    public static String[] LOCATION_PERMISSIONS = {
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    public static String[] STORAGE_PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    public static final String[] askStorage_and_camera  ={android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.CAMERA};


  /*  public static String ItemsJsonFile = Environment.getExternalStorageDirectory()
            + "/.fTouchTemp/Items.json";*/

//    public static String ItemsJsonFile = Environment.getExternalStorageDirectory()
//            + "/.pokeMon/";

//    public static String ItemsJsonFile = Environment.getExternalStorageDirectory()
//            + "/.fTouchTemp/Items.json";
//
//    public static String ItemsTemp_Folder = Environment.getExternalStorageDirectory()
//            + "/.fTouchTemp";

    public static String getItemsTemp_Folder(Context context) {

        if (context.getExternalFilesDir(null) != null) {
            return context.getExternalFilesDir(null).getAbsolutePath()
                    + "/.fTouchTemp";
        }
        return "";

    }


    public static String getRandom() {
        String result = "";
        Random random = new Random();
        result = String.format("%03d", random.nextInt(10000));
        return result;
    }



    public static String getItemsJsonFile(Context context) {

        if (context.getExternalFilesDir(null) != null) {
            return context.getExternalFilesDir(null).getAbsolutePath()
                    + "/.fTouchTemp/Items.json";
        }
        return "";

    }

    public static ProgressDialog _prProgressDialog(Context c, String msg, Boolean setCancelable) {
        ProgressDialog progressDialog = new ProgressDialog(c, R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage(msg); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.setCancelable(setCancelable);
        return progressDialog;
    }


    public static String getRealPathFromURI(Context context, Uri uri) {
        String path = "";
        if (context.getContentResolver() != null) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    public static String getPlaceOrderDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
        return df.format(calendar.getTime());
    }

    public static String getBytes(File filePath) {

        String result = "";
        byte[] byteArray = null;
        try {
            InputStream inputStream = new FileInputStream(filePath);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024 * 8];
            int bytesRead = 0;

            while ((bytesRead = inputStream.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();
            result = Base64.encodeToString(byteArray, 0);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static final String MobileNo_Pattern = "^[0-9]{10}$";


    public static Uri getPickImageResultUri(Context context, Intent data) {
        boolean isCamera = true;
        if (data != null && data.getData() != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutputUri(context) : data.getData();
    }

    /**
     * Test if we can open the given Android URI to test if permission required error is thrown.<br>
     */
    public static boolean isUriRequiresPermissions(Context context, Uri uri) {
        try {
            ContentResolver resolver = context.getContentResolver();
            InputStream stream = resolver.openInputStream(uri);
            stream.close();
            return false;
        } catch (FileNotFoundException e) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (e.getCause() instanceof ErrnoException) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    private static Uri getCaptureImageOutputUri(Context context) {
        Uri outputFileUri = null;
        File getImage = context.getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "pickImageResult.jpeg"));
        }
        // bitmapUri=outputFileUri;
        return outputFileUri;
    }

    public static void setLanguage(Context context, String language) {
        Locale myLocale = new Locale(language);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


    /**
     * Creates the temporary image file in the cache directory.
     *
     * @return The temporary image file.
     * @throws IOException Thrown if there is an error creating the file
     */
    public static File createTempImageFile(Context context, String filename) throws IOException {
        File storageDir = context.getExternalCacheDir();

        return File.createTempFile(
                filename,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }


    /**
     * Resamples the captured photo to fit the screen for better memory usage.
     *
     * @param context   The application context.
     * @param imagePath The path of the photo to be resampled.
     * @return The resampled bitmap
     */
    public static Bitmap resamplePic(Context context, String imagePath) {

        // Get device screen size information
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);

        int targetH = metrics.heightPixels;
        int targetW = metrics.widthPixels;

        // Get the dimensions of the original bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeFile(imagePath);
    }



    public static void permissionAlert(Context context,String title,String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .create(); //Read Update.
//        alertDialog.setContentView(R.layout.activity_dialog);

        alertDialog.setTitle(title);
//        alertDialog.setMessage("Please allow required permissions to function the app.");
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);

        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                checkpermission(context);
            }

        });


        alertDialog.setButton(Dialog.BUTTON_NEUTRAL, "Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                checkpermission(context);

                Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + context.getPackageName()));
                myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
                myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(myAppSettings);

            }

        });

        alertDialog.setCancelable(false);
        alertDialog.show();

    }



    public static void setUserInfo(ClsUserInfo objClsUserInfo, Context c) {

        Log.d("setUserInfo", "setUserInfo: ");

        String MyPREFERENCES = "_LoginDetails";
        SharedPreferences mPreferences = c.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();

        String CustomerId = "CustomerId";
        String Name = "Name";
        String LoginStatus = "LoginStatus";
        String RegisteredMobileNumber = "RegisteredMobileNumber";
        String EmailAddress = "EmailAddress";
        String State = "State";

        editor.putString(CustomerId, objClsUserInfo.getCustomerId());
        editor.putString(Name, objClsUserInfo.getName());
        editor.putString(LoginStatus, objClsUserInfo.getLoginStatus());
        editor.putString(RegisteredMobileNumber, objClsUserInfo.getRegisteredmobilenumber());
        editor.putString(LoginStatus, objClsUserInfo.getLoginStatus());
        editor.putString(EmailAddress, objClsUserInfo.getEmailaddress());
        editor.putString(State, objClsUserInfo.getState());

        editor.apply();
        editor.commit();
    }

    public static ClsUserInfo getUserInfo(Context c) {
        ClsUserInfo objClsUserInfo = new ClsUserInfo();
        String MyPREFERENCES = "_LoginDetails";
        SharedPreferences mPreferences = c.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String CustomerId = "CustomerId";
        String Name = "Name";
        String LoginStatus = "LoginStatus";
        String RegisteredMobileNumber = "RegisteredMobileNumber";
        String EmailAddress = "EmailAddress";
        String State = "state";

        objClsUserInfo.setCustomerId(mPreferences.getString(CustomerId, objClsUserInfo.getCustomerId()));
        objClsUserInfo.setName(mPreferences.getString(Name, objClsUserInfo.getName()));
        objClsUserInfo.setLoginStatus(mPreferences.getString(LoginStatus, objClsUserInfo.getLoginStatus()));
        objClsUserInfo.setRegisteredmobilenumber(mPreferences.getString(RegisteredMobileNumber, objClsUserInfo.getRegisteredmobilenumber()));
        objClsUserInfo.setLoginStatus(mPreferences.getString(LoginStatus, objClsUserInfo.getLoginStatus()));
        objClsUserInfo.setEmailaddress(mPreferences.getString(EmailAddress, objClsUserInfo.getEmailaddress()));
        objClsUserInfo.setState(mPreferences.getString(State, objClsUserInfo.getState()));

        return objClsUserInfo;
    }

    public static String round(double input, int places) {
        String strDouble = String.format("%." + places + "f", input);
        return strDouble;
    }

    public static String getCurrentDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        return sdf.format(d);
    }

    @SuppressLint("NewApi")
    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }


            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    public static String formatNumber(int decimals, double number) {
        StringBuilder sb = new StringBuilder(decimals + 2);
        sb.append("#.");
        for (int i = 0; i < decimals; i++) {
            sb.append("0");
        }
        return new DecimalFormat(sb.toString()).format(number);
    }

    public static String getEntryDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }


    public static String readableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups))
                + " " + units[digitGroups];
    }


    public static void generateFileFromString(Context context, String sFileName,
                                              String folderName, String sBody) {
        FileWriter writer = null;
        try {

            Log.e("Check", "generateFileFromString: " + sFileName);
//            File root = new File(Environment.getExternalStorageDirectory(), "/" + folderName);
            File root = new File(String.valueOf(context.getExternalFilesDir(null)), "/" + folderName);
            if (!root.exists()) {
                root.mkdirs();
            }

            File gpxfile = new File(root, sFileName);
            writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressLint("NewApi")
    public static void getStartNetworkJOB(Context context) {

        @SuppressLint({"NewApi", "LocalSuppress"}) JobInfo myJob = new JobInfo.Builder(
                78,
                new ComponentName(context, CheckNetworkJob.class))
                .setMinimumLatency(1000)
                .setOverrideDeadline(2000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .build();

        if (!isJobServiceOn(context)) {
            Log.d("--isJobServiceOn--", "Job is not Scheduled");

            @SuppressLint({"NewApi", "LocalSuppress"}) JobScheduler jobScheduler = (JobScheduler)
                    context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            jobScheduler.schedule(myJob);
        } else {
            Log.d("--isJobServiceOn--", "Job is Scheduled");
        }

    }

    public static boolean whatsappInstalledOrNot(String uri, Context context) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    /**
     * Send Message to Default WhatsApp whether
     * Normal WhatsApp or business WhatsApp.
     *
     * @param context
     * @param mobile
     * @param message
     * @param getWhatsAppDefaultApp
     */
    @SuppressLint("CheckResult")
    public static void SentMessageToWhatApp(Context context,
                                            String mobile,
                                            String message,
                                            String getWhatsAppDefaultApp) {

        boolean isWhatsappInstalled = ClsGlobal.whatsappInstalledOrNot("com.whatsapp", context);
        boolean isWhatsappBusinessInstalled = ClsGlobal.whatsappInstalledOrNot("com.whatsapp.w4b", context);

        if (isWhatsappBusinessInstalled || isWhatsappInstalled) {
            try {
                String url = "https://api.whatsapp.com/send?phone=" +
                        mobile + "&text=" + URLEncoder.encode(message, "UTF-8");

                Log.e("1message- ", message);
                Log.e("url- ", url);

                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.setAction(Intent.ACTION_VIEW);

                if (!getWhatsAppDefaultApp.equalsIgnoreCase("")
                        && getWhatsAppDefaultApp.equalsIgnoreCase("WhatsApp")) {
                    sendIntent.setPackage("com.whatsapp");
                    Log.e("message", "WhatsApp");
                } else if (!getWhatsAppDefaultApp.equalsIgnoreCase("")
                        && getWhatsAppDefaultApp.equalsIgnoreCase("Business WhatsApp")) {
                    sendIntent.setPackage("com.whatsapp.w4b");
                    Log.e("message", "Business WhatsApp");
                }

                Log.e("message", message);

                sendIntent.setData(Uri.parse(url));
                context.startActivity(sendIntent);

            } catch (Exception e) {
                if (!getWhatsAppDefaultApp.equalsIgnoreCase("")
                        && getWhatsAppDefaultApp.equalsIgnoreCase("WhatsApp")) {
                    Toast.makeText(context, "WhatsApp Not Install!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Business WhatsApp Not Install!", Toast.LENGTH_LONG).show();
                }
            }

        } else {
            Toast.makeText(context, "WhatsApp Not Install!", Toast.LENGTH_LONG).show();
        }

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private static SharedPreferences mPreferences;
    public static final String mPreferncesName = "MyPerfernces";

    /**
     * Get default CountryCode.
     *
     * @param context
     * @return
     */
    public static String getCountryCodePreferences(Context context) {
        mPreferences = context.getSharedPreferences(mPreferncesName, MODE_PRIVATE);

        return mPreferences.getString("Country_code", "") != null
                && !mPreferences.getString("Country_code", "")
                .equalsIgnoreCase("") ?
                mPreferences.getString("Country_code", "") : "Select Code";

    }

    @SuppressLint("NewApi")
    static boolean isJobServiceOn(Context context) {
        @SuppressLint({"NewApi", "LocalSuppress"}) JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        boolean hasBeenScheduled = false;

        for (JobInfo jobInfo : scheduler.getAllPendingJobs()) {
            if (jobInfo.getId() == 78) {
                hasBeenScheduled = true;
                break;
            }
        }

        return hasBeenScheduled;
    }


    public static void openSnackBarExcelFile(Activity context, String _FilePath, String _title) {
        LinearLayout.LayoutParams objLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        Snackbar snackbar = Snackbar.make(context.findViewById(R.id.coordinatorLayout),
                "Open file.",
                Snackbar.LENGTH_LONG);


        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        layout.setPadding(0, 0, 0, 0);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View snackView = context.getLayoutInflater().inflate(R.layout.my_snackbar, null);
        TextView txt_open = snackView.findViewById(R.id.txt_open);


        layout.addView(snackView, objLayoutParams);
        snackbar.show();
    }


    public static String getChangeDateFormat(String dbFormateString) {
        final String outFormat = "dd MMM yyyy hh:mm aa";
        final String InFORMAT = "dd-MM-yyyy hh mm aa";
        String result = "";
        try {
            if (dbFormateString != null && !dbFormateString.isEmpty() && dbFormateString != "") {
                Date date = new SimpleDateFormat(InFORMAT, Locale.ENGLISH).parse(dbFormateString);
                DateFormat formatter = new SimpleDateFormat(outFormat, Locale.getDefault());
                result = formatter.format(date.getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

}
