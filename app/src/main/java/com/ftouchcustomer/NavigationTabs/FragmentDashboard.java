package com.ftouchcustomer.NavigationTabs;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.R;

import java.util.Objects;

public class FragmentDashboard extends Fragment {

    LinearLayout ll_leads, ll_franchise;


    public FragmentDashboard() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_dashboard, container, false);

        ClsGlobal.isMoveToBack = true;
        setHasOptionsMenu(true);

        Toolbar toolbar = v.findViewById(R.id.toolbar);
        //for crate home button
        toolbar.setTitle("Dashboard");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        main(v);


        return v;
    }

    private void main(View v) {


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ClsGlobal.isMoveToBack = false;
    }
}
