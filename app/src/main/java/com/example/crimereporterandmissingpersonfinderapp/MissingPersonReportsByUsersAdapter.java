package com.example.crimereporterandmissingpersonfinderapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MissingPersonReportsByUsersAdapter extends RecyclerView.Adapter<MissingPersonReportsByUsersAdapter.ViewHolder> {

    MissingPersonData[] missingPersonData; // the missing person data array (array of objects) based on which the adapter binds the individual v=cards views with the data in the object at each position of array
    Context context; // the activity context in which the adapter is

    private static final int RESULT_LOAD_IMG = 0;

    // update dialog box image
    ImageView imageViewPersonImage;

    public MissingPersonReportsByUsersAdapter(MissingPersonData[] missingPersonData,AdminDashboardActivity activity) {
        this.missingPersonData = missingPersonData;
        this.context = activity;
    }

    public MissingPersonReportsByUsersAdapter(){

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.missing_person_reports_by_users,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final MissingPersonData missingPersonDataList = missingPersonData[position];

        holder.textViewPersonName.setText(missingPersonDataList.getMissingPersonName());
        holder.textViewReportStatus.setText(missingPersonDataList.getMissingPersonReportStatus());
        holder.missingPersonImage.setImageBitmap(missingPersonDataList.getMissingPersonImage());

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

        // update button
        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Missing Person Details");

                builder.setMessage("Name: " + missingPersonDataList.getMissingPersonName() + "\nAge: " + missingPersonDataList.getMissingPersonAge() + "\nGender: " + missingPersonDataList.getMissingPersonGender() + "\nLast Seen: " + missingPersonDataList.getMissingPersonLastSeenLocation() + "\nZip Code: " + missingPersonDataList.getMissingPersonZipCode() + "\nDetails: " + missingPersonDataList.getMissingPersonReportDetails() + "\nStatus: ");
                // Add more details to the message as needed

                // spinner

                builder.setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // getting status from spinner
                        String status=null;

                        // save form data in database
                        DBHelper dbHelper = new DBHelper(context);

                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        // data preparing to send
                        ContentValues values = new ContentValues();

                        // putting key value pairs in object
                        values.put(DatabaseContract.MissingPersons.COL_REPORT_STATUS, status);

                        // getting the report id from list to update the report
                        String reportId = missingPersonDataList.getMissingPersonId();

                        String whereClause = DatabaseContract.MissingPersons._ID + "=?";

                        String whereArgs[] = {String.valueOf(reportId)};

                        // saving form data in DB
                        long updatedRows = db.update(DatabaseContract.MissingPersons.TABLE_NAME, values, whereClause, whereArgs);

                        // check the returned updated rows
                        if (updatedRows != 1) {
                            Toast.makeText(context, "Report status not updated!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Report status updated successfully!", Toast.LENGTH_LONG).show();

                        }
                    }
                });
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


    // Used to start gallery activity
    public void getImageFromGallery(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        ((Activity) context).startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    // handles the gallery result from activity
    public void handleGalleryResult(Bitmap image) {
        Toast.makeText(context, "here", Toast.LENGTH_SHORT).show();
        imageViewPersonImage.setImageBitmap(image);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewPersonName;
        TextView textViewReportStatus;
        ImageView missingPersonImage;
        Button viewBtn, updateBtn, deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewPersonName = itemView.findViewById(R.id.tvPersonName);
            textViewReportStatus = itemView.findViewById(R.id.tvStatus);
            missingPersonImage = itemView.findViewById(R.id.imgMissingPerson);

            viewBtn = (Button) itemView.findViewById(R.id.viewBtn);
            updateBtn = (Button) itemView.findViewById(R.id.updateBtn);
            deleteBtn = (Button) itemView.findViewById(R.id.deleteBtn);
        }
    }
}




