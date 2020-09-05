package com.ftouchcustomer.NavigationTabs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.Appointment.AppointmentList.ActivityAppointmentMerchantList;
import com.ftouchcustomer.Appointment.AppointmentList.AdapterGetAppointment;
import com.ftouchcustomer.Appointment.GetAppointment.AppointmentViewModel;
import com.ftouchcustomer.Appointment.CancleAppointment.ClsCancelAppointment;
import com.ftouchcustomer.Appointment.GetAppointment.ClsGetAppointment;
import com.ftouchcustomer.Appointment.GetAppointment.ClsGetAppointmentResponseList;
import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.Global.ClsUserInfo;
import com.ftouchcustomer.LoginActivity;
import com.ftouchcustomer.Orders.ActivityCustomerOrderDetails;
import com.ftouchcustomer.R;
import com.ftouchcustomer.SplashActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

import static android.content.Context.MODE_PRIVATE;

public class FragmentAppointment extends Fragment {
    private AppointmentViewModel appointmentViewModel;
    private ProgressDialog pd;
    private RecyclerView recyclerView;
    private RelativeLayout ll_no_appointment_found;
    private AdapterGetAppointment adapterGetAppointment;
    private List<ClsGetAppointmentResponseList> list = new ArrayList<>();
    ProgressBar progress_bar;
    private Dialog mDialog;
    ClsUserInfo clsUserInfo;
    FloatingActionButton fab;

