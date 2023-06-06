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

public class SearchCrimeAdapter extends RecyclerView.Adapter<SearchCrimeAdapter.ViewHolder> {

    Crime[] crimeReportsData; // data of all crime reports
    Context context; // context of the activity in which adapter is

    public SearchCrimeAdapter(Crime[] crimeReportsData,SearchCrimeActivity activity) {
        this.crimeReportsData = crimeReportsData;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.search_crime_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Crime crimeDataList = crimeReportsData[position];

//        System.out.println(crimeDataList.getMissingPersonName()+missingPersonDataList.getUserName());

        holder.textViewCrimeType.setText(crimeDataList.getType());
        holder.textViewStreetDetails.setText(crimeDataList.getStreetDetails());
        holder.textViewCrimeDetails.setText(crimeDataList.getCrimeDetails());
        holder.textViewReportStatus.setText(crimeDataList.getStatus());

        // based on the status of report change the colour of status
        TextView statusTextView = holder.textViewReportStatus;

        String reportStatus = crimeDataList.getStatus();

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

        // setting image on card
        holder.crimeImage.setImageBitmap(crimeDataList.getCrimeImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Zip code: "+crimeDataList.getZipCode(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Crime Report Details");
                builder.setMessage("Crime type: " + crimeDataList.getType() + "\nStreet details: " + crimeDataList.getStreetDetails()+ "\nCity: " +crimeDataList.getCity()+ "\nZip code: " + crimeDataList.getZipCode()+ "\nReport status: " +crimeDataList.getStatus());
                // Add more details to the message as needed

                builder.setPositiveButton("OK", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (crimeReportsData != null) {
            return crimeReportsData.length;
        } else {
            return 0; // or any other appropriate value
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewCrimeType,textViewStreetDetails,textViewCrimeDetails,textViewReportStatus;
        ImageView crimeImage;

        Button btnDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewCrimeType = itemView.findViewById(R.id.tv2);
            textViewStreetDetails = itemView.findViewById(R.id.tv4);
            textViewCrimeDetails = itemView.findViewById(R.id.tv6);
            textViewReportStatus = itemView.findViewById(R.id.tv8);

            crimeImage = itemView.findViewById(R.id.img);

            btnDetails = (Button) itemView.findViewById(R.id.detailsBtn);
        }
    }

}

