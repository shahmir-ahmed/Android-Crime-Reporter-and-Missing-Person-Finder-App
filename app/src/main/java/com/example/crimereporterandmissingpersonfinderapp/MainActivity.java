package com.example.crimereporterandmissingpersonfinderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button crimesSearchButton;
    private Button missingPersonsSearchButton;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing UI buttons
        crimesSearchButton = (Button) findViewById(R.id.crimesSearch);
        missingPersonsSearchButton = (Button) findViewById(R.id.missingPersonsSearch);
        loginButton = (Button) findViewById(R.id.login);

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
            Toast.makeText(this, "Missing People Search Screen Opening!", Toast.LENGTH_SHORT).show();
        }
        // if login button is clicked
        else if(text.equals("Login")){
            Toast.makeText(this, "Login Screen Opening!", Toast.LENGTH_SHORT).show();
        }
    }

}