package com.example.crimereporterandmissingpersonfinderapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LodgeComplaintFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LodgeComplaintFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //
    private EditText editTextAddress;
    private EditText editTextCity;
    private EditText editTextPincode;
    private EditText editTextSubject;
    private EditText editTextComplaint;
    private Button buttonRegisterComplaint;


    public LodgeComplaintFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LodgeComplaintFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LodgeComplaintFragment newInstance(String param1, String param2) {
        LodgeComplaintFragment fragment = new LodgeComplaintFragment();
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
        return inflater.inflate(R.layout.fragment_lodge_complaint, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //
        editTextAddress = (EditText) getView().findViewById(R.id.editTextAddress);
        editTextCity = (EditText) getView().findViewById(R.id.editTextCity);
        editTextPincode = (EditText) getView().findViewById(R.id.editTextPincode);
        editTextSubject = (EditText) getView().findViewById(R.id.editTextSubject);
        editTextComplaint = (EditText) getView().findViewById(R.id.editTextComplaint);
        buttonRegisterComplaint = (Button) getView().findViewById(R.id.buttonRegisterComplaint);

        buttonRegisterComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerComplaint();
            }
        });
    }

    //
    private void registerComplaint() {
        String address = editTextAddress.getText().toString().trim();
        String city = editTextCity.getText().toString().trim();
        String pincode = editTextPincode.getText().toString().trim();
        String subject = editTextSubject.getText().toString().trim();
        String complaint = editTextComplaint.getText().toString().trim();

        if (TextUtils.isEmpty(address)) {
            editTextAddress.setError("Please enter your Address");
            return;
        }

        if (TextUtils.isEmpty(city)) {
            editTextCity.setError("Please enter your City");
            return;
        }

        if (TextUtils.isEmpty(pincode)) {
            editTextPincode.setError("Please enter your Postal Code");
            return;
        } else if (!TextUtils.isDigitsOnly(pincode)) {
            editTextPincode.setError("Invalid Postal Code");
            return;
        }

        if (TextUtils.isEmpty(subject)) {
            editTextSubject.setError("Please enter your Subject");
            return;
        }

        if (TextUtils.isEmpty(complaint)) {
            editTextComplaint.setError("Please enter your Complaint");
            return;
        }

        // Display success message
        Toast.makeText(getActivity(), "Complaint registered successfully!", Toast.LENGTH_SHORT).show();
    }
}