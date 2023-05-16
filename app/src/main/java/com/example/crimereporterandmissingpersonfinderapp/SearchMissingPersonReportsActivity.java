package com.example.crimereporterandmissingpersonfinderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SearchMissingPersonReportsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_crime);
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