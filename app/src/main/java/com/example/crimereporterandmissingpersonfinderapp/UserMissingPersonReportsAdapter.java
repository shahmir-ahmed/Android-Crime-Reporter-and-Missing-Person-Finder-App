package com.example.crimereporterandmissingpersonfinderapp;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.ArrayList;
import java.util.Arrays;

public class UserMissingPersonReportsAdapter extends RecyclerView.Adapter<UserMissingPersonReportsAdapter.ViewHolder> {

    MissingPersonData[] missingPersonData; // the missing person data array (array of objects) based on which the adapter binds the individual v=cards views with the data in the object at each position of array
    Context context; // the activity context in which the adapter is

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

        // view button
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

        // delete button
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm deletion?");

                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Deletion from the database based on the missing person report id
                        int missingPersonId = Integer.parseInt(missingPersonDataList.getMissingPersonId());

                        // db helper class object
                        DBHelper dbHelper = new DBHelper(context.getApplicationContext());

                        // sqlite object
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        // where clause
                        String whereClause = DatabaseContract.MissingPersons._ID+"=?";

                        // where args
                        String whereArgs[] = {String.valueOf(missingPersonId)};

                        int deletedRecord = db.delete(DatabaseContract.MissingPersons.TABLE_NAME, whereClause, whereArgs);

                        // if a single record is deleted
                        if(deletedRecord==1){
                            Toast.makeText(context, "Report deleted successfully!", Toast.LENGTH_SHORT).show();
                        }

                        // Remove the item from the data source
//                        Retrieve the object that needs to be removed from the data source array.
                        MissingPersonData data = missingPersonData[position];

//                        Create a new ArrayList by converting the data source array.
                        ArrayList<MissingPersonData> dataList = new ArrayList<>(Arrays.asList(missingPersonData));

//                        Remove the object from the ArrayList.
                        dataList.remove(data);

//                        Convert the ArrayList back to an MissingPersonData array.
                        missingPersonData = dataList.toArray(new MissingPersonData[dataList.size()]);

                        // Notify the adapter that the item is removed
                        notifyItemRemoved(position);

//                        to update the positions of the remaining items in the adapter
                        notifyItemRangeChanged(position, getItemCount());
                    }
                });

                builder.setNegativeButton("Cancel", null);
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
