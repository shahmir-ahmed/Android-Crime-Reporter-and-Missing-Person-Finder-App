package com.example.crimereporterandmissingpersonfinderapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ComplaintsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ComplaintsAdapter complaintsAdapter;
    private List<Complaint> complaintList;
    private DBHelper dbHelper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ComplaintsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ComplaintsFragment newInstance(String param1, String param2) {
        ComplaintsFragment fragment = new ComplaintsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(requireContext());

        complaintList = dbHelper.getUserComplaints();

        complaintsAdapter = new ComplaintsAdapter(complaintList, requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complaints, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(complaintsAdapter);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

}