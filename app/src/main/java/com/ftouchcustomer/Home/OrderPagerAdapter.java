package com.ftouchcustomer.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import com.ftouchcustomer.Appointment.GetAppointment.ClsGetAppointmentResponseList;
import com.ftouchcustomer.NavigationTabs.OrdersFragment;
import com.ftouchcustomer.Orders.ClsCustomerOrderResponse;
import com.ftouchcustomer.Orders.ClsCustomerOrderResponseList;
import com.ftouchcustomer.R;

import java.util.ArrayList;
import java.util.List;

public class OrderPagerAdapter extends PagerAdapter {

    private static final String TAG = "ImageViewPage";
    private LayoutInflater mLayoutInflater;
    private List<ClsCustomerOrderResponseList> mResources = new ArrayList<>();
    private Context context;
    OnClickViewPager onClickViewPager;


    public OrderPagerAdapter(Context mContext) {
        this.context = mContext;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addList(List<ClsCustomerOrderResponseList> list) {
        this.mResources = list;
        notifyDataSetChanged();
    }

    public void ClearAdapter() {
        this.mResources.clear();
        notifyDataSetChanged();
    }

    public void setOnClickViewPager(OnClickViewPager onClickViewPager) {
        this.onClickViewPager = onClickViewPager;
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

        View itemView = mLayoutInflater.inflate(R.layout.row_order_pager_card, container, false);

        ViewHolder viewHolder;
        ClsCustomerOrderResponseList current = mResources.get(position);
        if (current != null) {
            viewHolder = new ViewHolder();

            viewHolder.tvmName = itemView.findViewById(R.id.mName);
            viewHolder.tv_name = itemView.findViewById(R.id.tv_name);
            viewHolder.tv_mobile = itemView.findViewById(R.id.tv_mobile);
            viewHolder.tv_appointmentDate = itemView.findViewById(R.id.tv_appointmentDate);
            viewHolder.tv_status = itemView.findViewById(R.id.tv_status);
            viewHolder.tv_Services = itemView.findViewById(R.id.tv_Services);
            viewHolder.main_ll = itemView.findViewById(R.id.main_ll);

            viewHolder.tvmName.setText(mResources.get(position).getCompanyName());
            viewHolder.tv_name.setText(mResources.get(position).getCustomerName());

            viewHolder.tv_mobile.setText(mResources.get(position).getMobileNo());
            String _orderDate = mResources.get(position).getOrderDateStr();

            viewHolder.tv_appointmentDate.setText(_orderDate);
            viewHolder.tv_Services.setText("Total Items: " + mResources.get(position).getItems().toString());

            if (mResources.get(position).getOrderStatus().equalsIgnoreCase("Pending")) {
                viewHolder.tv_status.setBackgroundColor(Color.parseColor("#EED581"));
                viewHolder.tv_status.setTextColor(Color.parseColor("#906811"));
            } else if (mResources.get(position).getOrderStatus().equalsIgnoreCase("Completed")) {
                viewHolder.tv_status.setBackgroundColor(Color.parseColor("#CFEBDA"));
                viewHolder.tv_status.setTextColor(Color.parseColor("#365B37"));
            } else if (mResources.get(position).getOrderStatus().equalsIgnoreCase("Preparing")) {
                viewHolder.tv_status.setBackgroundColor(Color.parseColor("#D7D8D9"));
                viewHolder.tv_status.setTextColor(Color.parseColor("#1E2327"));
            } else if (mResources.get(position).getOrderStatus().equalsIgnoreCase("Cancelled")) {
                viewHolder.tv_status.setBackgroundColor(Color.parseColor("#F5C6CA"));
                viewHolder.tv_status.setTextColor(Color.parseColor("#6E252C"));
            } else if (mResources.get(position).getOrderStatus().equalsIgnoreCase("Delivered")) {
                viewHolder.tv_status.setBackgroundColor(Color.parseColor("#C9E6EA"));
                viewHolder.tv_status.setTextColor(Color.parseColor("#00707E"));
            } else if (mResources.get(position).getOrderStatus().equalsIgnoreCase("confirmed")) {
                viewHolder.tv_status.setBackgroundColor(Color.parseColor("#CBE5FE"));
                viewHolder.tv_status.setTextColor(Color.parseColor("#30517C"));
            } else if (mResources.get(position).getOrderStatus().equalsIgnoreCase("Ready")) {
                viewHolder.tv_status.setBackgroundColor(Color.parseColor("#D1C7CD"));
                viewHolder.tv_status.setTextColor(Color.parseColor("#5e3750"));
            } else {
                viewHolder.tv_status.setBackgroundColor(Color.parseColor("#EED581"));
                viewHolder.tv_status.setTextColor(Color.parseColor("#906811"));
            }

            viewHolder.tv_status.setText(mResources.get(position).getOrderStatus().toUpperCase());
            viewHolder.Bind(current, onClickViewPager);

            container.addView(itemView);

        }


        return itemView;
    }


    static class ViewHolder {

        TextView tvmName, tv_name, tv_mobile, tv_appointmentDate,
                tv_status, tv_Services, tv_appointment_no;
        LinearLayout main_ll, ll_appointment_no;

        void Bind(final ClsCustomerOrderResponseList customerMaster, OnClickViewPager onClickViewPager) {

            main_ll.setOnClickListener(v -> {
                onClickViewPager.OnClick(customerMaster);
            });

        }
    }


    public interface OnClickViewPager {
        void OnClick(ClsCustomerOrderResponseList clsCustomerOrderResponseList);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.d(TAG, "destroyItem() called with: " + "container = [" + container + "], position = [" + position
                + "], object = [" + object + "]");
        container.removeView((LinearLayout) object);
    }


}
