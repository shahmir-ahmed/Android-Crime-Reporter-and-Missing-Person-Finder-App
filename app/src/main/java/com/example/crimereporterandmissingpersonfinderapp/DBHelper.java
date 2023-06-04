package com.example.crimereporterandmissingpersonfinderapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "crmpfa.db";

    // shared preferences for user session
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String userIdKey = "idKey";
    SharedPreferences sharedpreferences;

    private Context context;

    // users table creation query
    private static final String CREATE_TABLE_USERS="CREATE TABLE "
            + DatabaseContract.Users.TABLE_NAME + " ("
            + DatabaseContract.Users._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DatabaseContract.Users.COL_NAME + " TEXT, "
            + DatabaseContract.Users.COL_USERNAME + " TEXT, "
            + DatabaseContract.Users.COL_PASSWORD + " TEXT, "
            + DatabaseContract.Users.COL_CNIC + " TEXT, "
            + DatabaseContract.Users.COL_CONTACT + " TEXT, "
            + DatabaseContract.Users.COL_GENDER + " TEXT)";

    // missing persons table creation query
    private static final String CREATE_TABLE_MISSING_PERSONS="CREATE TABLE "
            + DatabaseContract.MissingPersons.TABLE_NAME + " ("
            + DatabaseContract.MissingPersons._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DatabaseContract.MissingPersons.COL_NAME + " TEXT, "
            + DatabaseContract.MissingPersons.COL_AGE + " TEXT, "
            + DatabaseContract.MissingPersons.COL_GENDER + " TEXT, "
            + DatabaseContract.MissingPersons.COL_LAST_SEEN + " TEXT, "
            + DatabaseContract.MissingPersons.COL_ZIPCODE + " TEXT, "
            + DatabaseContract.MissingPersons.COL_REPORT_DETAILS + " TEXT, "
            + DatabaseContract.MissingPersons.COL_PERSON_IMAGE + " BLOB, "
            + DatabaseContract.MissingPersons.COL_REPORT_STATUS + " TEXT, "
            + DatabaseContract.MissingPersons.COL_USER_ID + " INTEGER, "
            + " FOREIGN KEY ("+DatabaseContract.MissingPersons.COL_USER_ID+") REFERENCES "+DatabaseContract.Users.TABLE_NAME+"("+DatabaseContract.Users._ID+"));";

    // admin table creation query
    private static final String CREATE_TABLE_ADMINS="CREATE TABLE "
            + DatabaseContract.Admins.TABLE_NAME + " ("
            + DatabaseContract.Admins._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DatabaseContract.Admins.COL_USERNAME + " TEXT, "
            + DatabaseContract.Admins.COL_PASSWORD + " TEXT)";


    // admin table insertion query
    private static final String INSERT_INTO_ADMINS = "INSERT INTO "
            + DatabaseContract.Admins.TABLE_NAME+" ( "
            + DatabaseContract.Admins.COL_USERNAME+" ,"
            + DatabaseContract.Admins.COL_PASSWORD+" )"
            + "VALUES ( 'admin', '12345'),"
            + "('admin2', '678910')";


    // complaints table creation query
    private static final String CREATE_TABLE_COMPLAINTS =
            "CREATE TABLE " + DatabaseContract.Complaints.TABLE_NAME + " (" +
                    DatabaseContract.Complaints._ID + " INTEGER PRIMARY KEY, " +
                    DatabaseContract.Complaints.COLUMN_ADDRESS + " TEXT, " +
                    DatabaseContract.Complaints.COLUMN_CITY + " TEXT, " +
                    DatabaseContract.Complaints.COLUMN_PINCODE + " TEXT, " +
                    DatabaseContract.Complaints.COLUMN_SUBJECT + " TEXT, " +
                    DatabaseContract.Complaints.COLUMN_COMPLAINT + " TEXT," +
                    DatabaseContract.Complaints.COLUMN_STATUS + " TEXT," +
                    DatabaseContract.Complaints.COL_USER_ID + " INTEGER," +
                    " FOREIGN KEY ("+DatabaseContract.Complaints.COL_USER_ID+") REFERENCES "+DatabaseContract.Users.TABLE_NAME+"("+DatabaseContract.Users._ID+"));";


    // crimes table creation query

    private static final String CREATE_TABLE_CRIMES=
            "CREATE TABLE " + DatabaseContract.Crimes.TABLE_NAME + " ("
                    + DatabaseContract.Crimes._ID + " INTEGER PRIMARY KEY,"
                    + DatabaseContract.Crimes.COLUMN_TYPE + " TEXT,"
                    + DatabaseContract.Crimes.COLUMN_STREET_NUMBER + " TEXT,"
                    + DatabaseContract.Crimes.COLUMN_CITY + " TEXT,"
                    + DatabaseContract.Crimes.COLUMN_ZIPCODE + " TEXT,"
                    + DatabaseContract.Crimes.COLUMN_CRIME_DETAILS + " TEXT,"
                    + DatabaseContract.Crimes.COLUMN_IMAGE + " BLOB)";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create users table when db is created for first time
        sqLiteDatabase.execSQL(CREATE_TABLE_USERS);

        // create missing persons table when db is created for first time
        sqLiteDatabase.execSQL(CREATE_TABLE_MISSING_PERSONS);

        // create admins table when db is created for first time
        sqLiteDatabase.execSQL(CREATE_TABLE_ADMINS);
        // and insert admin users in admins table
        sqLiteDatabase.execSQL(INSERT_INTO_ADMINS);

        // create complaints table when db is created for first time
        sqLiteDatabase.execSQL(CREATE_TABLE_COMPLAINTS);

        // create crimes table when db is created for first time
        sqLiteDatabase.execSQL(CREATE_TABLE_CRIMES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    // self-created methods to manage (create, read, update, delete) of the tables in DB

    // Methods for Complaints table
    // view operation for all users complaints
    public List<Complaint> getAllComplaints() {
        List<Complaint> complaintsList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        // using join to query user data along with complaint details

        String[] projection = {
                DatabaseContract.Complaints._ID,
                DatabaseContract.Complaints.COLUMN_CITY,
                DatabaseContract.Complaints.COLUMN_PINCODE,
                DatabaseContract.Complaints.COLUMN_SUBJECT,
                DatabaseContract.Complaints.COLUMN_COMPLAINT,
                DatabaseContract.Complaints.COLUMN_STATUS,
        };

        Cursor cursor = db.query(
                DatabaseContract.Complaints.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Complaint complaint = new Complaint();
            complaint.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.Complaints._ID)));
            complaint.setSubject(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Complaints.COLUMN_SUBJECT)));
