package com.example.crimereporterandmissingpersonfinderapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "crmpfa.db";

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


    // self-created methods to manage (create, read, update, delete) all the tables in DB

    // Methods for Complaints table
    // view operation
    public List<Complaint> getAllComplaints() {
        List<Complaint> complaintsList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

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

    // update operation
    public void updateComplaint(Complaint complaint) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Complaints.COLUMN_SUBJECT, complaint.getSubject());
//        values.put(DatabaseContract.Complaints.COLUMN_PERSON_NAME, complaint.getPersonName());
        values.put(DatabaseContract.Complaints.COLUMN_COMPLAINT, complaint.getComplaintDetails());
        values.put(DatabaseContract.Complaints.COLUMN_STATUS, complaint.getStatus());
        values.put(DatabaseContract.Complaints.COLUMN_CITY, complaint.getCity());

        String selection = DatabaseContract.Complaints._ID + " = ?";
        String[] selectionArgs = {String.valueOf(complaint.getId())};

        db.update(DatabaseContract.Complaints.TABLE_NAME, values, selection, selectionArgs);

        db.close();
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
