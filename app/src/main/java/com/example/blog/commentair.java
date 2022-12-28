package com.example.blog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class commentair extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentair);
        ImageView btnImg = findViewById(R.id.btnImg);

      /*  btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
          *//*  public void onClick(View view) {
            BottomSheetBehavior bottomSheetBehavior = new BottomSheetBehavior(commentair.this, R.style.BottomSheetDialogTheme);

                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.activity_commentair , (LinearLayout)findViewById(R.id.)
                        );
            }*//*
        });
*/
    }
}