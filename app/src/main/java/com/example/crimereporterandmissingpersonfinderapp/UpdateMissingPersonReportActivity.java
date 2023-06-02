package com.example.crimereporterandmissingpersonfinderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UpdateMissingPersonReportActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMG = 0;

    // UI Controls
    EditText etPersonName, etPersonAge, etLastSeen, etZipCode, etDetails;

    RadioGroup radioGroup;

    RadioButton rbMale, rbFemale;

    ImageView imageViewPersonImage;

    Button browseBtn, updateBtn;

    // shared preferences for user session
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String userIdKey = "idKey";
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_missing_person_report);

        // Creating bridge
        etPersonName = (EditText) findViewById(R.id.etPersonName);
        etPersonAge = (EditText) findViewById(R.id.etPersonAge);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        rbMale = (RadioButton)  findViewById(R.id.rbMale);
        rbFemale = (RadioButton)  findViewById(R.id.rbFemale);
        etLastSeen = (EditText) findViewById(R.id.etLastSeen);
        etZipCode = (EditText) findViewById(R.id.editTextZipCode);
        etDetails = (EditText) findViewById(R.id.etDetails);
        imageViewPersonImage = (ImageView) findViewById(R.id.imageViewPersonImage);

        browseBtn = (Button) findViewById(R.id.browseBtn);
        updateBtn = (Button) findViewById(R.id.updateBtn);

        // initailzing shared preferences
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        // on click listener on browse image button
        browseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getImageFromGallery();

            }
        });


        // on click listener on submit button
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting form details
                String name, age, gender, lastSeen, zipCode, reportDetails;

                name = etPersonName.getText().toString();

                age = etPersonAge.getText().toString();

                int radioButtonId = radioGroup.getCheckedRadioButtonId();

                lastSeen = etLastSeen.getText().toString();

                zipCode = etZipCode.getText().toString();

                reportDetails = etDetails.getText().toString();

                // validation
                // if name is not entered
                if(name.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter name!", Toast.LENGTH_LONG).show();
                }
                // if age is not entered
                else if(age.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter age!", Toast.LENGTH_LONG).show();
                }
                // if age is not entered
                else if(age.length()>2){
                    Toast.makeText(getApplicationContext(), "Invalid age!", Toast.LENGTH_LONG).show();
                }
                // if no radio button is selected
                else if(radioButtonId==-1){
                    Toast.makeText(getApplicationContext(), "Please select gender!", Toast.LENGTH_LONG).show();
                }
                // last seen field is not filled
                else if(lastSeen.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter last seen location!", Toast.LENGTH_LONG).show();
                }
                // zip code field
                else if(zipCode.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter zip code!", Toast.LENGTH_LONG).show();
                }
                else if(zipCode.length()>5 || zipCode.length()<5){
                    Toast.makeText(getApplicationContext(), "Zip code must be 5 digits!", Toast.LENGTH_LONG).show();
                }
                // report details are not entered
                else if(reportDetails.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter report details!", Toast.LENGTH_LONG).show();
                }
                // if image is not selected
                else if(imageViewPersonImage.getDrawable()==null){
                    Toast.makeText(getApplicationContext(), "Please choose an image!", Toast.LENGTH_LONG).show();
                }
                // if form is valid
                else{

                    // checking the gender
                    if(radioButtonId == R.id.rbMale){
                        gender = "Male";
                    }
                    else{
                        gender = "Female";
                    }

                    // save form data in database
                    DBHelper dbHelper = new DBHelper(getApplicationContext());

                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    // data preparing to send
                    ContentValues values = new ContentValues();

                    // putting key value pairs in object
                    values.put(DatabaseContract.MissingPersons.COL_NAME, name);
                    values.put(DatabaseContract.MissingPersons.COL_AGE, age);
                    values.put(DatabaseContract.MissingPersons.COL_GENDER, gender);
                    values.put(DatabaseContract.MissingPersons.COL_LAST_SEEN, lastSeen);
                    values.put(DatabaseContract.MissingPersons.COL_ZIPCODE, zipCode);
                    values.put(DatabaseContract.MissingPersons.COL_REPORT_DETAILS, reportDetails);

                    // Status intially set to submitted
                    values.put(DatabaseContract.MissingPersons.COL_REPORT_STATUS, "Submitted");

                    // getting the report id from intent


                    // missing person image
                    // Get the Bitmap from the ImageView
                    Bitmap bitmap = ((BitmapDrawable) imageViewPersonImage.getDrawable()).getBitmap();

                    // Convert the Bitmap to a byte array
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] personImage = baos.toByteArray();

                    // content value --> missing person image
                    values.put(DatabaseContract.MissingPersons.COL_PERSON_IMAGE, personImage);

                    // saving form data in DB
                    long rowId= db.insert(DatabaseContract.MissingPersons.TABLE_NAME, null,  values);

                    if(rowId == -1){
                        Toast.makeText(getApplicationContext(), "Report not submitted!", Toast.LENGTH_LONG).show();
                    } else{
                        Toast.makeText(getApplicationContext(), "Report submitted successfully!", Toast.LENGTH_LONG).show();

                        // reset all the fields
                        etPersonName.setText("");
                        etPersonAge.setText("");
                        radioGroup.clearCheck();
                        etLastSeen.setText("");
                        etZipCode.setText("");
                        etDetails.setText("");
                        imageViewPersonImage.setImageDrawable(null);
                    }


                    // Show missing person reports fragment

                }

            }
        });

    }

    // Used to start gallery activity
    public void getImageFromGallery(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    // get the gallery result in here
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getApplicationContext().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageViewPersonImage.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getApplicationContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}