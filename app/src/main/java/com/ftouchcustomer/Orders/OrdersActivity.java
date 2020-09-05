package com.ftouchcustomer.Orders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.Database.Entity.ClsOrderDetailsEntity;
import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.Global.ClsUserInfo;
import com.ftouchcustomer.Global.FileUploader;
import com.ftouchcustomer.Merchant.ClsMerchantResponseList;
import com.ftouchcustomer.PlaceOrder.ActivityPlaceOrder;
import com.ftouchcustomer.R;
import com.ftouchcustomer.ViewModelClass.AddToItemViewModel;
import com.google.gson.Gson;

import java.io.File;
import java.io.Serializable;
import java.util.List;



public class OrdersActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView order_no, txt_grand_total, txt_total_tax;
    RecyclerView rv_order_list;
    List<ClsOrderDetailsEntity> lstCurrentOrder;
    public static boolean OpenSales = false;
    public Double single_item_price = 0.0;
    public Double _itemTotal = 0.0;
    Double totalTaxAmt = 0.0;
    OrderDetailAdapter orderDetailAdapter;
    TextView txt_nodata;
    AddToItemViewModel addToItemViewModel;
    LinearLayout ll_list, ll_total;
    int _orderDetailID = 0;
    int _customerID = 0;
    String _customerCode = "";
    TextView txt_continue;
    ClsUserInfo obj;
    String _merchantCode = "";
    ClsMerchantResponseList clsMerchantResponseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        addToItemViewModel = new
                ViewModelProvider(this).get(AddToItemViewModel.class);
        clsMerchantResponseList = (ClsMerchantResponseList) getIntent().getSerializableExtra("clsMerchantResponseList");
        toolbar = findViewById(R.id.toolbar);
        setTitle("");
        setSupportActionBar(toolbar);

        order_no = toolbar.findViewById(R.id.order_no);

//        saleMode = getIntent().getStringExtra("saleMode");
//        entryMode = getIntent().getStringExtra("entryMode");
//        _taxApple = getIntent().getStringExtra("_taxApple");

        rv_order_list = findViewById(R.id.rv_order_list);
        rv_order_list.setLayoutManager(new LinearLayoutManager(OrdersActivity.this));

        ll_list = findViewById(R.id.ll_list);
        txt_continue = findViewById(R.id.txt_continue);
        ll_total = findViewById(R.id.ll_total);
        txt_nodata = findViewById(R.id.txt_nodata);

        txt_grand_total = findViewById(R.id.txt_grand_total);

        txt_total_tax = findViewById(R.id.txt_total_tax);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        _orderDetailID = getIntent().getIntExtra("_orderDetailID", 0);
        _customerID = getIntent().getIntExtra("_customerID", 0);
        _customerCode = getIntent().getStringExtra("_customerCode");
        _merchantCode = getIntent().getStringExtra("_merchantCode");

        Log.e("--obj--", "getDeliveryCharges: " + clsMerchantResponseList.getDeliveryCharges());
        Log.e("--obj--", "getDisplayCategories: " + clsMerchantResponseList.getDisplayCategories());
        Log.e("--obj--", "_orderDetailID: " + _orderDetailID);
        Log.e("--obj--", "_customerCode: " + _customerCode);
        Log.e("--obj--", "_merchantCode: " + _merchantCode);

        txt_total_tax.setTag(0.0);

        orderDetailAdapter = new OrderDetailAdapter(OrdersActivity.this);
        rv_order_list.setAdapter(orderDetailAdapter);

        viewOrderDetailListByID(_customerCode);

        orderDetailAdapter.SetOnClickListener(new OrderDetailAdapter.OnDeleteClick() {
            @Override
            public void onItemDelete(ClsOrderDetailsEntity obj, int position) {
                ShowAlertDialog("Do you want to delete this item...", obj.getOrderDetailID(), position);
            }
        });

        obj = ClsGlobal.getUserInfo(OrdersActivity.this);
        txt_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("--Menu--", "getLoginStatus: " + obj.getLoginStatus());

                Continue();
