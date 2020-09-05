package com.ftouchcustomer.NavigationTabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.R;


public class InquiryFragment extends Fragment {

//    public static InquiryFragment newInstance() {
//
//        return new InquiryFragment();
//    }

    public InquiryFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_cart, container, false);
        ClsGlobal.isMoveToBack = true;


        main(v);

        return v;


    }

    private void main(View v) {

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Cart");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ClsGlobal.isMoveToBack = false;
    }

}
