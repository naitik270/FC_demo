package com.ftouchcustomer.Orders;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.Database.GetSetValue.ClsOrderDetail;
import com.ftouchcustomer.DisplayImage.DisplayMultipleImgActivity;
import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.Merchant.ClsMerchantResponseList;
import com.ftouchcustomer.R;
import com.ftouchcustomer.ViewModelClass.AddToItemViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

public class ActivityOrderItemList extends AppCompatActivity {

    ClsMerchantResponseList clsMerchantResponseList;
    RecyclerView rv_menu_list;
    AdapterOrderList adapterOrderList;

    ProgressDialog pd;
    private MenuUrlViewModel menuUrlViewModel;
    TextView txt_no_data, txt_name;

    RecyclerView lst_letter;
    LinearLayout ll_uper_filter;
    ImageView iv_view;
    AddToItemViewModel addToItemViewModel;
    ClsOrderDetail _objClsOrderDetail = new ClsOrderDetail();
    String _footerValue = "", selected = "";

    TextView txt_footer_value;
    ImageView iv_remove, iv_clear_all;
    MultipleSelectionAdapter multipleSelectionAdapter;
    List<ClsLayerItemMaster> lstfinalLayerItemMaster = new ArrayList<>();
    List<ClsSelectionModel> lstFilterItemName;

    FragmentAddToItem fragmentAddToItem;
    public static final int DIALOG_QUEST_CODE = 300;
    FrameLayout FrameLayout;
    ImageView iv_add_to_cart;
    LinearLayout ll_cart;
    TextView txt_cart_badge;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        menuUrlViewModel = new ViewModelProvider(ActivityOrderItemList.this)
                .get(MenuUrlViewModel.class);

        addToItemViewModel = new
                ViewModelProvider(this).get(AddToItemViewModel.class);

        clsMerchantResponseList = (ClsMerchantResponseList) getIntent().getSerializableExtra("clsMerchantResponseList");

        ll_cart = findViewById(R.id.ll_cart);
        FrameLayout = findViewById(R.id.FrameLayout);
        rv_menu_list = findViewById(R.id.rv_menu_list);
        txt_no_data = findViewById(R.id.txt_no_data);
        txt_name = findViewById(R.id.txt_name);
        ll_uper_filter = findViewById(R.id.ll_uper_filter);
        lst_letter = findViewById(R.id.lst_letter);
        iv_view = findViewById(R.id.iv_view);
        txt_footer_value = findViewById(R.id.txt_footer_value);
        iv_remove = findViewById(R.id.iv_remove);
        iv_clear_all = findViewById(R.id.iv_clear_all);
        iv_add_to_cart = findViewById(R.id.iv_add_to_cart);
        txt_cart_badge = findViewById(R.id.txt_cart_badge);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);

        lst_letter.setLayoutManager(layoutManager);

        if (clsMerchantResponseList.getBusinessName() != null) {
            txt_name.setText(clsMerchantResponseList.getBusinessName());
        } else {
            txt_name.setText("No Name");
        }

        Log.d("--cust--", "onCreate: " + clsMerchantResponseList.getCustomerCode());

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityOrderItemList.this);
        rv_menu_list.setLayoutManager(linearLayoutManager);

        adapterOrderList = new AdapterOrderList(ActivityOrderItemList.this);
        rv_menu_list.setAdapter(adapterOrderList);

        pd = ClsGlobal._prProgressDialog(ActivityOrderItemList.this,
                "Wait for getting Menu...", true);
        pd.show();

        loadMenuUrl(clsMerchantResponseList.getCustomerCode(), "All");

//        loadMenuUrl("CAS002", "All");
        viewImage(clsMerchantResponseList.getMenuImgsUrl());
