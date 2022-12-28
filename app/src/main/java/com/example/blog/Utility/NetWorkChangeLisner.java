package com.example.blog.Utility;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatButton;

import com.example.blog.R;

public class NetWorkChangeLisner extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Common.isConnectedToInternet(context)) {



            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.internet_check_dialog, null);
            builder.setView(layout_dialog);


            AppCompatButton btnRetry = layout_dialog.findViewById(R.id.button3);

            //show dialog
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCancelable(false);

            dialog.getWindow().setGravity(Gravity.CENTER);

            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    onReceive(context, intent);
                }
            });
        }
    }
}
