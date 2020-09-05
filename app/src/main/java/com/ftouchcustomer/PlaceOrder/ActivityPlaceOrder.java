package com.ftouchcustomer.PlaceOrder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.ftouchcustomer.Address.ActivityAddress;
import com.ftouchcustomer.Database.Entity.ClsOrderDetailsEntity;
import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.Global.ClsUserInfo;
import com.ftouchcustomer.Global.FileUploader;

import com.ftouchcustomer.LoginActivity;
import com.ftouchcustomer.NavigationTabs.BottomNavigationActivity;
import com.ftouchcustomer.Orders.ClsGetMerchantPaymentMethodList;
import com.ftouchcustomer.R;
import com.ftouchcustomer.ViewModelClass.AddToItemViewModel;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

import static com.ftouchcustomer.Global.ConnectionCheckBroadcast.CheckInternetConnection;

public class ActivityPlaceOrder extends AppCompatActivity {

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    ImageView iv_get_address;
    TextView txt_delivery_description, txt_delivery_charges, txt_address, txt_name;
    TextView txt_mobile, txt_place_order;
    List<ClsOrderDetailsEntity> lst = new ArrayList<>();
    List<ClsOrderDetailsEntity> filterList = new ArrayList<>();
    int _orderDetailID = 0;
    int _customerID = 0;
    String _customerCode = "";
    EditText edt_comment;
    AddToItemViewModel addToItemViewModel;
    public static final int REQUEST_CODE = 102;
    String address = "";
    String type = "";
    String name = "";
    String mobile = "";
    int addressID = 0;
    TextView txt_payment_mode;
    List<ClsGetMerchantPaymentMethodList> lstClsGetMerchantPaymentMethodLists = new ArrayList<>();
    ClsUserInfo clsUserInfo;
    LinearLayout ll_address;
    LinearLayout ll_select_address;
    ImageView iv_change;
    RadioGroup rg;
    RadioButton rb_cod;
    double _deliveryCharges = 0.0;
    String _grandTotal = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        addToItemViewModel = new
                ViewModelProvider(this).get(AddToItemViewModel.class);