//        viewImage("http://136.233.136.42:89/Uploads/Document/Customer/CAS002");

        Log.d("--Menu--", "onCreate: " + clsMerchantResponseList.getMenuImgsUrl());

        multipleSelectionAdapter = new MultipleSelectionAdapter(ActivityOrderItemList.this);
        lst_letter.setAdapter(multipleSelectionAdapter);

        multipleSelectionAdapter.setOnCharClick((obj, position, viewHolder) -> {

            obj.setSelected(!obj.isSelected());
            lstFilterItemName.set(lstFilterItemName.indexOf(obj), obj);


            multipleSelectionAdapter.updateSelection(obj);

            viewHolder.view.setBackgroundColor(obj.isSelected() ? Color.parseColor("#5e3750") : Color.WHITE);
            viewHolder.txt_cat_name.setTextColor(obj.isSelected() ? Color.parseColor("#FFFFFF") : Color.BLACK);

            new AsyncTask<Void, Void, List<ClsLayerItemMaster>>() {

                @Override
                protected List<ClsLayerItemMaster> doInBackground(Void... voids) {

                    List<ClsLayerItemMaster> list = new ArrayList<>();
                    selected = StreamSupport.stream(lstFilterItemName)
//                    selected = StreamSupport.stream(lstfinalLayerItemMaster)
                            .filter(ClsSelectionModel::isSelected)
                            .map(ClsSelectionModel::get_character)
                            .distinct()
                            .collect(Collectors.joining(", "));

                    Gson gson1 = new Gson();
                    String jsonInString1 = gson1.toJson(lstFilterItemName);
                    Log.e("--Test--", "Gson: " + jsonInString1);


                    if (!selected.isEmpty()) {
                        list = StreamSupport.stream(lstfinalLayerItemMaster)
                                .filter(s -> {
                                    try {
                                        Log.d("--Test--", "selected: " + s.getITEM_NAME().toUpperCase().charAt(0));
                                        return selected.contains(String.valueOf(s.getITEM_NAME().toUpperCase().charAt(0)));
                                    } catch (Exception e) {

                                    }
                                    return false;
                                })
                                .collect(Collectors.toList());

                        list = sortAndAddSections(list);

                        Gson gson = new Gson();
                        String jsonInString = gson.toJson(list);
                        Log.e("--Item--", "Item: " + jsonInString);
                    }
                    return list;
                }

                @Override
                protected void onPostExecute(List<ClsLayerItemMaster> insertResult) {
                    super.onPostExecute(insertResult);

                    if (insertResult.size() > 0) {
                        adapterOrderList.AddItems(insertResult);
                    } else {
                        adapterOrderList.AddItems(lstfinalLayerItemMaster);
                    }

                }
            }.execute();

        });

        iv_clear_all.setOnClickListener(v -> {

//            selected = "";
            multipleSelectionAdapter.clearSelection();
            adapterOrderList.AddItems(lstfinalLayerItemMaster);
            lstFilterItemName = multipleSelectionAdapter.getAdapterList();

            Gson gson = new Gson();
            String jsonInString = gson.toJson(lstFilterItemName);
            Log.e("Check", "iv_clear_all---" + jsonInString);
        });

        iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addToItemViewModel.DeleteAllOrderDetail();
            }
        });


        addToItemViewModel.getFooterValue(clsMerchantResponseList.getCustomerCode()).observe(ActivityOrderItemList.this,
                _obj -> {
                    _objClsOrderDetail = _obj;

                    if (_obj.getSize() != 0) {
//                        _footerValue = " ".concat("BILL#:" + _obj.getOrderNo()).concat(", ITEMS: " + _obj.getSize())
//                                .concat(", TOTAL: \u20B9 " + ClsGlobal.round(_obj.getTotal(), 2));

                        _footerValue = "TOTAL: \u20B9 " + ClsGlobal.round(_obj.getTotal(), 2);

                    } else {
                        _footerValue = "NO ITEMS";
                    }

                    txt_cart_badge.setText(String.valueOf(_obj.getSize()));
                    txt_footer_value.setText(_footerValue);
                });


        iv_view.setOnClickListener(v -> {

            Intent intent = new Intent(ActivityOrderItemList.this, OrdersActivity.class);
            intent.putExtra("clsMerchantResponseList", clsMerchantResponseList);
            intent.putExtra("_orderDetailID", _objClsOrderDetail.getOrderDetailID());
            intent.putExtra("_customerID", _objClsOrderDetail.getCustomerID());
            intent.putExtra("_customerCode", _objClsOrderDetail.getCustomerCode());
            startActivity(intent);
        });

        txt_cart_badge.setText(String.valueOf(_objClsOrderDetail.getSize()));


        ll_cart.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityOrderItemList.this, OrdersActivity.class);
            intent.putExtra("clsMerchantResponseList", clsMerchantResponseList);
            intent.putExtra("_orderDetailID", _objClsOrderDetail.getOrderDetailID());
            intent.putExtra("_customerID", _objClsOrderDetail.getCustomerID());
            intent.putExtra("_customerCode", _objClsOrderDetail.getCustomerCode());
            startActivity(intent);
        });

    }


    private void viewImage(String menuImgUrl) {
        adapterOrderList.setOnViewImg((objClsLayerItemMaster, position) -> {

            Log.d("--Test--", "viewImage: "+objClsLayerItemMaster.getLAYERITEM_ID());

            Intent intent = new Intent(getApplicationContext(), DisplayMultipleImgActivity.class);
            intent.putExtra("ID", objClsLayerItemMaster.getLAYERITEM_ID());
            intent.putExtra("_imgMode", "Item");
            intent.putExtra("imgSave", "Preview");
            intent.putExtra("menuImgUrl", menuImgUrl);
            intent.putExtra("objClsLayerItemMaster", objClsLayerItemMaster);
            startActivity(intent);

        });

        adapterOrderList.setOnItem((objClsLayerItemMaster) -> {
            addItemToOrder(objClsLayerItemMaster);
            Log.e("--Menu--", "URL");
        });

    }

    void addItemToOrder(ClsLayerItemMaster objClsLayerItemMaster) {

        hidekeyboard();
        FrameLayout.setVisibility(View.GONE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentAddToItem = new FragmentAddToItem(objClsLayerItemMaster,
                clsMerchantResponseList.getBusinessName(),
                clsMerchantResponseList.getCustomerID(),
                clsMerchantResponseList.getCustomerCode());

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        transaction.add(android.R.id.content, fragmentAddToItem)
                .addToBackStack(null).commit();

        fragmentAddToItem.setOnCallbackResult(requestCode -> {
            if (requestCode == DIALOG_QUEST_CODE) {
                Log.e("requestCode", String.valueOf(requestCode));
                FrameLayout.setVisibility(View.VISIBLE);
            }
        });
        FrameLayout.setVisibility(View.INVISIBLE);
    }


    @SuppressLint("StaticFieldLeak")
    private void loadMenuUrl(String userCode, String mode) {


        Log.d("--all--", "userCode: " + userCode);

        menuUrlViewModel.getMenuListUrl(userCode)
                .observe(this, lstMerchantList -> {
                    new AsyncTaskSetHeader(lstMerchantList).execute();
                });
    }


    @SuppressLint("StaticFieldLeak")
    public class AsyncTaskSetHeader extends AsyncTask<Void, Void, Void> {

        public AsyncTaskSetHeader(List<ClsLayerItemMaster> lstLayerItemMaster) {
            lstfinalLayerItemMaster = lstLayerItemMaster;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            lstFilterItemName = StreamSupport.stream(lstfinalLayerItemMaster)
                    .map(s -> new ClsSelectionModel(String.valueOf(s.getITEM_NAME().toUpperCase().charAt(0)), false))
                    .distinct().collect(Collectors.toList());

            lstfinalLayerItemMaster = sortAndAddSections(lstfinalLayerItemMaster);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
            if (lstfinalLayerItemMaster != null && lstfinalLayerItemMaster.size() != 0) {
                txt_no_data.setVisibility(View.GONE);
                ll_uper_filter.setVisibility(View.VISIBLE);
                adapterOrderList.AddItems(lstfinalLayerItemMaster);
                multipleSelectionAdapter.AddItems(lstFilterItemName);
            } else {
                txt_no_data.setVisibility(View.VISIBLE);
                ll_uper_filter.setVisibility(View.GONE);
            }
        }
    }


    List<String> headerlist = new ArrayList<>();

    private List<ClsLayerItemMaster> sortAndAddSections(List<ClsLayerItemMaster> itemList) {
        headerlist.clear();
        List<ClsLayerItemMaster> tempList = new ArrayList<>();
        String header = "";
        for (int i = 0; i < itemList.size(); i++) {
            //If it is the start of a new section we create a new listcell and add it to our array

            if (itemList.get(i).getITEM_NAME() != null) {
                //if (!itemList.get(i).isHeader()) {
                if (!(header.equals(String.valueOf(itemList.get(i).getITEM_NAME().charAt(0)).toUpperCase()))) {
                    ClsLayerItemMaster sectionCell = new ClsLayerItemMaster(String.valueOf(itemList.get(i).getITEM_NAME()
                            .charAt(0)).toUpperCase());
                    sectionCell.setHeader(true);
                    //A CHECK IN ALL ARRAY
                    if (!headerlist.contains(String.valueOf(itemList.get(i)
                            .getITEM_NAME().charAt(0)).toUpperCase())) {

                        tempList.add(sectionCell);

                        headerlist.add(String.valueOf(itemList.get(i).getITEM_NAME()
                                .charAt(0)).toUpperCase());
                    }
                }
                //}
            }

            Log.e("check", "outside if");
            tempList.add(itemList.get(i));
        }
        return tempList;
    }

    void hidekeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(ActivityOrderItemList.this.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
