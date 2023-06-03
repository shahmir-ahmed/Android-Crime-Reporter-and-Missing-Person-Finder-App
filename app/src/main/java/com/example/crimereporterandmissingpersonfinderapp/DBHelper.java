package com.example.crimereporterandmissingpersonfinderapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
                    DatabaseContract.Complaints.COL_USER_ID + " INTEGER," +
                    " FOREIGN KEY ("+DatabaseContract.Complaints.COL_USER_ID+") REFERENCES "+DatabaseContract.Users.TABLE_NAME+"("+DatabaseContract.Users._ID+"));";

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    // self-created methods to manage (create, read, update, delete) all the tables in DB

}
