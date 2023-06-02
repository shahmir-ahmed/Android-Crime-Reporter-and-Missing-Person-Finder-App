package com.example.crimereporterandmissingpersonfinderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button crimesSearchButton;
    private Button missingPersonsSearchButton;
    private Button loginButton;

    // shared preferences for user session
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String userIdKey = "idKey";
    SharedPreferences sharedpreferences;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing UI buttons
        crimesSearchButton = (Button) findViewById(R.id.crimesSearch);
        missingPersonsSearchButton = (Button) findViewById(R.id.missingPersonsSearch);
        loginButton = (Button) findViewById(R.id.login);

        // Initializing shared preferences
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        // retrieiving the user id key from
        userID = sharedpreferences.getString(userIdKey, "");

        // if no session exists
        if(userID.equals("")){
            // do nothing
        }
        else{
            loginButton.setText("Home");
        }

        // Setting event listener
        crimesSearchButton.setOnClickListener(this);
        missingPersonsSearchButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

    }

    // When any button is clicked
    @Override
    public void onClick(View view) {

        // type cast the clicked view to button
        Button btn = (Button) view;

        // get the text on the button
        String text = btn.getText().toString();

        // if crimes search button is clicked
        if(text.equals("Crimes Around You")){
//            Toast.makeText(this, "Crimes Search Screen Opening!", Toast.LENGTH_SHORT).show();

            // Intent to activate the SearchCrimeActivity
            Intent intent = new Intent(this, SearchCrimeActivity.class);

            startActivity(intent);
        }
        // if missing persons search button is clicked
        else if(text.equals("Missing People")){
//            Toast.makeText(this, "Missing People Search Screen Opening!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, SearchMissingPersonReportsActivity.class);

            startActivity(intent);
        }
        // if login button is clicked
        else if(text.equals("Login")){
//            Toast.makeText(this, "Login Screen Opening!", Toast.LENGTH_SHORT).show();

            // Sending intent to Login Activity
            Intent intent = new Intent(this, LoginActivity.class);

            startActivity(intent);

        }
        // if login button with "Home" text is clicked
        else if(text.equals("Home")){

            Toast.makeText(this, "Welcome back! user id:"+userID, Toast.LENGTH_SHORT).show();

            // Sending intent to User Dashboard Activity
            Intent intent = new Intent(this, UserDashboardActivity.class);

            startActivity(intent);
        }
    }

    // on start method calling beacuse when logout from dashboard the login activity is shown and the main activity is in the back stack so when backed the main activity will have the home icon set so when backed from login after logout the main activity will be restarted from stopped state (after restart, start method will be called) so checking the shared preferences again to initialize the view
    @Override
    protected void onStart() {
//        System.out.println("HERE!!");
        // retrieving the user id key from shared preferences
        userID = sharedpreferences.getString(userIdKey, "");

        // if no session exists
        if (userID.equals("")) {
            // do nothing
        } else {
            loginButton.setText("Home");
        }

        super.onStart();
    }
}