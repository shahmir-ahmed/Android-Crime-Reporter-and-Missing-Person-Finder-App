package com.example.crimereporterandmissingpersonfinderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Window;

import com.google.android.material.tabs.TabLayout;

public class UserDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        // hiding the default action bar
//        getSupportActionBar().hide();
        // disabled by creating a custom style tag with dashboard theme in styles.xml and used that theme in manifest file dashboard activity tag theme attribute

        // Inflate the Toolbar and TabLayout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create an instance of the tab layout from the view.
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        // Set the text for each tab.
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label3));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label4));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label5));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label6));

        // Set the tabs to fill the entire layout.
        tabLayout.setTabGravity(TabLayout.GRAVITY_START);

        // Use PagerAdapter to manage page views in fragments.
        // Each page is represented by its own fragment.
        // This is another example of the adapter pattern.
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        // create pager adapter
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        // set adapter on the view pager
        viewPager.setAdapter(adapter);

        // Setting a listener for clicks.
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
}

//package com.example.crimereporterandmissingpersonfinderapp;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.viewpager.widget.ViewPager;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.view.MotionEvent;
//import android.view.View;
//
//import com.google.android.material.tabs.TabLayout;
//
//public class UserDashboardActivity extends AppCompatActivity {
//
//    private DraggableTabLayout tabLayout;
//    private ViewPager viewPager;
//
//    @SuppressLint("ClickableViewAccessibility")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_dashboard);
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        tabLayout = findViewById(R.id.tab_layout);
//
//        // Set the text for each tab.
//        tabLayout.getTabAt(0).setText(R.string.tab_label1);
//        tabLayout.getTabAt(1).setText(R.string.tab_label2);
//        tabLayout.getTabAt(2).setText(R.string.tab_label3);
//        tabLayout.getTabAt(3).setText(R.string.tab_label4);
//        tabLayout.getTabAt(4).setText(R.string.tab_label5);
//        tabLayout.getTabAt(5).setText(R.string.tab_label6);
//
//        viewPager = findViewById(R.id.pager);
//
//        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(adapter);
//
//        tabLayout.setupWithViewPager(viewPager);
//
//        // Enable dragging behavior on the tabs
//        tabLayout.setOnTouchListener(new View.OnTouchListener() {
//            private float startX;
//            private boolean isDragging = false;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        startX = event.getX();
//                        isDragging = false;
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        float newX = event.getX();
//                        float deltaX = newX - startX;
//
//                        if (Math.abs(deltaX) >= 20) {
//                            isDragging = true;
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        if (!isDragging) {
//                            // Click event on the tab, handle accordingly
//                            int position = tabLayout.getSelectedTabPosition();
//                            viewPager.setCurrentItem(position);
//                        }
//                        break;
//                }
//                return false;
//            }
//        });
//    }
//}
