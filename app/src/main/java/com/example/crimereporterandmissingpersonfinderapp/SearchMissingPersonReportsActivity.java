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
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

public class SearchMissingPersonReportsActivity extends AppCompatActivity {

    MissingPersonData[] missingPersonData;

    SearchMissingPersonAdapter searchMissingPersonAdapter;

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

        String table = DatabaseContract.Users.TABLE_NAME+" u"+","+ DatabaseContract.MissingPersons.TABLE_NAME+" m";

        String columns[] = {"m."+DatabaseContract.MissingPersons.COL_NAME+" AS missing_person_name", "m."+DatabaseContract.MissingPersons.COL_AGE, "m."+DatabaseContract.MissingPersons.COL_GENDER, "m."+DatabaseContract.MissingPersons.COL_LAST_SEEN, "m."+DatabaseContract.MissingPersons.COL_ZIPCODE, "m."+DatabaseContract.MissingPersons.COL_REPORT_DETAILS, "m."+DatabaseContract.MissingPersons.COL_PERSON_IMAGE, "m."+DatabaseContract.MissingPersons.COL_REPORT_STATUS, "u."+DatabaseContract.Users.COL_NAME+" AS user_name", "u."+DatabaseContract.Users.COL_CONTACT};

        String whereClause = "m."+DatabaseContract.MissingPersons.COL_USER_ID+"="+"u."+DatabaseContract.Users._ID;

        Cursor result = db.query(table, columns, whereClause, null, null, null, null);

        // if there are reports
        if(result.moveToFirst()) {
            // reset to intial position
            result.moveToPosition(-1);

            // get the number of rows
            int count = result.getCount();
            missingPersonData = new MissingPersonData[count]; // Initialize the array with the appropriate size

            int i = 0;

            // while there are next cursor positions to move
            while (result.moveToNext()) {

                // getting all the data
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

                String userContact = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Users.COL_CONTACT));
                            System.out.println(userName+userContact);
                // Convert byte array to Bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

                missingPersonData[i] = new MissingPersonData(userName, userContact, name, age, gender, zipCode, lastSeen, reportStatus, reportDetails, bitmap);

                i++;
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


        if(result.getCount()>0) {
            searchMissingPersonAdapter = new SearchMissingPersonAdapter(missingPersonData, SearchMissingPersonReportsActivity.this);
            recyclerView.setAdapter(searchMissingPersonAdapter);

            Toast.makeText(this, "Enter zip/postal code to search for missing persons!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "No reports found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // inflating the menu on activity toolbar
        getMenuInflater().inflate(R.menu.menu, menu);

        // getting the menu item from the xml file
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type zip/postal code here");

        // Set the keyboard type
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                // getting the query to search
                String query = searchView.getQuery().toString();

                // Validating the query
                // If the query is empty
                if(query.trim().isEmpty()){
                    Toast.makeText(SearchMissingPersonReportsActivity.this, "Please enter code!", Toast.LENGTH_SHORT).show();
                }
                else if(query.length() != 5){
                    Toast.makeText(SearchMissingPersonReportsActivity.this, "Zip code must be of 5 digits!", Toast.LENGTH_SHORT).show();
                }
                else{

                    // casting the zip code to integer to check if zip code entered is an integer or not
                    // try-catch will catch exception if the zip code entered is not an integer
                    try{

//                        System.out.println(query);

                        // to check that zip code is in integer only
                        int searchZipCodeInt = Integer.parseInt(query);

                        // correct zipcode
                        // retrieve all the missing person reports from DB for the zipcode
                        DBHelper dbHelper = new DBHelper(getApplicationContext());

                        SQLiteDatabase db = dbHelper.getReadableDatabase();

                        String table = DatabaseContract.Users.TABLE_NAME+" u"+","+ DatabaseContract.MissingPersons.TABLE_NAME+" m";

                        String columns[] = {"m."+DatabaseContract.MissingPersons.COL_NAME+" AS missing_person_name", "m."+DatabaseContract.MissingPersons.COL_AGE, "m."+DatabaseContract.MissingPersons.COL_GENDER, "m."+DatabaseContract.MissingPersons.COL_LAST_SEEN, "m."+DatabaseContract.MissingPersons.COL_ZIPCODE, "m."+DatabaseContract.MissingPersons.COL_REPORT_DETAILS, "m."+DatabaseContract.MissingPersons.COL_PERSON_IMAGE, "m."+DatabaseContract.MissingPersons.COL_REPORT_STATUS, "u."+DatabaseContract.Users.COL_NAME+" AS user_name", "u."+DatabaseContract.Users.COL_CONTACT};

                        String whereClause = "m."+DatabaseContract.MissingPersons.COL_USER_ID+"="+"u."+DatabaseContract.Users._ID+" AND "+"m."+DatabaseContract.MissingPersons.COL_ZIPCODE+"=?";

                        String whereArgs[] = {query};

                        Cursor result = db.query(table, columns, whereClause, whereArgs, null, null, null);


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

                                String userContact = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Users.COL_CONTACT));

                                // Convert byte array to Bitmap
                                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

                                missingPersonData[i] = new MissingPersonData(userName, userContact, name, age, gender, zipCode, lastSeen, reportStatus, reportDetails, bitmap);

                                i++;
                            }

                        }