//                callPlaceOrderAPI();
            }
        });
    }

    void Continue() {
        Intent intent = new Intent(getApplicationContext(), ActivityPlaceOrder.class);
        intent.putExtra("_orderDetailID", _orderDetailID);
        intent.putExtra("_customerID", _customerID);
        intent.putExtra("_customerCode", _customerCode);

        Log.d("--grandTotal--", "txt_grand_total: " + txt_grand_total.getTag().toString());

        intent.putExtra("_grandTotal", String.valueOf(txt_grand_total.getTag()));
        intent.putExtra("List", (Serializable) orderDetailAdapter.getPlaceOrderList());
        startActivity(intent);
    }

    public void viewOrderDetailListByID(String _customerCode) {


        Log.e("--obj--", "IN_customerCode: " + _customerCode);

        addToItemViewModel.getOrderDetailByID(_customerCode).observe(this, lstOrderDetailById -> {

            if (lstOrderDetailById != null && lstOrderDetailById.size() != 0) {

                txt_nodata.setVisibility(View.GONE);
                ll_list.setVisibility(View.VISIBLE);
                ll_total.setVisibility(View.VISIBLE);

                orderDetailAdapter.addList(lstOrderDetailById);
                lstCurrentOrder = lstOrderDetailById;

                rv_order_list.setAdapter(orderDetailAdapter);

                calculateAmount();
            } else {

                txt_nodata.setVisibility(View.VISIBLE);
                ll_list.setVisibility(View.GONE);
                ll_total.setVisibility(View.GONE);
            }


        });

    }

    // Calculate Total Amount:
    @SuppressLint("SetTextI18n")
    void calculateAmount() {
        _itemTotal = 0.0;
        if (lstCurrentOrder != null && lstCurrentOrder.size() != 0) {
            for (int i = 0; i < lstCurrentOrder.size(); i++) {

                single_item_price = (lstCurrentOrder.get(i).getRate() * lstCurrentOrder.get(i).getQuantity())
                        - lstCurrentOrder.get(i).getDiscount_amt();
                _itemTotal += single_item_price;
            }
        }
        getTotalTaxAmountFormList();
        double _totalTax = Double.parseDouble(txt_total_tax.getTag().toString());//15.25

        //apply tax
        double grandTotalAmount = 0.0;
        grandTotalAmount = _itemTotal + _totalTax;

        txt_grand_total.setTag(ClsGlobal.round(grandTotalAmount, 2));
        txt_grand_total.setText("[ \u20B9 " + ClsGlobal.round(grandTotalAmount, 2) + " ]");
    }


    OnUpdateFooterValueFromOrders onUpdateFooterValueFromOrders;

    public interface OnUpdateFooterValueFromOrders {
        void OnDeleteClick();
    }

    private void ShowAlertDialog(String message, int OrderDetailId, int position) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog OptionDialog = builder.create();
        builder.setMessage(message);

        builder.setPositiveButton("YES", (dialog, id) -> {

            addToItemViewModel.DeleteByOrderDetailID(OrderDetailId,"Item deleted successfully!");

            orderDetailAdapter.remove(position);
            orderDetailAdapter.notifyDataSetChanged();

            totalTaxAmt = 0.0;
            _itemTotal = 0.0;

            calculateAmount();

            if (onUpdateFooterValueFromOrders != null) {
                onUpdateFooterValueFromOrders.OnDeleteClick();
            }

            OptionDialog.dismiss();
            OptionDialog.cancel();
        });

        builder.setNegativeButton("NO", (dialogInterface, i) -> {
            OptionDialog.dismiss();
            OptionDialog.cancel();
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (OpenSales) {
            Intent intent = new Intent(OrdersActivity.this, ActivityOrderItemList.class);
            startActivity(intent);
            OpenSales = false;
        }
    }

    @SuppressLint("SetTextI18n")
    private void getTotalTaxAmountFormList() {
        double getTotalTaxAmount = 0.0;
        for (ClsOrderDetailsEntity currentObj : lstCurrentOrder) {
            getTotalTaxAmount += currentObj.getTotalTaxAmount();
        }
        txt_total_tax.setTag(ClsGlobal.round(getTotalTaxAmount, 2));
        txt_total_tax.setText("TOTAL TAX AMT: \u20B9 " + ClsGlobal.formatNumber(2,
                Double.parseDouble(ClsGlobal.round(getTotalTaxAmount, 2))));
    }


}
