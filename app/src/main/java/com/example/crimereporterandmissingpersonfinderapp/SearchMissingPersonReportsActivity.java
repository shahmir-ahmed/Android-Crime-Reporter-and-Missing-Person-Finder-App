package com.example.crimereporterandmissingpersonfinderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.Toast;

public class SearchMissingPersonReportsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_missing_person_reports);

        // creating recycler view object
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MissingPersonData[] myMovieData = new MissingPersonData[]{
                new MissingPersonData("Asim", "27", "Female", "234", "Cantt Fawara Chowk", "Seen","Missing Person from last 2 days",  R.drawable.person_1),
                new MissingPersonData("B", "37", "Male", "2324", "Cantt Fawara Chowk", "Resolved","Missing Person from last 3 days",  R.drawable.person_2),
                new MissingPersonData("C", "47", "Female", "23324", "Cantt Fawara Chowk", "Submitted","Missing Person from last 4 days",  R.drawable.person_3),
                new MissingPersonData("D", "57", "Male", "23434", "Cantt Fawara Chowk", "Rejected","Missing Person from last 5 days",  R.drawable.person_4),
                new MissingPersonData("ER", "67", "Female", "23234", "Cantt Fawara Chowk",  "Submitted", "Missing Person from last 6 days",R.drawable.person_5),
                new MissingPersonData("F", "77", "Male", "5646", "Cantt Fawara Chowk", "Resolved","Missing Person from last 7 days",  R.drawable.person),
        };

        MissingPersonAdapter myMovieAdapter = new MissingPersonAdapter(myMovieData,SearchMissingPersonReportsActivity.this);
        recyclerView.setAdapter(myMovieAdapter);
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