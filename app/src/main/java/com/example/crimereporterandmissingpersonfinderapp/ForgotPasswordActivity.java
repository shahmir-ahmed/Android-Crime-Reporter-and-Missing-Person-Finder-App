package com.example.crimereporterandmissingpersonfinderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

                // verify user email from users table
                // creating database helper class object
                DBHelper dbHelper = new DBHelper(getApplicationContext());

                // creating sqlite database object and getting the readable repository of database in the object
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                // columns for which data needs to be retrieved
                String columns[] = {DatabaseContract.Users.COL_USERNAME};

                // where clause
                String whereClause = DatabaseContract.Users.COL_USERNAME + "=?";

                // where clause args
                String whereArgs[] = {email};

                // query to check user trying to login exists
                Cursor result = db.query(DatabaseContract.Users.TABLE_NAME, columns, whereClause, whereArgs, null, null, null);

                // if user exists

                // query() method returns an Empty Cursor if there is not record found
                // You can use getCount() to confirm if the Cursor is empty or check if moveToFirst() returns false (i.e., it could not move to the first row).
                if (result.moveToFirst()) {
                    Intent intent = new Intent(this, ResetPasswordActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    Toast.makeText(this, "Email verified!", Toast.LENGTH_SHORT).show();
                }
                else{
                    emailEditText.setError("Email does not exists");
                    emailEditText.requestFocus();
                }
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