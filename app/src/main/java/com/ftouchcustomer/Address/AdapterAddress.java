package com.ftouchcustomer.Address;

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


import com.ftouchcustomer.Address.getAddress.ClsGetCustomerAddressListResponse;
import com.ftouchcustomer.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterAddress extends RecyclerView.Adapter<AdapterAddress.ViewHolder> {

    private List<ClsGetCustomerAddressListResponse> list = new ArrayList<>();
    private Context context;

    public AdapterAddress(Context mContext) {
        this.context = mContext;
    }

    public void addList(List<ClsGetCustomerAddressListResponse> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void OnClick(ClsGetCustomerAddressListResponse listResponse, int position, String mode);
    }

    private ItemClickListener itemClickListener;

    public void SetOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.row_address, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClsGetCustomerAddressListResponse current = list.get(position);

        holder.tvName.setText(current.getName().toUpperCase());
        holder.tvMobile.setText(current.getAddressMobileNo());

        if (current.getAddressLine2() == null || current.getAddressLine2().equals("")) {
            holder.tvAddress.setText(current.getAddressLine1().concat(", ")
                    .concat(current.getLandmark().concat(", ")
                            .concat(current.getPincode()).concat(", ")
                            .concat(current.getCity()).concat(", ")
                            .concat(current.getState())));
        } else {
            holder.tvAddress.setText(current.getAddressLine1().concat(", ")
                    .concat(current.getAddressLine2()).concat(", ").concat(current.getLandmark()
                            .concat(", ").concat(current.getPincode()).concat(", ")
                            .concat(current.getCity()).concat(", ").concat(current.getState())));
        }

        holder.tvAddressType.setText(current.getType());

        boolean b = current.getDefault();
        if (Boolean.TRUE.equals(b)) {
            holder.tvDefault.setText("Yes");
            holder.tvMakeDefault.setVisibility(View.GONE);
//            holder.view.setVisibility(View.GONE);
        } else {
            holder.tvDefault.setText("No");
            holder.tvMakeDefault.setVisibility(View.VISIBLE);
//            holder.view.setVisibility(View.VISIBLE);
        }

        holder.BindClick(current, itemClickListener, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvAddress;
        TextView tvName;
        TextView tvMobile;
        TextView tvAddressType;
        TextView tvDefault;
        TextView tvMakeDefault;
//        View view;
        ImageView ivDelete;
        ImageView ivEdit;
        LinearLayout ll_header;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAddress = itemView.findViewById(R.id.tv_address);
            tvName = itemView.findViewById(R.id.tv_name);
            tvMobile = itemView.findViewById(R.id.tv_mobile);
            tvAddressType = itemView.findViewById(R.id.tv_address_type);
            tvDefault = itemView.findViewById(R.id.tv_default);
            tvMakeDefault = itemView.findViewById(R.id.tv_make_default);
//            view = itemView.findViewById(R.id.view);
            ivDelete = itemView.findViewById(R.id.iv_delete);
            ivEdit = itemView.findViewById(R.id.iv_edit);
            ll_header = itemView.findViewById(R.id.ll_header);
        }

        void BindClick(ClsGetCustomerAddressListResponse listResponse,
                       ItemClickListener itemClickListener, int position) {

            ivDelete.setOnClickListener(v -> itemClickListener.OnClick(listResponse, position,"Delete"));

            ivEdit.setOnClickListener(v -> itemClickListener.OnClick(listResponse, position,"Edit"));

            tvMakeDefault.setOnClickListener(v -> itemClickListener.OnClick(listResponse, position,"Default"));

            ll_header.setOnClickListener(v -> itemClickListener.OnClick(listResponse, position, "LayoutClick"));

        }
    }
}
