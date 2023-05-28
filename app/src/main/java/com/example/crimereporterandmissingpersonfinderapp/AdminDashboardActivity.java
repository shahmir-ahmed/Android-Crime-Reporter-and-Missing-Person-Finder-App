package com.example.crimereporterandmissingpersonfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.material.tabs.TabLayout;

public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // hiding the default action bar
//        getSupportActionBar().hide();
        // disabled by creating a custom style tag with dashboard theme in styles.xml and used that theme in manifest file dashboard activity tag theme attribute

        // Inflate the Toolbar and TabLayout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create an instance of the tab layout from the view.
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        // Set the text for each tab.
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label7));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label8));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label9));

        // Set the tabs to fill the entire layout.
        tabLayout.setTabGravity(TabLayout.GRAVITY_START);

        // For scrollable tabs
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        // Use PagerAdapter to manage page views in fragments.
        // Each page is represented by its own fragment.
        // This is another example of the adapter pattern.
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        // create pager adapter for admin
        final PagerAdapterAdmin adapter = new PagerAdapterAdmin(getSupportFragmentManager(), tabLayout.getTabCount());
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

    // Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.dashboard_menu, menu);

        int positionOfMenuItem = 0; // or whatever...
        MenuItem item = menu.getItem(positionOfMenuItem);
        SpannableString s = new SpannableString("Logout");
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);
        item.setTitle(s);

        return true;
    }

    // On click event handler on options menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.dashboard_menu:
                Intent intent = new Intent(this, LoginActivity.class);

                // destroys all the activities in the stack except the first main activity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // starting the login activity over the main activity
                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
