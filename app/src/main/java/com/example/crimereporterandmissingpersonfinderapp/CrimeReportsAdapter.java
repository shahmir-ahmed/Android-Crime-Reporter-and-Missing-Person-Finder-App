package com.example.crimereporterandmissingpersonfinderapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class CrimeReportsAdapter  extends RecyclerView.Adapter<CrimeReportsAdapter.ViewHolder> {
    private List<Crime> crimeList;
    private Context context;
//    private String newStatus;

    public CrimeReportsAdapter(List<Crime> crimeList, Context context) {
        this.crimeList = crimeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_crime_reports, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Crime crime = crimeList.get(position);

        // Bind the complaint data to the views in the item layout
        holder.crimeTypeTextView.setText(crime.getType());
        holder.crimeStreetTextView.setText(crime.getStreetDetails());
        holder.crimeDetailsTextView.setText(crime.getCrimeDetails());

        holder.crimeImage.setImageBitmap(crime.getCrimeImage());

        holder.statusTextView.setText(crime.getStatus());

        // based on the status of report change the colour of status
        TextView statusTextView = holder.statusTextView;

        String reportStatus = crime.getStatus();

        int color;
        switch (reportStatus) {
            case "Submitted":
                color = Color.BLUE;
                break;
            case "Seen":
                color = Color.GREEN;
                break;
            case "Processing":
                color = Color.rgb(246,190,0);
                break;
            case "Completed":
                color = Color.DKGRAY;
                break;
            case "Rejected":
                color = Color.RED;
                break;
            default:
                color = Color.BLACK;
                break;
        }

        statusTextView.setTextColor(color);

        // Based on the status displaying view, update, delete button or more details button

        // if status is submitted then hide the more details button
        if(reportStatus.equals("Submitted")){
            holder.moreDetailsBtn.setVisibility(View.GONE);
        }
        // if status is other then submitted i.e. submitted, seen, processing, completed or rejected then hide the view, update and delete buttons
        else{
            holder.viewButton.setVisibility(View.GONE);
            holder.updateButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
        }

        // more details button (same details as shown in view button clicking appearing dialog)
        holder.moreDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
                builder.setTitle("Crime Report Details");
                builder.setMessage("Type: " + crime.getType() + "\nStreet Details: " + crime.getCity()+ "\nZip code: " +crime.getZipCode()+ "\nDetails: " + crime.getCrimeDetails()+"\nStatus: " +crime.getStatus());
                // Add more details to the message as needed

                builder.setPositiveButton("OK", null);
                androidx.appcompat.app.AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        // Handle view, update and delete button clicks
        // view button on card
        holder.viewButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Crime Report Details");
            builder.setMessage("Type: " + crime.getType() + "\nStreet: " + crime.getStreetDetails()+"\nCity: "+crime.getCity()+ "\nZip code: " +crime.getZipCode()+ "\nDetails: " + crime.getCrimeDetails()+"\nStatus: " +crime.getStatus());
            // Add more details to the message as needed

            builder.setPositiveButton("OK", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        // update button on card
        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                // Create a custom layout for the AlertDialog
                View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update_crime_report_details, null);
                builder.setView(dialogView);

                // Get references to the views in the custom layout
                Spinner spinnerCrime = dialogView.findViewById(R.id.spinnerCrime);
                EditText etStreet = dialogView.findViewById(R.id.editTextStreetNumber);
                Spinner spinnerCity = dialogView.findViewById(R.id.spinnerCity);
                EditText etZipCode = dialogView.findViewById(R.id.editTextZipCode);
                EditText etCrimeDetails = dialogView.findViewById(R.id.editTextZipCode);
                ImageView imageViewCrimeImage = dialogView.findViewById(R.id.imageCrime);

                // Set the initial form data
                // Set the crime type of the report as the selected item in the spinner
                spinnerCrime.setSelection(getCrimeIndex(crime.getType()));
                etStreet.setText(crime.getStreetDetails());
                spinnerCity.setSelection(getCityIndex(crime.getCity()));
                etZipCode.setText(crime.getZipCode());
                etCrimeDetails.setText(crime.getCrimeDetails());
                imageViewCrimeImage.setImageBitmap(crime.getCrimeImage());

                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get the updated form data
                        String updatedType = spinnerCrime.getSelectedItem().toString();
                        String updatedStreet = etStreet.getText().toString();
                        String updatedCity = spinnerCity.getSelectedItem().toString();
                        String updatedZipCode = etZipCode.getText().toString();
                        String updatedCrimeDetails = etCrimeDetails.getText().toString();

                        // Perform the necessary database update operations
                        DBHelper dbHelper = new DBHelper(context);
                        boolean success = dbHelper.updateCrime(crime.getId(),updatedType, updatedStreet, updatedCity, updatedZipCode, updatedCrimeDetails);

                        if (success) {
                            Toast.makeText(context, "Report updated successfully!", Toast.LENGTH_SHORT).show();
                            // Update the crime object with the new data
                            crime.setStreetDetails(updatedStreet);
                            crime.setCity(updatedCity);
                            crime.setZipCode(updatedZipCode);
                            crime.setZipCode(updatedCrimeDetails);
//                            crime.setCrimeImage(updatedCrimeImage);
                            // Notify the adapter that the data has changed
                            notifyItemChanged(position);

                            Toast.makeText(context, "Crime report updated successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Failed to update form!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();
            }
        });


        // delete button on card
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm Delete");
                builder.setMessage("Are you sure you want to delete this crime report?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = holder.getAdapterPosition();
                        Crime crime = crimeList.get(position);
                        int crimeId = crime.getId();

                        // Call the deleteComplaint method from DatabaseHelper
                        DBHelper databaseHelper = new DBHelper(context);
                        databaseHelper.deleteCrime(crimeId);

                        // Remove the complaint from the list
                        crimeList.remove(position);
                        notifyItemRemoved(position);

                        Toast.makeText(context, "Crime report deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private int getCityIndex(String city) {
        // Assuming you have a list of cities to populate the Spinner
        List<String> citiesList = getCitiesList();

        // Iterate over the list to find the index of the selected city
        for (int i = 0; i < citiesList.size(); i++) {
            if (citiesList.get(i).equals(city)) {
                return i;
            }
        }

        return 0; // Return 0 as the default index if the city is not found
    }

    private List<String> getCitiesList() {
        String[] citiesArray = context.getResources().getStringArray(R.array.city_array);
        return Arrays.asList(citiesArray);
    }

    private int getCrimeIndex(String crime) {
        // Assuming you have a list of crimes to populate the Spinner
        List<String> crimesList = getCrimesList();

        // Iterate over the list to find the index of the selected crime type
        for (int i = 0; i < crimesList.size(); i++) {
            if (crimesList.get(i).equals(crime)) {
                return i;
            }
        }

        return 0; // Return 0 as the default index if the crime is not found
    }

    private List<String> getCrimesList() {
        String[] crimesArray = context.getResources().getStringArray(R.array.crime_array);
        return Arrays.asList(crimesArray);
    }

    @Override
    public int getItemCount() {
        return crimeList.size();
    }

    // ViewHolder class for the RecyclerView item
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView crimeTypeTextView;
        TextView crimeStreetTextView;
        TextView crimeDetailsTextView;
        TextView statusTextView;
        ImageView crimeImage;
        Button viewButton, updateButton, deleteButton, moreDetailsBtn;

        public ViewHolder(View itemView) {
            super(itemView);

             crimeTypeTextView = itemView.findViewById(R.id.tv2);
             crimeStreetTextView = itemView.findViewById(R.id.tv4);
             crimeDetailsTextView = itemView.findViewById(R.id.tv6);
             statusTextView = itemView.findViewById(R.id.tv8);

             crimeImage = itemView.findViewById(R.id.imgCrime);

            viewButton = itemView.findViewById(R.id.viewBtn);
            updateButton = itemView.findViewById(R.id.updateBtn);
            deleteButton = itemView.findViewById(R.id.deleteBtn);

            moreDetailsBtn = itemView.findViewById(R.id.moreDetailsBtn);
        }
    }}

