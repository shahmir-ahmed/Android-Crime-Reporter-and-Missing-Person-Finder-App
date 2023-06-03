package com.example.crimereporterandmissingpersonfinderapp;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
//        holder.personNameTextView.setText(complaint.getPersonName());

        // Set the initial visibility of additional details views to GONE
        holder.cityTextView.setVisibility(View.GONE);
        holder.statusTextView.setVisibility(View.GONE);
        holder.complaintDetailsTextView.setVisibility(View.GONE);

        // Handle view, update and delete button clicks
        // Handle view button click to expand the view and show additional details
        holder.viewButton.setOnClickListener(v -> {
            if (holder.cityTextView.getVisibility() == View.GONE) {
                // Show additional details
                holder.cityTextView.setText(complaint.getCity());
                holder.statusTextView.setText(complaint.getStatus());
                holder.complaintDetailsTextView.setText(complaint.getComplaintDetails());

                holder.cityTextView.setVisibility(View.VISIBLE);
                holder.statusTextView.setVisibility(View.VISIBLE);
                holder.complaintDetailsTextView.setVisibility(View.VISIBLE);
            } else {
                // Hide additional details
                holder.cityTextView.setVisibility(View.GONE);
                holder.statusTextView.setVisibility(View.GONE);
                holder.complaintDetailsTextView.setVisibility(View.GONE);
            }
        });

        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle update operations

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
    public static class ComplaintViewHolder extends RecyclerView.ViewHolder {
        TextView subjectTextView;
//        TextView personNameTextView;
        TextView complaintDetailsTextView;
        TextView statusTextView;
        TextView cityTextView;
        Button viewButton;
        Button updateButton;
        Button deleteButton;

        public ComplaintViewHolder(View itemView) {
            super(itemView);
            subjectTextView = itemView.findViewById(R.id.tv2);
//            personNameTextView = itemView.findViewById(R.id.tv19);
            complaintDetailsTextView = itemView.findViewById(R.id.tv4);
            statusTextView = itemView.findViewById(R.id.tv6);
            cityTextView = itemView.findViewById(R.id.tv);

            viewButton = itemView.findViewById(R.id.viewBtn);
            updateButton = itemView.findViewById(R.id.updateBtn);
            deleteButton = itemView.findViewById(R.id.deleteBtn);
        }
    }

}

