package com.ftouchcustomer.Address;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.Address.defaultAddress.ClsDefaultAddress;
import com.ftouchcustomer.Address.deleteAddress.ClsDeleteAddress;
import com.ftouchcustomer.Address.getAddress.ClsGetCustomerAddressListResponse;
import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.Global.ClsUserInfo;
import com.ftouchcustomer.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ActivityAddress extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterAddress adapter;
    private ProgressDialog pd;
    private FillAddressViewModel fillAddressViewModel;
    private List<ClsGetCustomerAddressListResponse> list = new ArrayList<>();
    ClsUserInfo clsUserInfo = new ClsUserInfo();
    boolean goBack = true;
    TextView tv_no_item_found;
    String placeOrder = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        FloatingActionButton fab = findViewById(R.id.fab_add);
        recyclerView = findViewById(R.id.recyclerView);
        tv_no_item_found = findViewById(R.id.tv_no_item_found);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        placeOrder = getIntent().getStringExtra("placeOrder");
        fillAddressViewModel = new ViewModelProvider(this).get(FillAddressViewModel.class);
        adapter = new AdapterAddress(this);
        recyclerView.setAdapter(adapter);


        fab.setOnClickListener(v -> {
            Intent intent    = new Intent(this, ActivityFillAddress.class);
            intent.putExtra("addressId", 0);
            startActivity(intent);
        });

        pd = ClsGlobal._prProgressDialog(this, "Getting address...", true);
        pd.show();
        loadAdapter();

        pd.setOnDismissListener(dialogInterface -> {
                    if (goBack) onBackPressed();
                }
        );


        adapter.SetOnItemClickListener((listResponse, position, mode1) -> {


            if (mode1.equalsIgnoreCase("Edit")) {

                Intent intent = new Intent(this, ActivityFillAddress.class);
                intent.putExtra("addressId", listResponse.getAddressID());
                intent.putExtra("listResponse", listResponse);
                startActivity(intent);

            } else if (mode1.equalsIgnoreCase("Delete")) {
                deleteAddress(listResponse);
            } else if (mode1.equalsIgnoreCase("LayoutClick")) {
                layoutClick(listResponse);
            } else if (mode1.equalsIgnoreCase("Default")) {
                defaultAddress(listResponse);
            }
        });
    }

    void layoutClick(ClsGetCustomerAddressListResponse listResponse) {


        if (placeOrder != null && placeOrder.equalsIgnoreCase("placeOrder")) {

            String _concatAddress = listResponse.getAddressLine1().concat(", ")
                    .concat(listResponse.getAddressLine2()).concat(", ").concat(listResponse.getLandmark()
                            .concat(", ").concat(listResponse.getPincode()).concat(", ")
                            .concat(listResponse.getCity()).concat(", ").concat(listResponse.getState()));

            Intent intent = new Intent();
            intent.putExtra("name", listResponse.getName());
            intent.putExtra("mobile", listResponse.getAddressMobileNo());
            intent.putExtra("address", _concatAddress);
            intent.putExtra("type", listResponse.getType());
            intent.putExtra("addressID", listResponse.getAddressID());
            setResult(RESULT_OK, intent);
            finish();
        }

    }

    void deleteAddress(ClsGetCustomerAddressListResponse listResponse) {

        ClsDeleteAddress obj = new ClsDeleteAddress();
        obj.setMobileNo(listResponse.getAddressMobileNo());
        obj.setAddressID(listResponse.getAddressID());

        Gson gson = new Gson();
        String jsonInString = gson.toJson(obj);
        Log.e("--mode--", "clsDeleteAddress1 : " + jsonInString);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Address");
        builder.setMessage("Are you sure?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            fillAddressViewModel.deleteCustomerAddress(obj)
                    .observe(this, clsDeleteAddress -> {
                        String msg = clsDeleteAddress.getSuccess();
                        String getMessage = clsDeleteAddress.getMessage();
                        if (msg.equals("1")) {
                            loadAdapter();
                        }
                        Toast.makeText(this, getMessage, Toast.LENGTH_SHORT).show();
                    });
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void defaultAddress(ClsGetCustomerAddressListResponse listResponse) {
        ClsDefaultAddress clsDefaultAddress = new ClsDefaultAddress();
        clsDefaultAddress.setMobile(clsUserInfo.getRegisteredmobilenumber());
        clsDefaultAddress.setDefaultAddress(true);
        clsDefaultAddress.setAddressId(listResponse.getAddressID());

        Gson gson = new Gson();
        String jsonInString = gson.toJson(clsDefaultAddress);
        Log.e("--URL--", "clsDefaultAddress---" + jsonInString);

        fillAddressViewModel.defaultAddress(clsDefaultAddress)
                .observe(this, clsDefaultAddress1 -> {

                    String success = clsDefaultAddress1.getSuccess();
                    String msg = clsDefaultAddress1.getMessage();

                    if ("1".equals(success)) {
                        loadAdapter();
                        Toast.makeText(this, "Make default address Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadAdapter() {
        clsUserInfo = ClsGlobal.getUserInfo(this);

        String number = clsUserInfo.getRegisteredmobilenumber();
        Log.d("number", "loadAdapter: " + number);

        fillAddressViewModel.getCustomerAddress(number)
                .observe(this, clsGetCustomerAddressResponse -> {
                    pd.dismiss();

                    goBack = false;

                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(clsGetCustomerAddressResponse);
                    Log.e("--URL--", "address: " + jsonInString);

                    String msg = clsGetCustomerAddressResponse.getSuccess();


                    if ("0".equals(msg)) {
                        recyclerView.setVisibility(View.GONE);
                        tv_no_item_found.setVisibility(View.VISIBLE);
//                        Toast.makeText(this, clsGetCustomerAddressResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else if ("1".equals(msg)) {

                        list = clsGetCustomerAddressResponse.getData();

                        if (list.size() != 0) {
                            adapter.addList(list);
                            recyclerView.setVisibility(View.VISIBLE);
                            tv_no_item_found.setVisibility(View.GONE);
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            tv_no_item_found.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(this, "We are facing issues to loading your address.", Toast.LENGTH_SHORT).show();
                    }

                });
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadAdapter();
    }
}
