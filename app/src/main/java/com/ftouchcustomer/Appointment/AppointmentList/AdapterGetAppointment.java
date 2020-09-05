package com.ftouchcustomer.Appointment.AppointmentList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.Appointment.GetAppointment.ClsGetAppointmentResponseList;
import com.ftouchcustomer.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterGetAppointment extends RecyclerView.Adapter<AdapterGetAppointment.ViewHolder> {

    private List<ClsGetAppointmentResponseList> list = new ArrayList<>();
    private Context context;

    public AdapterGetAppointment(Context mContext) {
        this.context = mContext;
    }

    public void addList(List<ClsGetAppointmentResponseList> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void OnClick(ClsGetAppointmentResponseList listResponse, int position, String mode);
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
        View itemView = inflater.inflate(R.layout.row_appointment, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClsGetAppointmentResponseList current = list.get(position);

        holder.tv_name_contact.setText(current.getCustomerName().concat("  (").
                concat(current.getAppointmentMobileNo()).concat(")"));

        holder.tv_appointmentDate.setText(current.getAppointmentDate());
        holder.txt_confirm_data.setText(current.getAppointmentRequestDate());
        holder.tv_Services.setText(current.getServices());

        if (current.getAppointmentNo() != null &&
                !current.getAppointmentNo().equalsIgnoreCase("")) {
            holder.tv_appointment_no.setText(current.getAppointmentNo());
        } else {
            holder.tv_appointment_no.setText("Not generated");
        }

        if (current.getStatus().equalsIgnoreCase("Completed")) {
            holder.iv_status.setColorFilter(ContextCompat.getColor(context, R.color.Completed),
                    android.graphics.PorterDuff.Mode.SRC_IN);
            holder.ivDelete.setVisibility(View.GONE);
        } else if (current.getStatus().equalsIgnoreCase("Cancelled")) {
            holder.iv_status.setColorFilter(ContextCompat.getColor(context, R.color.Cancelled),
                    android.graphics.PorterDuff.Mode.SRC_IN);
            holder.ivDelete.setVisibility(View.GONE);
        } else if (current.getStatus().equalsIgnoreCase("confirmation pending")) {
            holder.iv_status.setColorFilter(ContextCompat.getColor(context, R.color.Pending),
                    android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (current.getStatus().equalsIgnoreCase("confirmed")) {
            holder.iv_status.setColorFilter(ContextCompat.getColor(context, R.color.Delivered),
                    android.graphics.PorterDuff.Mode.SRC_IN);
            holder.ll_confirm.setVisibility(View.VISIBLE);
        } else if (current.getStatus().equalsIgnoreCase("Verified")) {
            holder.iv_status.setColorFilter(ContextCompat.getColor(context, R.color.Preparing),
                    android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            holder.iv_status.setColorFilter(ContextCompat.getColor(context, R.color.black),
                    android.graphics.PorterDuff.Mode.SRC_IN);
        }

        holder.tv_status.setText(current.getStatus().toUpperCase());
        holder.BindClick(current, itemClickListener, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvmName;
        TextView tv_name_contact;
        TextView tv_mobile;
        TextView tv_appointmentDate;
        TextView tv_status;
        TextView tv_Services;
        TextView tv_appointment_no, txt_confirm_data;
        LinearLayout ll_confirm, ll_details;
        ImageView ivDelete, iv_status;
        View view_appointment_no;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_status = itemView.findViewById(R.id.iv_status);
            tvmName = itemView.findViewById(R.id.mName);
            txt_confirm_data = itemView.findViewById(R.id.txt_confirm_data);
            tv_name_contact = itemView.findViewById(R.id.tv_name_contact);
            tv_mobile = itemView.findViewById(R.id.tv_mobile);
            tv_appointmentDate = itemView.findViewById(R.id.tv_appointmentDate);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_Services = itemView.findViewById(R.id.tv_Services);
            ivDelete = itemView.findViewById(R.id.iv_delete);
            ll_details = itemView.findViewById(R.id.ll_details);
            tv_appointment_no = itemView.findViewById(R.id.tv_appointment_no);
            ll_confirm = itemView.findViewById(R.id.ll_confirm);
        }

        void BindClick(ClsGetAppointmentResponseList listResponse,
                       ItemClickListener itemClickListener, int position) {

            ivDelete.setOnClickListener(v -> itemClickListener.OnClick(listResponse, position, "Cancel"));
            ll_details.setOnClickListener(v -> itemClickListener.OnClick(listResponse, position, "Tracking"));
        }
    }
}
