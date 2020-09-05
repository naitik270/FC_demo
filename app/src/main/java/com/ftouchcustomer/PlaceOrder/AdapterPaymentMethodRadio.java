package com.ftouchcustomer.PlaceOrder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ftouchcustomer.Home.OrderPagerAdapter;
import com.ftouchcustomer.Orders.ClsCustomerOrderResponseList;
import com.ftouchcustomer.Orders.ClsGetMerchantPaymentMethodList;
import com.ftouchcustomer.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterPaymentMethodRadio extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<ClsGetMerchantPaymentMethodList> lst = new ArrayList<>();


    public AdapterPaymentMethodRadio(Context context, List<ClsGetMerchantPaymentMethodList> lst) {
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
        view = inflater.inflate(R.layout.row_single_line_radio, viewGroup, false);
        ViewHolder viewHolder;
        ClsGetMerchantPaymentMethodList _obj = lst.get(position);
        if (_obj != null) {
            viewHolder = new ViewHolder();

            viewHolder.ll_header = view.findViewById(R.id.ll_header);
            viewHolder.txt_name = view.findViewById(R.id.txt_name);
            viewHolder.rb_selection = view.findViewById(R.id.rb_selection);

            viewHolder.txt_name.setText(_obj.getPaymentMethod().toUpperCase().concat(" (")
                    .concat(_obj.getDescription().concat(")")));

            viewHolder.Bind(_obj,mOnClickRadioBtn);
        }

        return view;
    }

    OnClickRadioBtn mOnClickRadioBtn;

    public void setOnRadioBtnClick(OnClickRadioBtn mOnClickRadioBtn) {
        this.mOnClickRadioBtn = mOnClickRadioBtn;
    }
    public interface OnClickRadioBtn{
        void OnClick(ClsGetMerchantPaymentMethodList cls);
    }

    static class ViewHolder {

        TextView txt_name;
        RadioButton rb_selection;
        LinearLayout ll_header;

        void Bind(final ClsGetMerchantPaymentMethodList cls,
                  OnClickRadioBtn onClickViewPager) {

            rb_selection.setOnClickListener(v -> {
                onClickViewPager.OnClick(cls);
            });

        }
    }

}
