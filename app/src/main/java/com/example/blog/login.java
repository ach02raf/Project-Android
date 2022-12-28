package com.example.blog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.blog.Adapters.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class login extends AppCompatActivity {

    private TabLayout tablayout ;
    private ViewPager2 viewPager2 ;
    private FragmentAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tablayout = findViewById(R.id.tablayout);
        viewPager2 = findViewById(R.id.viewpage);

        tablayout.addTab(tablayout.newTab().setText("sign in"));
        tablayout.addTab(tablayout.newTab().setText("sign up"));


        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new FragmentAdapter(fragmentManager , getLifecycle());
        viewPager2.setAdapter(adapter);

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tablayout.selectTab(tablayout.getTabAt(position));
            }
        });



//animation
        float v = 0 ;

        tablayout.setTranslationY(300);
        tablayout.setAlpha(v);

        tablayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();



    }
}