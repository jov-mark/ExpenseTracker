package com.example.expensetracker.activity;


import android.os.Bundle;

import com.example.expensetracker.R;
import com.example.expensetracker.adapter.PagerAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewpager_main);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tablayout_main);
        tabLayout.setupWithViewPager(viewPager);
    }
}
