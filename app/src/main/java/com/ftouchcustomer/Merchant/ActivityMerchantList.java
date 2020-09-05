package com.ftouchcustomer.Merchant;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.Global.ClsUserInfo;
import com.ftouchcustomer.LoginActivity;
import com.ftouchcustomer.NavigationTabs.BottomNavigationActivity;
import com.ftouchcustomer.Orders.ActivityOrderItemList;
import com.ftouchcustomer.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.ftouchcustomer.Global.ConnectionCheckBroadcast.CheckInternetConnection;

public class ActivityMerchantList extends AppCompatActivity {

    private static final int PICK_GPS = 7;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    RecyclerView recyclerView;
    TextView tv_no_item_found;
    private MerchantViewModel merchantViewModel;
    private FusedLocationProviderClient fusedLocationProviderClient;
    ProgressBar progress_bar;
    ClsUserInfo clsUserInfo;

    String merchantName = "";
    String city = "";
    String pinCode = "";
    String category = "";
    String address = "";
    private String productName = "";
    private String requiredFav = "";
    private String requiredStore = "";

    private ProgressDialog pd;
    private List<ClsMerchantResponseList> responseList = new ArrayList<>();
    private AdapterMerchantList adapter;

    private double lat;
    private double lang;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);

        clsUserInfo = ClsGlobal.getUserInfo(this);

        merchantName = getIntent().getStringExtra("merchantName");
        city = getIntent().getStringExtra("city");
        pinCode = getIntent().getStringExtra("pinCode");
        category = getIntent().getStringExtra("category");

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        merchantViewModel = new ViewModelProvider(this).get(MerchantViewModel.class);

        FloatingActionButton fab = findViewById(R.id.fab_add);
        recyclerView = findViewById(R.id.recyclerView);
        tv_no_item_found = findViewById(R.id.tv_no_item_found);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        progress_bar = findViewById(R.id.progress_bar);
        adapter = new AdapterMerchantList(this, "FragmentMerchant");
        recyclerView.setAdapter(adapter);

        requestPermission();


        fab.setOnClickListener(view -> {
            if (responseList != null && responseList.size() != 0) {
                if (CheckInternetConnection(ActivityMerchantList.this)) {
                    Intent intent = new Intent(this, ActivityFilterMerchantList.class);
                    startActivityForResult(intent, REQUEST_CODE);

                } else {
                    Toast.makeText(ActivityMerchantList.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(ActivityMerchantList.this, "There is no store!", Toast.LENGTH_SHORT).show();

            }

        });

        adapter.setOnMobileClick(clsMerchantResponseList -> {
            if (clsMerchantResponseList.getMobileNo() != null) {

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + clsMerchantResponseList.getMobileNo()));
                startActivity(callIntent);
            } else {
//                Toast.makeText(this, "no contact details found", Toast.LENGTH_SHORT).show();
                dialog(clsMerchantResponseList.getCustomerCode(),
                        "Mobile Number", "Asking For Mobile Number");
            }
        });

        adapter.setOnTimeClick((lst, clsMerchantResponseList) -> {
            if (lst != null && lst.size() != 0) {
                displayTiming(lst);
            } else {
//                Toast.makeText(this, "No timing detail found", Toast.LENGTH_SHORT).show();
                dialog(clsMerchantResponseList.getCustomerCode()
                        , "Timing", "Asking For Timing");
            }
        });

        adapter.setOnShopNowClick(lst -> {
            if (lst.getMenuUrl() != null && !lst.getMenuUrl().equalsIgnoreCase("Not found")) {
                shopNow(lst);
            } else {
                Toast.makeText(this, "No Online items found", Toast.LENGTH_SHORT).show();
            }
        });


        adapter.setOnWtsAppClick(clsMerchantResponseList -> {
            if (clsMerchantResponseList.getMobileNo() != null) {
                ClsGlobal.SentMessageToWhatApp(this,
                        "+91 " + clsMerchantResponseList.getMobileNo(),
                        "Hiii", "WhatsApp");
            } else {
//                Toast.makeText(this, "Not Found WhatsApp Number", Toast.LENGTH_SHORT).show();
                dialog(clsMerchantResponseList.getCustomerCode(),
                        "WhatsApp Number", "Asking For WhatsApp Number");
            }
        });

        adapter.setOnMainImageClick(clsMerchantResponseList -> {
            if (clsMerchantResponseList.getLogoUrl().contains("Not found")) {
                dialog(clsMerchantResponseList.getCustomerCode()
                        , "Logo", "Asking For Logo");
            }
        });

        adapter.setOnCastLayoutClick(clsMerchantResponseList -> {
            Log.d("--adapter--", "category: " + clsMerchantResponseList.getCategories());
            if (clsMerchantResponseList.getCategories() != null &&
                    !clsMerchantResponseList.getCategories().equalsIgnoreCase("")) {
            } else {
                dialog(clsMerchantResponseList.getCustomerCode(),
                        "Category", "Asking For Category");
            }
        });

        adapter.setOnMapClick(clsMerchantResponseList -> {
            if (clsMerchantResponseList.getAddress() != null) {
                showAddressDialog(clsMerchantResponseList);
            } else {
                Toast.makeText(this, "No address found.", Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnFavItemClick((clsMerchantResponseList, tgb_fav) -> {


            if (clsUserInfo.getLoginStatus() != null &&
                    clsUserInfo.getLoginStatus().equalsIgnoreCase("Active")) {

                if (CheckInternetConnection(ActivityMerchantList.this)) {
                    // fill here after API suecss reposne . just unComment after solve
                    // clsMerchantResponseList.setIsFavorite(isFavorite);

                    getFavMerchant(clsMerchantResponseList,
                            clsMerchantResponseList.getFavourite().equalsIgnoreCase("Yes"), tgb_fav);
                } else {
                    Toast.makeText(ActivityMerchantList.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                }


            } else {

                tgb_fav.setChecked(false);
                checkLoginStatusDialog();
            }


        });

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(
                linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                progress_bar.setVisibility(View.VISIBLE);

                if (CheckInternetConnection(ActivityMerchantList.this)) {
                    Log.d("getMerchantList", "onLoadMore: ");
                    getMerchantList(++page);
                } else {
                    progress_bar.setVisibility(View.GONE);
                    Toast.makeText(ActivityMerchantList.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void checkLoginStatusDialog() {

        final Dialog dialog = new Dialog(ActivityMerchantList.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dilaog_cust_alert);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView content = dialog.findViewById(R.id.content);
        Button btn_ok = dialog.findViewById(R.id.btn_ok);
        btn_ok.setText("Login now?");
        Button btn_no = dialog.findViewById(R.id.btn_no);
        btn_no.setText("Later");
        btn_no.setVisibility(View.VISIBLE);

        content.setText(getText(R.string.alert_login_status));

        btn_ok.setOnClickListener(v -> {
            dialog.dismiss();
            //Reirect to login  Mode levo padse Lambu thy jase hmna...
            // no issue// login thaay to home par j redirect karse to pan chalse
            // to home walo j mode pass kari de. je stting ma login par che te j


            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("noLogin", "");
            startActivity(intent);

        });

        btn_no.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }

    void getFavMerchant(ClsMerchantResponseList clsMerchantResponseList, boolean isFavorite,
                        ToggleButton tgb_fav) {

        ClsFavoriteMerchantParams obj = new ClsFavoriteMerchantParams();
        obj.setCustomerID(Integer.parseInt(clsUserInfo.getCustomerId()));
        obj.setCustomerMobileNo(clsUserInfo.getRegisteredmobilenumber());
        obj.setMerchantCode(clsMerchantResponseList.getCustomerCode());
        obj.setFavourite(String.valueOf(!isFavorite));

        Gson gson = new Gson();
        String jsonInString = gson.toJson(obj);
        Log.e("--ClsFavoriteMerchant--", "Params: " + jsonInString);

        pd = ClsGlobal._prProgressDialog(ActivityMerchantList.this,
                "please wait...", false);
        pd.show();

        merchantViewModel.favoriteMerchant(obj).observe(this, clsFavoriteMerchantResponse -> {
            if (clsFavoriteMerchantResponse != null) {
                pd.dismiss();

                Gson gson1 = new Gson();
                String jsonInString1 = gson1.toJson(clsFavoriteMerchantResponse);
                Log.e("--ClsFavoriteMerchant--", "Response: " + jsonInString1);

                String success = clsFavoriteMerchantResponse.getSuccess();
                if (success.equalsIgnoreCase("1")) {
// Aiya

                    if (obj.getFavourite().equalsIgnoreCase("false")) {
                        tgb_fav.setChecked(false);
                        Toast.makeText(getApplicationContext(), clsMerchantResponseList.getBusinessName() +
                                " is removed from your favorite list!", Toast.LENGTH_SHORT).show();

                        clsMerchantResponseList.setFavourite("No");

                        adapter.updateList(clsMerchantResponseList);

                    } else if (obj.getFavourite().equalsIgnoreCase("true")){
                        tgb_fav.setChecked(true);

                        clsMerchantResponseList.setFavourite("Yes");
                        adapter.updateList(clsMerchantResponseList);
                        Toast.makeText(getApplicationContext(), clsMerchantResponseList.getBusinessName() +
                                " is added to your favorite list!", Toast.LENGTH_SHORT).show();
                    }


                } else if (success.equalsIgnoreCase("0")) {

                    Toast.makeText(this, "We are facing issues to manage your favourite Store..!" +
                                    " please contact to support",
                            Toast.LENGTH_SHORT).show();
                } else {

                }
            } else {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Something went wrong,please try again"
                        , Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("--ClsFavoriteMerchant--", "isFavorite: " + isFavorite);

    }

    void shopNow(ClsMerchantResponseList clsMerchantResponseList) {

        Intent intent = new Intent(this, ActivityOrderItemList.class);
        intent.putExtra("clsMerchantResponseList", clsMerchantResponseList);
        startActivity(intent);
    }

    void displayTiming(List<ClsTiming> lstClsTimings) {

        Dialog dialog = new Dialog(Objects.requireNonNull(this));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_listview);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(lp);

        ListView lv_lst = dialog.findViewById(R.id.lv_lst);
        AdapterTimeList adapterTimeList =
                new AdapterTimeList(this, lstClsTimings);
        lv_lst.setAdapter(adapterTimeList);
        dialog.show();
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("FragmentMerchant", "onResume: ");
    }

    private void getMerchantList(int currentPageNo) {


        ClsMerchantParams obj = new ClsMerchantParams();

        obj.setCurrentIndex(currentPageNo);
        obj.setLatitude(lat);
        obj.setLongitude(lang);
        obj.setCustomerMobileNo(clsUserInfo.getRegisteredmobilenumber());//OK

        if (merchantName == null || merchantName.isEmpty())
            obj.setCompanyName(null);
        else
            obj.setCompanyName(merchantName.trim());//Y to lower????  ahiya error aave che? aa 2 line prok wait

        if (productName == null || productName.isEmpty())
            obj.setProductName(null);
        else
            obj.setProductName(productName.trim());//Y to lower????  ahiya error aave che? aa 2 line prok wait

        if (pinCode == null || pinCode.isEmpty())
            obj.setPinCode(null);
        else
            obj.setPinCode(pinCode);

        if (city == null || city.isEmpty())
            obj.setCity(null);
        else
            obj.setCity(city);

        if (category == null || category.isEmpty())
            obj.setCategory(null);
        else
            obj.setCategory(category);

        if (requiredFav == null || requiredFav.isEmpty())
            obj.setFavouriteMerchant(null);
        else
            obj.setFavouriteMerchant(requiredFav);

        if (requiredStore == null || requiredStore.isEmpty())
            obj.setOnlineStore(null);
        else
            obj.setOnlineStore(requiredStore);

        if (address == null || address.isEmpty())
            obj.setAddress(null);
        else
            obj.setAddress(address);

        obj.setMobileNo(null);
        obj.setState(null);

        Gson gson = new Gson();
        String jsonInString = gson.toJson(obj);
        Log.e("--params--", "obj: " + jsonInString);



        merchantViewModel.getMerchantList(obj)
                .observe(this, clsMerchantResponse -> {
                    pd.dismiss();

                    String msg = clsMerchantResponse.getSuccess();
                    int _totalPages = clsMerchantResponse.getTotalPages();
                    Log.e("--params--", "_totalPages: " + _totalPages);
                    Log.e("--params--", "_totalRecords: " + clsMerchantResponse.getTotalRecords());

                    if ("0".equals(msg)) {
                        if (responseList.size() <= 0) {
                            tv_no_item_found.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }

                        progress_bar.setVisibility(View.GONE);


                    } else if ("1".equals(msg)) {
                        if (clsMerchantResponse.getData().size() > 0) {
                            responseList.addAll(clsMerchantResponse.getData());
                        }

                        if (responseList.size() > 0) {
                            tv_no_item_found.setVisibility(View.GONE);
                            progress_bar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            adapter.addList(responseList);
                        }
                    }

                });

    }

    private void dialog(String merchantCode, String askingFor, String label) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(label);

        LayoutInflater inflater = this.getLayoutInflater();
        View viewInflated = inflater.inflate(R.layout.dialog_edittext, null);

        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        builder.setView(viewInflated);

        builder.setPositiveButton("Save", (dialog, which) ->
                getMerchantProfileAdd(merchantCode, askingFor, input.getText().toString()));
        builder.setNegativeButton("Cancel", (dialog, which) ->
                dialog.cancel());

        builder.show();
    }

    private void getMerchantProfileAdd(String merchantCode, String askingFor, String comment) {

        ClsMerchantProfileAdd obj = new ClsMerchantProfileAdd();
        obj.setMerchantCode(merchantCode);
        obj.setCustomerId(clsUserInfo.getCustomerId());
        obj.setCustomerMobileNo(clsUserInfo.getRegisteredmobilenumber());
        obj.setName(clsUserInfo.getName());
        obj.setAskingFor(askingFor);
        obj.setComment(comment);

        merchantViewModel.getMerchantProfileAdd(obj)
                .observe(this, clsMerchantProfileAdd -> {

                    if (clsMerchantProfileAdd.getSuccess().equalsIgnoreCase("1")) {
                        Toast.makeText(this, "Requested Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, clsMerchantProfileAdd.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void requestPermission() {


        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,

                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, ClsGlobal.LOCATION_PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            fetchLastLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                fetchLastLocation();
            } else {
                Toast.makeText(this, "Please allow permission to get Service Provider near by you.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, BottomNavigationActivity.class);
                startActivity(intent);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void fetchLastLocation() {

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ex) {
                Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
            }
            try {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ex) {
                Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
            }

            if (!gps_enabled && !network_enabled) {
                showGPSDisabledAlertToUser();
            } else {
                Task<Location> task = fusedLocationProviderClient.getLastLocation();
                task.addOnSuccessListener(location -> {
                    if (location != null) {
                        currentLocation = location;

                        lat = currentLocation.getLatitude();
                        lang = currentLocation.getLongitude();
                        Log.d("--abc--", "fetchLastLocation: " + lat + "-" + lang);

                        pd = ClsGlobal._prProgressDialog(this, "Loading stores...", false);
                        pd.show();
                        if (CheckInternetConnection(this)) {
                            getMerchantList(1);
                        } else {
                            pd.dismiss();
                            Toast.makeText(this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } else {
            Toast.makeText(this, "No Location recorded", Toast.LENGTH_SHORT).show();
        }
    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device! please enable to get store near by you!")
                .setCancelable(false)
                .setPositiveButton("Enable now",
                        (dialog, id) -> {
                            Intent callGPSSettingIntent = new Intent(
                                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(callGPSSettingIntent, PICK_GPS);
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                (dialog, id) -> dialog.cancel());
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public static final int REQUEST_CODE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_GPS) {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                if (!isGpsEnabled) {
                    Intent intent = new Intent(this, BottomNavigationActivity.class);
                    startActivity(intent);
                    Toast.makeText(this, "Please on GPS to get Service Provider near by you.", Toast.LENGTH_SHORT).show();
                } else {
                    fetchLastLocation();
                }
            }

            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                merchantName = data.getStringExtra("requiredMerchantName");
                city = data.getStringExtra("requiredCity");
                pinCode = data.getStringExtra("requiredPinCode");
                category = data.getStringExtra("requiredCategory");
                Log.e("--params--", "category: " + category);

                address = data.getStringExtra("requiredAddress");
                requiredFav = data.getStringExtra("requiredFav");
                requiredStore = data.getStringExtra("requiredStore");
                Log.e("--params--", "requiredStore: " + requiredStore);

                responseList.clear();
                adapter.notifyDataSetChanged();
                getMerchantList(1);
            }
        } catch (Exception ex) {
            Toast.makeText(ActivityMerchantList.this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("SetTextI18n")
    private void showAddressDialog(ClsMerchantResponseList clsMerchantResponseList) {
        final Dialog dialog = new Dialog(ActivityMerchantList.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_warning);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView content = dialog.findViewById(R.id.content);
        content.setText(clsMerchantResponseList.getAddress());

        dialog.findViewById(R.id.bt_close).setOnClickListener(v -> {
            dialog.dismiss();
            Double _latitude = Double.valueOf(clsMerchantResponseList.getLatitude());
            Double _longitude = Double.valueOf(clsMerchantResponseList.getLongitude());

            String uri = String.format(Locale.ENGLISH,
                    "http://maps.google.com/maps?q=loc:%f,%f", _latitude, _longitude);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}