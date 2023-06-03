//package com.example.crimereporterandmissingpersonfinderapp;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ReportCrimeFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class ReportCrimeFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public ReportCrimeFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ReportCrimeFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ReportCrimeFragment newInstance(String param1, String param2) {
//        ReportCrimeFragment fragment = new ReportCrimeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_report_crime, container, false);
//    }
//}

package com.example.crimereporterandmissingpersonfinderapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportCrimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportCrimeFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1; // store the request code for image selection
    private EditText editTextStreetNumber;
    private Spinner spinnerCity, spinnerCrime;
    private EditText editTextZipCode;
    private EditText editTextCrimeDescription;
    private ImageView imageCrime;
    private Bitmap selectedImageBitmap;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ReportCrimeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManageCrimes.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportCrimeFragment newInstance(String param1, String param2) {
        ReportCrimeFragment fragment = new ReportCrimeFragment();
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
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_crime, container, false);

        // Initialize views
        editTextStreetNumber = view.findViewById(R.id.editTextStreetNumber);
        editTextZipCode = view.findViewById(R.id.editTextZipCode);
        editTextCrimeDescription = view.findViewById(R.id.editTextCrimeDescription);
        spinnerCrime = view.findViewById(R.id.spinnerCrime);
        spinnerCity = view.findViewById(R.id.spinnerCity);
        imageCrime = view.findViewById(R.id.imageCrime);
        Button buttonBrowse = view.findViewById(R.id.buttonBrowse);
        Button buttonRegisterCrime = view.findViewById(R.id.buttonRegisterCrime);

        // Retrieve the array from the XML file
        String[] cityArray = getResources().getStringArray(R.array.city_array);

        // Create ArrayAdapter and set it as the adapter for the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, cityArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCity.setAdapter(adapter);

        // Retrieve the array from the XML file
        String[] crimeArray = getResources().getStringArray(R.array.crime_array);

        // Create ArrayAdapter and set it as the adapter for the Spinner
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, crimeArray);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCrime.setAdapter(adapter1);

        // Set up browse button click listener
        buttonBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });
        //Set up register button click listener
        buttonRegisterCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForm();
            }
        });

        return view;
    }

    // handle the browse image button click
    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    // handle the image selection result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            // Get the selected image URI
            Uri imageUri = data.getData();

            // Validate the image type
            if (!isImageTypeValid(requireContext(), imageUri)) {
                Toast.makeText(requireContext(), "Invalid image type. Only PNG, JPG, and JPEG images are allowed.", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                InputStream inputStream = requireActivity().getContentResolver().openInputStream(imageUri);
                selectedImageBitmap = BitmapFactory.decodeStream(inputStream);
                imageCrime.setImageBitmap(selectedImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean isImageTypeValid(Context context, Uri imageUri) {
        String mimeType = context.getContentResolver().getType(imageUri);

        // Check if the MIME type is one of the allowed image types
        return mimeType != null && mimeType.startsWith("image/") && (mimeType.endsWith("jpeg")
                || mimeType.endsWith("png") || mimeType.endsWith("jpg"));
    }

    private void registerForm() {

        String streetNumber = editTextStreetNumber.getText().toString().trim();
        String zipCode = editTextZipCode.getText().toString().trim();
        String crimeDescription = editTextCrimeDescription.getText().toString().trim();

        String selectedCrime = spinnerCrime.getSelectedItem().toString();
//        System.out.println(selectedCrime);
        if (selectedCrime.equals("Select crime")) {
            Toast.makeText(getContext(), "Please select a city", Toast.LENGTH_SHORT).show();
            return;
        }

        if (streetNumber.isEmpty()) {
            editTextStreetNumber.setError("Street details are required");
            editTextStreetNumber.requestFocus();
            return;
        }
//        else if (!isValidStreetNumber(streetNumber)) {
//            editTextStreetNumber.setError("Invalid! Enter your street number between 1 - 30");
//            editTextStreetNumber.requestFocus();
//            return;
//        }

        String selectedCity = spinnerCity.getSelectedItem().toString();
        System.out.println(selectedCity);
        if (selectedCity.equals("Select city")) {
            Toast.makeText(getContext(), "Please select a city", Toast.LENGTH_SHORT).show();
            return;
        }

        if (zipCode.isEmpty()) {
            editTextZipCode.setError("Zip code is required");
            editTextZipCode.requestFocus();
            return;
        }
        else if (!isValidZipCode(zipCode)) {
            editTextZipCode.setError("Invalid! Enter 5 digit zip code");
            editTextZipCode.requestFocus();
            return;
        }

        if (crimeDescription.isEmpty()) {
            editTextCrimeDescription.setError("Please enter crime description");
            return;
        }

        if (selectedImageBitmap == null) {
            Toast.makeText(getContext(), "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Display toast message for successful registration
        Toast.makeText(getContext(), "Crime reported successfully", Toast.LENGTH_SHORT).show();
    }

    private boolean isValidZipCode(String zipCode) {
        // - The zip code shall be a 5 digit number
        return zipCode.matches("[0-9]{5}");
    }

    private boolean isValidStreetNumber(String streetNumber) {
        if (streetNumber.matches("[0-9]+")) {
            int number = Integer.parseInt(streetNumber);
            // - The street number shall be entered through a string and converted into int
            // - The street number should be greater than 0
            // - The street number should not exceed 30
            return number >= 1 && number <= 30;
        }
        return false;
    }
}