        main();
    }

    @SuppressLint({"ShowToast", "SetTextI18n"})
    private void main() {

        clsUserInfo = ClsGlobal.getUserInfo(this);
        _orderDetailID = getIntent().getIntExtra("_orderDetailID", 0);
        _customerID = getIntent().getIntExtra("_customerID", 0);
        _customerCode = getIntent().getStringExtra("_customerCode");
        _grandTotal = getIntent().getStringExtra("_grandTotal");

        lst = (List<ClsOrderDetailsEntity>) getIntent().getSerializableExtra("List");


        Log.d("--grandTotal--", "_grandTotal: " + _grandTotal);


        ll_select_address = findViewById(R.id.ll_select_address);
        txt_delivery_charges = findViewById(R.id.txt_delivery_charges);
        txt_name = findViewById(R.id.txt_name);
        ll_address = findViewById(R.id.ll_address);
        txt_mobile = findViewById(R.id.txt_mobile);
        iv_change = findViewById(R.id.iv_change);
        txt_delivery_description = findViewById(R.id.txt_delivery_description);
        rg = findViewById(R.id.rg);
        int selectedId = rg.getCheckedRadioButtonId();
        rb_cod = findViewById(selectedId);

        edt_comment = findViewById(R.id.edt_comment);
        txt_place_order = findViewById(R.id.txt_place_order);

        txt_payment_mode = findViewById(R.id.txt_payment_mode);
        SpannableString cString = new SpannableString(getString(R.string.other));
        cString.setSpan(new UnderlineSpan(), 0, cString.length(), 0);
        txt_payment_mode.setText(cString);

        txt_address = findViewById(R.id.txt_address);
        txt_address.setHint("SELECT DELIVERY ADDRESS");
        iv_get_address = findViewById(R.id.iv_get_address);


        iv_get_address.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ActivityAddress.class);
            intent.putExtra("placeOrder", "placeOrder");
            startActivityForResult(intent, REQUEST_CODE);
        });

        iv_change.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ActivityAddress.class);
            intent.putExtra("placeOrder", "placeOrder");
            startActivityForResult(intent, REQUEST_CODE);
        });

        txt_place_order.setOnClickListener(v -> {
            requestPermission();
        });

        getMerchantPaymentMethod();

        txt_payment_mode.setOnClickListener(v -> {

            if (lstClsGetMerchantPaymentMethodLists.size() > 0) {
                displayPaymentMethod(lstClsGetMerchantPaymentMethodLists);
            } else {
                Toast.makeText(ActivityPlaceOrder.this, "No other payment method found!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Boolean Validation() {

        if (txt_address.getText() == null || txt_address.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please select address first!", Toast.LENGTH_SHORT).show();
            txt_address.requestFocus();
            return false;
        }

        if (!CheckInternetConnection(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "You are not connected to Internet", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @SuppressLint("SetTextI18n")
    void displayPaymentMethod(List<ClsGetMerchantPaymentMethodList> lst) {

        Dialog dialog = new Dialog(Objects.requireNonNull(ActivityPlaceOrder.this));
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

        AdapterPaymentMethod adapterTimeList =
                new AdapterPaymentMethod(ActivityPlaceOrder.this, lst);

        lv_lst.setAdapter(adapterTimeList);

        dialog.show();
    }

//    ("CHA001")

    @SuppressLint("SetTextI18n")
    void getMerchantPaymentMethod() {


       /* addToItemViewModel.getMerchantPaymentMethod("CHA001").observe(this,
                clsGetMerchantPaymentMethodResponse -> {
*/

        addToItemViewModel.getMerchantPaymentMethod(_customerCode).observe(this,
                clsGetMerchantPaymentMethodResponse -> {

                    lstClsGetMerchantPaymentMethodLists = clsGetMerchantPaymentMethodResponse.getData();
                    Log.d("--List--", "size: " + lstClsGetMerchantPaymentMethodLists.size());
                    Log.d("--List--", "_customerCode: " + _customerCode);

                    Log.d("--List--", "getDeliveryCharges: " + clsGetMerchantPaymentMethodResponse.getDeliveryCharges());
                    Log.d("--List--", "getDeliveryChargesDescription: " + clsGetMerchantPaymentMethodResponse.getDeliveryChargesDescription());


                    if (clsGetMerchantPaymentMethodResponse.getDeliveryCharges() != null &&
                            !clsGetMerchantPaymentMethodResponse.getDeliveryCharges().equalsIgnoreCase("") &&
                            !clsGetMerchantPaymentMethodResponse.getDeliveryCharges().equalsIgnoreCase("0.0")) {

                        txt_delivery_charges.setText("Delivery Charges: \u20B9 " +
                                clsGetMerchantPaymentMethodResponse.getDeliveryCharges());

                        _deliveryCharges = Double.parseDouble(clsGetMerchantPaymentMethodResponse.getDeliveryCharges());
                    } else {
                        txt_delivery_charges.setText("Delivery Charges: \u20B9 0.0");

                        _deliveryCharges = 0.0;
                    }


                    if (clsGetMerchantPaymentMethodResponse.getDeliveryChargesDescription() != null &&
                            !clsGetMerchantPaymentMethodResponse.getDeliveryChargesDescription().equalsIgnoreCase("")) {
                        txt_delivery_description.setText("Description: " +
                                clsGetMerchantPaymentMethodResponse.getDeliveryChargesDescription());
                    } else {
                        txt_delivery_description.setText("Description: No Delivery Charges");
                    }

                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(lstClsGetMerchantPaymentMethodLists);
                    Log.e("--URL--", "Gson: " + jsonInString);

                });
    }

    private ProgressDialog pd;

    @SuppressLint("StaticFieldLeak")
    void callPlaceOrderAPI() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.d("--steps--", "step 1");
                pd = ClsGlobal._prProgressDialog(ActivityPlaceOrder.this,
                        "please wait your order is in process.", false);
                Log.d("--steps--", "step 2");
                pd.show();
                Log.d("--steps--", "step 3");
            }

            @Override
            protected Void doInBackground(Void... voids) {


                Log.d("--steps--", "step 4");
                if (lst != null && lst.size() != 0) {
                    Log.d("--steps--", "step 5");

                    filterList = StreamSupport.stream(lst)
                            .filter(str -> !str.isHeader())
                            .collect(Collectors.toList());

                    Log.d("--steps--", "step 6");
                    Gson gson = new Gson();
                    String itemsJsonString = gson.toJson(filterList);
                    Log.e("--Gson--", "value: " + itemsJsonString);


                    ClsGlobal.generateFileFromString(ActivityPlaceOrder.this, new File(ClsGlobal.getItemsJsonFile(ActivityPlaceOrder.this)).getName(),
                            ".fTouchTemp", itemsJsonString);

                    Log.d("--steps--", "step 7");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void insertResult) {
                super.onPostExecute(insertResult);


                Log.d("--steps--", "step 8");

                FileUploader fileUploader = new FileUploader(ActivityPlaceOrder.this);
                Log.d("--steps--", "step 9");

                File file = new File(ClsGlobal.getItemsTemp_Folder(ActivityPlaceOrder.this));
                Log.d("--steps--", "step 10");
                if (!file.exists()) {
                    Log.d("--steps--", "step 11");
                    file.mkdirs();
                    Log.d("--steps--", "step 12: " + file);

                } else {
                    Log.d("--steps--", "step 13 ELSE");
                }
                Log.d("--steps--", "step 14: " + file.getParentFile());


                fileUploader.placeOrder(new File(ClsGlobal.getItemsJsonFile(ActivityPlaceOrder.this)),
                        _customerID,
                        _customerCode, type,
                        filterList.size(), addressID, edt_comment.getText().toString(),
                        rb_cod.getText().toString(), ClsGlobal.getPlaceOrderDate(),
                        "Pending", "", _deliveryCharges,
                        Double.parseDouble(_grandTotal), Double.parseDouble(_grandTotal));

                Log.d("--steps--", "step 15");
                Log.d("--steps--", ": ");

                fileUploader.showUploadProgressBar(false);

                Log.d("--steps--", "step 16");

                fileUploader.SetCallBack(new FileUploader.FileUploaderCallback() {
                    @Override
                    public void onError() {
//                        latch.countDown();
                        Log.d("--steps--", "step 17");
                    }


                    @Override
                    public void onFinish(String responses) {

                        addToItemViewModel.DeleteOrderByCode(_customerCode, "Order placed successfully!");
                        Log.d("--steps--", "step 18");
                        pd.dismiss();
                        Log.d("--steps--", "step 19");

                        Intent i = new Intent(getApplicationContext(), BottomNavigationActivity.class);
                        Log.d("--steps--", "step 20");
                        i.putExtra("placeOrder", "placeOrder");
                        Log.d("--steps--", "step 21");
                        startActivity(i);
                        Log.d("--steps--", "step 22");
                        finish();
                        Log.d("--steps--", "step 23");

                    }

                    @Override
                    public void onProgressUpdate(int currentpercent, int totalpercent, String msg) {
                        Log.e("--Test--", "msg: " + msg);
                        Log.d("--steps--", "step 24: " + msg);

                    }
                });
            }
        }.execute();
        Log.d("--steps--", "step 25");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                if (data != null) {
                    ll_address.setVisibility(View.VISIBLE);
                    ll_select_address.setVisibility(View.GONE);
                    name = data.getStringExtra("name");
                    mobile = data.getStringExtra("mobile");
                    address = data.getStringExtra("address");
                    type = data.getStringExtra("type");
                    addressID = data.getIntExtra("addressID", 0);

                    Log.d("--steps--", "addressID: " + addressID);

                } else {
                    txt_address.setHint("NO ADDRESS SELECTED");
                    ll_address.setVisibility(View.GONE);
                    ll_select_address.setVisibility(View.VISIBLE);
                }
                Log.d("--add--", "address: " + address);
                txt_address.setText(address);
                txt_name.setText(name);
                txt_mobile.setText(mobile);
            }
        } catch (Exception ex) {
            Toast.makeText(ActivityPlaceOrder.this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void requestPermission() {


        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (Validation()) {
                if (clsUserInfo.getRegisteredmobilenumber() != null &&
                        !clsUserInfo.getRegisteredmobilenumber().isEmpty()) {
                    showAlertDialog("NoSkip");
                } else {
                    showAlertDialog("skip");
                }
            }
        } else {
            if (Validation()) {
                callPlaceOrderAPI();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
               /* boolean validation = Validation();
                if (validation) {
                    callPlaceOrderAPI();
                }*/
            } else {
                Toast.makeText(this, "Please allow Storage permission.", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @SuppressLint("SetTextI18n")
    private void showAlertDialog(String mode) {
        final Dialog dialog = new Dialog(ActivityPlaceOrder.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dilaog_cust_alert);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView content = dialog.findViewById(R.id.content);
        Button btn_ok = dialog.findViewById(R.id.btn_ok);


        if (mode != null && mode.equalsIgnoreCase("skip")) {
            content.setText(getText(R.string.alert_skip));
        } else {
            content.setText(getText(R.string.alert_permission));
        }

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                if (mode != null && mode.equalsIgnoreCase("skip")) {
                    Intent i = new Intent(ActivityPlaceOrder.this, LoginActivity.class);
                    i.putExtra("noLogin", "noLogin");
                    startActivity(i);
                    finish();
                } else {
//                    requestPermission();
                    ActivityCompat.requestPermissions(ActivityPlaceOrder.this, ClsGlobal.STORAGE_PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);

                }

            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


}
