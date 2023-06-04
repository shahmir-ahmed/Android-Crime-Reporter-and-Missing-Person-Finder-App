package com.example.crimereporterandmissingpersonfinderapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComplaintsLodgedByUsersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComplaintsLodgedByUsersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private RecyclerView recyclerView;
    private ComplaintsLodgedByUsersAdapter complaintsAdapter;
    private List<Complaint> complaintList;
    private DBHelper dbHelper;

    public ComplaintsLodgedByUsersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComplaintsLodgedByUsersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComplaintsLodgedByUsersFragment newInstance(String param1, String param2) {
        ComplaintsLodgedByUsersFragment fragment = new ComplaintsLodgedByUsersFragment();
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

        dbHelper = new DBHelper(requireContext());

        complaintList = dbHelper.getAllComplaints();

        complaintsAdapter = new ComplaintsLodgedByUsersAdapter(complaintList, requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complaints_lodged_by_users, container, false);

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