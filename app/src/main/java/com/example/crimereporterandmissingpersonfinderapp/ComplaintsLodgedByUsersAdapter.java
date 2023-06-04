package com.example.crimereporterandmissingpersonfinderapp;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ComplaintsLodgedByUsersAdapter  extends RecyclerView.Adapter<ComplaintsLodgedByUsersAdapter.ViewHolder> {
    private List<Complaint> complaintList;
    private Context context;
    private String newStatus;

    public ComplaintsLodgedByUsersAdapter(List<Complaint> complaintList, Context context) {
        this.complaintList = complaintList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaints_lodged_by_users, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Complaint complaint = complaintList.get(position);

        // Bind the complaint data to the views in the item layout
        holder.nameTextView.setText(complaint.getUserName());

        holder.cityTextView.setText(complaint.getSubject());

        holder.subjectTextView.setText(complaint.getSubject());

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

        holder.complaintDetailsTextView.setText(complaint.getComplaintDetails());


        // Handle view, update and delete button clicks
        // view button
        holder.viewButton.setOnClickListener(v -> {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
            builder.setTitle("Complaint Details");
            builder.setMessage("Name: " + complaint.getUserName() + "\nSubject: " + complaint.getSubject()+ "\nComplaint: " +complaint.getComplaintDetails()+ "\nAddress: " + complaint.getAddress()+ "\nCity: " +complaint.getCity()+ "\nPin code: " +complaint.getZipCode()   + "\nPhone: " +complaint.getUserContact()+ "\nEmail: "+complaint.getUserCNIC());
            // Add more details to the message as needed

            builder.setPositiveButton("OK", null);
            androidx.appcompat.app.AlertDialog dialog = builder.create();
            dialog.show();
        });

        // update button
        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update_complaint_status, null);
                builder.setView(dialogView);

                RadioGroup radioGroupStatus = dialogView.findViewById(R.id.radioGroupStatus);

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

                        // Update the status in the local database
                        DBHelper databaseHelper = new DBHelper(context);

                        int updatedRows = databaseHelper.updateComplaintStatus(complaint.getId(), newStatus);

                        Toast.makeText(context, "Complaint status updated successfully! ", Toast.LENGTH_SHORT).show();

                        // reflect changes in the view
                        complaint.setStatus(newStatus); // updating status of record

                        // Step 2: Notify the adapter
                        notifyItemChanged(position);
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

    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    // ViewHolder class for the RecyclerView item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subjectTextView,complaintDetailsTextView,statusTextView,nameTextView, cityTextView;
        Button viewButton;
        Button updateButton;
        Button deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv19);
            cityTextView = itemView.findViewById(R.id.tv);
            subjectTextView = itemView.findViewById(R.id.tv2);
            complaintDetailsTextView = itemView.findViewById(R.id.tv4);
            statusTextView = itemView.findViewById(R.id.tv6);

            viewButton = itemView.findViewById(R.id.viewBtn);
            updateButton = itemView.findViewById(R.id.updateBtn);
            deleteButton = itemView.findViewById(R.id.deleteBtn);
        }
    }}
