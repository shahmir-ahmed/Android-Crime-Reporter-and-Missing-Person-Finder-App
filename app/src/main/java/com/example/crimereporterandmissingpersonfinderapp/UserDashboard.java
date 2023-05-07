package com.example.crimereporterandmissingpersonfinderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class UserDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        // hiding the default action bar
        getSupportActionBar().hide();
    }
}