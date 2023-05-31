package com.example.crimereporterandmissingpersonfinderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

    public class MissingPersonAdapter extends RecyclerView.Adapter<MissingPersonAdapter.ViewHolder> {

        MissingPersonData[] missingPersonData;
        Context context;

        public MissingPersonAdapter(MissingPersonData[] missingPersonData,SearchMissingPersonReportsActivity activity) {
            this.missingPersonData = missingPersonData;
            this.context = activity;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.missing_person_list,parent,false);
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
            holder.missingPersonImage.setImageResource(missingPersonDataList.getMissingPersonImage());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, missingPersonDataList.getMissingPersonName(), Toast.LENGTH_SHORT).show();
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

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewPersonName = itemView.findViewById(R.id.tvPersonName);
                textViewLastSeenLocation = itemView.findViewById(R.id.tvLastSeenLocation);
                textViewReportDetails = itemView.findViewById(R.id.tvReportDetails);
                textViewReportStatus = itemView.findViewById(R.id.tvReportStatus);
                missingPersonImage = itemView.findViewById(R.id.imgMissingPerson);
            }
        }

    }
