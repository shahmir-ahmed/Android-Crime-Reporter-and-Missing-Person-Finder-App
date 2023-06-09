package com.example.crimereporterandmissingpersonfinderapp;

import android.content.Context;
import android.graphics.Color;
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

    public class SearchMissingPersonAdapter extends RecyclerView.Adapter<SearchMissingPersonAdapter.ViewHolder> {

        MissingPersonData[] missingPersonData; // data of all missing persons
        Context context; // context of the activity in which adapter is

        public SearchMissingPersonAdapter(MissingPersonData[] missingPersonData,SearchMissingPersonReportsActivity activity) {
            this.missingPersonData = missingPersonData;
            this.context = activity;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.search_missing_person_list,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final MissingPersonData missingPersonDataList = missingPersonData[position];

            System.out.println(missingPersonDataList.getMissingPersonName()+missingPersonDataList.getUserName());

            holder.textViewPersonName.setText(missingPersonDataList.getMissingPersonName());
            holder.textViewLastSeenLocation.setText(missingPersonDataList.getMissingPersonLastSeenLocation());
            holder.textViewReportDetails.setText(missingPersonDataList.getMissingPersonReportDetails());
            holder.textViewReportStatus.setText(missingPersonDataList.getMissingPersonReportStatus());

            // based on the status of report change the colour of status
            TextView statusTextView = holder.textViewReportStatus;

            String reportStatus = missingPersonDataList.getMissingPersonReportStatus();

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

            holder.missingPersonImage.setImageBitmap(missingPersonDataList.getMissingPersonImage());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Zip code: "+missingPersonDataList.getMissingPersonZipCode(), Toast.LENGTH_SHORT).show();
                }
            });

            holder.btnDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Missing Person Details");
                    builder.setMessage("Name: " + missingPersonDataList.getMissingPersonName() + "\nAge: " + missingPersonDataList.getMissingPersonAge()+ "\nGender: " +missingPersonDataList.getMissingPersonGender()+ "\nLast Seen: " + missingPersonDataList.getMissingPersonLastSeenLocation()+ "\nZip Code: " +missingPersonDataList.getMissingPersonZipCode()+ "\nDetails: " +missingPersonDataList.getMissingPersonReportDetails()+ "\nStatus: " +missingPersonDataList.getMissingPersonReportStatus()+ "\n\n\nReported By:\n"+"\nName: "+missingPersonDataList.getUserName()+"\nContact: "+missingPersonDataList.getUserContact());
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

            Button btnDetails;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewPersonName = itemView.findViewById(R.id.tvPersonName);
                textViewLastSeenLocation = itemView.findViewById(R.id.tvLastSeenLocation);
                textViewReportDetails = itemView.findViewById(R.id.tvReportDetails);
                textViewReportStatus = itemView.findViewById(R.id.tvReportStatus);
                missingPersonImage = itemView.findViewById(R.id.imgMissingPerson);
                btnDetails = (Button) itemView.findViewById(R.id.detailsBtn);
            }
        }

    }
