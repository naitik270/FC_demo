package com.ftouchcustomer.NavigationTabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.Database.Entity.ClsOrderDetailsEntity;
import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterCart extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int SECTION_VIEW = 0;
    private static final int CONTENT_VIEW = 1;

    List<ClsOrderDetailsEntity> lstOrderDetailList = new ArrayList<>();
    Context mContext;

    public AdapterCart(Context mContext) {
        this.mContext = mContext;
    }

    public void AddItems(List<ClsOrderDetailsEntity> lstOrderDetailList) {
        this.lstOrderDetailList = lstOrderDetailList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == SECTION_VIEW) {
            return new SectionHeaderViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_order_header, parent, false));
        } else {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_cart, parent, false));
        }
    }
    private OnClickMerchantCart mOnClickListenerCall;

    public void setOnMerchantCartClick(OnClickMerchantCart mOnClickListenerCall) {
        this.mOnClickListenerCall = mOnClickListenerCall;
    }
    public interface OnClickMerchantCart {
        void OnItemClick(ClsOrderDetailsEntity obj);
    }

    public static class SectionHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitleTextview;
        TextView txt_order_now;
//        ImageView iv_add_to_cart;

        public SectionHeaderViewHolder(View itemView) {
            super(itemView);
            headerTitleTextview = itemView.findViewById(R.id.txt_cat_name);
            txt_order_now = itemView.findViewById(R.id.txt_order_now);
//            iv_add_to_cart = itemView.findViewById(R.id.iv_add_to_cart);
            txt_order_now.setVisibility(View.VISIBLE);




        }

        void Bind(final ClsOrderDetailsEntity obj,
                  OnClickMerchantCart mOnClickListenerCall) {
            txt_order_now.setOnClickListener(v ->
                    // send current position via Onclick method.
                    mOnClickListenerCall.OnItemClick(obj));
        }

    }

    public List<ClsOrderDetailsEntity> getPlaceOrderList() {
        return this.lstOrderDetailList;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ClsOrderDetailsEntity current = lstOrderDetailList.get(position);
//        holder.txt_item_name.setText(current.getMerchantName().concat("( ").concat(current.getMerchantCode()).concat(") "));

        switch (holder.getItemViewType()) {

            case 0:
                SectionHeaderViewHolder sectionHeaderViewHolder = (SectionHeaderViewHolder) holder;
                sectionHeaderViewHolder.headerTitleTextview.setClickable(false);

                    sectionHeaderViewHolder.headerTitleTextview.setText(
                            current.getMerchantName().concat((" " +
                                    "( ").concat(String.valueOf(current.getTotalAmountMerchantWise()))
                                    .concat(" )")));

                sectionHeaderViewHolder.Bind(current, mOnClickListenerCall);

                break;
            case 1:

                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                if (current.isHeader()) {
                    itemViewHolder.txt_srno.setText(String.valueOf(position - 1).concat("."));
                } else {
                    itemViewHolder.txt_srno.setText(String.valueOf(position + 1).concat("."));
                }


                itemViewHolder.txt_srno.setVisibility(View.GONE);

                itemViewHolder.txt_item_name.setText(current.getItem().toUpperCase());
                itemViewHolder.txt_item_code.setText("code: " + current.getItemCode().toUpperCase());
                itemViewHolder.txt_display_tax_amount.setText("SGST:(" + ClsGlobal.round(current.getSGST(), 2) + "%) CGST:(" +
                        ClsGlobal.round(current.getCGST(), 2) +
                        "%) IGST:(" + ClsGlobal.round(current.getIGST(), 2) + "%)");
                itemViewHolder.qty.setText("(qty.) " + ClsGlobal.round(current.getQuantity(), 2));

                double _totalTax = current.getTotalTaxAmount();
                itemViewHolder.txt_display_tax.setText("Total Tax: \u20B9 " + ClsGlobal.round(_totalTax, 2));

                itemViewHolder.txt_discount.setText("Discount(" + ClsGlobal.round(current.getDiscount_per(), 2) + "%)" +
                        " \u20B9 " + ClsGlobal.round(current.getDiscount_amt(), 2));
                double _itemAmount = 0.0;

                itemViewHolder.txt_item_rate.setText("(rate) \u20B9 " + ClsGlobal.round(current.getRate(), 2));
                _itemAmount = (current.getRate() * current.getQuantity()) - current.getDiscount_amt();
                itemViewHolder.qty_amount.setText("\u20B9 " + ClsGlobal.round(_itemAmount, 2));

                ((ItemViewHolder) holder).BindDeleteItem(current, mOnDeleteClick, position);

                break;
        }
    }

    @Override
    public int getItemCount() {
        if (lstOrderDetailList != null) {
            return lstOrderDetailList.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (lstOrderDetailList.get(position).isHeader()) {
            return SECTION_VIEW;
        } else {
            return CONTENT_VIEW;
        }
    }

    private OnDeleteClick mOnDeleteClick;
    //
    public void SetOnClickListener(OnDeleteClick mOnDeleteClick) {
        this.mOnDeleteClick = mOnDeleteClick;
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

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_header;

        TextView txt_merchant_name, txt_item_name, txt_discount, txt_display_tax,
                txt_item_rate, txt_item_code,
                txt_Display_price, txt_display_tax_amount, txt_srno,
                qty, qty_amount;

        ImageView btn_remove_item;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ll_header = itemView.findViewById(R.id.ll_header);
            txt_merchant_name = itemView.findViewById(R.id.txt_merchant_name);
            txt_item_name = itemView.findViewById(R.id.txt_item_name);
            txt_item_code = itemView.findViewById(R.id.txt_item_code);
            txt_Display_price = itemView.findViewById(R.id.txt_Display_price);
            txt_discount = itemView.findViewById(R.id.txt_discount);
            txt_display_tax = itemView.findViewById(R.id.txt_display_tax);
            txt_item_rate = itemView.findViewById(R.id.txt_item_rate);
            txt_srno = itemView.findViewById(R.id.txt_srno);
            qty = itemView.findViewById(R.id.qty);
            qty_amount = itemView.findViewById(R.id.qty_amount);
            btn_remove_item = itemView.findViewById(R.id.btn_remove_item);
            txt_display_tax_amount = itemView.findViewById(R.id.txt_display_tax_amount);
        }

        void BindDeleteItem(final ClsOrderDetailsEntity obj, OnDeleteClick mOnDeleteClick, int position) {
            btn_remove_item.setOnClickListener(v ->

                    mOnDeleteClick.onItemDelete(obj, position));
        }
    }
}