//            complaint.setPersonName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Complaints.COLUMN_PERSON_NAME)));
            complaint.setComplaintDetails(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Complaints.COLUMN_COMPLAINT)));
            complaint.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Complaints.COLUMN_STATUS)));
            complaint.setCity(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Complaints.COLUMN_CITY)));

            complaintsList.add(complaint);
        }

        cursor.close();
        db.close();

        return complaintsList;
    }


    // view operation
    public List<Complaint> getUserComplaints() {

        List<Complaint> complaintsList = new ArrayList<>();

        // initializing shared preferences
        sharedpreferences = ((Activity) context).getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        int userId = Integer.parseInt(sharedpreferences.getString(userIdKey, ""));

        // retrieve all the missing person reports from DB reported by the user

        SQLiteDatabase db = getReadableDatabase();

        String columns[] = {DatabaseContract.Complaints._ID, DatabaseContract.Complaints.COLUMN_ADDRESS, DatabaseContract.Complaints.COLUMN_CITY, DatabaseContract.Complaints.COLUMN_PINCODE, DatabaseContract.Complaints.COLUMN_SUBJECT, DatabaseContract.Complaints.COLUMN_COMPLAINT, DatabaseContract.Complaints.COLUMN_STATUS};

        String whereClause = DatabaseContract.Complaints.COL_USER_ID+"=?"; // fetching only the login user complaints

        String whereArgs[] = {String.valueOf(userId)};


        Cursor result = db.query(DatabaseContract.Complaints.TABLE_NAME, columns, whereClause, whereArgs, null, null, null);


        // if there are reports
        if(result.moveToFirst()){
            // reset to initial position
            result.moveToPosition(-1);

            int i = 0;

            // while there are next cursor positions to move
            while(result.moveToNext()){

                // Creating compliant object
                Complaint complaint = new Complaint();

                // getting all the data
                complaint.setId(result.getInt(result.getColumnIndexOrThrow(DatabaseContract.Complaints._ID)));
                complaint.setAddress(result.getString(result.getColumnIndexOrThrow(DatabaseContract.Complaints.COLUMN_ADDRESS)));
                complaint.setCity(result.getString(result.getColumnIndexOrThrow(DatabaseContract.Complaints.COLUMN_CITY)));
                complaint.setZipCode(result.getString(result.getColumnIndexOrThrow(DatabaseContract.Complaints.COLUMN_PINCODE)));
                complaint.setSubject(result.getString(result.getColumnIndexOrThrow(DatabaseContract.Complaints.COLUMN_SUBJECT)));
                complaint.setComplaintDetails(result.getString(result.getColumnIndexOrThrow(DatabaseContract.Complaints.COLUMN_COMPLAINT)));
                complaint.setStatus(result.getString(result.getColumnIndexOrThrow(DatabaseContract.Complaints.COLUMN_STATUS)));

                complaintsList.add(complaint);
            }

        }

        return complaintsList;
    }


    // update operation
    public int updateComplaint(Complaint complaint) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

