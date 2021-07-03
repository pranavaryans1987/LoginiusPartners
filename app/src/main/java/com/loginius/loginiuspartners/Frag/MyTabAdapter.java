package com.loginius.loginiuspartners.Frag;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.loginius.loginiuspartners.Activity.Admin.ViewStudentActivity;

public class MyTabAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;

    public MyTabAdapter(ViewStudentActivity viewStudentActivity, FragmentManager supportFragmentManager, int tabCount) {
        super(supportFragmentManager);
        context = viewStudentActivity;
        totalTabs = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                CurrentFrag cr = new CurrentFrag(context);
                return cr;
            case 1:
                AlumniFrag af = new AlumniFrag(context);
                return af;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
