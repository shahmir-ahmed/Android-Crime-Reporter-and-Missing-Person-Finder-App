package com.example.crimereporterandmissingpersonfinderapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrimeReportsByUsersAdapter extends RecyclerView.Adapter<CrimeReportsByUsersAdapter.ViewHolder> {
    private List<Crime> crimeList;
    private Context context;
    private String newStatus;

    public CrimeReportsByUsersAdapter(List<Crime> crimeList, Context context) {
        this.crimeList = crimeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crime_reports_by_users, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Crime crime = crimeList.get(position);

        // Bind the complaint data to the views in the item layout
        holder.nameTextView.setText(crime.getUserName());
        holder.cityTextView.setText(crime.getCity());
        holder.crimeDescriptionTextView.setText(crime.getCrimeDetails());
        holder.statusTextView.setText(crime.getStatus());
        holder.crimeImageView.setImageBitmap(crime.getCrimeImage());

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

        // Handle view, update and delete button clicks
        holder.viewButton.setOnClickListener(v -> {
            // Show additional details
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
            builder.setTitle("Crime Report Details");
            builder.setMessage("Type: " + crime.getType() + "\nStreet: " + crime.getStreetDetails()+ "\nCity: " +crime.getCity()+ "\nZip code: " + crime.getZipCode()+"\nDetails: " +crime.getCrimeDetails()+ "\nStatus: " +crime.getStatus()+ "\n\n\nReported By:\n"+"\nName: "+crime.getUserName()+"\nCNIC: "+crime.getUserCNIC()+"\nContact: "+crime.getUserContact()+"\nEmail: "+crime.getUserEmail());
            // Add more details to the message as needed

            builder.setPositiveButton("OK", null);
            androidx.appcompat.app.AlertDialog dialog = builder.create();
            dialog.show();

        });

        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update_status, null);
                builder.setView(dialogView);

                RadioGroup radioGroupStatus = dialogView.findViewById(R.id.radioGroupStatus);

                // getting individual radio buttons
                RadioButton rbSubmitted = (RadioButton) dialogView.findViewById(R.id.radioButtonSubmitted);
                RadioButton rbSeen = (RadioButton) dialogView.findViewById(R.id.radioButtonSeen);
                RadioButton rbProcessing = (RadioButton) dialogView.findViewById(R.id.radioButtonProcessing);
                RadioButton rbCompleted = (RadioButton) dialogView.findViewById(R.id.radioButtonCompleted);
                RadioButton rbRejected = (RadioButton) dialogView.findViewById(R.id.radioButtonRejected);

                // based on status checking radio buttons
                switch (reportStatus) {
                    case "Submitted":
                        rbSubmitted.setChecked(true);
                        break;
                    case "Seen":
                        rbSeen.setChecked(true);
                        break;
                    case "Processing":
                        rbProcessing.setChecked(true);
                        break;
                    case "Completed":
                        rbCompleted.setChecked(true);
                        break;
                    case "Rejected":
                        rbRejected.setChecked(true);
                        break;
                }

                // update button in dialog
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int selectedRadioButtonId = radioGroupStatus.getCheckedRadioButtonId();

                        String newStatus = "";

                        switch (selectedRadioButtonId) {
                            case R.id.radioButtonSubmitted:
                                newStatus = "Submitted";
                                break;
                            case R.id.radioButtonSeen:
                                newStatus = "Seen";
                                break;
                            case R.id.radioButtonProcessing:
                                newStatus = "Processing";
                                break;
                            case R.id.radioButtonCompleted:
                                newStatus = "Completed";
                                break;
                            case R.id.radioButtonRejected:
                                newStatus = "Rejected";
                                break;
                        }

                        try {

                            // Update the status in the local database
                            DBHelper databaseHelper = new DBHelper(context);
                            databaseHelper.updateCrimeStatus(crime.getId(), newStatus);

                            // reflect changes in the view
                            crime.setStatus(newStatus); // updating status of record

                            // Step 2: Notify the adapter
                            notifyItemChanged(position);

                            Toast.makeText(context, "Report status updated!", Toast.LENGTH_SHORT).show();

                        }catch (Exception e){
                            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Cancel", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

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

                        Toast.makeText(context, "Crime report deleted successfully!", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("No", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return crimeList.size();
    }

    // ViewHolder class for the RecyclerView item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView cityTextView;
        TextView crimeDescriptionTextView;
        TextView statusTextView;
        ImageView crimeImageView;

        Button viewButton;
        Button updateButton;
        Button deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.tv2);
            cityTextView = itemView.findViewById(R.id.tv4);
            crimeDescriptionTextView = itemView.findViewById(R.id.tv6);
            statusTextView = itemView.findViewById(R.id.tv8);
            crimeImageView = itemView.findViewById(R.id.crimeImage);

            viewButton = itemView.findViewById(R.id.viewBtn);
            updateButton = itemView.findViewById(R.id.updateBtn);
            deleteButton = itemView.findViewById(R.id.deleteBtn);
        }
    }}
