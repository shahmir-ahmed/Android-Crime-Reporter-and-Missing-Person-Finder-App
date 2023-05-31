package com.example.crimereporterandmissingpersonfinderapp;

import android.provider.BaseColumns;

public final class DatabaseContract {

    public DatabaseContract(){};

    public static abstract class Users implements BaseColumns{

        public static final String TABLE_NAME="users";
        public static final String COL_NAME="name";
        public static final String COL_USERNAME="username";
        public static final String COL_PASSWORD="password";
        public static final String COL_CNIC="cnic";
        public static final String COL_CONTACT="contact";
        public static final String COL_GENDER="gender";

    }

    // Complaints table


    // Crimes table


    // Admins table


    // Missing persons table
    public static abstract class MissingPersons implements BaseColumns{

        public static final String TABLE_NAME="missing_persons";
        public static final String COL_NAME="name";
        public static final String COL_AGE="age";
        public static final String COL_GENDER="gender";
        public static final String COL_LAST_SEEN="last_seen";
        public static final String COL_ZIPCODE="zipcode";
        public static final String COL_REPORT_DETAILS="report_details";
        public static final String COL_PERSON_IMAGE="image";
        public static final String COL_REPORT_STATUS="report_status";

    }
}
