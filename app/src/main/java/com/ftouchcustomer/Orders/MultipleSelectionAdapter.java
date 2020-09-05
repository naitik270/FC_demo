package com.ftouchcustomer.Orders;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.Merchant.AdapterMerchantList;
import com.ftouchcustomer.Merchant.ClsMerchantResponseList;
import com.ftouchcustomer.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

public class MultipleSelectionAdapter extends RecyclerView.Adapter<MultipleSelectionAdapter.ViewHolder> {

    Context context;
    List<ClsSelectionModel> mData = new ArrayList<>();
    private OnCharClick mOnCharClick;

    public MultipleSelectionAdapter(Context context) {
        this.context = context;
    }

    public void AddItems(List<ClsSelectionModel> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void clearSelection(){
        this.mData = StreamSupport.stream(mData)
                .map(s -> new ClsSelectionModel(String.valueOf(
                        s.get_character().toUpperCase().charAt(0)), false))
                .distinct()
                .collect(Collectors.toList());
        notifyDataSetChanged();

    }


    public interface OnCharClick {
        void OnItemClick(ClsSelectionModel obj, int position, ViewHolder viewHolder);
    }

    public void setOnCharClick(OnCharClick mOnCharClick) {
        this.mOnCharClick = mOnCharClick;
    }

    public void updateSelection(ClsSelectionModel clsSelectionModel){
        this.mData.set(this.mData.indexOf(clsSelectionModel),clsSelectionModel);
               notifyDataSetChanged();

    }

    public List<ClsSelectionModel> getAdapterList(){
        return this.mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_menu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClsSelectionModel obj = mData.get(position);
        holder.txt_cat_name.setText(obj.get_character().toUpperCase());

        holder.linear_layout.setBackgroundColor(obj.isSelected() ? Color.parseColor("#5e3750") : Color.WHITE);
        holder.txt_cat_name.setTextColor(obj.isSelected() ? Color.parseColor("#FFFFFF") : Color.BLACK);

        holder.Bind(obj, mOnCharClick, position,holder);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_cat_name;
        LinearLayout linear_layout;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            txt_cat_name = itemView.findViewById(R.id.txt_cat_name);
            linear_layout = itemView.findViewById(R.id.linear_layout);
        }


        void Bind(final ClsSelectionModel obj, OnCharClick mOnCharClick, int position,
                  ViewHolder viewHolder) {
            linear_layout.setOnClickListener(v ->
                    mOnCharClick.OnItemClick(obj, position, viewHolder));
        }
    }
}

