package com.ftouchcustomer.Merchant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ftouchcustomer.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterTimeList extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<ClsTiming> lstClsTimings = new ArrayList<>();


    public AdapterTimeList(Context context, List<ClsTiming> lstClsTimings) {
        this.context = context;
        this.lstClsTimings = lstClsTimings;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lstClsTimings.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return lstClsTimings.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.row_single_line, viewGroup, false);
        ClsTiming _obj = lstClsTimings.get(position);

        TextView txt_name = view.findViewById(R.id.txt_name);

        txt_name.setText(_obj.getWeekDay().toUpperCase().concat(" (").concat(_obj.getTime().concat(")")));

        return view;
    }

}
