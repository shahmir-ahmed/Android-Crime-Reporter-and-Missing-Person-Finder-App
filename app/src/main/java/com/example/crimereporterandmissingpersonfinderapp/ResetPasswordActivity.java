package com.example.crimereporterandmissingpersonfinderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

        // getting the user email passe dfrom forgot password activity
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
                // Perform actual login logic here, such as authentication against a server

                try {
                    // creating database helper class object
                    DBHelper dbHelper = new DBHelper(getApplicationContext());

                    // creating sqlite database object and getting the readable repository of database in the object
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();

                    values.put(DatabaseContract.Users.COL_PASSWORD, password);

                    // where clause
                    String whereClause = DatabaseContract.Users.COL_USERNAME + "=?";

                    // where clause args
                    String whereArgs[] = {userEmail};

                    // query to check user trying to login exists
                    long updatedRow = db.update(DatabaseContract.Users.TABLE_NAME, values, whereClause, whereArgs);

                    // if password updated
                    if (updatedRow ==1) {

                        // redirect to login screen
                        Intent intent1 = new Intent(this, LoginActivity.class);

                        // destroys all the activities in the stack except the first main activity
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        // starting the login activity over the main activity
                        startActivity(intent1);

                        Toast.makeText(this, "Password reset successfully!", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(this, "Err", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(this, "Something went wrong\n Please try again later!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}