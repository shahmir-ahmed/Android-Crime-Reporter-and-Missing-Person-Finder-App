package com.example.crimereporterandmissingpersonfinderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class UserMissingPersonReportsAdapter extends RecyclerView.Adapter<UserMissingPersonReportsAdapter.ViewHolder> {

    MissingPersonData[] missingPersonData;
    Context context;

    public UserMissingPersonReportsAdapter(MissingPersonData[] missingPersonData,UserDashboardActivity activity) {
        this.missingPersonData = missingPersonData;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_missing_person_reports,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MissingPersonData missingPersonDataList = missingPersonData[position];
        holder.textViewPersonName.setText(missingPersonDataList.getMissingPersonName());
        holder.textViewLastSeenLocation.setText(missingPersonDataList.getMissingPersonLastSeenLocation());
        holder.textViewReportDetails.setText(missingPersonDataList.getMissingPersonReportDetails());
        holder.textViewReportStatus.setText(missingPersonDataList.getMissingPersonReportStatus());
        holder.missingPersonImage.setImageBitmap(missingPersonDataList.getMissingPersonImage());

        // Getting the status because based on the status displaying view, update, delete button or more details button

        String status = missingPersonDataList.getMissingPersonReportStatus();

        // if status is submitted then hide the more details button
        if(status.equals("Submitted")){
            holder.moreDetailsBtn.setVisibility(View.GONE);

//            ViewGroup parent = (ViewGroup) holder.moreDetailsBtn.getParent();
//            parent.removeView(holder.moreDetailsBtn);
        }
        // if status is other then submitted i.e. submitted, seen, processing, completed or rejected then hide the view, update and delete buttons
        else{
            holder.viewBtn.setVisibility(View.GONE);
            holder.updateBtn.setVisibility(View.GONE);
            holder.deleteBtn.setVisibility(View.GONE);
        }
        holder.viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Missing Person Details");
                builder.setMessage("Name: " + missingPersonDataList.getMissingPersonName() + "\nAge: " + missingPersonDataList.getMissingPersonAge()+ "\nGender: " +missingPersonDataList.getMissingPersonGender()+ "\nLast Seen: " + missingPersonDataList.getMissingPersonLastSeenLocation()+ "\nZip Code: " +missingPersonDataList.getMissingPersonZipCode()+ "\nDetails: " +missingPersonDataList.getMissingPersonReportDetails()+ "\nStatus: " +missingPersonDataList.getMissingPersonReportStatus());
                // Add more details to the message as needed

                builder.setPositiveButton("OK", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (missingPersonData != null) {
            return missingPersonData.length;
        } else {
            return 0; // or any other appropriate value
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewPersonName;
        TextView textViewLastSeenLocation;
        TextView textViewReportDetails;
        TextView textViewReportStatus;
        ImageView missingPersonImage;

        Button viewBtn, updateBtn, deleteBtn, moreDetailsBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPersonName = itemView.findViewById(R.id.tvPersonName);
            textViewLastSeenLocation = itemView.findViewById(R.id.tvLastSeenLocation);
            textViewReportDetails = itemView.findViewById(R.id.tvReportDetails);
            textViewReportStatus = itemView.findViewById(R.id.tvReportStatus);
            missingPersonImage = itemView.findViewById(R.id.imgMissingPerson);

            viewBtn = (Button) itemView.findViewById(R.id.viewBtn);
            updateBtn = (Button) itemView.findViewById(R.id.updateBtn);
            deleteBtn = (Button) itemView.findViewById(R.id.deleteBtn);

            moreDetailsBtn = (Button) itemView.findViewById(R.id.moreDetailsBtn);
        }
    }

}
