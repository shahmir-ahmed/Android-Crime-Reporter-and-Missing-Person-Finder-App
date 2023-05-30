package com.example.crimereporterandmissingpersonfinderapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "crmpfa.db";

    private static final String CREATE_TABLE_USERS="CREATE TABLE "
            + DatabaseContract.Users.TABLE_NAME + " ("
            + DatabaseContract.Users._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DatabaseContract.Users.COL_NAME + " TEXT, "
            + DatabaseContract.Users.COL_USERNAME + " TEXT, "
            + DatabaseContract.Users.COL_PASSWORD + " TEXT, "
            + DatabaseContract.Users.COL_CNIC + " TEXT, "
            + DatabaseContract.Users.COL_CONTACT + " TEXT, "
            + DatabaseContract.Users.COL_GENDER + " TEXT)";

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    // self-created methods to manage (create, read, update, delete) all the tables in DB

}
