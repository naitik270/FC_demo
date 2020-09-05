package com.ftouchcustomer.Merchant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.R;

import java.util.List;

public class AdapterCategoriesFilters extends RecyclerView.Adapter<AdapterCategoriesFilters.ViewHolder>  {


    private List<ClsCatagoryList> list;

    public AdapterCategoriesFilters(List<ClsCatagoryList> list, UpdateSelectStatus updateSelectStatus) {
        this.list = list;
        this.updateSelectStatus = updateSelectStatus;
    }

    public void AddItems(List<ClsCatagoryList> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public interface UpdateSelectStatus {
        void updateSelectStatus(ClsCatagoryList clsCatagoryList, boolean checked);
    }

    private UpdateSelectStatus updateSelectStatus;

    @NonNull
    @Override
    public AdapterCategoriesFilters.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_categories, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCategoriesFilters.ViewHolder holder, int position) {
        ClsCatagoryList current = list.get(position);

        holder.tv_CName.setText(current.getCategory());
        holder.checkBox.setChecked(current.getSelected());

        holder.main_layout.setOnClickListener(view -> {
            if (!holder.checkBox.isChecked()) {
                holder.checkBox.setChecked(true);
                current.setSelected(true);
                updateSelectStatus.updateSelectStatus(current, true);
            } else {
                holder.checkBox.setChecked(false);
                current.setSelected(false);
                updateSelectStatus.updateSelectStatus(current, false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        TextView tv_CName;
        LinearLayout main_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.cbCategories);
            tv_CName = itemView.findViewById(R.id.tv_CName);
            main_layout = itemView.findViewById(R.id.main_layout);
        }
    }
}
