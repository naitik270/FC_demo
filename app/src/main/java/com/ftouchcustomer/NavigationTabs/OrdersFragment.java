package com.ftouchcustomer.NavigationTabs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.Global.ClsUserInfo;
import com.ftouchcustomer.Merchant.ActivityFilterMerchantList;
import com.ftouchcustomer.Merchant.ActivityMerchantList;
import com.ftouchcustomer.Merchant.EndlessRecyclerViewScrollListener;
import com.ftouchcustomer.Orders.ActivityCustomerOrderDetails;
import com.ftouchcustomer.Orders.AdapterCustomerOrderList;
import com.ftouchcustomer.Orders.ClsCustomerOrderResponseList;
import com.ftouchcustomer.Orders.ClsCustomerOrderListParams;
import com.ftouchcustomer.Orders.OrderListViewModel;
import com.ftouchcustomer.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

import static android.content.Context.MODE_PRIVATE;
import static com.ftouchcustomer.Global.ConnectionCheckBroadcast.CheckInternetConnection;


public class OrdersFragment extends Fragment {

    private RecyclerView recyclerView;
    private OrderListViewModel orderListViewModel;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private ProgressBar progress_bar;
    private static final String mPreferncesName = "MyPerfernces";
    private String getWhatsAppDefaultApp = "";
    String url = null;
    private FloatingActionButton fab_merchant_list;
    ClsUserInfo clsUserInfo;
    private ProgressDialog pd;
    private List<ClsCustomerOrderResponseList> responseOrderList = new ArrayList<>();
    private AdapterCustomerOrderList adapterOrderList;
    //    TextView txt_no_data;
    RelativeLayout ll_no_order_found;
    TextView tv_new_order;


    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        orderListViewModel = new ViewModelProvider
                (Objects.requireNonNull(getActivity())).get(OrderListViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_merchant, container, false);

        setHasOptionsMenu(true);

        Toolbar toolbar = v.findViewById(R.id.toolbar);

        toolbar.setTitle("Merchant");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        clsUserInfo = ClsGlobal.getUserInfo(getActivity());
        main(v);

        return v;
    }


    private void main(View v) {
//        txt_no_data = v.findViewById(R.id.txt_no_data);
        fab_merchant_list = v.findViewById(R.id.fab_merchant_list);
        tv_new_order = v.findViewById(R.id.tv_new_order);
        ll_no_order_found = v.findViewById(R.id.ll_no_order_found);
        recyclerView = v.findViewById(R.id.recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        progress_bar = v.findViewById(R.id.progress_bar);

        adapterOrderList = new AdapterCustomerOrderList(getActivity());
        recyclerView.setAdapter(adapterOrderList);

        if (isFirstTime()) {
            showIntro();
        }

        adapterOrderList.setOnLayoutClick(clsCustomerOrderResponseList -> {
            Intent intent = new Intent(getActivity(), ActivityCustomerOrderDetails.class);
            intent.putExtra("CustomerID", clsCustomerOrderResponseList.getCustomerID());
            intent.putExtra("MobileNo", clsCustomerOrderResponseList.getMobileNo());
            intent.putExtra("OrderId", clsCustomerOrderResponseList.getOrderID());
            intent.putExtra("MerchantCode", clsCustomerOrderResponseList.getMerchantCode());
            intent.putExtra("CanceledBy", clsCustomerOrderResponseList.getCustomerName());
            startActivity(intent);
        });

        tv_new_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ActivityMerchantList.class);
                startActivity(intent);
            }
        });


        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(
                linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                progress_bar.setVisibility(View.VISIBLE);

                if (CheckInternetConnection(getActivity())) {
                    getOrderListAPI(++page);
                } else {
                    progress_bar.setVisibility(View.GONE);

                    Toast.makeText(getActivity(),
                            "Please check your internet connection!", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });

        Log.e("--list--", "MobileNumber: " + clsUserInfo.getRegisteredmobilenumber());
        Log.e("--list--", "getCustomerId: " + clsUserInfo.getCustomerId());

        pd = ClsGlobal._prProgressDialog(getActivity(), "Getting list...", false);
        pd.show();

        fab_merchant_list.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), ActivityMerchantList.class);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (CheckInternetConnection(getActivity())) {
            responseOrderList.clear();
            adapterOrderList.notifyDataSetChanged();
            getOrderListAPI(1);
        } else {
            pd.dismiss();
            Toast.makeText(getActivity(), "Please check your internet connection!", Toast.LENGTH_SHORT).show();
        }
    }

    void getOrderListAPI(int CurrentIndex) {
        ClsCustomerOrderListParams obj = new ClsCustomerOrderListParams();
        obj.setCurrentIndex(CurrentIndex);
        obj.setMobileNo(clsUserInfo.getRegisteredmobilenumber());
        obj.setCustomerID(Integer.parseInt(clsUserInfo.getCustomerId()));

        Log.e("--list--", "MobileNumber: " + clsUserInfo.getRegisteredmobilenumber());
        Log.e("--list--", "getCustomerId: " + clsUserInfo.getCustomerId());
        Log.e("--list--", "CurrentIndex: " + CurrentIndex);

        orderListViewModel.getCustomerOrderList(obj).observe(Objects.requireNonNull(getActivity()),
                clsCustomerOrderResponse -> {

                    if (clsCustomerOrderResponse != null) {
                        pd.dismiss();

                        String msg = clsCustomerOrderResponse.getSuccess();
                        int _totalPages = clsCustomerOrderResponse.getTotalPages();

                        Log.e("--list--", "getOrderListAPI: " + msg);
                        Log.e("--list--", "_totalPages: " + _totalPages);
                        Log.e("--list--", "getData: " + clsCustomerOrderResponse.getData());


                        if ("0".equals(msg)) {
                            if (responseOrderList.size() <= 0) {
                                ll_no_order_found.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }
                            progress_bar.setVisibility(View.GONE);

                        } else if ("1".equals(msg)) {
                            Log.e("Check", "\"1\".equals(msg)");
                            if (clsCustomerOrderResponse.getData().size() > 0) {
                                responseOrderList.addAll(clsCustomerOrderResponse.getData());
                            }
                            if (responseOrderList.size() > 0) {
                                Log.e("Check", "responseOrderList.size() > 0)");
                                ll_no_order_found.setVisibility(View.GONE);
                                progress_bar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                adapterOrderList.addList(responseOrderList);
                            }
                        }

                    }
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Order List");
    }

    private void showIntro() {
        new MaterialTapTargetPrompt.Builder(getActivity())
                .setTarget(fab_merchant_list)
                .setPrimaryText("New Order")
                .setSecondaryText("Here from you shopping something")
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
        boolean ranBefore = preferences.getBoolean("OrdersFragment", false);
        if (!ranBefore) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("OrdersFragment", true);
            editor.apply();
        }
        return !ranBefore;
    }

}