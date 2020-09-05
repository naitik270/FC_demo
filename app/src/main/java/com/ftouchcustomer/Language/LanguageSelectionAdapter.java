package com.ftouchcustomer.Language;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.Classes.ClsSettingNameVal;
import com.ftouchcustomer.Interface.OnClickListenerSetting;
import com.ftouchcustomer.Interface.OnRadioClickLanguage;
import com.ftouchcustomer.R;

import java.util.ArrayList;
import java.util.List;

public class LanguageSelectionAdapter extends RecyclerView.Adapter<LanguageSelectionAdapter.ViewHolder> {


    private LayoutInflater mInflater;
    private Context mContext;
    private List<ClsLanguageGetSet> list = new ArrayList<>();

    private int selectedPosition = -1;
    private OnRadioClickLanguage mOnLanguageClick;


    public LanguageSelectionAdapter(Context mContext, List<ClsLanguageGetSet> list) {
        this.mContext = mContext;
        this.list = list;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void SetOnClickListener(OnRadioClickLanguage mOnLanguageClick) {
        this.mOnLanguageClick = mOnLanguageClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_language_selection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClsLanguageGetSet currentObj = list.get(position);
        holder.txt_language.setText(currentObj.getLanguageName());
        holder.ckb_selection.setChecked(currentObj.isSelected());


        holder.ckb_selection.setTag(position);

//
        holder.ckb_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCheckChanged(position);
            }
        });

        holder.ll_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCheckChanged(position);
            }
        });

        holder.Bind(list.get(position), mOnLanguageClick, position);
    }


    public void itemCheckChanged(int position) {
        selectedPosition = position;
        notifyDataSetChanged();

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_language;
        private RadioButton ckb_selection;
        private LinearLayout ll_header;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_header = itemView.findViewById(R.id.ll_header);
            txt_language = itemView.findViewById(R.id.txt_language);
            ckb_selection = itemView.findViewById(R.id.ckb_selection);
        }

        void Bind(final ClsLanguageGetSet clsLanguageGetSet,
                  OnRadioClickLanguage mOnLanguageClick, int position) {
            ll_header.setOnClickListener(v ->
                    // send current position via Onclick method.
                    mOnLanguageClick.OnItemClick(clsLanguageGetSet, position));
        }
    }


}
