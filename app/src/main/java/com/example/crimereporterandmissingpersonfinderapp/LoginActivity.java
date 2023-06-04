package com.example.crimereporterandmissingpersonfinderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    // edit text fields username and password
    EditText etUsername, etPassword;

    // login, forgot password and sign up buttons
    Button btnLogin, btnForgotPassword, btnSignUp;

    // shared preferences for user session
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String userIdKey = "idKey";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Initialize views
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnForgotPassword = (Button) findViewById(R.id.btn_forgot_password);
        btnSignUp = (Button) findViewById(R.id.btn_signup);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        /*
        // dummy user data
        // creating database helper class object
        DBHelper dbHelper = new DBHelper(getApplicationContext());

        // creating sqlite database object and getting the readable repository of database in the object
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DatabaseContract.Users.COL_NAME, "Shahmir Ahmed");
        values.put(DatabaseContract.Users.COL_CNIC, "76678667867");
        values.put(DatabaseContract.Users.COL_CONTACT, "03452129816");
        values.put(DatabaseContract.Users.COL_GENDER, "Male");
        values.put(DatabaseContract.Users.COL_USERNAME, "ahmed1212514@gmail.com");
        values.put(DatabaseContract.Users.COL_PASSWORD, "12345678");

        long id = db.insert(DatabaseContract.Users.TABLE_NAME, null, values);

        if(id>0){
            Toast.makeText(LoginActivity.this, "inserted:"+id, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(LoginActivity.this, "Err 1", Toast.LENGTH_SHORT).show();
        }

        ContentValues values1 = new ContentValues();

        values1.put(DatabaseContract.Users.COL_NAME, "Shehryar Ahmed");
        values1.put(DatabaseContract.Users.COL_CNIC, "878979779");
        values1.put(DatabaseContract.Users.COL_CONTACT, "03215102450");
        values1.put(DatabaseContract.Users.COL_GENDER, "Male");
        values1.put(DatabaseContract.Users.COL_USERNAME, "sheryara17@gmail.com");
        values1.put(DatabaseContract.Users.COL_PASSWORD, "88888888");

        long id1 = db.insert(DatabaseContract.Users.TABLE_NAME, null, values1);

        if(id1>0){
            Toast.makeText(LoginActivity.this, "inserted:"+id1, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(LoginActivity.this, "Err 2", Toast.LENGTH_SHORT).show();
        }
        */


        // Set click listeners
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform login logic here
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                // trimming username and password
                username = username.trim();
                password = password.trim();

                if (username.isEmpty() || password.isEmpty()) {
//                    Toast.makeText(LoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                    etUsername.setError("Please enter username");
                    etPassword.setError("Please enter password");
                    etUsername.requestFocus();
                    etPassword.requestFocus();
                    return;
                } else {
                    // Perform actual login logic here, such as authentication against a server

                    // creating database helper class object
                    DBHelper dbHelper = new DBHelper(getApplicationContext());

                    // creating sqlite database object and getting the readable repository of database in the object
                    SQLiteDatabase db = dbHelper.getReadableDatabase();

                    // columns for which data needs to be retrieved
                    String columns[] = {DatabaseContract.Users._ID, DatabaseContract.Users.COL_USERNAME, DatabaseContract.Users.COL_PASSWORD};

                    // where clause
                    String whereClause = DatabaseContract.Users.COL_USERNAME+"=? AND "+DatabaseContract.Users.COL_PASSWORD+"=?";

                    // where clause args
                    String whereArgs[] = {username, password};

                    // query to check user trying to login exists
                    Cursor result = db.query(DatabaseContract.Users.TABLE_NAME, columns, whereClause, whereArgs, null, null, null);

                    // if user exists

                    // query() method returns an Empty Cursor if there is not record found
                    // You can use getCount() to confirm if the Cursor is empty or check if moveToFirst() returns false (i.e., it could not move to the first row).
                    if(result.moveToFirst()){

                        Toast.makeText(LoginActivity.this, "Logged in successfully, Welcome "+username, Toast.LENGTH_SHORT).show();

                        // send intent to user dashboard activity
//                        Intent intent = new Intent(LoginActivity.this, UserDashboardActivity.class);

                        // saving user id in shared preferences
                        String userID = String.valueOf(result.getInt(result.getColumnIndexOrThrow(DatabaseContract.Users._ID))); // getting the id

                        SharedPreferences.Editor editor = sharedpreferences.edit(); // getting editor object ti edit the shared pref.

                        editor.putString(userIdKey, userID); // putting the user id in shared preferences
                        editor.commit(); // committing the changes


                        Intent intent = new Intent(LoginActivity.this, UserDashboardActivity.class);

                        // send username of user with the intent
                        intent.putExtra("username", username);

                        startActivity(intent); // starting activity using intent

//                        intent.putExtra("userId", userID); // putting in intent
//
//                        startActivity(intent); // starting activity using intent

                        result.close(); // close the cursor

                    }

                    // Check if the user trying to login is admin
                    else{
                        // Here when we know that the user trying to login is not registered user
//                        Toast.makeText(LoginActivity.this, "You are not registered user!", Toast.LENGTH_SHORT).show();

                        // check user credentials are present as admin or not from database
                        // columns for which data needs to be retrieved
                        String columns1[] = {DatabaseContract.Admins.COL_USERNAME, DatabaseContract.Admins.COL_PASSWORD};

                        // where clause
                        String whereClause1 = DatabaseContract.Admins.COL_USERNAME+"=? AND "+DatabaseContract.Admins.COL_PASSWORD+"=?";

                        // where clause args
                        String whereArgs1[] = {username, password};

                        // query to check user trying to login exists
                        Cursor result1 = db.query(DatabaseContract.Admins.TABLE_NAME, columns1, whereClause1, whereArgs1, null, null, null);

                        // if user exists

                        // query() method returns an Empty Cursor if there is not record found
                        // You can use getCount() to confirm if the Cursor is empty or check if moveToFirst() returns false (i.e., it could not move to the first row).
                        if(result1.moveToFirst()){

                            Toast.makeText(LoginActivity.this, "Logged in successfully, Welcome "+username, Toast.LENGTH_SHORT).show();

                            // send intent to user dashboard activity
                            Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);

                            startActivity(intent);
                            result1.close(); // close the cursor
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Incorrect username or password!", Toast.LENGTH_SHORT).show();
                        }


//                        // send intent to admin dashboard activity
//                        Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
////
//                        startActivity(intent);
                    }

                    db.close(); // close the sqlite database object


                }
            }
        });

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform forgot password logic here
//                Toast.makeText(LoginActivity.this, "Forgot password clicked", Toast.LENGTH_SHORT).show();

                // Sending intent to forgot password activity
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);

                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform sign up logic here
//                Toast.makeText(LoginActivity.this, "Sign up clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);

                startActivity(intent);
            }
        });
    }
}
