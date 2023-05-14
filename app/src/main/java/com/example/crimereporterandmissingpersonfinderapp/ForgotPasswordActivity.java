package com.example.crimereporterandmissingpersonfinderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText emailEditText;

    Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = findViewById(R.id.email_ET);

        submitButton = findViewById(R.id.submit_btn);

        submitButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            //String password = passwordEditText.getText().toString();
            //String confirmPassword = confirmPasswordEditText.getText().toString();

            if (email.isEmpty()) 
            {
                emailEditText.setError("Email is required.");
                emailEditText.requestFocus();
            }
            else if (!isValidEmail(email))
            {
                emailEditText.setError("Invalid email.");
                emailEditText.requestFocus();
            }
            else {
                Intent intent = new Intent(this, ResetPasswordActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
                }
            });

    }

    private boolean isValidEmail(String email)
    {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}