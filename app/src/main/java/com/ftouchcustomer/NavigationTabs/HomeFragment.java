package com.ftouchcustomer.NavigationTabs;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.ftouchcustomer.Appointment.AppointmentList.ActivityAppointmentMerchantList;
import com.ftouchcustomer.Appointment.GetAppointment.AppointmentViewModel;
import com.ftouchcustomer.Appointment.GetAppointment.ClsGetAppointment;
import com.ftouchcustomer.Appointment.GetAppointment.ClsGetAppointmentResponseList;
import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.Global.ClsUserInfo;

import com.ftouchcustomer.Home.OrderPagerAdapter;
import com.ftouchcustomer.LoginActivity;
import com.ftouchcustomer.Merchant.ActivityMerchantList;
import com.ftouchcustomer.Orders.ActivityCustomerOrderDetails;
import com.ftouchcustomer.Orders.ClsCustomerOrderListParams;
import com.ftouchcustomer.Orders.ClsCustomerOrderResponseList;
import com.ftouchcustomer.Orders.OrderListViewModel;
import com.ftouchcustomer.R;
import com.google.gson.Gson;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.RectanglePromptBackground;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    Toolbar toolbar;
    LinearLayout ll_show;
    LinearLayout ll_show_order;
    ViewPager viewPager;
    ViewPager viewPager_order;
    DotsIndicator dots_indicator_order;
    DotsIndicator dotsIndicator;
    AppointmentPagerAdapter adapter;
    OrderPagerAdapter orderPagerAdapter;
    ClsUserInfo clsUserInfo;

    private AppointmentViewModel appointmentViewModel;
    private List<ClsGetAppointmentResponseList> list = new ArrayList<>();
    private List<ClsCustomerOrderResponseList> lstOrderList = new ArrayList<>();
    private OrderListViewModel orderListViewModel;


    TextView txt_customer_order;
    TextView txt_appointment;

    TextView txt_new_order;
    TextView txt_new_appointment;
    LinearLayout progress_bar_layout_app, progress_bar_layout;
    LinearLayout ll_no_orders;
    LinearLayout ll_no_appointment;


    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_home, container, false);

        setHasOptionsMenu(true);

        main(v);


        toolbar.setTitle(getString(R.string.text_home));
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));


        return v;
    }

    private void main(View v) {
        progress_bar_layout = v.findViewById(R.id.progress_bar_layout);
        txt_new_order = v.findViewById(R.id.txt_new_order);
        txt_new_appointment = v.findViewById(R.id.txt_new_appointment);
        progress_bar_layout_app = v.findViewById(R.id.progress_bar_layout_app);
        txt_customer_order = v.findViewById(R.id.txt_customer_order);
        txt_appointment = v.findViewById(R.id.txt_appointment);
        ll_no_orders = v.findViewById(R.id.ll_no_orders);
        ll_no_appointment = v.findViewById(R.id.ll_no_appointment);
        toolbar = v.findViewById(R.id.toolbar);
        ll_show = v.findViewById(R.id.ll_show);
        ll_show_order = v.findViewById(R.id.ll_show_order);
        viewPager_order = v.findViewById(R.id.viewPager_order);
        viewPager = v.findViewById(R.id.viewPager);
        dotsIndicator = v.findViewById(R.id.dots_indicator);
        dots_indicator_order = v.findViewById(R.id.dots_indicator_order);
        clsUserInfo = ClsGlobal.getUserInfo(getActivity());

        orderListViewModel = new ViewModelProvider(this).get(OrderListViewModel.class);
        appointmentViewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);

        adapter = new AppointmentPagerAdapter(getActivity());
        orderPagerAdapter = new OrderPagerAdapter(getActivity());


        txt_new_order.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), ActivityMerchantList.class);
            startActivity(intent);
        });

        txt_new_appointment.setOnClickListener(v1 -> {
            if (!clsUserInfo.getRegisteredmobilenumber().equalsIgnoreCase("")) {
                Intent intent = new Intent(getActivity(), ActivityAppointmentMerchantList.class);
                startActivity(intent);
            } else {
                areYouSureDialog();
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

        Log.d("--log--", "onResume");

        if (!clsUserInfo.getRegisteredmobilenumber().equalsIgnoreCase("")) {

            Log.d("--log--", "1st IFIF");

            if (ClsGlobal.isNetworkConnected(getActivity())) {
                Log.d("--log--", "2nd IFIF");
                getOrderListAPI();
                Log.d("--log--", "2nd API");
                loadAdapter();
                Log.d("--log--", "2nd loadAdp");
            } else {
                Toast.makeText(getActivity(), "Please check your internet connection!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void getOrderListAPI() {

        progress_bar_layout.setVisibility(View.VISIBLE);

        Date date = Calendar.getInstance().getTime();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, +7);
        Date newDate = calendar.getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fromDate = df.format(date);
        String toDate = df.format(newDate);

        Log.d("--log--", "Method");
        ClsCustomerOrderListParams obj = new ClsCustomerOrderListParams();
        obj.setCurrentIndex(1);
        obj.setMobileNo(clsUserInfo.getRegisteredmobilenumber());
        obj.setCustomerID(Integer.parseInt(clsUserInfo.getCustomerId()));


        orderListViewModel.getCustomerOrderList(obj).observe(Objects.requireNonNull(getActivity()), clsCustomerOrderResponse -> {

            if (clsCustomerOrderResponse != null) {
//                pd.dismiss();


                Log.d("--log--", "line 1");

                String msg = clsCustomerOrderResponse.getSuccess();
                int _totalPages = clsCustomerOrderResponse.getTotalPages();

                if ("0".equals(msg)) {

                    ll_no_orders.setVisibility(View.VISIBLE);

                } else if ("1".equals(msg)) {

                    if (viewPager_order != null) {

                        orderPagerAdapter.ClearAdapter();
                        viewPager_order.removeAllViews();

                        viewPager_order.setAdapter(null);
                        orderPagerAdapter.setOnClickViewPager(null);

                        lstOrderList = clsCustomerOrderResponse.getData();

                        if (lstOrderList.size() > 0) {
                            orderPagerAdapter = new OrderPagerAdapter(getActivity());
                            viewPager_order.setAdapter(orderPagerAdapter);

                            lstOrderList = StreamSupport.stream(lstOrderList)
                                    .limit(5)
                                    .collect(Collectors.toList());
                            Log.e("Check", " lstOrderList 5" + lstOrderList.size());

                            orderPagerAdapter.addList(lstOrderList);
                            txt_customer_order.setVisibility(View.VISIBLE);
                            ll_no_orders.setVisibility(View.GONE);

                            orderPagerAdapter.setOnClickViewPager(clsCustomerOrderResponseList -> {
                                Intent intent = new Intent(getActivity(), ActivityCustomerOrderDetails.class);
                                intent.putExtra("CustomerID", clsCustomerOrderResponseList.getCustomerID());
                                intent.putExtra("MobileNo", clsCustomerOrderResponseList.getMobileNo());
                                intent.putExtra("OrderId", clsCustomerOrderResponseList.getOrderID());
                                intent.putExtra("MerchantCode", clsCustomerOrderResponseList.getMerchantCode());
                                intent.putExtra("CanceledBy", clsCustomerOrderResponseList.getCustomerName());
                                startActivity(intent);
                            });
                        } else {
                            Log.d("--log--", "line 6");

                            ll_no_orders.setVisibility(View.VISIBLE);
                        }
                        Log.d("--log--", "line 7");
                    } else {
                        Log.d("--log--", "line 8");
                        ll_no_orders.setVisibility(View.VISIBLE);
                    }
                    dots_indicator_order.setViewPager(viewPager_order);
                    Log.d("--log--", "line 9");
                }
            }
            progress_bar_layout.setVisibility(View.GONE);
        });
    }


    private void loadAdapter() {
        progress_bar_layout_app.setVisibility(View.VISIBLE);
        Date date = Calendar.getInstance().getTime();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, +7);
        Date newDate = calendar.getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fromDate = df.format(date);
        String toDate = df.format(newDate);

        ClsGetAppointment clsGetAppointment = new ClsGetAppointment();
        clsGetAppointment.setCurrentIndex(1);
        clsGetAppointment.setCustomerMobileNo(clsUserInfo.getRegisteredmobilenumber());
        clsGetAppointment.setMode("Customer");

        Log.d("Date---", "fromDate: " + fromDate);
        Log.d("Date---", "toDate: " + toDate);

        Gson gson = new Gson();
        String jsonInString = gson.toJson(clsGetAppointment);
        Log.e("--URL--", "loadAdapter: " + jsonInString);

        appointmentViewModel.getAppointment(clsGetAppointment)
                .observe(getViewLifecycleOwner(), clsGetAppointmentResponse -> {

                    String msg = clsGetAppointmentResponse.getSuccess();

                    if ("0".equals(msg)) {
                        ll_no_appointment.setVisibility(View.VISIBLE);
                    } else if ("1".equals(msg)) {

                        if (viewPager != null) {

                            viewPager.setAdapter(adapter);
                            list = clsGetAppointmentResponse.getData();

                            if (list.size() > 0) {
                                ll_no_appointment.setVisibility(View.GONE);
                                txt_appointment.setVisibility(View.VISIBLE);

                                list = StreamSupport.stream(list)
                                        .limit(5)
                                        .collect(Collectors.toList());
                                Log.e("Check", " lstOrderList 5" + list.size());
                                adapter.addList(list);
                            } else {
                                ll_no_appointment.setVisibility(View.VISIBLE);

                            }
                        } else {
                            txt_appointment.setVisibility(View.VISIBLE);
                            ll_no_appointment.setVisibility(View.VISIBLE);
                        }
                        dotsIndicator.setViewPager(viewPager);
                    } else if ("2".equals(msg)) {
                        Toast.makeText(getActivity(), "Mode is Required.", Toast.LENGTH_SHORT).show();
                    } else if ("3".equals(msg)) {
                        Toast.makeText(getActivity(), "Merchant Code is Required.", Toast.LENGTH_SHORT).show();
                    } else if ("4".equals(msg)) {
                        Toast.makeText(getActivity(), "Customer Mobile No is Required.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }

                    progress_bar_layout_app.setVisibility(View.GONE);

                });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home");
    }

    private void showIntro() {

        new MaterialTapTargetPrompt.Builder(getActivity())
                .setTarget(ll_show_order)
                .setPrimaryText("Your recent orders")
                .setSecondaryText("here you can see your recent orders")
                .setAnimationInterpolator(new FastOutSlowInInterpolator())
                .setBackgroundColour(Color.parseColor("#814a6d"))
                .setCaptureTouchEventOnFocal(true)
                .setAutoDismiss(true)
                .setCaptureTouchEventOutsidePrompt(true)
                .setPromptBackground(new RectanglePromptBackground())
                .setPromptFocal(new RectanglePromptFocal())
                .setPromptStateChangeListener((prompt, state) -> {
                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                            state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {

                        new MaterialTapTargetPrompt.Builder(getActivity())
                                .setTarget(ll_show)
                                .setPrimaryText("Appointment")
                                .setSecondaryText("Here you shows your booked Appointment")
                                .setAnimationInterpolator(new FastOutSlowInInterpolator())
                                .setBackgroundColour(Color.parseColor("#814a6d"))
                                .setCaptureTouchEventOnFocal(true)
                                .setAutoDismiss(true)
                                .setCaptureTouchEventOutsidePrompt(true)
                                .setPromptBackground(new RectanglePromptBackground())
                                .setPromptFocal(new RectanglePromptFocal())
                                .show();
                    }
                })
                .show();
    }

    private boolean isFirstTime() {
        SharedPreferences preferences = getActivity().getSharedPreferences("IsFirstTime", MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("HomeFragment", false);
        if (!ranBefore) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("HomeFragment", true);
            editor.apply();
        }
        return !ranBefore;
    }
}
