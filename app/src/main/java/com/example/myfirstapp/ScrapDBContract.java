package com.example.myfirstapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

//Creating SQlite Database Table contract and parametrizing our data path for get() and delete() methods

public class ScrapDBContract {

    private ScrapDBContract(){}

    public static final String CONTENT_AUTHORITY = "com.example.myfirstapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_VEHICLE = "reminder-path";


    public static final class ScrapEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_VEHICLE);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VEHICLE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VEHICLE;


        public static final String TABLE_NAME = "scrapList";
        public final static String _ID = BaseColumns._ID;

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ID = "identification";
        public static final String COLUMN_DATE = "dateFormatted";
        public static final String COLUMN_DESCRIPTION = "scrapDescription";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

    //Retrieve String type of the ScrapList table column names

    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

}

