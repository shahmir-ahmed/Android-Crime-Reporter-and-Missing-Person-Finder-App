package com.example.crimereporterandmissingpersonfinderapp;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class ComplaintsAdapter  extends RecyclerView.Adapter<ComplaintsAdapter.ComplaintViewHolder> {
    private List<Complaint> complaintList;
    private Context context;

    public ComplaintsAdapter(List<Complaint> complaintList, Context context) {
        this.complaintList = complaintList;
        this.context = context;
    }

    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_complaints, parent, false);
        return new ComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, int position) {
        Complaint complaint = complaintList.get(position);

        // Bind the complaint data to the views in the item layout
        holder.subjectTextView.setText(complaint.getSubject());
        holder.complaintDetailsTextView.setText(complaint.getComplaintDetails());
        holder.statusTextView.setText(complaint.getStatus());

        // based on the status of report change the colour of status
        TextView statusTextView = holder.statusTextView;

        String reportStatus = complaint.getStatus();

        int color;
        switch (reportStatus) {
            case "Submitted":
                color = Color.BLUE;
                break;
            case "Seen":
                color = Color.GREEN;
                break;
            case "Processing":
                color = Color.YELLOW;
                break;
            case "Completed":
                color = Color.RED;
                break;
            case "Rejected":
                color = Color.GRAY;
                break;
            default:
                color = Color.BLACK;
                break;
        }

        statusTextView.setTextColor(color);

        // Getting the status because based on the status displaying view, update, delete button or more details button
        String status = complaint.getStatus();

        // if status is submitted then hide the more details button
        if(status.equals("Submitted")){
            holder.moreDetailsBtn.setVisibility(View.GONE);
        }
        // if status is other then submitted i.e. submitted, seen, processing, completed or rejected then hide the view, update and delete buttons
        else{
            holder.viewButton.setVisibility(View.GONE);
            holder.updateButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
        }

//        holder.personNameTextView.setText(complaint.getPersonName());

        // Set the initial visibility of additional details views to GONE
//        holder.cityTextView.setVisibility(View.GONE);
//        holder.statusTextView.setVisibility(View.GONE);
//        holder.complaintDetailsTextView.setVisibility(View.GONE);

        // Handle view, update and delete button clicks
        // Handle view button click to expand the view and show additional details
        holder.viewButton.setOnClickListener(v -> {
//            if (holder.cityTextView.getVisibility() == View.GONE) {
//                // Show additional details
//                holder.cityTextView.setText(complaint.getCity());
//                holder.statusTextView.setText(complaint.getStatus());
//                holder.complaintDetailsTextView.setText(complaint.getComplaintDetails());
//
//                holder.cityTextView.setVisibility(View.VISIBLE);
//                holder.statusTextView.setVisibility(View.VISIBLE);
//                holder.complaintDetailsTextView.setVisibility(View.VISIBLE);
//            } else {
//                // Hide additional details
//                holder.cityTextView.setVisibility(View.GONE);
//                holder.statusTextView.setVisibility(View.GONE);
//                holder.complaintDetailsTextView.setVisibility(View.GONE);
//            }

            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
            builder.setTitle("Complaint Details");
            builder.setMessage("City: " + complaint.getCity() + "\nAddress: " + complaint.getAddress()+ "\nZip code: " +complaint.getZipCode()+ "\nSubject: " + complaint.getSubject()+ "\nComplaint: " +complaint.getComplaintDetails()+"\nStatus: " +complaint.getStatus());
            // Add more details to the message as needed

            builder.setPositiveButton("OK", null);
            androidx.appcompat.app.AlertDialog dialog = builder.create();
            dialog.show();


        });

        // more details button (same details as shown in view button clicking appearing dialog)
        holder.moreDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
                builder.setTitle("Complaint Details");
                builder.setMessage("City: " + complaint.getCity() + "\nAddress: " + complaint.getAddress()+ "\nZip code: " +complaint.getZipCode()+ "\nSubject: " + complaint.getSubject()+ "\nComplaint: " +complaint.getComplaintDetails()+"\nStatus: " +complaint.getStatus());
                // Add more details to the message as needed

                builder.setPositiveButton("OK", null);
                androidx.appcompat.app.AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle update operations
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
//                builder.setTitle("Missing Person Details");

                // Create a custom layout for the AlertDialog
                View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update_complaint_details, null);
                builder.setView(dialogView);

                // Get references to the views in the custom layout
                // Creating bridge
                EditText etAddress = (EditText) dialogView.findViewById(R.id.editTextAddress);
                final Spinner spinnerCity = (Spinner) dialogView.findViewById(R.id.spinnerCity);

                // getting cities to set by adapter on spinner
                String[] cityArray = ((Activity) context).getResources().getStringArray(R.array.city_array);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(((Activity) context).getApplicationContext(), android.R.layout.simple_list_item_1, cityArray);

                spinnerCity.setAdapter(adapter);

                EditText etZipCode = (EditText) dialogView.findViewById(R.id.editTextPincode);
                EditText etSubject = (EditText) dialogView.findViewById(R.id.editTextSubject);
                EditText etComplaint = (EditText) dialogView.findViewById(R.id.editTextComplaint);

                // setting the report data on form
                etAddress.setText(complaint.getAddress());

                // check the city
                String city = complaint.getCity();

                String cities[] = ((Activity) context).getResources().getStringArray(R.array.city_array);

                // Set the current city as the selected item in the spinner
                int selectedCityIndex = Arrays.asList(cities).indexOf(city);
                spinnerCity.setSelection(selectedCityIndex);

                etZipCode.setText(complaint.getZipCode());

                etSubject.setText(complaint.getSubject());
                etZipCode.setText(complaint.getZipCode());
                etComplaint.setText(complaint.getComplaintDetails());


                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // getting form details
                        String newAddress, newCity, newZipCode, newSubject, newComplaint;

                        newAddress = etAddress.getText().toString();

                        newCity = spinnerCity.getSelectedItem().toString();

                        newZipCode = etZipCode.getText().toString();

                        newSubject = etSubject.getText().toString();

                        newComplaint = etComplaint.getText().toString();

                        if (TextUtils.isEmpty(newAddress)) {
//                            etAddress.setError("Please enter address");
                            Toast.makeText(((Activity) context).getApplicationContext(), "Please enter address", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (newCity.equals("Select city")) {
                            Toast.makeText(((Activity) context).getApplicationContext(), "Please select city", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if (!isValidZipCode(newZipCode)) {
                            Toast.makeText(((Activity) context).getApplicationContext(), "Invalid! Enter 5 digit zip code", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (TextUtils.isEmpty(newZipCode)) {
//                            etZipCode.setError("Please enter zip code");
                            Toast.makeText(((Activity) context).getApplicationContext(), "Please enter zip code", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if (!TextUtils.isDigitsOnly(newZipCode)) {
//                            etZipCode.setError("Invalid Postal Code");
                            Toast.makeText(((Activity) context).getApplicationContext(), "Invalid Postal Code", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (TextUtils.isEmpty(newSubject)) {
//                            etSubject.setError("Please enter your Subject");
                            Toast.makeText(((Activity) context).getApplicationContext(), "Please enter your Subject", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (TextUtils.isEmpty(newComplaint)) {
//                            etComplaint.setError("Please enter your Complaint");
                            Toast.makeText(((Activity) context).getApplicationContext(), "Please enter your Complaint", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {
                                // updating object
                                complaint.setAddress(newAddress);
                                complaint.setCity(newCity);
                                complaint.setZipCode(newZipCode);
                                complaint.setSubject(newSubject);
                                complaint.setComplaintDetails(newComplaint);

                                DBHelper dbHelper = new DBHelper(context);

                                // passing the object to update the details in DB
                                int updatedRows = dbHelper.updateComplaint(complaint);

                                // check the returned updated rows
                                if (updatedRows != 1) {
                                    Toast.makeText(context, "Report not updated!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, "Report updated successfully!", Toast.LENGTH_LONG).show();

                                    // reflect changes in the view

                                    // Step 2: Notify the adapter
                                    notifyItemChanged(position);
                                }
                            }
                    }
                });


                builder.setNegativeButton("Cancel",  null);

                androidx.appcompat.app.AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Confirm Delete");

                builder.setMessage("Are you sure you want to delete this complaint?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = holder.getAdapterPosition();

                        Complaint complaint = complaintList.get(position);

                        int complaintId = complaint.getId();

                        // Call the deleteComplaint method from DatabaseHelper
                        DBHelper databaseHelper = new DBHelper(context);

                        databaseHelper.deleteComplaint(complaintId);

                        // Remove the complaint from the list
                        complaintList.remove(position);
                        notifyItemRemoved(position);

                        Toast.makeText(context, "Complaint deleted successfully!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private boolean isValidZipCode(String zipCode) {
        // - The zip code shall be a 5 digit number
        return zipCode.matches("[0-9]{5}");
    }
    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    // ViewHolder class for the RecyclerView item
    public static class ComplaintViewHolder extends RecyclerView.ViewHolder {
        TextView subjectTextView;
//        TextView personNameTextView;
        TextView complaintDetailsTextView;
        TextView statusTextView;
//        TextView cityTextView;
        Button viewButton, updateButton, deleteButton, moreDetailsBtn;

        public ComplaintViewHolder(View itemView) {
            super(itemView);
            subjectTextView = itemView.findViewById(R.id.tv2);
//            personNameTextView = itemView.findViewById(R.id.tv19);
            complaintDetailsTextView = itemView.findViewById(R.id.tv4);
            statusTextView = itemView.findViewById(R.id.tv6);
//            cityTextView = itemView.findViewById(R.id.tv);

            viewButton = itemView.findViewById(R.id.viewBtn);
            updateButton = itemView.findViewById(R.id.updateBtn);
            deleteButton = itemView.findViewById(R.id.deleteBtn);

            moreDetailsBtn = itemView.findViewById(R.id.moreDetailsBtn);
        }
    }


}

