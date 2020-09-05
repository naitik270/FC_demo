package com.ftouchcustomer.Appointment.NewAppointment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ftouchcustomer.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterServiceSelection extends RecyclerView.Adapter<AdapterServiceSelection.ViewHolder> {

    private List<ClsServicesList> list;

    public AdapterServiceSelection(List<ClsServicesList> list, UpdateSelectStatus updateSelectStatus) {
        this.list = list;
        this.updateSelectStatus = updateSelectStatus;
    }

    public void AddItems(List<ClsServicesList> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public interface UpdateSelectStatus {
        void updateSelectStatus(ClsServicesList objservicesList, boolean checked);
    }

    private UpdateSelectStatus updateSelectStatus;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_multiple_selection, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClsServicesList current = list.get(position);

        String feature_name_list = TextUtils.join(", ", current.getFeatureNameList());

        holder.tv_SName.setText(current.getServiceName());
        holder.price.setText(String.valueOf(current.getRate()));
        holder.checkBox.setChecked(current.getSelected());
        holder.tv_features_name_list.setText(feature_name_list);

        holder.main_layout.setOnClickListener(view -> {
            if (!holder.checkBox.isChecked()) {
                holder.checkBox.setChecked(true);
                current.setSelected(true);
//                updateSelectStatus(position);
                updateSelectStatus.updateSelectStatus(current, true);
            } else {
                holder.checkBox.setChecked(false);
                current.setSelected(false);
                updateSelectStatus.updateSelectStatus(current, false);
            }
        });

//        holder.checkBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
//            current.setSelected(isChecked);
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView price;
        TextView tv_SName;
        TextView tv_features_name_list;
        LinearLayout main_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.cbService);
            price = itemView.findViewById(R.id.tv_price);
            tv_SName = itemView.findViewById(R.id.tv_SName);
            tv_features_name_list = itemView.findViewById(R.id.tv_features_name_list);
            main_layout = itemView.findViewById(R.id.main_layout);
        }
    }
}
