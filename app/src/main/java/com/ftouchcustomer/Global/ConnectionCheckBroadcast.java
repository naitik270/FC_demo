package com.ftouchcustomer.Global;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class ConnectionCheckBroadcast extends BroadcastReceiver {

    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;


        if (CheckInternetConnection(context)) {
//
//            Toast.makeText(context,
//                    "Internet Connection", Toast.LENGTH_SHORT)
//                    .show();

        } else {
//            Toast.makeText(context,
//                    "Please check your internet connection!", Toast.LENGTH_SHORT)
//                    .show();
        }

    }


    public static boolean CheckInternetConnection(Context context) {

        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}

