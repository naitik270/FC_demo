package com.ftouchcustomer.Profile.State;

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

public class CountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> /*implements Filterable*/ {//newHarderList
    private static final int HARDER_VIEW = 0;
    private static final int COUNTRY_VIEW = 1;

    private ArrayList<ClsState> states;

    public void AddItems(List<ClsState> filterList) {
        this.states = (ArrayList<ClsState>) filterList;
        notifyDataSetChanged();
    }

    public interface onStateClickListener {
        void onStateClick(ClsState clsState);
    }

    onStateClickListener stateListener;

    public CountryAdapter(ArrayList<ClsState> states, onStateClickListener stateListener) {
        this.states = states;
        this.stateListener = stateListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//newHarderList
        if (viewType == COUNTRY_VIEW) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item_country_state_city, parent, false);
            return new CountryHolder(view);
        } else {
            return new SectionHeaderViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.header_line, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case 0:
                SectionHeaderViewHolder SectionHeaderviewHolder = (SectionHeaderViewHolder) holder;
                SectionHeaderviewHolder.headerTitleTextview.setClickable(false);
                SectionHeaderviewHolder.headerTitleTextview.setText(states.get(position).getStateName());
                break;
            case 1:
                CountryHolder countryHolder = (CountryHolder) holder; //newHarderList to

                ClsState clsState = states.get(position);
                countryHolder.tvCountryCode.setText(clsState.getStateID().toString() + "");
                countryHolder.tvCountry.setText(clsState.getStateName());
                countryHolder.llCountry.setOnClickListener(view ->
                        stateListener.onStateClick(states.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    @Override
    public int getItemViewType(int position) {//newHarderList
        try {
            if (states.get(position).getStateID() != (-2)) {
                return COUNTRY_VIEW;
            } else {
                return HARDER_VIEW;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return COUNTRY_VIEW;
        }
    }

    public static class CountryHolder extends RecyclerView.ViewHolder {
        TextView tvCountry, tvCountryCode;
        LinearLayout llCountry;

        public CountryHolder(@NonNull View itemView) {
            super(itemView);
            tvCountry = itemView.findViewById(R.id.tvCountry);
            tvCountryCode = itemView.findViewById(R.id.tvCountryCode);
            llCountry = itemView.findViewById(R.id.llCountry);
        }
    }

    public static class SectionHeaderViewHolder extends RecyclerView.ViewHolder {//newHarderList
        TextView headerTitleTextview;

        public SectionHeaderViewHolder(View itemView) {
            super(itemView);
            headerTitleTextview = itemView.findViewById(R.id.txt_cat_name);
        }
    }
}
