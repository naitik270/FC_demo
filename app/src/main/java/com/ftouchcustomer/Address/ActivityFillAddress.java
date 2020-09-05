package com.ftouchcustomer.Address;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.ftouchcustomer.Address.getAddress.ClsGetCustomerAddressListResponse;
import com.ftouchcustomer.Address.postAddress.ClsCustomerAddress;
import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.Global.ClsUserInfo;
import com.ftouchcustomer.Profile.City.ClsCity;
import com.ftouchcustomer.Profile.Country.CountryViewModels;
import com.ftouchcustomer.Profile.State.StateListActivity;
import com.ftouchcustomer.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ActivityFillAddress extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private static final int PICK_CONTACT = 5;
    private CountryViewModels countryViewModels;
    private FillAddressViewModel fillAddressViewModel;
    private List<ClsCity> cities = new ArrayList<>();
    private List<String> citiesName = new ArrayList<>();
    ClsGetCustomerAddressListResponse listAddress;

    EditText et_mobile, et_name;
    EditText et_address1, et_address2;
    EditText et_landmark, et_pin_code;
    TextView tv_address_type, tv_save, tv_state;
    ImageView iv_expand_more, iv_browse_contact;
    CheckBox cb_default;
    RelativeLayout layoutState, relative_layout_save;
    TextView toolbar_name;
    private AutoCompleteTextView tv_city;
    boolean defaultAddress = false;
    int stateId = 0;
    ProgressDialog pd;
    int addressId = 0;
    ClsUserInfo clsUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_addresses);

        clsUserInfo = ClsGlobal.getUserInfo(this);

        countryViewModels = new ViewModelProvider(this).get(CountryViewModels.class);
        fillAddressViewModel = new ViewModelProvider(this).get(FillAddressViewModel.class);

        Intent intent1 = getIntent();
        listAddress = (ClsGetCustomerAddressListResponse) intent1.getSerializableExtra("listResponse");
        addressId = intent1.getIntExtra("addressId", 0);

        Gson gson = new Gson();
        String jsonInString = gson.toJson(listAddress);
        Log.e("--mode--", "onCreate: " + jsonInString);
        Log.e("--mode--", "addressId: " + addressId);


        et_mobile = findViewById(R.id.et_mobile);
        et_mobile.setText(clsUserInfo.getRegisteredmobilenumber());
        et_name = findViewById(R.id.et_name);
        et_address1 = findViewById(R.id.et_address1);
        et_address2 = findViewById(R.id.et_address2);
        et_landmark = findViewById(R.id.et_landmark);
        tv_state = findViewById(R.id.tv_state);
        tv_city = findViewById(R.id.tv_city);
        et_pin_code = findViewById(R.id.et_pin_code);
        tv_address_type = findViewById(R.id.tv_address_type);
        tv_save = findViewById(R.id.tv_save);
        cb_default = findViewById(R.id.cb_default);
        toolbar_name = findViewById(R.id.toolbar_name);
        layoutState = findViewById(R.id.ll_state);
        relative_layout_save = findViewById(R.id.relative_layout_save);
        iv_expand_more = findViewById(R.id.iv_expand_more);
        iv_browse_contact = findViewById(R.id.iv_browse_contact);


        iv_expand_more.setOnClickListener(v -> {
            if (ClsGlobal.isNetworkConnected(this)) {
                Intent intent = new Intent(this, StateListActivity.class);
                startActivityForResult(intent, 2);
            } else {
                Toast.makeText(this, "Please check your Internet.", Toast.LENGTH_SHORT).show();
            }
        });

        iv_browse_contact.setOnClickListener(view -> {
            requestPermission();
        });


        cb_default.setOnCheckedChangeListener((buttonView, isChecked) -> {
            defaultAddress = isChecked;
        });

        tv_address_type.setOnClickListener(v -> selectAddressType());

        relative_layout_save.setOnClickListener(v -> {
            if (ClsGlobal.isNetworkConnected(this)) {
                checkValidations();
            } else {
                Toast.makeText(this, "Please check your Internet!", Toast.LENGTH_SHORT).show();
            }
        });


        if (addressId != 0) {
            tv_save.setText("Update");
            toolbar_name.setText("Update Address");

            if (listAddress.getDefault()) {
                cb_default.setChecked(true);
            } else {
                cb_default.setChecked(false);
            }

            et_mobile.setText(listAddress.getAddressMobileNo());
            et_name.setText(listAddress.getName());
            et_address1.setText(listAddress.getAddressLine1());
            et_address2.setText(listAddress.getAddressLine2());
            et_landmark.setText(listAddress.getLandmark());
            tv_state.setText(listAddress.getState());
            tv_state.setTag(listAddress.getStateId());
            stateId = listAddress.getStateId();

            tv_city.setText(listAddress.getCity());
            tv_address_type.setText(listAddress.getType());
            et_pin_code.setText(listAddress.getPincode());
        }

    }

    private void postCustomerAddress(int addressId) {
        Log.e("--mode--", "addressId: " + addressId);
        ClsCustomerAddress clsCustomerAddress = new ClsCustomerAddress();

        clsCustomerAddress.setAddressID(addressId);
        clsCustomerAddress.setMobileNo(clsUserInfo.getRegisteredmobilenumber());
        clsCustomerAddress.setAddressMobileNo(et_mobile.getText().toString());
        clsCustomerAddress.setName(et_name.getText().toString());
        clsCustomerAddress.setAddressLine1(et_address1.getText().toString());
        clsCustomerAddress.setAddressLine2(et_address2.getText().toString());
        clsCustomerAddress.setLandmark(et_landmark.getText().toString());
        clsCustomerAddress.setState(tv_state.getText().toString());
        clsCustomerAddress.setStateID(stateId);
        clsCustomerAddress.setCity(tv_city.getText().toString());
        clsCustomerAddress.setPinCode(et_pin_code.getText().toString());
        clsCustomerAddress.setType(tv_address_type.getText().toString());
        clsCustomerAddress.setDefault(defaultAddress);

        Gson gson = new Gson();
        String jsonInString = gson.toJson(clsCustomerAddress);
        Log.e("--mode--", "insert: " + jsonInString);

        pd = ClsGlobal._prProgressDialog(this, "Updating address...", false);
        pd.show();

        if (addressId != 0) {
            fillAddressViewModel.updateCustomerAddress(clsCustomerAddress)
                    .observe(this, clsUpdateAddress1 -> {
                        pd.dismiss();
                        String msg = clsUpdateAddress1.getSuccess();
                        Log.e("--mode--", "insert: " + jsonInString);
                        if ("0".equals(msg)) {
                            Toast.makeText(this, "Fail to update the address.", Toast.LENGTH_SHORT).show();
                        } else if ("1".equals(msg)) {
                            Toast.makeText(this, "Address updated successfully.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, ActivityAddress.class);
                            intent.putExtra("placeOrder", "");
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Please fill all the data.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            fillAddressViewModel.updateAddress(clsCustomerAddress)
                    .observe(this, clsCustomerAddress1 -> {
                        pd.dismiss();
                        String msg = clsCustomerAddress1.getSuccess();

                        if ("0".equals(msg)) {
                            Toast.makeText(this, "Fail to save Address.", Toast.LENGTH_SHORT).show();
                        } else if ("1".equals(msg)) {
                            Toast.makeText(this, "Address updated successfully.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, "Please fill all the data.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    private void selectAddressType() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String[] lan = {"Office", "Home", "Other"};
        builder.setTitle("Address type")

                .setSingleChoiceItems(lan, -1, (arg0, arg1) -> {

                })
                .setPositiveButton("OK", (dialog, id) -> {

                    int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();

                    String language = selectedPosition + " selected";
                    Log.d("--abc--", "SelectType: " + language);

                    switch (selectedPosition) {
                        case 0:
                            tv_address_type.setText("Office");
                            break;

                        case 1:
                            tv_address_type.setText("Home");
                            break;

                        case 2:
                            tv_address_type.setText("Other");
                            break;
                    }
                })

                .setNegativeButton("Cancel", (dialog, id) -> {

                })
                .show();
    }

    public void getCity(int StateID) {

        countryViewModels.getCityResponse(StateID).observe(this, clsCityResponce -> {
            if (clsCityResponce != null) {
                cities = clsCityResponce.getData();
                if (cities.size() != 0) {
                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(cities);
                    Log.e("city", "city---" + jsonInString);
                    for (ClsCity city : cities) {
                        citiesName.add(city.getCityName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
                            (this, android.R.layout.simple_list_item_1, (List<String>) citiesName);

                    tv_city.setThreshold(1);
                    tv_city.setAdapter(adapter);
                } else {
                    Toast.makeText(this, clsCityResponce.getSuccess() + " " + StateID, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Something went wrong".concat(String.valueOf(StateID)), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkValidations() {

        if (et_mobile.getText().toString().equals("")) {
            et_mobile.setError("Required");
            return;
        } else if (et_mobile.getText().toString().length() != 10) {
            et_mobile.setError("10 Digit number required");
            return;
        }

        if (et_name.getText().toString().equals("")) {
            et_name.setError("Required");
            return;
        }

        if (et_address1.getText().toString().equals("")) {
            et_address1.setError("Required");
            return;
        }

        if (et_landmark.getText().toString().equals("")) {
            et_landmark.setError("Required");
            return;
        }

        if (tv_address_type.getText().toString().equals("")) {
            tv_address_type.setError("Required");
            return;
        }

        if (tv_state.getText().toString().equals("")) {
            tv_state.setError("Select");
            return;
        }
        if (tv_city.getText().toString().equals("")) {
            tv_city.setError("Required");
            return;
        }

        if (et_pin_code.getText().toString().equals("")) {
            et_pin_code.setError("Required");
            return;
        } else if (et_pin_code.getText().toString().length() != 6) {
            et_pin_code.setError("6 Digit number required");
            return;
        }


        postCustomerAddress(addressId);

        ClsGlobal.hideKeyboard(ActivityFillAddress.this);

        /* if (et_name != null && et_mobile != null && tv_state != null && tv_city != null && et_pin_code != null && et_landmark != null && tv_address_type != null && et_address1 != null) {
            pd = ClsGlobal._prProgressDialog(this, "Updating address...", false);
            pd.show();
//            Toast.makeText(this, "mode " + mode, Toast.LENGTH_SHORT).show();
            if (mode.equalsIgnoreCase("New")) {
                postCustomerAddress();
            } else if (mode.equalsIgnoreCase("update")) {
                updateCustomerAddress();
            }
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case (PICK_CONTACT): {
                    if (resultCode == Activity.RESULT_OK) {
                        Uri contactData = data.getData();
                        Cursor c = managedQuery(contactData, null, null, null, null);
                        if (c.moveToFirst()) {
                            String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                            String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                            if (hasPhone.equalsIgnoreCase("1")) {
                                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                                phones.moveToFirst();

                                String cNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                cNumber = cNumber.replace(" ", "");
                                cNumber = cNumber.replace("+91", "");
                                cNumber = cNumber.replace("-", "");
                                et_mobile.setText(cNumber);
                            }
                        }
                    }
                }
                case (2): {
                    String state = data.getStringExtra("state");
                    stateId = data.getIntExtra("stateId", 0);
                    tv_state.setText(state);
                    tv_state.setTextColor(Color.parseColor("#757575"));
                    if (!tv_state.equals("")) {
                        getCity(stateId);
                    }
                }
            }
        } catch (Exception ex) {
            Log.d("state selection", "onActivityResult: " + ex.toString());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
//                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, PICK_CONTACT);
                } else {
                    // Permission Denied
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
