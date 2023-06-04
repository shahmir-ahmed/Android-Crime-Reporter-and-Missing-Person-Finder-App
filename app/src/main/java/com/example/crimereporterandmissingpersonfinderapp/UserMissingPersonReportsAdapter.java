package com.example.crimereporterandmissingpersonfinderapp;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class UserMissingPersonReportsAdapter extends RecyclerView.Adapter<UserMissingPersonReportsAdapter.ViewHolder> {

//    private AlertDialog alertDialog;
//    private View toastView;
//    private TextView textViewMessage;
    MissingPersonData[] missingPersonData; // the missing person data array (array of objects) based on which the adapter binds the individual v=cards views with the data in the object at each position of array
    Context context; // the activity context in which the adapter is

    private static final int RESULT_LOAD_IMG = 0;

    // update dialog box image
    ImageView imageViewPersonImage;

    public UserMissingPersonReportsAdapter(MissingPersonData[] missingPersonData, UserDashboardActivity activity) {
        this.missingPersonData = missingPersonData;
        this.context = activity;
    }

    public UserMissingPersonReportsAdapter(){

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

        // more details button (same details as shown in view button clicking appearing dialog)
        holder.moreDetailsBtn.setOnClickListener(new View.OnClickListener() {
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
//                builder.setTitle("Missing Person Details");

                // Create a custom layout for the AlertDialog
                View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update_missing_person_details, null);
                builder.setView(dialogView);

//                prevent the dialog from dismissing when positive button is clicked and dismiss manually at some time
//                builder.setCancelable(false);

                // Get references to the views in the custom layout
                // Creating bridge
                EditText etPersonName = (EditText) dialogView.findViewById(R.id.etPersonName);
                EditText etPersonAge = (EditText) dialogView.findViewById(R.id.etPersonAge);

                RadioGroup radioGroup = (RadioGroup) dialogView.findViewById(R.id.radioGroup1);
                RadioButton rbMale = (RadioButton) dialogView.findViewById(R.id.rbMale);
                RadioButton rbFemale = (RadioButton) dialogView.findViewById(R.id.rbFemale);

                EditText etLastSeen = (EditText) dialogView.findViewById(R.id.etLastSeen);
                EditText etZipCode = (EditText) dialogView.findViewById(R.id.editTextZipCode);
                EditText etDetails = (EditText) dialogView.findViewById(R.id.etDetails);
                imageViewPersonImage = (ImageView) dialogView.findViewById(R.id.imageViewPersonImage);

                // setting the report data on form
                etPersonName.setText(missingPersonDataList.getMissingPersonName());
                etPersonAge.setText(missingPersonDataList.getMissingPersonAge());

                // check the gender
                String gender = missingPersonDataList.getMissingPersonGender();

                // based on the gender check the radio button
                if(gender.equals("Male")){
                    rbMale.setChecked(true);
                }
                else{
                    rbFemale.setChecked(true);
                }

                etLastSeen.setText(missingPersonDataList.getMissingPersonLastSeenLocation());
                etZipCode.setText(missingPersonDataList.getMissingPersonZipCode());
                etDetails.setText(missingPersonDataList.getMissingPersonReportDetails());

                imageViewPersonImage.setImageBitmap(missingPersonDataList.getMissingPersonImage());


//                Button browseBtn = (Button) dialogView.findViewById(R.id.browseBtn);


                // on click listener on browse image button
//                browseBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        getImageFromGallery();
//
//                    }
//                });

                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // getting form details
                        String name, age, gender, lastSeen, zipCode, reportDetails;

                        name = etPersonName.getText().toString();

                        age = etPersonAge.getText().toString();

                        int radioButtonId = radioGroup.getCheckedRadioButtonId();

                        lastSeen = etLastSeen.getText().toString();

                        zipCode = etZipCode.getText().toString();

                        reportDetails = etDetails.getText().toString();

                        // validation
                        // if name is not entered
                        if (name.trim().isEmpty()) {
                            Toast.makeText(dialogView.getContext(), "Please enter name!", Toast.LENGTH_LONG).show();

//                            // Show a Toast over the Dialog
//                            // Display a Toast message
//                            Toast toast = Toast.makeText(context, "Please enter name!", Toast.LENGTH_SHORT);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
//                            toast.show();

                            // Inflate the custom Toast layout
//                            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
//                            View layout = inflater.inflate(R.layout.custom_toast_layout, ((Activity)context).findViewById(R.id.custom_toast_layout));
//
//                            // Find the TextView in the custom layout
//                            TextView textViewMessage = layout.findViewById(R.id.textView_message);
//
//                            // Set the desired message
//                            String message = "Please enter name.";
//                            textViewMessage.setText(message);
//
//                            // Create a Toast object and set the custom view
//                            Toast toast = new Toast(context);
//                            toast.setDuration(Toast.LENGTH_SHORT);
//                            toast.setView(layout);
//
//                            // Show the Toast
//                            toast.show();

                            // Create a Handler object
//                            Handler handler = new Handler();
//
//                            // Define a Runnable to show the Toast
//                            Runnable toastRunnable = new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(context, "This is a Toast message", Toast.LENGTH_SHORT).show();
//                                }
//                            };
//
//                            // Show the Toast after a delay
//                            handler.postDelayed(toastRunnable, 1000); // Delay of 1000 milliseconds (1 second)

                            // Create a LayoutInflater object using the adapter's context
//                            LayoutInflater inflater = LayoutInflater.from(context);
//
//// Inflate the custom layout for the Toast message
//                            View toastLayout = inflater.inflate(R.layout.custom_toast_layout, null);
//
//// Set the text for the Toast message
//                            TextView toastText = toastLayout.findViewById(R.id.textView_message);
//                            toastText.setText("This is a Toast message");
//
//// Create a Toast object using the adapter's context
//                            Toast toast = new Toast(context);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
//                            toast.setDuration(Toast.LENGTH_SHORT);
//                            toast.setView(toastLayout);
//
//// Show the Toast message
//                            toast.show();

//                            // Create a custom Toast layout and set the message
//                            LayoutInflater inflater = LayoutInflater.from(context);
//                            View toastView = inflater.inflate(R.layout.custom_toast_layout, null);
//                            TextView textViewMessage = toastView.findViewById(R.id.textView_message);
//                            textViewMessage.setText("This is a Toast message");
//
//// Create the custom Toast object
//                            Toast toast = new Toast(context);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
//                            toast.setDuration(Toast.LENGTH_SHORT);
//                            toast.setView(toastView);
//
//// Show the custom Toast
//                            toast.show();

//                            // Create the custom Toast view and set the message
//                            LayoutInflater inflater = LayoutInflater.from(context);
//                            toastView = inflater.inflate(R.layout.custom_toast_layout, null);
//                            textViewMessage = toastView.findViewById(R.id.textView_message);
//                            textViewMessage.setText("This is a Toast message");
//
//// Show the custom Toast by adding the view to the dialog's window
//                            if (((AlertDialog)dialog).getWindow() != null) {
//                                ((AlertDialog)dialog).getWindow().addContentView(toastView, new ViewGroup.LayoutParams(
//                                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                            }

                        }
                        // if age is not entered
                        else if (age.trim().isEmpty()) {
                            Toast.makeText(dialogView.getContext(), "Please enter age!", Toast.LENGTH_LONG).show();
                        }
                        // if age is greater than 2 digits
                        else if (age.length() > 2) {
                            Toast.makeText(dialogView.getContext(), "Invalid age!", Toast.LENGTH_LONG).show();
                        }
                        // if no radio button is selected
                        else if (radioButtonId == -1) {
                            Toast.makeText(dialogView.getContext(), "Please select gender!", Toast.LENGTH_LONG).show();
                        }
                        // last seen field is not filled
                        else if (lastSeen.trim().isEmpty()) {
                            Toast.makeText(dialogView.getContext(), "Please enter last seen location!", Toast.LENGTH_LONG).show();
                        }
                        // zip code field
                        else if (zipCode.trim().isEmpty()) {
                            Toast.makeText(dialogView.getContext(), "Please enter zip code!", Toast.LENGTH_LONG).show();
                        }
                        else if (zipCode.length() != 5) {
                            Toast.makeText(dialogView.getContext(), "Zip code must be 5 digits!", Toast.LENGTH_LONG).show();
                        }
                        // report details are not entered
                        else if (reportDetails.trim().isEmpty()) {
                            Toast.makeText(dialogView.getContext(), "Please enter report details!", Toast.LENGTH_LONG).show();
                        }
                        // if image is not selected
                        else if (imageViewPersonImage.getDrawable() == null) {
                            Toast.makeText(dialogView.getContext(), "Please choose an image!", Toast.LENGTH_LONG).show();
                        }
                        // if form is valid
                        else {

                            // checking the gender
                            if (radioButtonId == R.id.rbMale) {
                                gender = "Male";
                            } else {
                                gender = "Female";
                            }

                            try {
                                // save form data in database
                                DBHelper dbHelper = new DBHelper(context);

                                SQLiteDatabase db = dbHelper.getWritableDatabase();

                                // data preparing to send
                                ContentValues values = new ContentValues();

                                // putting key value pairs in object
                                values.put(DatabaseContract.MissingPersons.COL_NAME, name);
                                values.put(DatabaseContract.MissingPersons.COL_AGE, age);
                                values.put(DatabaseContract.MissingPersons.COL_GENDER, gender);
                                values.put(DatabaseContract.MissingPersons.COL_LAST_SEEN, lastSeen);
                                values.put(DatabaseContract.MissingPersons.COL_ZIPCODE, zipCode);
                                values.put(DatabaseContract.MissingPersons.COL_REPORT_DETAILS, reportDetails);

                                // getting the report id from list to update the report
                                String reportId = missingPersonDataList.getMissingPersonId();

                                /*
                                // missing person image
                                // Get the Bitmap from the ImageView
                                Bitmap bitmap = ((BitmapDrawable) imageViewPersonImage.getDrawable()).getBitmap();

                                // Convert the Bitmap to a byte array
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                byte[] personImage = baos.toByteArray();

                                // content value --> missing person image
                                values.put(DatabaseContract.MissingPersons.COL_PERSON_IMAGE, personImage);
                                */

                                String whereClause = DatabaseContract.MissingPersons._ID + "=?";

                                String whereArgs[] = {String.valueOf(reportId)};

                                // saving form data in DB
                                long updatedRows = db.update(DatabaseContract.MissingPersons.TABLE_NAME, values, whereClause, whereArgs);

                                // check the returned updated rows
                                if (updatedRows != 1) {
                                    Toast.makeText(context, "Report not updated!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, "Report updated successfully!", Toast.LENGTH_LONG).show();

                                    // dismiss the dialog manually
//                                    dialog.dismiss();

                                    // reflect changes in the view
                                    // Assuming you have the position and updated record
                                    int updatedPosition = position; // position
                                    // updating object
                                    missingPersonData[position].setMissingPersonName(name);
                                    missingPersonData[position].setMissingPersonAge(age);
                                    missingPersonData[position].setMissingPersonGender(gender);
                                    missingPersonData[position].setMissingPersonLastSeenLocation(lastSeen);
                                    missingPersonData[position].setMissingPersonZipCode(zipCode);
                                    missingPersonData[position].setMissingPersonReportDetails(reportDetails);

                                    // Step 2: Notify the adapter
                                    notifyItemChanged(updatedPosition);
                                }
                            }catch(Exception e){
                                Toast.makeText(context, "Error occurred!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });


                builder.setNegativeButton("Cancel",  null);

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
//    public void getImageFromGallery(){
//        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//        photoPickerIntent.setType("image/*");
//        ((Activity) context).startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
//    }

    // handles the gallery result from activity
//    public void handleGalleryResult(Bitmap image) {
//        Toast.makeText(context, "here", Toast.LENGTH_SHORT).show();
//        imageViewPersonImage.setImageBitmap(image);
//    }

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




