package com.example.crimereporterandmissingpersonfinderapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrimeReportsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrimeReportsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private CrimeReportsAdapter crimeAdapter;
    private List<Crime> crimeList;
    private DBHelper dbHelper;
    SharedPreferences sharedPreferences;


    public CrimeReportsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrimeReportsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrimeReportsFragment newInstance(String param1, String param2) {
        CrimeReportsFragment fragment = new CrimeReportsFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crime_reports, container, false);

        // getting th user id from shared preferences to retrieve the user crimes from table only
        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("idKey", "");

        System.out.println("user id:"+userId);

        dbHelper = new DBHelper(getActivity().getApplicationContext());

        crimeList = dbHelper.getUserCrimes(userId);

        if(crimeList.stream().count()==0){
            Toast.makeText(getActivity().getApplicationContext(), "No crime reports!", Toast.LENGTH_SHORT).show();
        }else{
            crimeAdapter = new CrimeReportsAdapter(crimeList, requireContext());
        }

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(crimeAdapter);

        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

}