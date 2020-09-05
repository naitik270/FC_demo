package com.ftouchcustomer.Orders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AdapterOrderList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int SECTION_VIEW = 0;
    private static final int CONTENT_VIEW = 1;
    Context context;
    List<ClsLayerItemMaster> mlist = new ArrayList<>();

    public AdapterOrderList(Context context) {
        this.context = context;
    }

    public void AddItems(List<ClsLayerItemMaster> list) {
        if (list != null) {
            this.mlist = list;
            notifyDataSetChanged();

            Gson gson = new Gson();
            String jsonInString = gson.toJson(this.mlist);
            Log.e("--Test--", "Adapter: " + jsonInString);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == SECTION_VIEW) {
            return new SectionHeaderViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_order_header, parent, false));
        } else {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_order_item, parent, false));
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ClsLayerItemMaster current = mlist.get(position);
        switch (holder.getItemViewType()) {
            case 0:
                SectionHeaderViewHolder SectionHeaderviewHolder = (SectionHeaderViewHolder) holder;
                SectionHeaderviewHolder.headerTitleTextview.setClickable(false);
                SectionHeaderviewHolder.headerTitleTextview.setText(current.getFirst_letter());
                break;
            case 1:
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.txt_item_name.setText("" + current.getITEM_NAME().toUpperCase());
                String hsnCode = "";
                if (current.getHSN_SAC_CODE() != null && !current.getHSN_SAC_CODE().isEmpty()) {
                    hsnCode = current.getHSN_SAC_CODE().toUpperCase();
                } else {
                    hsnCode = "";
                }

                List<String> _tagList = current.getTagList();
                itemViewHolder.cast_layout.removeAllViews();

                if (current.getTagList() != null &&
                        current.getTagList().size() != 0) {

                    for (String name : _tagList) {
                        AddOrderList(itemViewHolder.cast_layout, name, "Visible");
                    }

                } else {
                    AddOrderList(itemViewHolder.cast_layout, "NO TAGS!", "Gone");
                }

                itemViewHolder.txt_Display_item_code.setText("" + current.getITEM_CODE()
                        .toUpperCase().concat(", UNIT: ").concat(current.getUNIT_CODE().toUpperCase()));

                itemViewHolder.txt_Display_unit.setText(hsnCode);

                if (current.getTAX_TYPE().equalsIgnoreCase("INCLUSIVE")) {
                    itemViewHolder.txt_rate.setText(String.valueOf(current.getAverageSaleRate()));
                } else {
                    itemViewHolder.txt_rate.setText(String.valueOf(current.getRATE_PER_UNIT()));
                }


                itemViewHolder.viewImgClick(current, onItemClick, position);
                itemViewHolder.Bind(current, onClickSalesItem);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mlist != null) {
            return mlist.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mlist.get(position).isHeader()) {

            return SECTION_VIEW;
        } else {
            return CONTENT_VIEW;
        }

    }

    private OnImageClick onItemClick;

    public void setOnViewImg(OnImageClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnClickSalesItem {
        void OnClick(ClsLayerItemMaster clsLayerItemMaster);
    }

    private OnClickSalesItem onClickSalesItem;

    public void setOnItem(OnClickSalesItem onClickSalesItem) {
        this.onClickSalesItem = onClickSalesItem;
    }

    public interface OnImageClick {
        void onItemImgClick(ClsLayerItemMaster objClsLayerItemMaster, int position);
    }

    public static class SectionHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitleTextview;

        SectionHeaderViewHolder(View itemView) {
            super(itemView);
            headerTitleTextview = itemView.findViewById(R.id.txt_cat_name);
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_item_name, txt_Display_item_code, txt_Display_unit, txt_rate, txt_final_stock;

        private CardView card;
        private LinearLayout linearLayout;
        private RelativeLayout ll_color;

        LinearLayout cast_layout;

        ImageView iv_photo;

        ItemViewHolder(View itemView) {
            super(itemView);

            txt_item_name = itemView.findViewById(R.id.txt_item_name);
            txt_Display_item_code = itemView.findViewById(R.id.txt_Display_item_code);
            txt_Display_unit = itemView.findViewById(R.id.txt_Display_unit);
            txt_rate = itemView.findViewById(R.id.txt_rate);
            txt_final_stock = itemView.findViewById(R.id.txt_final_stock);
            linearLayout = itemView.findViewById(R.id.linear);
            ll_color = itemView.findViewById(R.id.ll_color);
            iv_photo = itemView.findViewById(R.id.iv_photo);
            cast_layout = itemView.findViewById(R.id.cast_layout);
        }

        void Bind(final ClsLayerItemMaster clsLayerItemMaster, OnClickSalesItem onClickSalesItem) {
            linearLayout.setOnClickListener(v ->
                    // send current position via Onclick method.
                    onClickSalesItem.OnClick(clsLayerItemMaster));
        }

        void viewImgClick(ClsLayerItemMaster clsLayerItemMaster,
                          OnImageClick onItemImgClick, int position) {
            iv_photo.setOnClickListener(v -> onItemImgClick.onItemImgClick(clsLayerItemMaster, position));

        }

    }

    private void AddOrderList(LinearLayout linearLayout, String open, String mode) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View layout2 = inflater.inflate(R.layout.row_category_name, null);

        TextView txt_categories = layout2.findViewById(R.id.txt_categories);

        if (mode.equalsIgnoreCase("Visible")) {
            txt_categories.setVisibility(View.VISIBLE);
            txt_categories.setText(open);
            linearLayout.addView(layout2, linearLayout.getChildCount() - 1);
        } else {
            txt_categories.setVisibility(View.GONE);
        }
    }


}
