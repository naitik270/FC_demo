package com.ftouchcustomer.Orders;

import android.annotation.SuppressLint;
import android.content.Context;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.Animation.Tools;
import com.ftouchcustomer.Animation.ViewAnimation;
import com.ftouchcustomer.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class AdapterCustomerOrderList extends RecyclerView.Adapter<AdapterCustomerOrderList.ViewHolder> {

    List<ClsCustomerOrderResponseList> list = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    View itemView;


    private OnItemClickListener mOnItemClickListener;


    private OnClickListenerCall mOnClickListenerCall;

    public interface OnItemClickListener {
        void onItemClick(View view, ClsCustomerOrderResponseList obj, int position);
    }

    public void setOnLayoutClick(OnClickListenerCall mOnClickListenerCall) {
        this.mOnClickListenerCall = mOnClickListenerCall;
    }

    public interface OnClickListenerCall {
        void OnLayoutClick(ClsCustomerOrderResponseList clsCustomerOrderResponseList);
    }

    public AdapterCustomerOrderList(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void addList(List<ClsCustomerOrderResponseList> list) {
//        this.lst.addAll(list);

        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = mInflater.inflate(R.layout.row_cust_order_list, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClsCustomerOrderResponseList currentObj = list.get(position);

//        holder.txt_company_name.setText(currentObj.getCompanyName());

        Gson gson = new Gson();
        String jsonInString = gson.toJson(currentObj);
        Log.e("--countries--", "countries: " + jsonInString);
        Log.e("--countries--", "currentObj: " + currentObj.getCompanyName());

        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, list.get(position), position);
                }
            }
        });
//
        if (currentObj.getOrderNo() != null &&
                !currentObj.getOrderNo().equalsIgnoreCase("")) {
            holder.txt_order_no.setText("Order #" + currentObj.getOrderNo());
        } else {
            holder.txt_order_no.setText("Order #Not generated");
        }

        holder.txt_date.setText("Order Date: " + currentObj.getOrderDateStr());
        holder.txt_amount.setText("\u20B9 " + currentObj.getGrandTotal());
        holder.txt_items_count.setText("Items: " + currentObj.getItems());

        if (currentObj.getOrderStatus().equalsIgnoreCase("Pending")) {
            holder.iv_status.setColorFilter(ContextCompat.getColor(mContext, R.color.Pending), android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (currentObj.getOrderStatus().equalsIgnoreCase("Completed")) {
            holder.iv_status.setColorFilter(ContextCompat.getColor(mContext, R.color.Completed), android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (currentObj.getOrderStatus().equalsIgnoreCase("Preparing")) {
            holder.iv_status.setColorFilter(ContextCompat.getColor(mContext, R.color.Preparing), android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (currentObj.getOrderStatus().equalsIgnoreCase("Cancelled")) {
            holder.iv_status.setColorFilter(ContextCompat.getColor(mContext, R.color.Cancelled), android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (currentObj.getOrderStatus().equalsIgnoreCase("Delivered")) {
            holder.iv_status.setColorFilter(ContextCompat.getColor(mContext, R.color.Delivered), android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (currentObj.getOrderStatus().equalsIgnoreCase("confirmed")) {
            holder.iv_status.setColorFilter(ContextCompat.getColor(mContext, R.color.confirmed), android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (currentObj.getOrderStatus().equalsIgnoreCase("Ready")) {
            holder.iv_status.setColorFilter(ContextCompat.getColor(mContext, R.color.Ready), android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            holder.iv_status.setColorFilter(ContextCompat.getColor(mContext, R.color.Pending), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        holder.txt_order_status.setText(currentObj.getOrderStatus());

        holder.Bind(currentObj, mOnClickListenerCall);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_items_count;
        TextView txt_order_status;
        TextView txt_date;
        TextView txt_order_no;
        TextView txt_amount;
        LinearLayout lyt_parent;
        ImageView iv_status;
        LinearLayout ll_details;


        public ViewHolder(View itemView) {
            super(itemView);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_items_count = itemView.findViewById(R.id.txt_items_count);
            lyt_parent = itemView.findViewById(R.id.lyt_parent);
            txt_order_status = itemView.findViewById(R.id.txt_order_status);
            txt_amount = itemView.findViewById(R.id.txt_amount);
            txt_order_no = itemView.findViewById(R.id.txt_order_no);
            iv_status = itemView.findViewById(R.id.iv_status);
            ll_details = itemView.findViewById(R.id.ll_details);
        }

        void Bind(final ClsCustomerOrderResponseList clsCustomerOrderResponseList,
                  OnClickListenerCall mOnClickListenerCall) {
            ll_details.setOnClickListener(v ->
                    // send current position via Onclick method.
                    mOnClickListenerCall.OnLayoutClick(clsCustomerOrderResponseList));
        }

    }


    private boolean toggleLayoutExpand(boolean show, View view, View lyt_expand) {
        Tools.toggleArrow(show, view);
        if (show) {
            ViewAnimation.expand(lyt_expand);
        } else {
            ViewAnimation.collapse(lyt_expand);
        }
        return show;
    }


}
