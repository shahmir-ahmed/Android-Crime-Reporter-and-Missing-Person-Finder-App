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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MissingPersonReportsByUsersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MissingPersonReportsByUsersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    MissingPersonData[] missingPersonData; // all reports

    MissingPersonReportsByUsersAdapter missingPersonReportsByUsersAdapter;

    RecyclerView recyclerView; // recycler view for cards

    public MissingPersonReportsByUsersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MissingPersonReportsByUsers.
     */
    // TODO: Rename and change types and number of parameters
    public static MissingPersonReportsByUsersFragment newInstance(String param1, String param2) {
        MissingPersonReportsByUsersFragment fragment = new MissingPersonReportsByUsersFragment();
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
        return inflater.inflate(R.layout.fragment_missing_person_reports_by_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // creating recycler view object
        recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        try {
            // retrieve all the missing person reports from DB reported by the users
            DBHelper dbHelper = new DBHelper(getActivity().getApplicationContext());

            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String table = DatabaseContract.Users.TABLE_NAME + " u" + "," + DatabaseContract.MissingPersons.TABLE_NAME + " m";

            String columns[] = {"m." + DatabaseContract.MissingPersons._ID, " m." + DatabaseContract.MissingPersons.COL_NAME + " AS missing_person_name", "m." + DatabaseContract.MissingPersons.COL_AGE, "m." + DatabaseContract.MissingPersons.COL_GENDER, "m." + DatabaseContract.MissingPersons.COL_LAST_SEEN, "m." + DatabaseContract.MissingPersons.COL_ZIPCODE, "m." + DatabaseContract.MissingPersons.COL_REPORT_DETAILS, "m." + DatabaseContract.MissingPersons.COL_PERSON_IMAGE, "m." + DatabaseContract.MissingPersons.COL_REPORT_STATUS, "u." + DatabaseContract.Users.COL_NAME + " AS user_name", "u." + DatabaseContract.Users.COL_GENDER, "u." + DatabaseContract.Users.COL_CNIC, "u." + DatabaseContract.Users.COL_CONTACT};

            String whereClause = "m." + DatabaseContract.MissingPersons.COL_USER_ID + "=" + "u." + DatabaseContract.Users._ID;

            Cursor result = db.query(table, columns, whereClause, null, null, null, null);

            // if there are reports
            if (result.moveToFirst()) {
                // reset to intial position
                result.moveToPosition(-1);

                // get the number of rows
                int count = result.getCount();
                missingPersonData = new MissingPersonData[count]; // Initialize the array with the appropriate size

                int i = 0;

                // while there are next cursor positions to move
                while (result.moveToNext()) {

                    // getting all the data
                    String reportId = String.valueOf(result.getInt(result.getColumnIndexOrThrow(DatabaseContract.MissingPersons._ID)));
                    String name = result.getString(result.getColumnIndexOrThrow("missing_person_name"));
                    String age = result.getString(result.getColumnIndexOrThrow(DatabaseContract.MissingPersons.COL_AGE));
                    String gender = result.getString(result.getColumnIndexOrThrow(DatabaseContract.MissingPersons.COL_GENDER));
                    String zipCode = result.getString(result.getColumnIndexOrThrow(DatabaseContract.MissingPersons.COL_ZIPCODE));
                    String lastSeen = result.getString(result.getColumnIndexOrThrow(DatabaseContract.MissingPersons.COL_LAST_SEEN));
                    String reportDetails = result.getString(result.getColumnIndexOrThrow(DatabaseContract.MissingPersons.COL_REPORT_DETAILS));
                    String reportStatus = result.getString(result.getColumnIndexOrThrow(DatabaseContract.MissingPersons.COL_REPORT_STATUS));
                    byte[] image = result.getBlob(result.getColumnIndexOrThrow(DatabaseContract.MissingPersons.COL_PERSON_IMAGE));

                    // user data
                    String userName = result.getString(result.getColumnIndexOrThrow("user_name"));

                    String userGender = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Users.COL_GENDER));

                    String userCNIC = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Users.COL_CNIC));

                    String userContact = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Users.COL_CONTACT));

//                System.out.println(userName+userContact);
                    // Convert byte array to Bitmap
                    Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

                    missingPersonData[i] = new MissingPersonData(reportId, userName, userGender, userCNIC, userContact, name, age, gender, zipCode, lastSeen, reportStatus, reportDetails, bitmap);

                    i++;
                }

            }

            // if there are reports made by user the attach adapter with view
            if (result.getCount() > 0) {
                missingPersonReportsByUsersAdapter = new MissingPersonReportsByUsersAdapter(missingPersonData, (AdminDashboardActivity) getActivity());
                recyclerView.setAdapter(missingPersonReportsByUsersAdapter);
            } else {
//                Toast.makeText(getActivity().getApplicationContext(), "No missing person reports found!", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "Error occured!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}