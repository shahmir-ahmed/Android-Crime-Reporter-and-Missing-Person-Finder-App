package com.example.crimereporterandmissingpersonfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class UserDashboardActivity extends AppCompatActivity {

    int backPressedCount = 0;

    // shared preferences for user session
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String userNameKey = "userNameKey";
    SharedPreferences sharedpreferences;
    String userName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        // initialize the shared preferences
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

//        Intent intent = getIntent();
//
//        userName = intent.getStringExtra("username");

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

        // For scrollable tabs
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.dashboard_menu, menu);

        // get the username of user through shared preferences
        userName = sharedpreferences.getString(userNameKey, "");

        // user name setting on options menu
        int positionOfMenuItem = 0; // or whatever...
        MenuItem item = menu.getItem(positionOfMenuItem);
        SpannableString s = new SpannableString(userName);
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);
        item.setTitle(s);

        // Logout text changing color
        int positionOfMenuItem1 = 1; // or whatever...
        MenuItem item1 = menu.getItem(positionOfMenuItem1);
        SpannableString s1 = new SpannableString("Logout");
        s1.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s1.length(), 0);
        item1.setTitle(s1);

//        return super.onCreateOptionsMenu(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.dashboard_menu:

                // clear the shared preferences
                SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();

                finish(); // finishes the dashboard activity // dont know why it needs to be finished when login activity intent is sent clearing all the activities except main activity

                Intent intent = new Intent(this, LoginActivity.class);

                // destroys all the activities in the stack except the first main activity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // starting the login activity over the main activity
                startActivity(intent);

                Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
//        if(backPressedCount==0){
//            Toast.makeText(this, "Press again to logout", Toast.LENGTH_LONG).show();
//            backPressedCount++;
//        }
//        // logout
//        else if(backPressedCount==2){
//
//            // clear the shared preferences
//            SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedpreferences.edit();
//            editor.clear();
//            editor.commit();

//            Intent intent = new Intent(this, UserDashboardActivity.class);
//
//            // destroys all the activities in the stack except the first main activity
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            // starting the login activity over the main activity
//            startActivity(intent);

//            Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show();

//        }

//        finish(); // end the dashboard activity

        // intent to main activity
        Intent intent = new Intent(this, MainActivity.class);

        // destroys all the activities in the stack except the first main activity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // starting the login activity over the main activity
        startActivity(intent);
    }

    // Function to logout the user/admin
//    public void logout(View view){
//
//        Intent intent = new Intent(this, LoginActivity.class);
//
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        startActivity(intent);
//
//    }
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