//        values.put(DatabaseContract.Complaints.COLUMN_SUBJECT, complaint.getSubject());
//////        values.put(DatabaseContract.Complaints.COLUMN_PERSON_NAME, complaint.getPersonName());
////        values.put(DatabaseContract.Complaints.COLUMN_COMPLAINT, complaint.getComplaintDetails());
////        values.put(DatabaseContract.Complaints.COLUMN_STATUS, complaint.getStatus());
////        values.put(DatabaseContract.Complaints.COLUMN_CITY, complaint.getCity());

        // putting key value pairs in object
        values.put(DatabaseContract.Complaints.COLUMN_ADDRESS, complaint.getAddress());
        values.put(DatabaseContract.Complaints.COLUMN_CITY, complaint.getCity());
        values.put(DatabaseContract.Complaints.COLUMN_PINCODE, complaint.getZipCode());
        values.put(DatabaseContract.Complaints.COLUMN_SUBJECT, complaint.getSubject());
        values.put(DatabaseContract.Complaints.COLUMN_COMPLAINT, complaint.getComplaintDetails());

        String selection = DatabaseContract.Complaints._ID + " = ?";
        String[] selectionArgs = {String.valueOf(complaint.getId())};

        int updatedRows = db.update(DatabaseContract.Complaints.TABLE_NAME, values, selection, selectionArgs);

        db.close();

        return updatedRows;
    }

    // delete operation
    public void deleteComplaint(int complaintId) {
        SQLiteDatabase db = getWritableDatabase();

        String selection = DatabaseContract.Complaints._ID + " = ?";

        String[] selectionArgs = { String.valueOf(complaintId) };

        int deletedRows = db.delete(DatabaseContract.Complaints.TABLE_NAME, selection, selectionArgs);

        db.close();

        if (deletedRows > 0) {
            Log.d("DatabaseHelper", "Complaint deleted successfully");
        } else {
            Log.d("DatabaseHelper", "Failed to delete complaint");
        }
    }

}
