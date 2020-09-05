package com.ftouchcustomer.NavigationTabs;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ftouchcustomer.Database.Entity.ClsOrderDetailsEntity;
import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.Merchant.ActivityMerchantList;
import com.ftouchcustomer.PlaceOrder.ActivityPlaceOrder;
import com.ftouchcustomer.R;
import com.ftouchcustomer.ViewModelClass.AddToItemViewModel;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class CartFragment extends Fragment {

    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
//    TextView tv_no_item_found;

    LinearLayout ll_no_orders;
    AdapterCart adapterCart;
    AddToItemViewModel addToItemViewModel;
    TextView txt_new_order;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addToItemViewModel = new
                ViewModelProvider(getActivity()).get(AddToItemViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_cart, container, false);

        Toolbar toolbar = v.findViewById(R.id.toolbar);
        toolbar.setTitle("Cart");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setHasOptionsMenu(true);

        main(v);
        return v;
    }

    private void main(View v) {
        txt_new_order = v.findViewById(R.id.txt_new_order);
        recyclerView = v.findViewById(R.id.recyclerView);
        ll_no_orders = v.findViewById(R.id.ll_no_orders);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapterCart = new AdapterCart(getActivity());
        recyclerView.setAdapter(adapterCart);
        Log.e("--AddTO--", "main");

        getCartList();

        adapterCart.SetOnClickListener(new AdapterCart.OnDeleteClick() {
            @Override
            public void onItemDelete(ClsOrderDetailsEntity obj, int position) {
                ShowAlertDialog("Do you want to delete this item...", obj.getOrderDetailID(), position);
            }
        });

        adapterCart.setOnMerchantCartClick(new AdapterCart.OnClickMerchantCart() {
            @Override
            public void OnItemClick(ClsOrderDetailsEntity obj) {

                Continue(obj);
            }
        });


        txt_new_order.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), ActivityMerchantList.class);
            startActivity(intent);
        });

    }

    private void ShowAlertDialog(String message, int OrderDetailId, int position) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog OptionDialog = builder.create();
        builder.setMessage(message);

        builder.setPositiveButton("YES", (dialog, id) -> {

            addToItemViewModel.DeleteByOrderDetailID(OrderDetailId, "Item deleted successfully!");
            getCartList();

            OptionDialog.dismiss();
            OptionDialog.cancel();
        });

        builder.setNegativeButton("NO", (dialogInterface, i) -> {
            OptionDialog.dismiss();
            OptionDialog.cancel();
        });
        builder.show();
    }

    void Continue(ClsOrderDetailsEntity obj) {
        Intent intent = new Intent(getActivity(), ActivityPlaceOrder.class);
        intent.putExtra("_orderDetailID", obj.getOrderDetailID());
        intent.putExtra("_customerID", obj.getCustomerID());
        intent.putExtra("_customerCode", obj.getCustomerCode());
        intent.putExtra("List", (Serializable) adapterCart.getPlaceOrderList());
        intent.putExtra("_grandTotal", String.valueOf(obj.getTotalAmountMerchantWise()));

        Gson gson = new Gson();
        String jsonInString = gson.toJson(adapterCart.getPlaceOrderList());
        Log.e("--AddTO--", "obj: " + jsonInString);

        startActivity(intent);
    }


    @SuppressLint("StaticFieldLeak")
    void getCartList() {

        new AsyncTask<Void, Void, List<ClsOrderDetailsEntity>>() {
            ProgressDialog pd;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pd = ClsGlobal._prProgressDialog(getActivity(),
                        "Wait for getting Menu...", true);
                pd.show();
            }

            @Override
            protected List<ClsOrderDetailsEntity> doInBackground(Void... voids) {

                List<ClsOrderDetailsEntity> list =
                        addToItemViewModel.getOrderDetailListByMerchantCode();

                Gson gson = new Gson();
                String jsonInString = gson.toJson(list);
                Log.e("Check", "list : " + jsonInString);

                return list;
            }

            @Override
            protected void onPostExecute(List<ClsOrderDetailsEntity> result) {
                super.onPostExecute(result);

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("Check", "getOrderDetailListByMerchantCode: ");
                if (result != null && result.size() != 0) {
//
                    ll_no_orders.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    ll_no_orders.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

                adapterCart.AddItems(result);
//                listLiveData.removeObserver(observer);

            }
        }.execute();

    }

}