                        if(result.getCount()>0) {
                            searchMissingPersonAdapter = new SearchMissingPersonAdapter(missingPersonData, SearchMissingPersonReportsActivity.this);
                            recyclerView.setAdapter(searchMissingPersonAdapter);

                            Toast.makeText(SearchMissingPersonReportsActivity.this, "Reports found for: " + query + " zip code!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(SearchMissingPersonReportsActivity.this, "No reports found for: "+query+" zip code!", Toast.LENGTH_SHORT).show();
                        }

                        // if the zip code is an integer then this code will run
//                        Toast.makeText(SearchMissingPersonReportsActivity.this, "Code: "+searchZipCode, Toast.LENGTH_SHORT).show();

                    }
                    catch(Exception e){
                        Toast.makeText(SearchMissingPersonReportsActivity.this, "Zip/postal code should contain only numbers!", Toast.LENGTH_SHORT).show();
                    }
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                // changed and the query text is empty
                if(s.length()==0){

                    // retrieve all the missing person reports from DB
                    DBHelper dbHelper = new DBHelper(getApplicationContext());

                    SQLiteDatabase db = dbHelper.getReadableDatabase();

                    String table = DatabaseContract.Users.TABLE_NAME+" u"+","+ DatabaseContract.MissingPersons.TABLE_NAME+" m";

                    String columns[] = {"m."+DatabaseContract.MissingPersons.COL_NAME+" AS missing_person_name", "m."+DatabaseContract.MissingPersons.COL_AGE, "m."+DatabaseContract.MissingPersons.COL_GENDER, "m."+DatabaseContract.MissingPersons.COL_LAST_SEEN, "m."+DatabaseContract.MissingPersons.COL_ZIPCODE, "m."+DatabaseContract.MissingPersons.COL_REPORT_DETAILS, "m."+DatabaseContract.MissingPersons.COL_PERSON_IMAGE, "m."+DatabaseContract.MissingPersons.COL_REPORT_STATUS, "u."+DatabaseContract.Users.COL_NAME+" AS user_name", "u."+DatabaseContract.Users.COL_CONTACT};

                    String whereClause = "m."+DatabaseContract.MissingPersons.COL_USER_ID+"="+"u."+DatabaseContract.Users._ID;

                    Cursor result = db.query(table, columns, whereClause, null, null, null, null);

                    // if there are reports
                    if(result.moveToFirst()) {
                        // reset to intial position
                        result.moveToPosition(-1);

                        // get the number of rows
                        int count = result.getCount();
                        missingPersonData = new MissingPersonData[count]; // Initialize the array with the appropriate size

                        int i = 0;

                        // while there are next cursor positions to move
                        while (result.moveToNext()) {

                            // getting all the data
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

                            String userContact = result.getString(result.getColumnIndexOrThrow(DatabaseContract.Users.COL_CONTACT));
//                            System.out.println(userName+userContact);
                            // Convert byte array to Bitmap
                            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

                            missingPersonData[i] = new MissingPersonData(userName, userContact, name, age, gender, zipCode, lastSeen, reportStatus, reportDetails, bitmap);

                            i++;
                        }
                    }

                    if(result.getCount()>0) {
                        searchMissingPersonAdapter = new SearchMissingPersonAdapter(missingPersonData, SearchMissingPersonReportsActivity.this);
                        recyclerView.setAdapter(searchMissingPersonAdapter);
                    }
                    else{
                        Toast.makeText(SearchMissingPersonReportsActivity.this, "No reports found!", Toast.LENGTH_SHORT).show();
                    }

                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}