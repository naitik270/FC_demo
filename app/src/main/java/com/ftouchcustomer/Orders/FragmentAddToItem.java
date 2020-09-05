package com.ftouchcustomer.Orders;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.ftouchcustomer.Database.Entity.ClsOrderDetailsEntity;
import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.R;
import com.ftouchcustomer.ViewModelClass.AddToItemViewModel;
import com.google.gson.Gson;

import java.util.Date;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class FragmentAddToItem extends DialogFragment implements TextWatcher {


    private Double counter = 1.00;
    private Double total_Amount = 0.0;
    double _includingTotalAmount = 0.0;

    private TextView txt_title, txt_without_tax_total, txt_with_sale_rate;
    private EditText edt_qty;
    private EditText edt_comment;
    private Button Btn_Add, Btn_Min;
    private ImageButton bt_close;
    private TextView txt_with_tax_total, txt_total_tax_amount;
    private TextView txt_cgst, txt_sgst, txt_igst;
    private TextView txt_cgst_val, txt_sgst_val, txt_igst_val;
    private TextView txt_discount_val, txt_discount_per;
    private TextView txt_net_amount;
    private LinearLayout ll_tax;
    private ImageView iv_clear_qty, iv_clear_comments;


    ClsLayerItemMaster objClsLayerItemMaster;
    private CallbackResult callbackResult;
    private OnUpdateFooterValue onUpdateFooterValue;
    AddToItemViewModel addToItemViewModel;
    int _customerID = 0;
    String _customerCode = "";
    String merchantName = "";

    Button btn_add_to_order;

    public FragmentAddToItem() {

    }

    public FragmentAddToItem(ClsLayerItemMaster objClsLayerItemMaster, String merchantName, int _customerID, String _customerCode) {
        this.objClsLayerItemMaster = objClsLayerItemMaster;
        this._customerID = _customerID;
        this.merchantName = merchantName;
        this._customerCode = _customerCode;
    }

    public void setOnCallbackResult(final CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }


    public void setOnUpdateFooterValue(OnUpdateFooterValue onUpdateFooterValue) {
        this.onUpdateFooterValue = onUpdateFooterValue;

        Log.e("--OnClick--", "itemCount: " + onUpdateFooterValue);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addToItemViewModel = new
                ViewModelProvider(getActivity()).get(AddToItemViewModel.class);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_item_to_order, container, false);
        btn_add_to_order = view.findViewById(R.id.btn_add_to_order);
        txt_title = view.findViewById(R.id.txt_title);
        txt_title.setText(objClsLayerItemMaster.getITEM_NAME().toUpperCase());

        edt_comment = view.findViewById(R.id.edt_comment);
        txt_with_sale_rate = view.findViewById(R.id.txt_with_sale_rate);

        if (objClsLayerItemMaster.getTAX_TYPE().equalsIgnoreCase("INCLUSIVE")) {
            txt_with_sale_rate.setText(String.valueOf(objClsLayerItemMaster.getAverageSaleRate()));
        } else {
            txt_with_sale_rate.setText(String.valueOf(objClsLayerItemMaster.getRATE_PER_UNIT()));
        }

        txt_with_sale_rate.setTag(objClsLayerItemMaster.getRATE_PER_UNIT());

        iv_clear_comments = view.findViewById(R.id.iv_clear_comments);
        iv_clear_qty = view.findViewById(R.id.iv_clear_qty);
        edt_qty = view.findViewById(R.id.edt_qty);
        edt_qty.addTextChangedListener(this);
        edt_qty.setText(String.valueOf(1.00));
        edt_qty.setTag(1.00);

        txt_with_tax_total = view.findViewById(R.id.txt_with_tax_total);
        Btn_Add = view.findViewById(R.id.Btn_Add);
        Btn_Min = view.findViewById(R.id.Btn_Min);

        txt_total_tax_amount = view.findViewById(R.id.txt_total_tax_amount);
        txt_total_tax_amount.setTag(0.0);
        txt_discount_per = view.findViewById(R.id.txt_discount_per);
        ll_tax = view.findViewById(R.id.ll_tax);
        txt_cgst = view.findViewById(R.id.txt_cgst);
        txt_cgst_val = view.findViewById(R.id.txt_cgst_val);
        txt_sgst = view.findViewById(R.id.txt_sgst);
        txt_sgst_val = view.findViewById(R.id.txt_sgst_val);
        txt_igst = view.findViewById(R.id.txt_igst);
        txt_igst_val = view.findViewById(R.id.txt_igst_val);
        txt_discount_val = view.findViewById(R.id.txt_discount_val);
        bt_close = view.findViewById(R.id.bt_close);
        txt_net_amount = view.findViewById(R.id.txt_net_amount);
        txt_net_amount.setTag(0.0);

        txt_without_tax_total = view.findViewById(R.id.txt_without_tax_total);
        total_Amount = Double.valueOf(ClsGlobal.round(Double.parseDouble(txt_without_tax_total.getText().toString()), 2));

        Log.d("--amount--", "price: " + total_Amount);

        iv_clear_qty.setOnClickListener(v -> {

            edt_qty.setText(String.valueOf(1.00));
            edt_qty.setTag(1.00);
            edt_qty.requestFocus();
        });

        iv_clear_comments.setOnClickListener(v -> {
            edt_comment.setText("");
            edt_comment.requestFocus();
        });

        Btn_Add.setOnClickListener(v -> {
            Double _qty = 0.0;
            if (counter >= 0.0) {
                if (edt_qty.getText() != null && !edt_qty.getText().toString().isEmpty()) {
                    _qty = Double.valueOf(ClsGlobal.round(Double.valueOf(edt_qty.getText().toString().equalsIgnoreCase("")
                            || edt_qty.getText().toString().equalsIgnoreCase(".")
                            ? "0.00" : edt_qty.getText().toString()), 2));//11.45
                    _qty += 1.00;
                    counter = Double.valueOf(ClsGlobal.round(_qty, 2));
                    edt_qty.setText(String.valueOf(ClsGlobal.round(_qty, 2)));
                } else {
                    edt_qty.setText("0.00");
                    counter = 0.00;
                }
                Count();
            }

            hidekeyboard();

        });

        Btn_Min.setOnClickListener(v -> {

            double _qty = 0.00;

            if (counter >= 0.00) {
                if (edt_qty.getText() != null && !edt_qty.getText().toString().isEmpty()) {
                    _qty = Double.parseDouble(ClsGlobal.round(Double.parseDouble(edt_qty.getText().toString().equalsIgnoreCase("")
                            || edt_qty.getText().toString().equalsIgnoreCase(".")
                            ? "0.00" : edt_qty.getText().toString()), 2));//11.45
                    _qty -= 1.00;

                    if (_qty <= 0.00) {
                        _qty = 0.00;
                        Log.e("_qty", "_qty call");
                    }
                    counter = Double.valueOf(ClsGlobal.round(_qty, 2));

                    Log.e("_qty", String.valueOf(_qty));
                    edt_qty.setText(String.valueOf(ClsGlobal.round(_qty, 2)));
                } else {
                    edt_qty.setText("0.00");
                    counter = 0.00;
                }
                Count();
                hidekeyboard();

            }
        });

        bt_close.setOnClickListener(v -> {
            hidekeyboard();
            dismiss();
            callbackResult.sendResult(300);
        });

        rateCalculation("withTax");

        btn_add_to_order.setOnClickListener(v -> {
            insertAddToOrder();

            hidekeyboard();
            dismiss();
            callbackResult.sendResult(300);

        });

        addToItemViewModel.getLstOrderDetail().observe(getActivity(),
                list -> {

                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(list);
                    Log.e("--AddTO--", "list: " + jsonInString);

                    lstClsOrderDetailsEntities = list;
                    Log.e("--AddTO--", "size: " + lstClsOrderDetailsEntities.size());
                });

        return view;
    }

    List<ClsOrderDetailsEntity> lstClsOrderDetailsEntities;

    private void insertAddToOrder() {
        ClsOrderDetailsEntity _obj = new ClsOrderDetailsEntity();
        _obj.setItemCode(objClsLayerItemMaster.getITEM_CODE());
        _obj.setItem(objClsLayerItemMaster.getITEM_NAME());
        _obj.setAmount(Double.parseDouble(ClsGlobal.round
                (Double.parseDouble(txt_without_tax_total.getText().toString()), 2)));

        double Qty = 0.0;
        if (edt_qty.getText() != null && edt_qty.getText().toString().length() != 0) {
            String txtVal = edt_qty.getText().toString();
            if (txtVal.equalsIgnoreCase(".")) {
                txtVal = "0";
            }
            Qty = Double.parseDouble(txtVal);
        }

        if (Qty <= 0) {
            Toast.makeText(getContext(), "Quantity is required!", Toast.LENGTH_SHORT).show();
            return;
        }

        _obj.setQuantity(Double.parseDouble(ClsGlobal.round(Qty, 2)));
        _obj.setCGST(objClsLayerItemMaster.getCGST());
        _obj.setLAYERITEM_ID(objClsLayerItemMaster.getLAYERITEM_ID());
        _obj.setSGST(objClsLayerItemMaster.getSGST());
        _obj.setIGST(objClsLayerItemMaster.getIGST());
        _obj.setHSN_SAC_CODE(objClsLayerItemMaster.getHSN_SAC_CODE());
        _obj.setDiscount_amt(0.00);
        _obj.setDiscount_per(0.00);
        _obj.setGrandTotal(Double.parseDouble(ClsGlobal.round
                (Double.parseDouble(txt_net_amount.getTag().toString()), 2)));
        _obj.setItemComment("");
        _obj.setOrderDetailID(0);
        _obj.setOrderID("0");
        _obj.setOrderNo("0001");
        _obj.setMerchantName(merchantName);
        Log.d("setMerchantName", "insertAddToOrder: " + merchantName);
        _obj.setRate(Double.parseDouble(txt_with_sale_rate.getTag().toString()));

        double _SaleRateWithTax = 0.0;
        if (txt_with_tax_total.getTag() != null &&
                txt_with_tax_total.getTag().toString().equalsIgnoreCase("")) {

            String txtValWithTax = String.valueOf(txt_with_tax_total.getTag());
            if (txtValWithTax.equalsIgnoreCase(".")) {
                txtValWithTax = "0";
            }
            _SaleRateWithTax = Double.parseDouble(txtValWithTax);
        }
//        if (_SaleRateWithTax <= 0) {//1.0
//            Toast.makeText(getContext(), "Rate is required!", Toast.LENGTH_SHORT).show();
//            return;
//        }
        _obj.setSaleRate(_SaleRateWithTax);


        double _SaleRateWithoutTax = 0.0;
        if (txt_with_tax_total.getTag() != null &&
                txt_with_tax_total.getTag().toString().equalsIgnoreCase("")) {

            String txtVal = String.valueOf(txt_with_tax_total.getTag());
            if (txtVal.equalsIgnoreCase(".")) {
                txtVal = "0";
            }
            _SaleRateWithoutTax = Double.parseDouble(txtVal);
        }
//
//        if (_SaleRateWithoutTax <= 0) {//1.0
//            Toast.makeText(getContext(), "Rate is required!", Toast.LENGTH_SHORT).show();
//            return;
//        }

        _obj.setSaleRateWithoutTax(_SaleRateWithoutTax);
        _obj.setSaveStatus("NO");
        _obj.setTotalTaxAmount(Double.parseDouble(txt_total_tax_amount.getTag().toString()));
        _obj.setUNIT(objClsLayerItemMaster.getUNIT_CODE());
        _obj.setEntryDate(new Date());
        _obj.setCustomerID(_customerID);
        _obj.setCustomerCode(_customerCode);

        Gson gson = new Gson();
        String jsonInString = gson.toJson(_obj);
        Log.e("--AddTO--", "list: " + jsonInString);

        Log.d("--AddTO--", "merchantName: " + merchantName);

        addToItemViewModel.insert(_obj);
    }

    @SuppressLint("SetTextI18n")
    void rateCalculation(String mode) {

        edt_qty.removeTextChangedListener(this);

        double _withTaxRate = 0.0;
        double _qty = 0.00;
        double _total = 0.0;

        if (txt_with_sale_rate.getText() != null && !txt_with_sale_rate.getText().toString().isEmpty() &&
                !txt_with_sale_rate.getText().toString().equalsIgnoreCase(".") &&
                !txt_with_sale_rate.getText().toString().equalsIgnoreCase(" ")) {

            _withTaxRate = Double.parseDouble(txt_with_sale_rate.getText().toString());
        }

        if (edt_qty.getText() != null && !edt_qty.getText().toString().isEmpty() &&
                !edt_qty.getText().toString().equalsIgnoreCase(".") &&
                !edt_qty.getText().toString().equalsIgnoreCase(" ")) {

            _qty = Double.parseDouble(edt_qty.getText().toString());

        }

        _total = _withTaxRate * _qty;

        txt_without_tax_total.setText(String.valueOf(ClsGlobal.round(_total, 3)));


        txt_cgst.setText("CGST(" + ClsGlobal.round(objClsLayerItemMaster.getCGST(), 2) + "%)");
        txt_sgst.setText("SGST(" + ClsGlobal.round(objClsLayerItemMaster.getSGST(), 2) + "%)");
        txt_igst.setText("IGST(" + ClsGlobal.round(objClsLayerItemMaster.getIGST(), 2) + "%)");

        double _cgstVal = (_total * objClsLayerItemMaster.getCGST()) / 100;
        double _sgstVal = (_total * objClsLayerItemMaster.getSGST()) / 100;
        double _igstVal = (_total * objClsLayerItemMaster.getIGST()) / 100;

        txt_cgst_val.setText(String.valueOf(ClsGlobal.round(_cgstVal, 3)));
        txt_sgst_val.setText(String.valueOf(ClsGlobal.round(_sgstVal, 3)));
        txt_igst_val.setText(String.valueOf(ClsGlobal.round(_igstVal, 3)));

        double _totalTax = _cgstVal + _sgstVal + _igstVal;
        txt_total_tax_amount.setText(String.valueOf(ClsGlobal.round(_totalTax, 3)));
        txt_total_tax_amount.setTag(_totalTax);

        txt_with_tax_total.setText(String.valueOf(ClsGlobal.round(_total + _totalTax, 2)));
        txt_with_tax_total.setTag(_total + _totalTax);

        edt_qty.addTextChangedListener(this);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return dialog;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hidekeyboard();
        dismiss();
        callbackResult.sendResult(300);
    }

    private void Count() {
        if (txt_with_sale_rate.getText().toString().equalsIgnoreCase(".")) {
            _includingTotalAmount = counter * Double.parseDouble("0.00");
        } else {
            _includingTotalAmount = counter * Double.parseDouble(txt_with_sale_rate.getText().toString().equalsIgnoreCase("")
                    ? "0.00" : txt_with_sale_rate.getText().toString());
        }
        rateCalculation("qty");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (getActivity().getCurrentFocus() == txt_with_sale_rate) {
            rateCalculation("withTax");
        } else if (getActivity().getCurrentFocus() == edt_qty) {
            rateCalculation("qty");
        }
    }

    public interface CallbackResult {
        void sendResult(int requestCode);
    }

    public interface OnUpdateFooterValue {
        void OnClick();
    }

    void hidekeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}