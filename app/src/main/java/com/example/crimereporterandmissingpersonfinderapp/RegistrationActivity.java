package com.example.crimereporterandmissingpersonfinderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize EditText views and RadioGroup
        EditText etName = findViewById(R.id.etName);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etDob = findViewById(R.id.etDob);
        EditText etPhone = findViewById(R.id.etPhoneNumber);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        // Set OnClickListener for etDob EditText
        etDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current date
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Create DatePickerDialog and show it
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegistrationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Set selected date in etDob EditText
                                etDob.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            }
                        },
                        year,
                        month,
                        day
                );
                datePickerDialog.show();
            }
        });

        // Set OnClickListener for submit button
        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get values from EditText fields
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String dob = etDob.getText().toString();
                String phone = etPhone.getText().toString();

                // Validate input fields
                if (name.isEmpty()) {
                    etName.setError("Please enter your name");
                    etName.requestFocus();
                    return;
                }else if (email.isEmpty()) { etEmail.setError("Please enter your email");
                    etEmail.requestFocus();
                    return;
                }else if (!isValidEmail(email)) {
                    etEmail.setError("Please enter a valid email");
                    etEmail.requestFocus();
                    return;
                }else if (password.isEmpty()) {
                    etPassword.setError("Please enter a password");
                    etPassword.requestFocus();
                    return;
                }else if (!isValidPassword(password)) {
                    etPassword.setError("Password must be at least 8 characters long and contain a combination of [a-z],[A-Z],[0-9],[@#_$%^&+=]");
                    etPassword.requestFocus();
                    return;
                }else if (dob.isEmpty()) {
                    etDob.setError("Please select your date of birth");
                    etDob.requestFocus();
                    return;
                }else if (phone.isEmpty()) {
                    etPhone.setError("Please enter your phone number");
                    etPhone.requestFocus();
                    return;
                }else if (!isValidPhone(phone)) {
                    etPhone.setError("Phone number must be 11 digits starting with 03 or 12 digits starting with 923");
                    etPhone.requestFocus();
                    return;
                }

                // Get selected gender from RadioGroup
                int selectedGenderId = radioGroup.getCheckedRadioButtonId();
                String gender = "";
                if (selectedGenderId == R.id.rbMale) {
                    gender = "Male";
                } else if (selectedGenderId == R.id.rbFemale) {
                    gender ="Female";
                }else if(!isGenderSelected(radioGroup)){
                    Toast.makeText(getApplicationContext(), "Please select your gender", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isGenderSelected(RadioGroup radioGroup) {
        int selectedGenderId = radioGroup.getCheckedRadioButtonId();
        return selectedGenderId != -1;
    }

    private boolean isValidPhone(String phone) {
        String pattern = "^03[0-9]{2}[0-9]{7}$";
        //^: Matches the start of the string.
        //03: Matches the country code for Pakistan.
        //[0-9]{2}: Matches the two-digit code for a specific mobile network operator.
        //[0-9]{7}: Matches the 7-digit subscriber number.
        //$: Matches the end of the string.
        return phone.matches(pattern);
    }

    private boolean isValidPassword(String password) {
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@|`~=(){}$!%*+_#^,./';:?&-])[A-Za-z\\d@|`~=(){}$!%*+_#^,./';:?&-]{8,20}$";
        //^: Matches the start of the string.
        //(?=.*[a-z]): Positive lookahead to ensure that the password contains at least one lowercase letter.
        //(?=.*[A-Z]): Positive lookahead to ensure that the password contains at least one uppercase letter.
        //(?=.*\\d): Positive lookahead to ensure that the password contains at least one digit.
        //(?=.*[@|`~=(){}$!%*+_#^,./';:?&-]): Positive lookahead to ensure that the password contains at least one special character.
        //[A-Za-z\\d@$!%*?&]{8,20}: Matches any combination of letters, digits, and special characters, between 8 and 20 characters long.
        //$: Matches the end of the string.
        return password.matches(pattern);
    }

    private boolean isValidEmail(String email) {
        String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        //[a-zA-Z0-9._-]+: Matches one or more characters that are letters, digits, periods, underscores, or hyphens.
        //@: Matches the "@" symbol.
        //[a-z]+: Matches one or more characters that are letters (lowercase).
        //\\.: Matches a period (escaped with a backslash to avoid interpreting it as a special character).
        //[a-z]+: Matches one or more characters that are letters (lowercase).
        return email.matches(pattern);
    }
}