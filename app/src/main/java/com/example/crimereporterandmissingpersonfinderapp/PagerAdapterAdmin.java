package com.example.crimereporterandmissingpersonfinderapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapterAdmin extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public PagerAdapterAdmin(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ReportMissingPersonFragment();
            case 1:
                return new CrimeReportsByUsersFragment();
            case 2:
                return new LodgeComplaintFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}