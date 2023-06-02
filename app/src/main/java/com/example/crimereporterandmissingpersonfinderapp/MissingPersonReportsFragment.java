package com.example.crimereporterandmissingpersonfinderapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MissingPersonReportsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MissingPersonReportsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    MissingPersonData[] missingPersonData;

    UserMissingPersonReportsAdapter userMissingPersonReportsAdapter;

    RecyclerView recyclerView;

    // shared preferences for user session
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String userIdKey = "idKey";
    SharedPreferences sharedpreferences;

    public MissingPersonReportsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MissingPersonReportsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MissingPersonReportsFragment newInstance(String param1, String param2) {
        MissingPersonReportsFragment fragment = new MissingPersonReportsFragment();
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
        return inflater.inflate(R.layout.fragment_missing_person_reports, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // creating recycler view object
        recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));


        // intializing shared preferences
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        int userId = Integer.parseInt(sharedpreferences.getString(userIdKey, ""));


        // retrieve all the missing person reports from DB reported by the user
        DBHelper dbHelper = new DBHelper(getActivity().getApplicationContext());

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String columns[] = {DatabaseContract.MissingPersons._ID, DatabaseContract.MissingPersons.COL_NAME, DatabaseContract.MissingPersons.COL_AGE, DatabaseContract.MissingPersons.COL_GENDER, DatabaseContract.MissingPersons.COL_LAST_SEEN, DatabaseContract.MissingPersons.COL_ZIPCODE, DatabaseContract.MissingPersons.COL_REPORT_DETAILS, DatabaseContract.MissingPersons.COL_PERSON_IMAGE, DatabaseContract.MissingPersons.COL_REPORT_STATUS};

        String whereClause = DatabaseContract.MissingPersons.COL_USER_ID+"=?";

        String whereArgs[] = {String.valueOf(userId)};

        Cursor result = db.query(DatabaseContract.MissingPersons.TABLE_NAME, columns, whereClause, whereArgs, null, null, null);

        // if there are reports
        if(result.moveToFirst()){
            // reset to initial position
            result.moveToPosition(-1);

            // get the number of rows
            int count = result.getCount();
            missingPersonData = new MissingPersonData[count]; // Initialize the array with the appropriate size

            int i = 0;

            // while there are next cursor positions to move
            while(result.moveToNext()){

                // getting all the data
                String id = result.getString(result.getColumnIndexOrThrow(DatabaseContract.MissingPersons._ID));
                String name = result.getString(result.getColumnIndexOrThrow(DatabaseContract.MissingPersons.COL_NAME));
                String age = result.getString(result.getColumnIndexOrThrow(DatabaseContract.MissingPersons.COL_AGE));
                String gender = result.getString(result.getColumnIndexOrThrow(DatabaseContract.MissingPersons.COL_GENDER));
                String zipCode = result.getString(result.getColumnIndexOrThrow(DatabaseContract.MissingPersons.COL_ZIPCODE));
                String lastSeen = result.getString(result.getColumnIndexOrThrow(DatabaseContract.MissingPersons.COL_LAST_SEEN));
                String reportDetails = result.getString(result.getColumnIndexOrThrow(DatabaseContract.MissingPersons.COL_REPORT_DETAILS));
                String reportStatus = result.getString(result.getColumnIndexOrThrow(DatabaseContract.MissingPersons.COL_REPORT_STATUS));
                byte[] image = result.getBlob(result.getColumnIndexOrThrow(DatabaseContract.MissingPersons.COL_PERSON_IMAGE));

                // Convert byte array to Bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

                // Convert Bitmap to integer representation
//                    int imageInt = bitmap.getGenerationId();

                missingPersonData[i] = new MissingPersonData(id, name, age, gender, zipCode, lastSeen, reportStatus, reportDetails, bitmap);

                i++;
            }

        }

        // if there are reports made by user the attach adapter with view
        if(result.getCount()>0) {
            userMissingPersonReportsAdapter = new UserMissingPersonReportsAdapter(missingPersonData, (UserDashboardActivity) getActivity());
            recyclerView.setAdapter(userMissingPersonReportsAdapter);
        }
        else{
            Toast.makeText(getActivity().getApplicationContext(), "No reports found!", Toast.LENGTH_SHORT).show();
        }
    }
}