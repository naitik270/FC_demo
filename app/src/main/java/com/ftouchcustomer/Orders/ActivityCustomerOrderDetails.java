package com.ftouchcustomer.Orders;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.Database.Entity.ClsOrderDetailsEntity;
import com.ftouchcustomer.Global.ApiClient;
import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.Interface.InterfaceGetOrderList;
import com.ftouchcustomer.Payment.ActivityMakePayment;
import com.ftouchcustomer.PlaceOrder.ClsCancelOrderParams;
import com.ftouchcustomer.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java8.util.stream.StreamSupport;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCustomerOrderDetails extends AppCompatActivity {

    LinearLayout ll_payment_method, ll_payment_date, ll_payment_ref;

    TextView txt_order_status, txt_delivery_type, txt_status_remark,
            txt_address, txt_comment;

    LinearLayout progress_bar_layout;

    int CustomerID = 0;
    String MobileNo = "";
    String MerchantCode = "";
    String CanceledBy = "";
    int OrderId = 0;
    TextView txt_name;
    TextView txt_cancel_order;
    TextView txt_download_invoice;
    TextView txt_tracking_no;
    OnlineOrderItemAdapter adapter;
    List<ClsOrderDetailsEntity> list;

    TextView txt_payment_method, txt_payment_ref_no, txt_payment_date, txt_payment_status;
    CancelOrderViewModel mCancelOrderViewModel;

    LinearLayout ll_data;
    TextView txt_no_internet, txt_order_no, txt_make_payment, txt_merchant_name;

    RecyclerView rv;

    ImageView iv_status_order, iv_status_payment;
    TextView txt_track, txt_total, txt_delivery_charges, txt_grand_total;
    TextView txt_mobile, txt_merchant_address,txt_email;

    ClsCustomerOrderDetail.ClsDataOrderDetails clsOrderDetail;
    double _grandTotal = 0.0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_display);

        mCancelOrderViewModel = new ViewModelProvider(this).get(CancelOrderViewModel.class);

        main();
    }

    private void main() {

        CustomerID = getIntent().getIntExtra("MerchantID", 0);
        OrderId = getIntent().getIntExtra("OrderId", 0);
        MobileNo = getIntent().getStringExtra("MobileNo");
        MerchantCode = getIntent().getStringExtra("MerchantCode");
        CanceledBy = getIntent().getStringExtra("CanceledBy");

        Log.d("--log--", "CustomerID: " + CustomerID);
        Log.d("--log--", "OrderId: " + OrderId);
        Log.d("--log--", "MobileNo: " + MobileNo);

        Toolbar toolbar = findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("");
        toolbar.setSubtitle("");


        txt_mobile = findViewById(R.id.txt_mobile);
        txt_merchant_address = findViewById(R.id.txt_merchant_address);
        txt_email = findViewById(R.id.txt_email);

        txt_make_payment = findViewById(R.id.txt_make_payment);
        txt_tracking_no = findViewById(R.id.txt_tracking_no);
        txt_grand_total = findViewById(R.id.txt_grand_total);
        txt_delivery_charges = findViewById(R.id.txt_delivery_charges);
        txt_total = findViewById(R.id.txt_total);
        txt_order_no = findViewById(R.id.txt_order_no);
        ll_data = findViewById(R.id.ll_data);
        txt_download_invoice = findViewById(R.id.txt_download_invoice);
        txt_cancel_order = findViewById(R.id.txt_cancel_order);
        txt_order_status = findViewById(R.id.txt_order_status);
        txt_delivery_type = findViewById(R.id.txt_delivery_type);
        txt_status_remark = findViewById(R.id.txt_status_remark);
        txt_address = findViewById(R.id.txt_address);
        txt_comment = findViewById(R.id.txt_comment);
        txt_name = findViewById(R.id.txt_name);
        txt_payment_method = findViewById(R.id.txt_payment_method);
        txt_payment_ref_no = findViewById(R.id.txt_payment_ref_no);
        txt_payment_date = findViewById(R.id.txt_payment_date);
        txt_payment_status = findViewById(R.id.txt_payment_status);
        txt_merchant_name = findViewById(R.id.txt_merchant_name);

        // LinearLayout.
        progress_bar_layout = findViewById(R.id.progress_bar_layout);

        ll_payment_ref = findViewById(R.id.ll_payment_ref);
        ll_payment_date = findViewById(R.id.ll_payment_date);
        ll_payment_method = findViewById(R.id.ll_payment_method);

        rv = findViewById(R.id.rv);

        txt_track = findViewById(R.id.txt_track);

        iv_status_order = findViewById(R.id.iv_status_order);
        iv_status_payment = findViewById(R.id.iv_status_payment);

        txt_no_internet = findViewById(R.id.txt_no_internet);
        adapter = new OnlineOrderItemAdapter();

        txt_cancel_order.setOnClickListener(v -> areYouSureDialog());

        txt_download_invoice.setOnClickListener(v -> {

            if (invoiceURL != null && !invoiceURL.equalsIgnoreCase("")) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(invoiceURL));
                startActivity(i);
            } else {
                Toast.makeText(ActivityCustomerOrderDetails.this, "No url found.", Toast.LENGTH_SHORT).show();
            }

        });

        txt_track.setOnClickListener(v -> bottomDialogTrack());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ClsGlobal.isNetworkConnected(getApplicationContext())) {
            txt_no_internet.setVisibility(View.GONE);
            ll_data.setVisibility(View.VISIBLE);
            getOrderDetailsFromApi();
        } else {
            ll_data.setVisibility(View.GONE);
            txt_no_internet.setVisibility(View.VISIBLE);
        }

    }

    double total_amount = 0;

    private void areYouSureDialog() {

        final Dialog dialog = new Dialog(ActivityCustomerOrderDetails.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
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
        btn_no.setText("NO");
        btn_no.setVisibility(View.VISIBLE);

        EditText edt_comment = dialog.findViewById(R.id.edt_comment);
        LinearLayout ll_comment = dialog.findViewById(R.id.ll_comment);
        ll_comment.setVisibility(View.VISIBLE);
        content.setText(getText(R.string.alert_cancel_order));

        btn_ok.setOnClickListener(v -> {
            dialog.dismiss();
            cancelPlaceOrder(edt_comment.getText().toString());
        });

        btn_no.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }

    String invoiceURL = "";
    private ProgressDialog pd;

    private void cancelPlaceOrder(String _value) {

        ClsCancelOrderParams obj = new ClsCancelOrderParams();
        obj.setOrderID(OrderId);
        obj.setMobileNo(MobileNo);
        obj.setMerchantCode(MerchantCode);
        obj.setCancelledBy(CanceledBy);
        obj.setMode("Customer");
        obj.setReason(_value);

        pd = ClsGlobal._prProgressDialog(ActivityCustomerOrderDetails.this,
                "please wait,the order is cancelled!", false);
        pd.show();

        mCancelOrderViewModel.cancelOrder(obj).observe(this, clsPlaceOrderDeleteResponse -> {

            if (clsPlaceOrderDeleteResponse != null) {

                if (clsPlaceOrderDeleteResponse.getSuccess().equalsIgnoreCase("1")) {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Order cancelled successfully."
                            , Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Order not cancelled."
                            , Toast.LENGTH_SHORT).show();
                }

            } else {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Something went wrong,please try again"
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getOrderItems(String OrderItemsUrl) {
        InterfaceGetOrderList mainInterface = ApiClient.getRetrofitInstance()
                .create(InterfaceGetOrderList.class);

        Call<List<ClsOrderDetailsEntity>> call = mainInterface.getOnlineOrderItems(OrderItemsUrl);
        Log.e("--va--", "getOrderItems: " + call.request().url());

        call.enqueue(new Callback<List<ClsOrderDetailsEntity>>() {
            @Override
            public void onResponse(@NonNull Call<List<ClsOrderDetailsEntity>> call,
                                   @NonNull Response<List<ClsOrderDetailsEntity>> response) {

                if (response.body() != null && response.isSuccessful()) {
                    Gson gson2 = new Gson();
                    String jsonInString2 = gson2.toJson(response.body());
                    Log.e("Check", "response:--- " + jsonInString2);

                    if (response.body().size() > 0) {
                        adapter.AddItems(response.body());
                        Objects.requireNonNull(rv).setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        rv.setAdapter(adapter);
                        total_amount = StreamSupport.stream(adapter.getAdapterList())
                                .mapToDouble(ClsOrderDetailsEntity::getAmount).sum();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ClsOrderDetailsEntity>> call,
                                  @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong,please try again",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getOrderDetailsFromApi() {
        progress_bar_layout.setVisibility(View.VISIBLE);

        InterfaceGetOrderList mainInterface = ApiClient.getRetrofitInstance()
                .create(InterfaceGetOrderList.class);

        Call<ClsCustomerOrderDetail> call = mainInterface.getOnlineOrdersDetails(OrderId,
                CustomerID, MobileNo);

        Log.e("--va--", "Url: " + call.request().url());

        call.enqueue(new Callback<ClsCustomerOrderDetail>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<ClsCustomerOrderDetail> call,
                                   @NonNull Response<ClsCustomerOrderDetail> response) {
                progress_bar_layout.setVisibility(View.GONE);

                if (response.body() != null && response.isSuccessful()) {
                    clsOrderDetail = response.body().getData();

                    if (clsOrderDetail.getOrderStatus().equalsIgnoreCase("Cancelled")) {
                        iv_status_order.setColorFilter(ContextCompat.getColor(ActivityCustomerOrderDetails.this, R.color.Cancelled), android.graphics.PorterDuff.Mode.SRC_IN);
                    } else if (clsOrderDetail.getOrderStatus().equalsIgnoreCase("preparing")) {
                        iv_status_order.setColorFilter(ContextCompat.getColor(ActivityCustomerOrderDetails.this, R.color.Preparing), android.graphics.PorterDuff.Mode.SRC_IN);
                    } else if (clsOrderDetail.getOrderStatus().equalsIgnoreCase("pending")) {
                        iv_status_order.setColorFilter(ContextCompat.getColor(ActivityCustomerOrderDetails.this, R.color.Pending), android.graphics.PorterDuff.Mode.SRC_IN);
                    } else if (clsOrderDetail.getOrderStatus().equalsIgnoreCase("Confirmed")) {
                        iv_status_order.setColorFilter(ContextCompat.getColor(ActivityCustomerOrderDetails.this, R.color.confirmed), android.graphics.PorterDuff.Mode.SRC_IN);
                    } else if (clsOrderDetail.getOrderStatus().equalsIgnoreCase("Completed")) {
                        iv_status_order.setColorFilter(ContextCompat.getColor(ActivityCustomerOrderDetails.this, R.color.Completed), android.graphics.PorterDuff.Mode.SRC_IN);
                        Log.d("--val--", "Completed: " + clsOrderDetail.getOrderStatus());
                    } else if (clsOrderDetail.getOrderStatus().equalsIgnoreCase("Delivered")) {
                        iv_status_order.setColorFilter(ContextCompat.getColor(ActivityCustomerOrderDetails.this,
                                R.color.Delivered), android.graphics.PorterDuff.Mode.SRC_IN);

                    } else {
                        iv_status_order.setColorFilter(ContextCompat.getColor(ActivityCustomerOrderDetails.this,
                                R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
                    }

                    txt_order_status.setText(clsOrderDetail.getOrderStatus());

                    if (clsOrderDetail.getOrderStatus() != null &&
                            clsOrderDetail.getOrderStatus().equalsIgnoreCase("Pending")) {

                        txt_cancel_order.setVisibility(View.VISIBLE);

                    } else {
                        txt_cancel_order.setVisibility(View.GONE);
                    }

                   /* if (clsOrderDetail.getOrderStatus() != null &&
                            clsOrderDetail.getOrderStatus().equalsIgnoreCase("cancelled")) {

                        txt_make_payment.setVisibility(View.GONE);

                    } else {
                        txt_make_payment.setVisibility(View.VISIBLE);
                    }
*/
                    if (clsOrderDetail.getOrderStatus() != null) {

                        if (clsOrderDetail.getOrderStatus().equalsIgnoreCase("cancelled")
                                || clsOrderDetail.getOrderStatus().equalsIgnoreCase("preparing")
                                || clsOrderDetail.getOrderStatus().equalsIgnoreCase("confirmed")
                                || clsOrderDetail.getOrderStatus().equalsIgnoreCase("pending")) {
                            txt_download_invoice.setVisibility(View.GONE);
                        } else {
                            txt_download_invoice.setVisibility(View.VISIBLE);
                        }
                    }

                    if (clsOrderDetail.getOrderNO() != null &&
                            !clsOrderDetail.getOrderNO().equalsIgnoreCase("")) {
                        txt_order_no.setText("#" + clsOrderDetail.getOrderNO());
                    } else {
                        txt_order_no.setText("Not generated");
                    }

                    txt_tracking_no.setText(String.valueOf(clsOrderDetail.getOrderID()));
                    txt_delivery_type.setText(clsOrderDetail.getDeliveryType());

                    txt_status_remark.setText(clsOrderDetail.getCancelReason());

                    txt_address.setText(clsOrderDetail.getAddress());
                    txt_comment.setText(clsOrderDetail.getComment());
                    txt_name.setText(clsOrderDetail.getMerchantName());

                    txt_mobile.setText(clsOrderDetail.getContactPersonMobileNo());
                    txt_email.setText(clsOrderDetail.getContactEmail());
                    txt_merchant_address.setText(clsOrderDetail.getContactAddress());

                    txt_merchant_name.setText(clsOrderDetail.getMerchantName());

                    txt_total.setText("\u20B9 " + clsOrderDetail.getTotalAmount());
                    txt_delivery_charges.setText("\u20B9 " + clsOrderDetail.getDeliveryCharges());

                    _grandTotal = clsOrderDetail.getTotalAmount() + clsOrderDetail.getDeliveryCharges();
                    txt_grand_total.setText("\u20B9 " + _grandTotal);

                    if (clsOrderDetail.getPaymentStatus().equalsIgnoreCase("Pending")) {

                        ll_payment_method.setVisibility(View.GONE);
                        ll_payment_ref.setVisibility(View.GONE);
                        ll_payment_date.setVisibility(View.GONE);

                        iv_status_payment.setColorFilter(ContextCompat.getColor(ActivityCustomerOrderDetails.this,
                                R.color.Cancelled), android.graphics.PorterDuff.Mode.SRC_IN);

                    } else if (clsOrderDetail.getPaymentStatus().equalsIgnoreCase("Complete")) {

                        ll_payment_method.setVisibility(View.VISIBLE);
                        ll_payment_ref.setVisibility(View.VISIBLE);
                        ll_payment_date.setVisibility(View.VISIBLE);

                        iv_status_payment.setColorFilter(ContextCompat.getColor(ActivityCustomerOrderDetails.this,
                                R.color.Pending), android.graphics.PorterDuff.Mode.SRC_IN);

                        txt_payment_method.setText(clsOrderDetail.getPaymentMethod());
                        txt_payment_ref_no.setText(clsOrderDetail.getPaymentReferenceNo());
                        txt_payment_date.setText(clsOrderDetail.getPaymentDate());

                    } else {
                        txt_payment_method.setVisibility(View.GONE);
                        txt_payment_ref_no.setVisibility(View.GONE);
                        txt_payment_date.setVisibility(View.GONE);

                        iv_status_payment.setColorFilter(ContextCompat.getColor(ActivityCustomerOrderDetails.this,
                                R.color.Cancelled), android.graphics.PorterDuff.Mode.SRC_IN);
                    }

                    txt_payment_status.setText(clsOrderDetail.getPaymentStatus());

                    if (clsOrderDetail.getInvoiceURL() != null) {
                        invoiceURL = clsOrderDetail.getInvoiceURL();
                    } else {
                        invoiceURL = "";
                    }


                    if (clsOrderDetail.getPaymentStatus() != null &&
                            clsOrderDetail.getPaymentStatus().equalsIgnoreCase("Pending")
                            && clsOrderDetail.getOrderStatus() != null &&
                            !clsOrderDetail.getOrderStatus().equalsIgnoreCase("Cancelled")) {

                        txt_make_payment.setVisibility(View.VISIBLE);

                        txt_make_payment.setOnClickListener(v -> {

                            total_amount = StreamSupport.stream(adapter.getAdapterList())
                                    .mapToDouble(ClsOrderDetailsEntity::getAmount).sum();

                            Intent intent = new Intent(ActivityCustomerOrderDetails.this,
                                    ActivityMakePayment.class);
                            intent.putExtra("clsOrderDetail", clsOrderDetail);
                            intent.putExtra("total_amount", total_amount);

                            Log.d("--amt--", "total_amount: " + total_amount);


                            startActivity(intent);
                        });

                    } else {
                        txt_make_payment.setVisibility(View.GONE);
                    }


                    getOrderItems(clsOrderDetail.getItemsFileUrl());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ClsCustomerOrderDetail> call, @NonNull Throwable t) {
                progress_bar_layout.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Something went wrong,please try again"
                        , Toast.LENGTH_SHORT).show();
            }
        });

    }

    BottomSheetDialog dialog;

    @SuppressLint("SetTextI18n")
    public void bottomDialogTrack() {
        View view = getLayoutInflater().inflate(R.layout.dialog_item_list, null);
        dialog = new BottomSheetDialog(ActivityCustomerOrderDetails.this);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.show();

        ImageView iv_order_date = dialog.findViewById(R.id.iv_order_date);
        ImageView iv_confirmed = dialog.findViewById(R.id.iv_confirmed);
        ImageView iv_preparing = dialog.findViewById(R.id.iv_preparing);
        ImageView iv_cancelled = dialog.findViewById(R.id.iv_cancelled);
        ImageView iv_ready = dialog.findViewById(R.id.iv_ready);
        iv_ready.setVisibility(View.GONE);
        ImageView iv_delivery = dialog.findViewById(R.id.iv_delivery);
        ImageView iv_complete = dialog.findViewById(R.id.iv_complete);

        View vw_order = dialog.findViewById(R.id.vw_order);
        View vw_confirmed = dialog.findViewById(R.id.vw_confirmed);
        View vw_preparing = dialog.findViewById(R.id.vw_preparing);
        View vw_cancelled = dialog.findViewById(R.id.vw_cancelled);
        View vw_ready = dialog.findViewById(R.id.vw_ready);
        vw_ready.setVisibility(View.GONE);
        View vw_delivery = dialog.findViewById(R.id.vw_delivery);

        TextView txt_order_date = dialog.findViewById(R.id.txt_order_date);
        TextView txt_confirmed_datetime = dialog.findViewById(R.id.txt_confirmed_datetime);
        TextView txt_preparing_date = dialog.findViewById(R.id.txt_preparing_date);
        TextView txt_cancelled_datetime = dialog.findViewById(R.id.txt_cancelled_datetime);
        TextView txt_ready_datetime = dialog.findViewById(R.id.txt_ready_datetime);
        txt_ready_datetime.setVisibility(View.GONE);

        TextView txt_delivery_datetime = dialog.findViewById(R.id.txt_delivery_datetime);
        TextView txt_complete_datetime = dialog.findViewById(R.id.txt_complete_datetime);
        TextView txt_cancelled_reason = dialog.findViewById(R.id.txt_cancelled_reason);


        TextView txt_name_by = dialog.findViewById(R.id.txt_name_by);
        TextView txt_mobile = dialog.findViewById(R.id.txt_mobile);
        TextView txt_note = dialog.findViewById(R.id.txt_note);
        txt_note.setText("ready to pickup or deliver.");


        txt_order_date.setText("Order Placed: " + clsOrderDetail.getOrderPlaceDate());
        txt_preparing_date.setText("Preparing: " + clsOrderDetail.getPreparingdate());
        txt_confirmed_datetime.setText("Confirmed: " + clsOrderDetail.getConfirmeddate());
        txt_ready_datetime.setText("Ready: " + clsOrderDetail.getReadydate());
        txt_delivery_datetime.setText("Pick-up: " + clsOrderDetail.getDeliveredDate());
        txt_complete_datetime.setText("Complete: " + clsOrderDetail.getCompletedate());
        txt_cancelled_datetime.setText("Cancelled: " + clsOrderDetail.getCancelleddate());

        Log.d("--date--", "Date: " + clsOrderDetail.getDeliveredDate());
        txt_cancelled_reason.setText("Reason: " + clsOrderDetail.getCancelReason());

        if (clsOrderDetail.getOrderStatus() != null &&
                clsOrderDetail.getOrderStatus().equalsIgnoreCase("Delivered")) {

            txt_order_date.setVisibility(View.VISIBLE);
            txt_preparing_date.setVisibility(View.VISIBLE);
            txt_confirmed_datetime.setVisibility(View.VISIBLE);
            txt_delivery_datetime.setVisibility(View.VISIBLE);
            txt_complete_datetime.setVisibility(View.VISIBLE);

            txt_name_by.setText("Pick-up by: " + clsOrderDetail.getReceivedBy());
            txt_mobile.setText("Mobile: " + clsOrderDetail.getReceivedMobileNO());

            txt_note.setVisibility(View.VISIBLE);
            txt_name_by.setVisibility(View.VISIBLE);
            txt_mobile.setVisibility(View.VISIBLE);



            iv_order_date.setVisibility(View.VISIBLE);
            vw_order.setVisibility(View.VISIBLE);

            iv_confirmed.setVisibility(View.VISIBLE);
            vw_confirmed.setVisibility(View.VISIBLE);
            vw_cancelled.setVisibility(View.GONE);

            iv_preparing.setVisibility(View.VISIBLE);
            vw_preparing.setVisibility(View.VISIBLE);

            iv_complete.setVisibility(View.VISIBLE);

            txt_delivery_datetime.setVisibility(View.VISIBLE);
            txt_cancelled_datetime.setVisibility(View.GONE);

            iv_delivery.setVisibility(View.VISIBLE);
            vw_delivery.setVisibility(View.VISIBLE);

            txt_cancelled_datetime.setVisibility(View.GONE);
            vw_cancelled.setVisibility(View.GONE);
            iv_cancelled.setVisibility(View.GONE);

        }


        if (clsOrderDetail.getOrderStatus() != null &&
                clsOrderDetail.getOrderStatus().equalsIgnoreCase("Pending")) {

            iv_order_date.setVisibility(View.VISIBLE);
            txt_order_date.setVisibility(View.VISIBLE);
            vw_order.setVisibility(View.GONE);


            iv_confirmed.setVisibility(View.GONE);
            vw_confirmed.setVisibility(View.GONE);

            iv_preparing.setVisibility(View.GONE);
            vw_preparing.setVisibility(View.GONE);

            vw_cancelled.setVisibility(View.GONE);
            iv_cancelled.setVisibility(View.GONE);

            iv_delivery.setVisibility(View.GONE);
            vw_delivery.setVisibility(View.GONE);
            iv_complete.setVisibility(View.GONE);


            txt_preparing_date.setVisibility(View.GONE);
            txt_confirmed_datetime.setVisibility(View.GONE);
            txt_delivery_datetime.setVisibility(View.GONE);
            txt_cancelled_datetime.setVisibility(View.GONE);
            txt_complete_datetime.setVisibility(View.GONE);


        }

        if (clsOrderDetail.getOrderStatus() != null &&
                clsOrderDetail.getOrderStatus().equalsIgnoreCase("Cancelled")) {

            txt_order_date.setVisibility(View.VISIBLE);
            txt_cancelled_datetime.setVisibility(View.VISIBLE);
            txt_cancelled_reason.setVisibility(View.VISIBLE);


            iv_order_date.setVisibility(View.VISIBLE);
            vw_order.setVisibility(View.VISIBLE);

            vw_cancelled.setVisibility(View.GONE);
            iv_cancelled.setVisibility(View.VISIBLE);

            iv_confirmed.setVisibility(View.GONE);
            vw_confirmed.setVisibility(View.GONE);

            iv_preparing.setVisibility(View.GONE);
            vw_preparing.setVisibility(View.GONE);


            iv_delivery.setVisibility(View.GONE);
            vw_delivery.setVisibility(View.GONE);

            iv_complete.setVisibility(View.GONE);

            txt_preparing_date.setVisibility(View.GONE);
            txt_confirmed_datetime.setVisibility(View.GONE);
            txt_delivery_datetime.setVisibility(View.GONE);
            txt_complete_datetime.setVisibility(View.GONE);


        }

        if (clsOrderDetail.getOrderStatus() != null &&
                clsOrderDetail.getOrderStatus().equalsIgnoreCase("Confirmed")) {

            txt_order_date.setVisibility(View.VISIBLE);
            txt_confirmed_datetime.setVisibility(View.VISIBLE);

            txt_preparing_date.setVisibility(View.GONE);
            txt_delivery_datetime.setVisibility(View.GONE);
            txt_complete_datetime.setVisibility(View.GONE);
            txt_cancelled_datetime.setVisibility(View.GONE);

            iv_order_date.setVisibility(View.VISIBLE);
            vw_order.setVisibility(View.VISIBLE);

            vw_cancelled.setVisibility(View.GONE);
            iv_cancelled.setVisibility(View.GONE);

            iv_confirmed.setVisibility(View.VISIBLE);
            vw_confirmed.setVisibility(View.GONE);

            iv_preparing.setVisibility(View.GONE);
            vw_preparing.setVisibility(View.GONE);


            iv_delivery.setVisibility(View.GONE);
            vw_delivery.setVisibility(View.GONE);

            iv_complete.setVisibility(View.GONE);

        }

        if (clsOrderDetail.getOrderStatus() != null &&
                clsOrderDetail.getOrderStatus().equalsIgnoreCase("Preparing")) {

            txt_order_date.setVisibility(View.VISIBLE);
            txt_confirmed_datetime.setVisibility(View.VISIBLE);
            txt_preparing_date.setVisibility(View.VISIBLE);


            txt_delivery_datetime.setVisibility(View.GONE);
            txt_complete_datetime.setVisibility(View.GONE);
            txt_cancelled_datetime.setVisibility(View.GONE);


            iv_order_date.setVisibility(View.VISIBLE);
            vw_order.setVisibility(View.VISIBLE);

            vw_cancelled.setVisibility(View.GONE);
            iv_cancelled.setVisibility(View.GONE);

            iv_confirmed.setVisibility(View.VISIBLE);
            vw_confirmed.setVisibility(View.VISIBLE);

            iv_preparing.setVisibility(View.VISIBLE);
            vw_preparing.setVisibility(View.GONE);


            iv_delivery.setVisibility(View.GONE);
            vw_delivery.setVisibility(View.GONE);

            iv_complete.setVisibility(View.GONE);


        }
/*

                    if (clsOrderDetail.getOrderStatus() != null) {

                        if (clsOrderDetail.getOrderStatus().equalsIgnoreCase("Ready")
                                || clsOrderDetail.getOrderStatus().equalsIgnoreCase("Ready to pickup")) {


                            txt_order_date.setVisibility(View.VISIBLE);
                            txt_confirmed_datetime.setVisibility(View.VISIBLE);
                            txt_preparing_date.setVisibility(View.VISIBLE);
                            txt_complete_datetime.setVisibility(View.VISIBLE);

                            txt_ready_datetime.setVisibility(View.GONE);
                            txt_delivery_datetime.setVisibility(View.GONE);
                            txt_cancelled_datetime.setVisibility(View.GONE);


                        }
                    }

*/

        if (clsOrderDetail.getOrderStatus() != null &&
                clsOrderDetail.getOrderStatus().equalsIgnoreCase("Completed")) {

            txt_order_date.setVisibility(View.VISIBLE);
            txt_confirmed_datetime.setVisibility(View.VISIBLE);
            txt_preparing_date.setVisibility(View.VISIBLE);
            txt_complete_datetime.setVisibility(View.VISIBLE);
            txt_note.setVisibility(View.VISIBLE);

            iv_order_date.setVisibility(View.VISIBLE);


            vw_order.setVisibility(View.VISIBLE);

            iv_confirmed.setVisibility(View.VISIBLE);
            vw_confirmed.setVisibility(View.VISIBLE);

            iv_preparing.setVisibility(View.VISIBLE);
            vw_preparing.setVisibility(View.VISIBLE);

            iv_complete.setVisibility(View.VISIBLE);

            txt_delivery_datetime.setVisibility(View.GONE);
            txt_cancelled_datetime.setVisibility(View.GONE);

            iv_delivery.setVisibility(View.GONE);
            vw_delivery.setVisibility(View.GONE);

            vw_cancelled.setVisibility(View.GONE);
            iv_cancelled.setVisibility(View.GONE);

        }

        if (clsOrderDetail.getOrderPlaceDate() != null &&
                !clsOrderDetail.getOrderPlaceDate().equalsIgnoreCase("")) {
            vw_order.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            iv_order_date.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            vw_order.setBackgroundColor(ContextCompat.getColor(this, R.color.txt_light_Color));
            iv_order_date.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.txt_light_Color), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        if (clsOrderDetail.getCancelleddate() != null &&
                !clsOrderDetail.getCancelleddate().equalsIgnoreCase("")) {
            vw_cancelled.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            iv_cancelled.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            vw_cancelled.setBackgroundColor(ContextCompat.getColor(this, R.color.txt_light_Color));
            iv_cancelled.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.txt_light_Color), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        if (clsOrderDetail.getDeliveredDate() != null &&
                !clsOrderDetail.getDeliveredDate().equalsIgnoreCase("")) {
            vw_delivery.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            iv_delivery.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            vw_delivery.setBackgroundColor(ContextCompat.getColor(this, R.color.txt_light_Color));
            iv_delivery.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.txt_light_Color), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        if (clsOrderDetail.getReadydate() != null &&
                !clsOrderDetail.getReadydate().equalsIgnoreCase("")) {
            vw_ready.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            iv_ready.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            vw_ready.setBackgroundColor(ContextCompat.getColor(this, R.color.txt_light_Color));
            iv_ready.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.txt_light_Color), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        if (clsOrderDetail.getConfirmeddate() != null &&
                !clsOrderDetail.getConfirmeddate().equalsIgnoreCase("")) {
            vw_confirmed.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            iv_confirmed.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            vw_confirmed.setBackgroundColor(ContextCompat.getColor(this, R.color.txt_light_Color));
            iv_confirmed.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.txt_light_Color), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        if (clsOrderDetail.getPreparingdate() != null &&
                !clsOrderDetail.getPreparingdate().equalsIgnoreCase("")) {
            vw_preparing.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            iv_preparing.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            vw_preparing.setBackgroundColor(ContextCompat.getColor(this, R.color.txt_light_Color));
            iv_preparing.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.txt_light_Color), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        if (clsOrderDetail.getCompletedate() != null &&
                !clsOrderDetail.getCompletedate().equalsIgnoreCase("")) {
            iv_complete.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            iv_complete.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.txt_light_Color), android.graphics.PorterDuff.Mode.SRC_IN);
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

}
