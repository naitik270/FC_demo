package com.ftouchcustomer.NavigationTabs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.ftouchcustomer.Appointment.GetAppointment.ClsGetAppointmentResponseList;
import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class AppointmentPagerAdapter extends PagerAdapter {

    private static final String TAG = "ImageViewPage";
    private LayoutInflater mLayoutInflater;
    private List<ClsGetAppointmentResponseList> mResources = new ArrayList<>();
    private Context context;

    public AppointmentPagerAdapter(Context mContext) {
        this.context = mContext;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addList(List<ClsGetAppointmentResponseList> list) {
        this.mResources = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View itemView = mLayoutInflater.inflate(R.layout.row_dashboard_appointment, container, false);

        TextView tvmName = itemView.findViewById(R.id.mName);
        TextView txt_confirm_date = itemView.findViewById(R.id.txt_confirm_date);
        TextView tv_name = itemView.findViewById(R.id.tv_name);
        TextView tv_mobile = itemView.findViewById(R.id.tv_mobile);
        TextView tv_appointmentDate = itemView.findViewById(R.id.tv_appointmentDate);
        TextView tv_status = itemView.findViewById(R.id.tv_status);
        TextView tv_Services = itemView.findViewById(R.id.tv_Services);
        TextView tv_appointment_no = itemView.findViewById(R.id.tv_appointment_no);
        LinearLayout ll_appointment_no = itemView.findViewById(R.id.ll_appointment_no);
        LinearLayout main_ll = itemView.findViewById(R.id.main_ll);
        LinearLayout ll_confirm_date = itemView.findViewById(R.id.ll_confirm_date);
//        View view_appointment_no = itemView.findViewById(R.id.view_appointment_no);

        tvmName.setText(mResources.get(position).getMerchantName());
        tv_name.setText(mResources.get(position).getCustomerName());
        tv_mobile.setText(mResources.get(position).getAppointmentMobileNo());
        String _appointmentDate = mResources.get(position).getAppointmentRequestDate();
        Log.d("--val--", "_appointmentDate: " + _appointmentDate);
        tv_appointmentDate.setText(_appointmentDate);
        tv_Services.setText(mResources.get(position).getServices());

        if (mResources.get(position).getAppointmentNo() != null &&
                !mResources.get(position).getAppointmentNo().equalsIgnoreCase("")) {
            tv_appointment_no.setText(mResources.get(position).getAppointmentNo());
        } else {
            tv_appointment_no.setText("Not generated");
        }

        if (mResources.get(position).getAppointmentDate() != null &&
                !mResources.get(position).getAppointmentDate().equalsIgnoreCase("")) {
            ll_confirm_date.setVisibility(View.VISIBLE);

        } else {
            ll_confirm_date.setVisibility(View.GONE);

        }

        txt_confirm_date.setText(mResources.get(position).getAppointmentDate());

        if (mResources.get(position).getStatus().equalsIgnoreCase("Completed")) {
            tv_status.setBackgroundColor(Color.parseColor("#CFEBDA"));
            tv_status.setTextColor(Color.parseColor("#365B37"));
        } else if (mResources.get(position).getStatus().equalsIgnoreCase("Cancelled")) {
            tv_status.setBackgroundColor(Color.parseColor("#F5C6CA"));
            tv_status.setTextColor(Color.parseColor("#6E252C"));
        } else if (mResources.get(position).getStatus().equalsIgnoreCase("confirmation pending")) {
            tv_status.setBackgroundColor(Color.parseColor("#C9E6EA"));
            tv_status.setTextColor(Color.parseColor("#00707E"));
        } else if (mResources.get(position).getStatus().equalsIgnoreCase("confirmed")) {
            tv_status.setBackgroundColor(Color.parseColor("#CBE5FE"));
            tv_status.setTextColor(Color.parseColor("#30517C"));
        } else if (mResources.get(position).getStatus().equalsIgnoreCase("Verified")) {
            tv_status.setBackgroundColor(Color.parseColor("#D1C7CD"));
            tv_status.setTextColor(Color.parseColor("#5e3750"));
        } else {
            tv_status.setBackgroundColor(Color.parseColor("#EED581"));
            tv_status.setTextColor(Color.parseColor("#906811"));
        }

        tv_status.setText(mResources.get(position).getStatus().toUpperCase());

        main_ll.setOnClickListener(v -> {
            Dialog mDialog = new Dialog(context);

            mDialog = new Dialog(context);
            mDialog.setContentView(R.layout.row_appointment_get);
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.setCancelable(true);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(Objects.requireNonNull(mDialog.getWindow()).getAttributes());
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            mDialog.getWindow().setAttributes(lp);
            mDialog.show();

            TextView tv_merchant_name;
            TextView tv_name1;
            TextView tv_mobile_number;
            TextView tv_appointment_date;
            TextView tv_appointment_no1;
            TextView tv_appointment_request_date;
            TextView tv_services;
            TextView tv_remark;
            TextView tv_status1;
            TextView tv_reason;
            TextView tv_cancelledBy;
            LinearLayout ll_cancel;

            tv_merchant_name = mDialog.findViewById(R.id.tv_merchant_name);
            tv_name1 = mDialog.findViewById(R.id.tv_name);
            tv_mobile_number = mDialog.findViewById(R.id.tv_mobile_number);
            tv_appointment_date = mDialog.findViewById(R.id.tv_appointment_date);
            tv_appointment_request_date = mDialog.findViewById(R.id.tv_appointment_request_dt);
            tv_appointment_no1 = mDialog.findViewById(R.id.tv_appointment_no);
            tv_services = mDialog.findViewById(R.id.tv_services);
            tv_remark = mDialog.findViewById(R.id.tv_remark);
            tv_status1 = mDialog.findViewById(R.id.tv_status);
            tv_reason = mDialog.findViewById(R.id.tv_reason);
            tv_cancelledBy = mDialog.findViewById(R.id.tv_cancelledBy);
            ll_cancel = mDialog.findViewById(R.id.ll_cancel);

            tv_merchant_name.setText(mResources.get(position).getMerchantName());
            tv_name1.setText(mResources.get(position).getCustomerName());
            tv_mobile_number.setText(mResources.get(position).getAppointmentMobileNo());
            tv_appointment_date.setText(mResources.get(position).getAppointmentDate());
            tv_appointment_request_date.setText(mResources.get(position).getAppointmentRequestDate());
            tv_services.setText(mResources.get(position).getServices());
            tv_remark.setText(mResources.get(position).getStatusRemark());

            if (mResources.get(position).getStatus().equalsIgnoreCase("Completed")) {
                tv_status1.setBackgroundColor(Color.parseColor("#CFEBDA"));
                tv_status1.setTextColor(Color.parseColor("#365B37"));
            } else if (mResources.get(position).getStatus().equalsIgnoreCase("Cancelled")) {
                tv_status1.setBackgroundColor(Color.parseColor("#F5C6CA"));
                tv_status1.setTextColor(Color.parseColor("#6E252C"));
            } else if (mResources.get(position).getStatus().equalsIgnoreCase("confirmation pending")) {
                tv_status1.setBackgroundColor(Color.parseColor("#C9E6EA"));
                tv_status1.setTextColor(Color.parseColor("#00707E"));
            } else if (mResources.get(position).getStatus().equalsIgnoreCase("confirmed")) {
                tv_status1.setBackgroundColor(Color.parseColor("#CBE5FE"));
                tv_status1.setTextColor(Color.parseColor("#30517C"));
            } else if (mResources.get(position).getStatus().equalsIgnoreCase("Verified")) {
                tv_status1.setBackgroundColor(Color.parseColor("#D1C7CD"));
                tv_status1.setTextColor(Color.parseColor("#5e3750"));
            } else {
                tv_status1.setBackgroundColor(Color.parseColor("#EED581"));
                tv_status1.setTextColor(Color.parseColor("#906811"));
            }

            tv_status1.setText(" " + mResources.get(position).getStatus().toUpperCase() + " ");

            tv_reason.setText(mResources.get(position).getReason());


            if (mResources.get(position).getAppointmentNo() != null &&
                    !mResources.get(position).getAppointmentNo().equalsIgnoreCase("")) {
                tv_appointment_no1.setText(mResources.get(position).getAppointmentNo());
            } else {
                tv_appointment_no1.setText("Not generated");
            }

            tv_cancelledBy.setText(mResources.get(position).getCancelledBy());

            if (mResources.get(position).getStatus().equalsIgnoreCase("Cancelled")) {
                ll_cancel.setVisibility(View.VISIBLE);
            } else {
                ll_cancel.setVisibility(View.GONE);
            }

        });


        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.d(TAG, "destroyItem() called with: " + "container = [" + container + "], position = [" + position
                + "], object = [" + object + "]");
        container.removeView((LinearLayout) object);
    }
}
