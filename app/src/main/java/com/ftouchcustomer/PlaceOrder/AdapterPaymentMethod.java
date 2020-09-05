package com.ftouchcustomer.PlaceOrder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ftouchcustomer.Merchant.ClsTiming;
import com.ftouchcustomer.Orders.ClsGetMerchantPaymentMethodList;
import com.ftouchcustomer.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterPaymentMethod extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<ClsGetMerchantPaymentMethodList> lst = new ArrayList<>();


    public AdapterPaymentMethod(Context context, List<ClsGetMerchantPaymentMethodList> lst) {
        this.context = context;
        this.lst = lst;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lst.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return lst.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.row_single_line, viewGroup, false);
        ClsGetMerchantPaymentMethodList _obj = lst.get(position);

        TextView txt_name = view.findViewById(R.id.txt_name);

        txt_name.setText(_obj.getPaymentMethod().toUpperCase().concat(" (").concat(_obj.getDescription().concat(")")));

        return view;
    }

}
