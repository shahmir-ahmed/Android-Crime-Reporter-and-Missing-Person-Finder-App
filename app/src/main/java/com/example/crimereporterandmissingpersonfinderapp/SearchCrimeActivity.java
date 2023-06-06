package com.example.crimereporterandmissingpersonfinderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SearchCrimeActivity extends AppCompatActivity {

    Crime[] crimeReportsData;
    SearchCrimeAdapter searchCrimeAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_crime);

        // creating recycler view object
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // retrieve all the crime reports from DB
        DBHelper dbHelper = new DBHelper(getApplicationContext());

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String columns[] = {DatabaseContract.Crimes.COLUMN_TYPE, DatabaseContract.Crimes.COLUMN_STREET_DETAILS, DatabaseContract.Crimes.COLUMN_CITY, DatabaseContract.Crimes.COLUMN_ZIPCODE, DatabaseContract.Crimes.COLUMN_CRIME_DETAILS, DatabaseContract.Crimes.COLUMN_IMAGE, DatabaseContract.Crimes.COLUMN_STATUS};

        Cursor result = db.query(DatabaseContract.Crimes.TABLE_NAME, columns, null, null, null, null, null);

        // if there are reports
        if(result.moveToFirst()) {
            // reset to intial position
            result.moveToPosition(-1);

            // get the number of rows
            int count = result.getCount();
            crimeReportsData = new Crime[count]; // Initialize the array with the appropriate size

            int i = 0;

            // while there are next cursor positions to move
            while (result.moveToNext()) {

                // getting all the data
                String crimeType = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Crimes.COLUMN_TYPE));
                String streetDetails = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Crimes.COLUMN_STREET_DETAILS));
                String city = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Crimes.COLUMN_CITY));
                String zipCode = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Crimes.COLUMN_ZIPCODE));
                String crimeDetails = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Crimes.COLUMN_CRIME_DETAILS));
                byte[] image = result.getBlob(result.getColumnIndexOrThrow(DatabaseContract.Crimes.COLUMN_IMAGE));
                String reportStatus = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Crimes.COLUMN_STATUS));

                // Convert byte array to Bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

                crimeReportsData[i] = new Crime(crimeType, streetDetails, city, zipCode, crimeDetails, bitmap, reportStatus);

                i++;
            }

        }


        if(result.getCount()>0) {
            searchCrimeAdapter = new SearchCrimeAdapter(crimeReportsData, SearchCrimeActivity.this);
            recyclerView.setAdapter(searchCrimeAdapter);

            Toast.makeText(this, "Enter zip/postal code to search for crime reports!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "No crime reports found!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(SearchCrimeActivity.this, "Please enter code!", Toast.LENGTH_SHORT).show();
                }
                else{

                    // casting the zip code to integer to check if zip code entered is an integer or not
                    // try-catch will catch exception if the zip code entered is not an integer
                    try{

                        int intZipCode = Integer.parseInt(query);

                        // if the zip code is an integer then this code will run
//                        Toast.makeText(SearchCrimeActivity.this, "Code: "+zipCode, Toast.LENGTH_SHORT).show();
                        // retrieve all the missing person reports from DB
                        DBHelper dbHelper = new DBHelper(getApplicationContext());

                        SQLiteDatabase db = dbHelper.getReadableDatabase();

                        String columns[] = {DatabaseContract.Crimes.COLUMN_TYPE, DatabaseContract.Crimes.COLUMN_STREET_DETAILS, DatabaseContract.Crimes.COLUMN_CITY, DatabaseContract.Crimes.COLUMN_ZIPCODE, DatabaseContract.Crimes.COLUMN_CRIME_DETAILS, DatabaseContract.Crimes.COLUMN_IMAGE, DatabaseContract.Crimes.COLUMN_STATUS};

                        String whereClause = DatabaseContract.Crimes.COLUMN_ZIPCODE+"=?";

                        String whereByArgs[] = {query};

                        Cursor result = db.query(DatabaseContract.Crimes.TABLE_NAME, columns, whereClause, whereByArgs, null, null, null);

                        // if there are reports
                        if(result.moveToFirst()) {
                            // reset to intial position
                            result.moveToPosition(-1);

                            // get the number of rows
                            int count = result.getCount();
                            crimeReportsData = new Crime[count]; // Initialize the array with the appropriate size

                            int i = 0;

                            // while there are next cursor positions to move
                            while (result.moveToNext()) {

                                // getting all the data
                                String crimeType = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Crimes.COLUMN_TYPE));
                                String streetDetails = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Crimes.COLUMN_STREET_DETAILS));
                                String city = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Crimes.COLUMN_CITY));
                                String zipCode = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Crimes.COLUMN_ZIPCODE));
                                String crimeDetails = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Crimes.COLUMN_CRIME_DETAILS));
                                byte[] image = result.getBlob(result.getColumnIndexOrThrow(DatabaseContract.Crimes.COLUMN_IMAGE));
                                String reportStatus = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Crimes.COLUMN_STATUS));

                                // Convert byte array to Bitmap
                                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

                                crimeReportsData[i] = new Crime(crimeType, streetDetails, city, zipCode, crimeDetails, bitmap, reportStatus);

                                i++;
                            }

                        }


                        if(result.getCount()>0) {
                            searchCrimeAdapter = new SearchCrimeAdapter(crimeReportsData, SearchCrimeActivity.this);
                            recyclerView.setAdapter(searchCrimeAdapter);

                            Toast.makeText(SearchCrimeActivity.this, "Reports found for: " + query + " zip code!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(SearchCrimeActivity.this, "No reports found for: "+query+" zip code!", Toast.LENGTH_SHORT).show();
                        }

                    }
                    catch(Exception e){
                        Toast.makeText(SearchCrimeActivity.this, "Zip/postal code should contain only numbers!", Toast.LENGTH_SHORT).show();
                    }
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                // changed and the query text is empty
                if(s.length()==0){
                    // retrieve all the crime reports from DB
                    DBHelper dbHelper = new DBHelper(getApplicationContext());

                    SQLiteDatabase db = dbHelper.getReadableDatabase();

                    String columns[] = {DatabaseContract.Crimes.COLUMN_TYPE, DatabaseContract.Crimes.COLUMN_STREET_DETAILS, DatabaseContract.Crimes.COLUMN_CITY, DatabaseContract.Crimes.COLUMN_ZIPCODE, DatabaseContract.Crimes.COLUMN_CRIME_DETAILS, DatabaseContract.Crimes.COLUMN_IMAGE, DatabaseContract.Crimes.COLUMN_STATUS};

                    Cursor result = db.query(DatabaseContract.Crimes.TABLE_NAME, columns, null, null, null, null, null);

                    // if there are reports
                    if(result.moveToFirst()) {
                        // reset to intial position
                        result.moveToPosition(-1);

                        // get the number of rows
                        int count = result.getCount();
                        crimeReportsData = new Crime[count]; // Initialize the array with the appropriate size

                        int i = 0;

                        // while there are next cursor positions to move
                        while (result.moveToNext()) {

                            // getting all the data
                            String crimeType = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Crimes.COLUMN_TYPE));
                            String streetDetails = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Crimes.COLUMN_STREET_DETAILS));
                            String city = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Crimes.COLUMN_CITY));
                            String zipCode = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Crimes.COLUMN_ZIPCODE));
                            String crimeDetails = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Crimes.COLUMN_CRIME_DETAILS));
                            byte[] image = result.getBlob(result.getColumnIndexOrThrow(DatabaseContract.Crimes.COLUMN_IMAGE));
                            String reportStatus = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Crimes.COLUMN_STATUS));

                            // Convert byte array to Bitmap
                            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

                            crimeReportsData[i] = new Crime(crimeType, streetDetails, city, zipCode, crimeDetails, bitmap, reportStatus);

                            i++;
                        }

                    }


                    if(result.getCount()>0) {
                        searchCrimeAdapter = new SearchCrimeAdapter(crimeReportsData, SearchCrimeActivity.this);
                        recyclerView.setAdapter(searchCrimeAdapter);

                    }
                    else{
                        Toast.makeText(SearchCrimeActivity.this, "No reports found!", Toast.LENGTH_SHORT).show();
                    }
                }


                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}