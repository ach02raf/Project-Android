package com.example.blog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.blog.Utility.NetWorkChangeLisner;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Context context;

    NetWorkChangeLisner netWorkChangeLisner = new NetWorkChangeLisner();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.login);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(), login.class));
           /* if you want to finish the first activity then just call
            finish(); */
            }
        });
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkChangeLisner, filter);
        super.onStart();

    }

    @Override
    protected void onStop() {
        unregisterReceiver(netWorkChangeLisner);
        super.onStop();
    }


  /*  private boolean isConnected (){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(context.CONNECTIVITY_SERVICE);
        return  connectivityManager.getActiveNetworkInfo()!=null  && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting() ;
    }*/
}