package com.example.crimereporterandmissingpersonfinderapp;//package com.example.crimereporterandmissingpersonfinderapp;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//
//import com.google.android.material.tabs.TabLayout;
//
//public class DraggableTabLayout extends TabLayout {
//
//    public DraggableTabLayout(Context context) {
//        super(context);
//    }
//
//    public DraggableTabLayout(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public DraggableTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        return false;
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return false;
//    }
//}

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;

public class DraggableTabLayout extends TabLayout {

    private static final int TAB_MIN_WIDTH_MARGIN_DP = 56; // Adjust this value as needed

    public DraggableTabLayout(@NonNull Context context) {
        super(context);
    }

    public DraggableTabLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DraggableTabLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean performClick() {
        // Override performClick to suppress performClick warning
        return super.performClick();
    }

    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        super.setupWithViewPager(viewPager);
        adjustTabMinWidthMargin();
    }

    private void adjustTabMinWidthMargin() {
        try {
            Field tabMinWidthMarginField = TabLayout.class.getDeclaredField("tabMinWidthMargin");
            tabMinWidthMarginField.setAccessible(true);
            int tabMinWidthMargin = (int) (TAB_MIN_WIDTH_MARGIN_DP * getResources().getDisplayMetrics().density);
            tabMinWidthMarginField.setInt(this, tabMinWidthMargin);
            tabMinWidthMarginField.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

