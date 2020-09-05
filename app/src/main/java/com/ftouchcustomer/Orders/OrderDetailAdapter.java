package com.ftouchcustomer.Orders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.Database.Entity.ClsOrderDetailsEntity;
import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Desktop on 6/2/2018.
 */

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder> {
    List<ClsOrderDetailsEntity> lstOrderDetailList = new ArrayList<ClsOrderDetailsEntity>();
    private Context mContext;
    private OnDeleteClick mOnDeleteClick;

    public OrderDetailAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void addList(List<ClsOrderDetailsEntity> lstOrderDetailList) {
        this.lstOrderDetailList = lstOrderDetailList;
        notifyDataSetChanged();
    }

    //
    public void SetOnClickListener(OnDeleteClick mOnDeleteClick) {
        this.mOnDeleteClick = mOnDeleteClick;
    }

    public List<ClsOrderDetailsEntity> getPlaceOrderList() {
        return this.lstOrderDetailList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_card_order_details, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        ClsOrderDetailsEntity current = lstOrderDetailList.get(position);

        holder.txt_srno.setText(String.valueOf(position + 1).concat("."));
        holder.item_name.setText(current.getItem().toUpperCase());
        holder.txt_item_code.setText("code: " + current.getItemCode().toUpperCase());
        holder.txt_display_tax_amount.setText("SGST:(" + ClsGlobal.round(current.getSGST(), 2) + "%) CGST:(" +
                ClsGlobal.round(current.getCGST(), 2) +
                "%) IGST:(" + ClsGlobal.round(current.getIGST(), 2) + "%)");
        holder.qty.setText("(qty.) " + ClsGlobal.round(current.getQuantity(), 2));

        double _totalTax = current.getTotalTaxAmount();
        holder.txt_display_tax.setText("Total Tax: \u20B9 " + ClsGlobal.round(_totalTax, 2));

        holder.txt_discount.setText("Discount(" + ClsGlobal.round(current.getDiscount_per(), 2) + "%)" + " \u20B9 " + ClsGlobal.round(current.getDiscount_amt(), 2));
        double _itemAmount = 0.0;


        holder.txt_item_rate.setText("(rate) \u20B9 " + ClsGlobal.round(current.getRate(), 2));
        _itemAmount = (current.getRate() * current.getQuantity()) - current.getDiscount_amt();
        holder.qty_amount.setText("\u20B9 " + ClsGlobal.round(_itemAmount, 2));

        holder.Bind(current, mOnDeleteClick, position);

    }

    @Override
    public int getItemCount() {
        return lstOrderDetailList.size();
    }

    public void remove(int position) {
        lstOrderDetailList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
        notifyDataSetChanged();
    }

    public interface OnDeleteClick {
        void onItemDelete(ClsOrderDetailsEntity obj, int position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_srno, item_name, qty, txt_item_rate, qty_amount;
        TextView txt_discount, txt_item_code;
        TextView txt_display_tax;
        TextView txt_display_tax_amount;
        ImageView btn_remove_item;


        public MyViewHolder(View itemView) {
            super(itemView);

            txt_srno = itemView.findViewById(R.id.txt_srno);
            item_name = itemView.findViewById(R.id.item_name);
            qty = itemView.findViewById(R.id.qty);
            txt_item_rate = itemView.findViewById(R.id.txt_item_rate);
            qty_amount = itemView.findViewById(R.id.qty_amount);
            btn_remove_item = itemView.findViewById(R.id.btn_remove_item);
            txt_discount = itemView.findViewById(R.id.txt_discount);
            txt_item_code = itemView.findViewById(R.id.txt_item_code);
            txt_display_tax = itemView.findViewById(R.id.txt_display_tax);
            txt_display_tax_amount = itemView.findViewById(R.id.txt_display_tax_amount);
//
//            if (mode.equalsIgnoreCase("OrderActivity")) {
//                btn_remove_item.setVisibility(View.VISIBLE);
//            } else if (mode.equalsIgnoreCase("OrderDetailInfoActivity")) {
//                btn_remove_item.setVisibility(View.GONE);
//            }


        }

        void Bind(final ClsOrderDetailsEntity obj, OnDeleteClick mOnDeleteClick, int position) {
            btn_remove_item.setOnClickListener(v ->

                    mOnDeleteClick.onItemDelete(obj, position));
        }

    }


}
