package com.example.crimereporterandmissingpersonfinderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ResetPasswordActivity extends AppCompatActivity {

    String userEmail; // user email passed from forgot password activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("email");

        EditText passwordEditText = findViewById(R.id.editTextNewPassword);
        EditText confirmPasswordEditText = findViewById(R.id.editTextConfirmPassword);


        Button resetPassword = findViewById(R.id.reset_button);
        resetPassword.setOnClickListener(view -> {
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            if (password.isEmpty()) {
                passwordEditText.setError("Password is required.");
                passwordEditText.requestFocus();
            }
            else if (confirmPassword.isEmpty()) {
                confirmPasswordEditText.setError("Confirm password is required.");
                confirmPasswordEditText.requestFocus();
            }
            else if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_@#$%^&+=])(?=\\S+$).{8,}$")) {
                passwordEditText.setError("Password should be a combination of integers, special characters, uppercase, and lowercase, and the minimum password length should be eight.");
                passwordEditText.requestFocus();
            }
            else if (!password.equals(confirmPassword)) {
                confirmPasswordEditText.setError("Passwords do not match.");
                confirmPasswordEditText.requestFocus();
            }
            else {
                // Update user password in the database using email

                Toast.makeText(this, "Password reset successfully.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}