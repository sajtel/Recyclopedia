package com.example.myfirstapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//THIS HELPS TO PROPERLY ASSESS DATA FROM DATA BASE SUCH AS LOADER MANAGER, SEARCH AND DELETE OPTIONS

//SOME OF THIS CODE WAS USED FROM : https://www.11zon.com/zon/android/display-sqlite-data-in-recyclerview.php


public class ScrapListProvider extends ContentProvider {

    public static final String LOG_TAG = ScrapListProvider.class.getSimpleName();

    private static final int SCRAP = 100;

    private static final int SCRAP_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // POINTERS TO CONTENT AUTHORITY, PATH, SCRAP AND IT'S ID
    static {
        sUriMatcher.addURI(ScrapDBContract.CONTENT_AUTHORITY, ScrapDBContract.PATH_VEHICLE, SCRAP);

        sUriMatcher.addURI(ScrapDBContract.CONTENT_AUTHORITY, ScrapDBContract.PATH_VEHICLE + "/#", SCRAP_ID);
    }

    private ScrapDBHelper mDbHelper;

    // Initiliaze Database Helper methods

    @Override
    public boolean onCreate() {
        mDbHelper = new ScrapDBHelper(getContext());



        return true;
    }

    //Handles query requests

    @Nullable
    @Override
    public Cursor query(Uri uri,String[] projection,String selection,String[] selectionArgs, String sortOrder) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        //This cursor will hold the result of the query
        Cursor cursor = null;


        //To query the database
        int match = sUriMatcher.match(uri);
        switch(match) {
            case SCRAP:
                cursor = database.query(ScrapDBContract.ScrapEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case SCRAP_ID:
                selection = ScrapDBContract.ScrapEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(ScrapDBContract.ScrapEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("cannot query unknown URI" + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch(match) {
            case SCRAP:
                return ScrapDBContract.ScrapEntry.CONTENT_LIST_TYPE;
            case SCRAP_ID:
                return ScrapDBContract.ScrapEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch(match){
            case SCRAP:
                return insertScrap(uri,values);

            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertScrap(Uri uri, ContentValues values) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(ScrapDBContract.ScrapEntry.TABLE_NAME, null, values);

        if ( id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;
        final int match = sUriMatcher.match(uri);
        switch(match) {
            case SCRAP:
                rowsDeleted = database.delete(ScrapDBContract.ScrapEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case SCRAP_ID:
                selection = ScrapDBContract.ScrapEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId((uri))) };
                rowsDeleted = database.delete(ScrapDBContract.ScrapEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri );
        }

        if (rowsDeleted !=0 ) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);

        switch(match) {
            case SCRAP:
                return updateScrap(uri, values, selection, selectionArgs);
            case SCRAP_ID:
                selection = ScrapDBContract.ScrapEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateScrap(uri,values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateScrap(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if(values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(ScrapDBContract.ScrapEntry.TABLE_NAME, values, selection, selectionArgs);

        if(rowsUpdated != 0 ) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
