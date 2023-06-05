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
    public static class Complaints implements BaseColumns {
        public static final String TABLE_NAME = "complaints";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_PINCODE = "pincode";
        public static final String COLUMN_SUBJECT = "subject";
        public static final String COLUMN_COMPLAINT = "complaint";
        public static final String COLUMN_STATUS = "status";
        public static final String COL_USER_ID="user_id";
    }


    // Crimes table
    public static class Crimes implements BaseColumns {
        public static final String TABLE_NAME = "crimes";
        public static final String COLUMN_TYPE = "crime_type";
        public static final String COLUMN_STREET_DETAILS = "street_details";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_ZIPCODE = "zipcode";
        public static final String COLUMN_CRIME_DETAILS = "crime_details";
        public static final String COLUMN_IMAGE = "image";

        public static final String COLUMN_USER_ID = "user_id";
    }



    // Admins table
    public static abstract class Admins implements BaseColumns{
        public static final String TABLE_NAME="admins";
        public static final String COL_USERNAME ="username";
        public static final String COL_PASSWORD ="password";
    }



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
        public static final String COL_USER_ID="user_id";

    }
}
