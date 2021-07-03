package com.loginius.loginiuspartners.Activity.Admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.loginius.loginiuspartners.Frag.MyTabAdapter;
import com.loginius.loginiuspartners.R;

public class ViewStudentActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("CURRENT"));
        tabLayout.addTab(tabLayout.newTab().setText("ALUMNI"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        MyTabAdapter myTabAdapter = new MyTabAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(myTabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText("CURRENT"));
        tabLayout.addTab(tabLayout.newTab().setText("ALUMNI"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        MyTabAdapter myTabAdapter = new MyTabAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(myTabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}