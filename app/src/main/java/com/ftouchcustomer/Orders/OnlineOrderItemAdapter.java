package com.ftouchcustomer.Orders;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.Database.Entity.ClsOrderDetailsEntity;
import com.ftouchcustomer.R;

import java.util.ArrayList;
import java.util.List;

public class OnlineOrderItemAdapter extends RecyclerView.Adapter<OnlineOrderItemAdapter.MyViewHolder> {

    List<ClsOrderDetailsEntity> list = new ArrayList<>();

    public OnlineOrderItemAdapter() {
    }

    public void AddItems(List<ClsOrderDetailsEntity> item) {
        this.list = item;
        notifyDataSetChanged();
    }

    public List<ClsOrderDetailsEntity> getAdapterList() {
        return list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_online_order_placed, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ClsOrderDetailsEntity current = list.get(position);
        holder.txt_item_name.setText(current.getItem());
        holder.txt_Display_item_code.setText(current.getItemCode());
        holder.txt_Display_unit.setText(current.getUNIT());
        holder.txt_Display_price.setText("\u20B9 " + current.getRate());
        holder.txt_final_stock.setText("qty: " + current.getQuantity()  );

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView Cv_header;

        TextView txt_item_name, txt_Display_item_code, txt_final_stock,
                txt_Display_unit, txt_Display_price;

        public MyViewHolder(View itemView) {
            super(itemView);
            Cv_header = itemView.findViewById(R.id.Cv_header);
            txt_item_name = itemView.findViewById(R.id.txt_item_name);
            txt_final_stock = itemView.findViewById(R.id.txt_final_stock);
            txt_Display_item_code = itemView.findViewById(R.id.txt_Display_item_code);
            txt_Display_unit = itemView.findViewById(R.id.txt_Display_unit);
            txt_Display_price = itemView.findViewById(R.id.txt_Display_price);
        }

    }


}
