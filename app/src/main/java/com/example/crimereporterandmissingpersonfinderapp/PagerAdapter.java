package com.example.crimereporterandmissingpersonfinderapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
//        return null;
        switch (position) {
            case 0:
                return new ReportMissingPersonFragment();
            case 1:
                return new MissingPersonReportsFragment();
//            case 2:
//                return new ReportCrimeFragment();
//            case 3:
//                return new CrimeReportsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}