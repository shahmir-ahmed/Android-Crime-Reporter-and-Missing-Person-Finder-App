package com.example.crimereporterandmissingpersonfinderapp;

import static android.app.Activity.RESULT_OK;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportMissingPersonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportMissingPersonFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RESULT_LOAD_IMG = 0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // UI Controls
    EditText etPersonName, etPersonAge, etLastSeen, etZipCode, etDetails;

    RadioGroup radioGroup;

    RadioButton rbMale, rbFemale;

    ImageView imageViewPersonImage;

    Button browseBtn, submitBtn;

    // shared preferences for user session
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String userIdKey = "idKey";
    SharedPreferences sharedpreferences;

    public ReportMissingPersonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportMissingPersonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportMissingPersonFragment newInstance(String param1, String param2) {
        ReportMissingPersonFragment fragment = new ReportMissingPersonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_missing_person, container, false);
    }


    //    Use getView() or the View parameter from implementing the onViewCreated method.
//    It returns the root view for the fragment (the one returned by onCreateView() method).
//    With this you can call findViewById().
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        // Creating bridge
        etPersonName = (EditText) getView().findViewById(R.id.etPersonName);
        etPersonAge = (EditText) getView().findViewById(R.id.etPersonAge);
        radioGroup = (RadioGroup) getView().findViewById(R.id.radioGroup1);
        rbMale = (RadioButton)  getView().findViewById(R.id.rbMale);
        rbFemale = (RadioButton)  getView().findViewById(R.id.rbFemale);
        etLastSeen = (EditText) getView().findViewById(R.id.etLastSeen);
        etZipCode = (EditText) getView().findViewById(R.id.editTextZipCode);
        etDetails = (EditText) getView().findViewById(R.id.etDetails);
        imageViewPersonImage = (ImageView) getView().findViewById(R.id.imageViewPersonImage);

        browseBtn = (Button) getView().findViewById(R.id.browseBtn);
        submitBtn = (Button) getView().findViewById(R.id.submitBtn);

        // initailzing shared preferences
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, getContext().MODE_PRIVATE);

        // on click listener on browse image button
        browseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getImageFromGallery();

            }
        });


        // on click listener on submit button
        submitBtn.setOnClickListener(new View.OnClickListener() {
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
//                    Toast.makeText(getView().getContext(), "Please enter name!", Toast.LENGTH_LONG).show();
                    etPersonName.setError("Name is required");
                    etPersonName.requestFocus();
                    return;
                }
                // if age is not entered
                else if(age.trim().isEmpty()){
//                    Toast.makeText(getView().getContext(), "Please enter age!", Toast.LENGTH_LONG).show();
                    etPersonAge.setError("Age is required");
                    etPersonAge.requestFocus();
                    return;
                }
                // if age is not entered
                else if(age.length()!=2){
                    Toast.makeText(getView().getContext(), "Invalid age!", Toast.LENGTH_LONG).show();
                }
                // if no radio button is selected
                else if(radioButtonId==-1){
                    Toast.makeText(getView().getContext(), "Please select gender!", Toast.LENGTH_LONG).show();
                }
                // last seen field is not filled
                else if(lastSeen.trim().isEmpty()){
//                    Toast.makeText(getView().getContext(), "Please enter last seen location!", Toast.LENGTH_LONG).show();
                    etLastSeen.setError("Last seen location is required");
                    etLastSeen.requestFocus();
                    return;
                }
                // zip code field
                else if(zipCode.trim().isEmpty()){
//                    Toast.makeText(getView().getContext(), "Please enter zip code!", Toast.LENGTH_LONG).show();
                    etZipCode.setError("Zip code is required");
                    etZipCode.requestFocus();
                    return;
                }
                else if(zipCode.length()>5 || zipCode.length()<5){
//                    Toast.makeText(getView().getContext(), "Zip code must be 5 digits!", Toast.LENGTH_LONG).show();
                    etZipCode.setError("Zip code must be 5 digits!");
                    etZipCode.requestFocus();
                    return;
                }
                // report details are not entered
                else if(reportDetails.trim().isEmpty()){
//                    Toast.makeText(getView().getContext(), "Please enter report details!", Toast.LENGTH_LONG).show();
                    etDetails.setError("Report details are required");
                    etDetails.requestFocus();
                    return;
                }
                // if image is not selected
                else if(imageViewPersonImage.getDrawable()==null){
                    Toast.makeText(getView().getContext(), "Please choose an image!", Toast.LENGTH_LONG).show();
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
                    DBHelper dbHelper = new DBHelper(getActivity().getApplicationContext());

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

                    // getting the user id from shared preferences
                    int userId = Integer.parseInt(sharedpreferences.getString(userIdKey, ""));

                    values.put(DatabaseContract.MissingPersons.COL_USER_ID, userId);

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
                        Toast.makeText(getView().getContext(), "Report not submitted!", Toast.LENGTH_LONG).show();
                    } else{
                        Toast.makeText(getView().getContext(), "Report submitted successfully!", Toast.LENGTH_LONG).show();

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
                final InputStream imageStream = requireActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageViewPersonImage.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getView().getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getView().getContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

}