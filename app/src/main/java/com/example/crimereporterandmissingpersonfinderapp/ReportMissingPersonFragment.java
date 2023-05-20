package com.example.crimereporterandmissingpersonfinderapp;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.FileNotFoundException;
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
    EditText etPersonName, etPersonAge, etLastSeen, etDetails;

    RadioGroup radioGroup;

    RadioButton rbMale, rbFemale;

    ImageView imageViewPersonImage;

    Button browseBtn, submitBtn;

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
        etLastSeen = (EditText) getView().findViewById(R.id.etLastSeen);
        etDetails = (EditText) getView().findViewById(R.id.etDetails);
        imageViewPersonImage = (ImageView) getView().findViewById(R.id.imageViewPersonImage);

        browseBtn = (Button) getView().findViewById(R.id.browseBtn);
        submitBtn = (Button) getView().findViewById(R.id.submitBtn);

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
                String name, age, gender, lastSeen, reportDetails;

                name = etPersonName.getText().toString();

                age = etPersonAge.getText().toString();

                lastSeen = etLastSeen.getText().toString();

                reportDetails = etDetails.getText().toString();

                // validation
                // if name is not entered
                if(name.trim().isEmpty()){
                    Toast.makeText(getView().getContext(), "Please enter name!", Toast.LENGTH_LONG).show();
                }
                // if age is not entered
                else if(age.trim().isEmpty()){
                    Toast.makeText(getView().getContext(), "Please enter age!", Toast.LENGTH_LONG).show();
                }
                // if no radio button is selected
                else if(radioGroup.getCheckedRadioButtonId()==-1){
                    Toast.makeText(getView().getContext(), "Please select gender!", Toast.LENGTH_LONG).show();
                }
                // last seen field is not filled
                else if(lastSeen.trim().isEmpty()){
                    Toast.makeText(getView().getContext(), "Please enter last seen location!", Toast.LENGTH_LONG).show();
                }
                // report details are not entered
                else if(reportDetails.trim().isEmpty()){
                    Toast.makeText(getView().getContext(), "Please enter report details!", Toast.LENGTH_LONG).show();
                }
                // if image is not selected
                else if(imageViewPersonImage.getDrawable()==null){
                    Toast.makeText(getView().getContext(), "Please choose an image!", Toast.LENGTH_LONG).show();
                }
                // if form is valid
                else{

                    // save form data in database

                    Toast.makeText(getView().getContext(), "Report submitted!", Toast.LENGTH_LONG).show();

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