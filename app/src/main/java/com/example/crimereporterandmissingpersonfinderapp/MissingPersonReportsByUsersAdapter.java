package com.example.crimereporterandmissingpersonfinderapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

        // based on the status of report change the colour of status
        TextView statusTextView = holder.textViewReportStatus;

        String reportStatus = missingPersonDataList.getMissingPersonReportStatus();

        int color;
        switch (reportStatus) {
            case "Submitted":
                color = Color.BLUE;
                break;
            case "Seen":
                color = Color.MAGENTA;
                break;
            case "Processing":
                color = Color.GRAY;
                break;
            case "Completed":
                color = Color.GREEN;
                break;
            case "Rejected":
                color = Color.RED;
                break;
            default:
                color = Color.BLACK;
                break;
        }

        statusTextView.setTextColor(color);

        holder.missingPersonImage.setImageBitmap(missingPersonDataList.getMissingPersonImage());

        // view button
        holder.viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Missing Person Details");
                builder.setMessage("Name: " + missingPersonDataList.getMissingPersonName() + "\nAge: " + missingPersonDataList.getMissingPersonAge()+ "\nGender: " +missingPersonDataList.getMissingPersonGender()+ "\nLast Seen: " + missingPersonDataList.getMissingPersonLastSeenLocation()+ "\nZip Code: " +missingPersonDataList.getMissingPersonZipCode()+ "\nDetails: " +missingPersonDataList.getMissingPersonReportDetails()+ "\nStatus: " +missingPersonDataList.getMissingPersonReportStatus()+ "\n\n\nReported By:\n"+"\nName: "+missingPersonDataList.getUserName()+"\nGender: "+missingPersonDataList.getUserGender()+"\nCNIC: "+missingPersonDataList.getUserCNIC()+"\nContact: "+missingPersonDataList.getUserContact());
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
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Update Report Status");
//
//                // Get the report and user details from your data source
//                String name = missingPersonDataList.getMissingPersonName();
//                String age = missingPersonDataList.getMissingPersonAge();
//                String gender = missingPersonDataList.getMissingPersonGender();
//                String lastSeen = missingPersonDataList.getMissingPersonLastSeenLocation();
//                String details = missingPersonDataList.getMissingPersonReportDetails();
//                String status = missingPersonDataList.getMissingPersonReportStatus();
//                String userName = missingPersonDataList.getUserName();
//                String userGender = missingPersonDataList.getUserGender();
//                String userCNIC = missingPersonDataList.getUserCNIC();
//                String userContact = missingPersonDataList.getUserContact();
//
//                // Create an array of status options for the spinner
//                String[] statusOptions = {"Submitted", "Seen", "Processing", "Completed", "Rejected"};
//
//                // Create a SpannableString for the message with formatted gender text
//                SpannableString message = new SpannableString("Person's name: " + name + "\nAge: " + age + "\nGender: "+gender+"\nLast Seen: "+lastSeen+"\nDetails: "+details+"Status: "+"\n\nSubmitted By: "+"\nName: "+userName+"\nGender: "+userGender+"\nCNIC: "+userCNIC+"\nContact: "+userContact);
//
//                // Create a spinner and set its adapter
//                Spinner spinnerStatus = new Spinner(context);
//                ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, statusOptions);
//                statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinnerStatus.setAdapter(statusAdapter);
//
//                // Set the current status of the report as the selected item in the spinner
//                int selectedStatusIndex = Arrays.asList(statusOptions).indexOf(status);
//                spinnerStatus.setSelection(selectedStatusIndex);
//
//                // Create a LinearLayout to hold the text and spinner
//                LinearLayout layout = new LinearLayout(context);
//                layout.setOrientation(LinearLayout.HORIZONTAL);
//                layout.setGravity(Gravity.CENTER_VERTICAL);
//
//                // Create a TextView for the gender text
//                TextView textViewGender = new TextView(context);
//                textViewGender.setText(message);
//                layout.addView(textViewGender);
//
//                // Add some spacing between the gender text and spinner
//                int spacing = (int) (context.getResources().getDisplayMetrics().density * 8); // adjust spacing as needed
//                layout.addView(spinnerStatus, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL));
//                layout.setPadding(spacing, 0, 0, 0);
//
//
//                // Set the custom view as the message of the dialog box
//                builder.setView(layout);


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Update Report Status");

                // Get the report and user details from your data source
                String name = missingPersonDataList.getMissingPersonName();
                String age = missingPersonDataList.getMissingPersonAge();
                String gender = missingPersonDataList.getMissingPersonGender();
                String lastSeen = missingPersonDataList.getMissingPersonLastSeenLocation();
                String details = missingPersonDataList.getMissingPersonReportDetails();
                String status = missingPersonDataList.getMissingPersonReportStatus();
                String userName = missingPersonDataList.getUserName();
                String userGender = missingPersonDataList.getUserGender();
                String userCNIC = missingPersonDataList.getUserCNIC();
                String userContact = missingPersonDataList.getUserContact();

                // Create an array of status options for the spinner
                String[] statusOptions = {"Submitted", "Seen", "Processing", "Completed", "Rejected"};

                // Create a SpannableString for the message with formatted gender text
                SpannableString message = new SpannableString("Person's name: " + name + "\nAge: " + age + "\nGender: " + gender + "\nLast Seen: " + lastSeen + "\nDetails: " + details + "\nStatus: " + status + "\n\nSubmitted By:\nName: " + userName + "\nGender: " + userGender + "\nCNIC: " + userCNIC + "\nContact: " + userContact);

                // Create a spinner and set its adapter
                Spinner spinnerStatus = new Spinner(context);
                ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, statusOptions);
                statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerStatus.setAdapter(statusAdapter);

                // Set the current status of the report as the selected item in the spinner
                int selectedStatusIndex = Arrays.asList(statusOptions).indexOf(status);
                spinnerStatus.setSelection(selectedStatusIndex);

                // Create a LinearLayout to hold the text and spinner
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(48, 0, 48, 0);

                // Create a TextView for the message
                TextView textViewMessage = new TextView(context);
                textViewMessage.setText(message);
                textViewMessage.setPadding(0, 0, 0, 16); // Add bottom padding
                layout.addView(textViewMessage);

                // Create a LinearLayout to hold the "Status" text and spinner
                LinearLayout statusLayout = new LinearLayout(context);
                statusLayout.setOrientation(LinearLayout.HORIZONTAL);
                statusLayout.setGravity(Gravity.CENTER_VERTICAL);

                // Create a TextView for the "Status" text
                TextView textViewStatus = new TextView(context);
                textViewStatus.setText("Status: ");
                textViewStatus.setPadding(0, 0, 8, 0); // Add right padding
                statusLayout.addView(textViewStatus);

                // Add the spinner to the status layout
                statusLayout.addView(spinnerStatus);

                // Add the status layout to the main layout
                layout.addView(statusLayout);

                // Set the custom view as the message of the dialog box
                builder.setView(layout);


//                LayoutInflater inflater = LayoutInflater.from(context);
//                View dialogView = inflater.inflate(R.layout.update_missing_person_report_status, null);
//                builder.setView(dialogView);
//
//                Spinner spinnerStatus = dialogView.findViewById(R.id.spinner_status);
//                ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, statusOptions);
//                statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinnerStatus.setAdapter(statusAdapter);
//
//                // Spinner status with current status set on top sending with the report data to display in the update popup dialog
//                // Retrieve the array from the XML file
//                String[] statusArray = context.getResources().getStringArray(R.array.status_array);
//
//                // Create ArrayAdapter and set it as the adapter for the Spinner
//                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, statusArray);
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//                spinnerStatus.setAdapter(adapter);


                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // getting status from spinner
                        String status = spinnerStatus.getSelectedItem().toString();

                        try {
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

                                // reflect changes in the view
                                // Assuming you have the position and updated record
                                int updatedPosition = position; // position
                                missingPersonData[position].setMissingPersonReportStatus(status);// updating status record

                                // Step 2: Notify the adapter
                                notifyItemChanged(updatedPosition);
                            }
                        }catch(Exception e){
                            Toast.makeText(context, "Error occurred!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Cancel", null);
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




