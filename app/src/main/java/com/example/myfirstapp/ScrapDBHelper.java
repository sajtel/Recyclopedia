package com.example.myfirstapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myfirstapp.ScrapDBContract.*;

import java.util.ArrayList;

public class ScrapDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "scraplist.db";
    public static final int DATABASE_VERSION = 2;

    public ScrapDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create a string that contains the SQL line to create the scraps table

    //Overriding the onCreate method and establishing data field, and row structure
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SCRAPLIST_TABLE = "CREATE TABLE " +
                ScrapDBContract.ScrapEntry.TABLE_NAME + " (" +
                ScrapEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ScrapEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                ScrapEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                ScrapEntry.COLUMN_ID + " TEXT NOT NULL, " +
                ScrapEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL,"+
                ScrapEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        //Execute statement
        db.execSQL(SQL_CREATE_SCRAPLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Creating deleting query in order to delete row from TABLE_NAME

    public void DeleteScrap(int id){

        String delete_query = "DELETE FROM "+ ScrapDBContract.ScrapEntry.TABLE_NAME+" WHERE _id = "+id;
        Log.i("tag","qery");
        SQLiteDatabase db = this .getWritableDatabase();
        db.rawQuery(delete_query, null).moveToFirst();
    }

    //get all scraps and applying filter based on timestamp and scrap name (Ascending)
    public ArrayList<Scraps> getScraps(String status_db) {
        ArrayList<Scraps> scrapsList = new ArrayList<>();
        SQLiteDatabase db;
        Cursor cursor;

        Log.i("Scrao DB HELPER", "statys:"+status_db);
        // select all query
        if(status_db == null){
            String select_query2 ="SELECT * FROM " + ScrapDBContract.ScrapEntry.TABLE_NAME+ " ORDER BY name ASC";
            db = this .getWritableDatabase();
            cursor = db.rawQuery(select_query2, null);
        }
        else if(status_db.equals("Date")){
            String select_query= "SELECT * FROM " + ScrapDBContract.ScrapEntry.TABLE_NAME+" ORDER BY timestamp DESC";
            db = this .getWritableDatabase();
            cursor = db.rawQuery(select_query, null);
        }

        else {
            String select_query2 ="SELECT * FROM " + ScrapDBContract.ScrapEntry.TABLE_NAME+ " ORDER BY name ASC";
            db = this .getWritableDatabase();
            cursor = db.rawQuery(select_query2, null);
        }

        // looping through all rows and adding to arraylist
        if (cursor.moveToFirst()) {
            do {
                Scraps scrapsModel = new Scraps();
                scrapsModel.setId(Integer.parseInt(cursor.getString(0)));
                scrapsModel.setName(cursor.getString(1));
                scrapsModel.setDateFormatted(cursor.getString(2));
                scrapsModel.setIdentification(cursor.getString(3));
                scrapsModel.setScrapDescription(cursor.getString(4));
                scrapsModel.setTimestamp(cursor.getString(5));
                scrapsList.add(scrapsModel);
            }while (cursor.moveToNext());
        }
        return scrapsList;
    }




}

