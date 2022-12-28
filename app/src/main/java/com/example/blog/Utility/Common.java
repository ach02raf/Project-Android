package com.example.blog.Utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import java.net.NetworkInterface;

public class Common {
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo  info = connectivityManager.getActiveNetworkInfo();
            if (info != null) {

                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }

        return false;

    }
}
