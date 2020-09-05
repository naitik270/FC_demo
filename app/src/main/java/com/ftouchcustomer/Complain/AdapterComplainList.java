package com.ftouchcustomer.Complain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterComplainList extends RecyclerView.Adapter<AdapterComplainList.MyViewHolder> {


    View itemView;
    List<ClsComplainList> list = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    OnClickListener onClickListener;

    public AdapterComplainList(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void AddItems(List<ClsComplainList> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void SetOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = mInflater.inflate(R.layout.row_single_line, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ClsComplainList current = list.get(position);

        holder.txt_name.setText(current.getDispositionCode().toUpperCase());

        holder.Bind(current, onClickListener, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_header;
        TextView txt_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            ll_header = itemView.findViewById(R.id.ll_header);
        }

        void Bind(ClsComplainList obj, OnClickListener onClickListener, int position) {
            ll_header.setOnClickListener(v -> {
                onClickListener.OnItemClick(obj, position);
            });
        }
    }
    public interface OnClickListener {
        void OnItemClick(ClsComplainList obj, int position);
    }

}