    public FragmentAppointment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        clsUserInfo = ClsGlobal.getUserInfo(getActivity());
        Log.e("--Menu--", "getLoginStatus: " + clsUserInfo.getLoginStatus());
        appointmentViewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fab = view.findViewById(R.id.fab_add);
        progress_bar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.recyclerView);
        ll_no_appointment_found = view.findViewById(R.id.ll_no_appointment_found);
        TextView new_appointment = view.findViewById(R.id.new_appointment);

        adapterGetAppointment = new AdapterGetAppointment(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (isFirstTime()) {
            showIntro();
        }

        fab.setOnClickListener(v -> {
            if (!clsUserInfo.getRegisteredmobilenumber().equalsIgnoreCase("")) {
                Intent intent = new Intent(getActivity(), ActivityAppointmentMerchantList.class);
                startActivity(intent);
            } else {
                areYouSureDialog();
            }
        });

        new_appointment.setOnClickListener(view13 -> {

            if (!clsUserInfo.getRegisteredmobilenumber().equalsIgnoreCase("")) {
                Intent intent = new Intent(getActivity(), ActivityAppointmentMerchantList.class);
                startActivity(intent);
            } else {
                areYouSureDialog();
            }

        });


        adapterGetAppointment.SetOnItemClickListener((listResponse, position, mode) -> {

            Gson gson1 = new Gson();
            String jsonInString1 = gson1.toJson(listResponse);
            Log.e("--appointment--", "list response: " + jsonInString1);
            Log.e("--appointment--", "mode: " + mode);

            if (mode.equalsIgnoreCase("Cancel")) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Cancel appointment?");

                LayoutInflater inflater = this.getLayoutInflater();
                View viewInflated = inflater.inflate(R.layout.dialog_edittext, null);

                final EditText input = (EditText) viewInflated.findViewById(R.id.input);
                builder.setView(viewInflated);

                builder.setNegativeButton("Cancel", (dialog, which) ->
                        dialog.cancel());
                builder.setPositiveButton("Save", (dialog, which) ->
                {
                    pd = ClsGlobal._prProgressDialog(getActivity(), "Canceling Appointment...", true);
                    pd.show();
                    if (!input.getText().toString().equalsIgnoreCase("")) {

                        ClsCancelAppointment clsCancelAppointment = new ClsCancelAppointment();
                        clsCancelAppointment.setMerchantId(0);
                        clsCancelAppointment.setMerchantCode("");
                        clsCancelAppointment.setAppointmentId(listResponse.getAppointmentID());
                        clsCancelAppointment.setCustomerId(clsUserInfo.getCustomerId());
                        clsCancelAppointment.setCustomerMobileNo(clsUserInfo.getRegisteredmobilenumber());
                        clsCancelAppointment.setReason(input.getText().toString());
                        clsCancelAppointment.setCancelledBy("You");
                        clsCancelAppointment.setMode("Customer");

                        Gson gson = new Gson();
                        String jsonInString = gson.toJson(clsCancelAppointment);
                        Log.e("--URL--", "clsCancelAppointment---" + jsonInString);

                        appointmentViewModel.cancelAppointment(clsCancelAppointment)
                                .observe(getViewLifecycleOwner(), clsCancelAppointment1 -> {

                                    String success = clsCancelAppointment1.getSuccess();

                                    String msg = clsCancelAppointment1.getMessage();
                                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

                                    if (success.equalsIgnoreCase("1")) {
                                        pd.dismiss();
                                        loadAdapter();
                                    }
                                });
                    } else {
                        input.setError("Reason is required.");
                    }
                });

                builder.show();
            } else if (mode.equalsIgnoreCase("Tracking")) {

                mDialog = new Dialog(getActivity());
                if (mDialog.isShowing()) return;
                mDialog = new Dialog(getActivity());
                mDialog.setContentView(R.layout.row_appointment_get);
                mDialog.setCanceledOnTouchOutside(true);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                mDialog.setCancelable(true);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(Objects.requireNonNull(mDialog.getWindow()).getAttributes());
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                mDialog.getWindow().setAttributes(lp);
                mDialog.show();

                TextView tv_merchant_name;
                TextView tv_name;
                TextView tv_mobile_number;
                TextView tv_appointment_date;
                TextView tv_appointment_no;
                TextView tv_appointment_request_date;
                TextView tv_services;
                TextView tv_remark;
                TextView tv_status;
                TextView tv_reason;
                TextView tv_cancelledBy;
                LinearLayout ll_cancel;

                tv_merchant_name = mDialog.findViewById(R.id.tv_merchant_name);
                tv_name = mDialog.findViewById(R.id.tv_name);
                tv_mobile_number = mDialog.findViewById(R.id.tv_mobile_number);
                tv_appointment_date = mDialog.findViewById(R.id.tv_appointment_date);
                tv_appointment_request_date = mDialog.findViewById(R.id.tv_appointment_request_dt);
                tv_appointment_no = mDialog.findViewById(R.id.tv_appointment_no);
                tv_services = mDialog.findViewById(R.id.tv_services);
                tv_remark = mDialog.findViewById(R.id.tv_remark);
                tv_status = mDialog.findViewById(R.id.tv_status);
                tv_reason = mDialog.findViewById(R.id.tv_reason);
                tv_cancelledBy = mDialog.findViewById(R.id.tv_cancelledBy);
                ll_cancel = mDialog.findViewById(R.id.ll_cancel);

                tv_merchant_name.setText(listResponse.getMerchantName());
                tv_name.setText(listResponse.getCustomerName());
                tv_mobile_number.setText(listResponse.getAppointmentMobileNo());
                tv_appointment_date.setText(listResponse.getAppointmentDate());
                tv_appointment_request_date.setText(listResponse.getAppointmentRequestDate());

                if (listResponse.getAppointmentNo() != null &&
                        !listResponse.getAppointmentNo().equalsIgnoreCase("")) {
                    tv_appointment_no.setText(listResponse.getAppointmentNo());
                } else {
                    tv_appointment_no.setText("Not generated");
                }

                tv_services.setText(listResponse.getServices());
                tv_remark.setText(listResponse.getStatusRemark());
                if (listResponse.getStatus().equalsIgnoreCase("Completed")) {
                    tv_status.setBackgroundColor(Color.parseColor("#CFEBDA"));
                    tv_status.setTextColor(Color.parseColor("#365B37"));
                } else if (listResponse.getStatus().equalsIgnoreCase("Cancelled")) {
                    tv_status.setBackgroundColor(Color.parseColor("#F5C6CA"));
                    tv_status.setTextColor(Color.parseColor("#6E252C"));
                } else if (listResponse.getStatus().equalsIgnoreCase("confirmation pending")) {
                    tv_status.setBackgroundColor(Color.parseColor("#C9E6EA"));
                    tv_status.setTextColor(Color.parseColor("#00707E"));
                } else if (listResponse.getStatus().equalsIgnoreCase("confirmed")) {
                    tv_status.setBackgroundColor(Color.parseColor("#CBE5FE"));
                    tv_status.setTextColor(Color.parseColor("#30517C"));
                } else if (listResponse.getStatus().equalsIgnoreCase("Verified")) {
                    tv_status.setBackgroundColor(Color.parseColor("#D1C7CD"));
                    tv_status.setTextColor(Color.parseColor("#5e3750"));
                } else {
                    tv_status.setBackgroundColor(Color.parseColor("#EED581"));
                    tv_status.setTextColor(Color.parseColor("#906811"));
                }

                tv_status.setText(" " + listResponse.getStatus().toUpperCase() + " ");

                tv_reason.setText(listResponse.getReason());

                tv_cancelledBy.setText(listResponse.getCancelledBy());

                if (listResponse.getStatus().equalsIgnoreCase("Cancelled")) {
                    ll_cancel.setVisibility(View.VISIBLE);
                } else {
                    ll_cancel.setVisibility(View.GONE);
                }
            }
        });
    }

    private void areYouSureDialog() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dilaog_cust_alert);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView content = dialog.findViewById(R.id.content);
        Button btn_ok = dialog.findViewById(R.id.btn_ok);
        btn_ok.setText("LOGIN");
        Button btn_no = dialog.findViewById(R.id.btn_no);
        btn_no.setText("NO");
        btn_no.setVisibility(View.GONE);

        content.setText(getText(R.string.alert_login_appointment));

        btn_ok.setOnClickListener(v -> {
            dialog.dismiss();
//            cancelPlaceOrder();

            Intent i = new Intent(getActivity(), LoginActivity.class);
            i.putExtra("noLogin", "");
            startActivity(i);
            getActivity().finish();
        });

        btn_no.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!clsUserInfo.getRegisteredmobilenumber().equalsIgnoreCase("")) {
            if (ClsGlobal.isNetworkConnected(getActivity())) {
                pd = ClsGlobal._prProgressDialog(getActivity(), "Getting list...", false);
                pd.show();
                loadAdapter();
            } else {
                Toast.makeText(getActivity(), "Please check your internet connection!", Toast.LENGTH_SHORT).show();
            }
        } else {
            ll_no_appointment_found.setVisibility(View.VISIBLE);
        }
    }

    private void loadAdapter() {
        ClsGetAppointment clsGetAppointment = new ClsGetAppointment();
        clsGetAppointment.setCurrentIndex(1);
        clsGetAppointment.setCustomerMobileNo(clsUserInfo.getRegisteredmobilenumber());
        clsGetAppointment.setMode("Customer");

        Gson gson = new Gson();
        String jsonInString = gson.toJson(clsGetAppointment);
        Log.e("--URL--", "loadAdapter: " + jsonInString);

        appointmentViewModel.getAppointment(clsGetAppointment)
                .observe(getViewLifecycleOwner(), clsGetAppointmentResponse -> {
                    pd.dismiss();

                    String msg = clsGetAppointmentResponse.getSuccess();

                    if ("0".equals(msg)) {
                        recyclerView.setVisibility(View.GONE);
                        ll_no_appointment_found.setVisibility(View.VISIBLE);
                    } else if ("1".equals(msg)) {

                        recyclerView.setAdapter(adapterGetAppointment);
                        list = clsGetAppointmentResponse.getData();


                        String jsonInStringxx = gson.toJson(list);
                        Log.e("--URL--", "jsonInStringxx---" + jsonInStringxx);


                        if (list.size() != 0) {
                            adapterGetAppointment.addList(list);
                        }

                    } else if ("2".equals(msg)) {
                        Toast.makeText(getActivity(), "Mode is Required.", Toast.LENGTH_SHORT).show();
                    } else if ("3".equals(msg)) {
                        Toast.makeText(getActivity(), "Merchant Code is Required.", Toast.LENGTH_SHORT).show();
                    } else if ("4".equals(msg)) {
                        Toast.makeText(getActivity(), "Customer Mobile No is Required.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showIntro() {
        new MaterialTapTargetPrompt.Builder(getActivity())
                .setTarget(fab)
                .setPrimaryText("New Appointment")
                .setSecondaryText("Here you booked your Appointment")
                .setAnimationInterpolator(new FastOutSlowInInterpolator())
                .setBackgroundColour(Color.parseColor("#814a6d"))
                .setCaptureTouchEventOnFocal(true)
                .setAutoDismiss(true)
                .setCaptureTouchEventOutsidePrompt(true)
                .setAutoDismiss(true)
                .show();
    }

    private boolean isFirstTime() {
        SharedPreferences preferences = getActivity().getSharedPreferences("IsFirstTime", MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("FragmentAppointment", false);
        if (!ranBefore) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("FragmentAppointment", true);
            editor.apply();
        }
        return !ranBefore;
    }
}
