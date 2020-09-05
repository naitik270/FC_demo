package com.ftouchcustomer.Settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.Classes.ClsSettingNameVal;
import com.ftouchcustomer.Interface.OnClickListenerSetting;
import com.ftouchcustomer.R;

import java.util.ArrayList;
import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<ClsSettingNameVal> mSettingName = new ArrayList<>();
    private OnClickListenerSetting mOnClickListenerSetting;

    public SettingAdapter(Context context, int i, List<ClsSettingNameVal> list,
                          OnClickListenerSetting onClickListenerSetting) {
        this.mContext = context;
        this.mSettingName = list;
        this.mOnClickListenerSetting = onClickListenerSetting;
        this.mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_setting_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClsSettingNameVal currentObj = mSettingName.get(position);
        holder.imageView.setImageResource(currentObj.getImages());
        holder.Txt_SettingName_Display.setText(currentObj.getSettingName());
        holder.txt_description.setText(currentObj.getDescription());
        holder.Bind(mSettingName.get(position), mOnClickListenerSetting);
    }

    @Override
    public int getItemCount() {
        return mSettingName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Txt_SettingName_Display, txt_description;
        private ImageView imageView;
        private LinearLayout ll_header;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_header = itemView.findViewById(R.id.ll_header);
            Txt_SettingName_Display = itemView.findViewById(R.id.Txt_SettingName_Display);
            imageView = itemView.findViewById(R.id.imageView);
            txt_description = itemView.findViewById(R.id.txt_description);
        }

        void Bind(final ClsSettingNameVal clsSettingNames,
                  OnClickListenerSetting OnClickListenerSetting) {
            ll_header.setOnClickListener(v ->
                    // send current position via Onclick method.
                    OnClickListenerSetting.OnItemClick(clsSettingNames));
        }

    }


}
