package com.example.crimereporterandmissingpersonfinderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.Toast;

public class SearchMissingPersonReportsActivity extends AppCompatActivity {

    MissingPersonData[] missingPersonData;

    MissingPersonAdapter missingPersonAdapter;

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_missing_person_reports);

        // creating recycler view object
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // retrieve all the missing person reports from DB
        DBHelper dbHelper = new DBHelper(getApplicationContext());

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String columns[] = {DatabaseContract.MissingPersons.COL_NAME, DatabaseContract.MissingPersons.COL_AGE, DatabaseContract.MissingPersons.COL_GENDER, DatabaseContract.MissingPersons.COL_LAST_SEEN, DatabaseContract.MissingPersons.COL_ZIPCODE, DatabaseContract.MissingPersons.COL_REPORT_DETAILS, DatabaseContract.MissingPersons.COL_PERSON_IMAGE, DatabaseContract.MissingPersons.COL_REPORT_STATUS};

        Cursor result = db.query(DatabaseContract.MissingPersons.TABLE_NAME, columns, null, null, null, null, null);

        // if there are reports
        if(result.moveToFirst()){
            result.moveToPosition(-1);

            int count = result.getCount();
            missingPersonData = new MissingPersonData[count]; // Initialize the array with the appropriate size

            // while there are next cursor positions to move
            while(result.moveToNext()){
                // loop from 0 till the size of result-1
                for(int i = 0; i<result.getCount(); i++){

                    // getting all the data
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
                    int imageInt = bitmap.hashCode();

                    missingPersonData[i] = new MissingPersonData(name, age, gender, zipCode, lastSeen, reportStatus, reportDetails, imageInt);
                }
            }

        }

//        MissingPersonData[] myMovieData = new MissingPersonData[]{
//                new MissingPersonData("Asim", "27", "Female", "234", "Cantt Fawara Chowk", "Seen","Missing Person from last 2 days",  R.drawable.person_1),
//                new MissingPersonData("B", "37", "Male", "2324", "Cantt Fawara Chowk", "Resolved","Missing Person from last 3 days",  R.drawable.person_2),
//                new MissingPersonData("C", "47", "Female", "23324", "Cantt Fawara Chowk", "Submitted","Missing Person from last 4 days",  R.drawable.person_3),
//                new MissingPersonData("D", "57", "Male", "23434", "Cantt Fawara Chowk", "Rejected","Missing Person from last 5 days",  R.drawable.person_4),
//                new MissingPersonData("ER", "67", "Female", "23234", "Cantt Fawara Chowk",  "Submitted", "Missing Person from last 6 days",R.drawable.person_5),
//                new MissingPersonData("F", "77", "Male", "5646", "Cantt Fawara Chowk", "Resolved","Missing Person from last 7 days",  R.drawable.person),
//        };


        missingPersonAdapter = new MissingPersonAdapter(missingPersonData,SearchMissingPersonReportsActivity.this);
        recyclerView.setAdapter(missingPersonAdapter);

        if(missingPersonAdapter.getItemCount()==0){
            Toast.makeText(this, "No reports found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // inflating the menu on activity tooLbar
        getMenuInflater().inflate(R.menu.menu, menu);

        // getting the menu item from the xml file
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type zip/postal code here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                // getting the query to search
                String query = searchView.getQuery().toString();

                // Validating the query
                // If the query is empty
                if(query.isEmpty()){
                    Toast.makeText(SearchMissingPersonReportsActivity.this, "Please enter code!", Toast.LENGTH_SHORT).show();
                }
                else{

                    // casting the zip code to integer to check if zip code entered is an integer or not
                    // try-catch will catch exception if the zip code entered is not an integer
                    try{

                        int zipCode = Integer.parseInt(query);

                        // correct zipcode


                        // if the zip code is an integer then this code will run
                        Toast.makeText(SearchMissingPersonReportsActivity.this, "Code: "+zipCode, Toast.LENGTH_SHORT).show();
                    }
                    catch(Exception e){
                        Toast.makeText(SearchMissingPersonReportsActivity.this, "Zip/postal code should contain only numbers!", Toast.LENGTH_SHORT).show();
                    }
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}