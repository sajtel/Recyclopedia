package com.example.myfirstapp;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.provider.MediaStore;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PostScrapActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {

    public static final int Camera_request = 9999;
    ImageView image;
    EditText text;
    Spinner spinner;
    Button cam;
    Button back_button;
    Button post;
    private EditText mEditTextName;
    private EditText mEditTextId;
    private EditText mEditDescription;

    private static SQLiteDatabase mDatabase;

    //Overriding onCreate Method

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_scrap);

        cam = (Button) findViewById(R.id.btn_camera);
        image = (ImageView)findViewById(R.id.image);

        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        text = (EditText) findViewById(R.id.scrap_descripiton);
        post = (Button)findViewById(R.id.add_item);
        spinner = (Spinner)findViewById(R.id.spinner);
        populateSpinner();
        spinner.setOnItemSelectedListener(this);


        //Set onItemCLickListener on back button to return to home fragment

        back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostScrapActivity.this.finish();
            }
        });

        //Instantiate database and invoke Add Method
        ScrapDBHelper dbHelper = new ScrapDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        mEditTextName = findViewById(R.id.scrap_name);
        mEditTextId = findViewById(R.id.poster_id);
        mEditDescription= findViewById(R.id.scrap_descripiton);
        Button buttonAdd = findViewById(R.id.add_item);

        // Set on Item Click Listener on Post Button to invoke method addItem;

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

    }

    //Creating a date object and a string variable to set today's date to it

    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    static LocalDateTime now = LocalDateTime.now();
    static String a = dtf.format(now);
    public static String DATE = a;

    // Creating addItem method to getText to our EditText fields and to invoke put() method in SQLite class

    private void addItem() {

        if (mEditTextName.getText().toString().trim().length() == 0 || mEditTextId.getText().toString().trim().length() == 0) {
            return;
        }

        String name = mEditTextName.getText().toString();
        String id = mEditTextId.getText().toString();
        String desc = mEditDescription.getText().toString();

        ContentValues cv = new ContentValues();
        cv.put(ScrapDBContract.ScrapEntry.COLUMN_NAME, name);
        cv.put(ScrapDBContract.ScrapEntry.COLUMN_DESCRIPTION, desc);
        cv.put(ScrapDBContract.ScrapEntry.COLUMN_ID, id);
        cv.put(ScrapDBContract.ScrapEntry.COLUMN_DATE, a);
        mDatabase.insert(ScrapDBContract.ScrapEntry.TABLE_NAME, null, cv);
        //HomeFragment.mAdapter.swapCursor(getAllItems());
        mEditTextName.getText().clear();
        mEditTextId.getText().clear();
        mEditDescription.getText().clear();
        Toast.makeText(this, "Scrap Added", Toast.LENGTH_SHORT).show();
    }

    // Points cursor to all rows in DB table for populating list view in the future
    public static Cursor getAllItems() {
        return mDatabase.query(
                ScrapDBContract.ScrapEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ScrapDBContract.ScrapEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }

    //Creates a new intent that accesses the MediaStore in order to initiate a camera request

    private void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Camera_request);
    }

    //Overriding onActivityResult to post image data in activity

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Camera_request) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            image.setImageDrawable(null);
            image.setImageBitmap(bitmap);
        }
    }

    //Method to populate elements of spinner located in the array "spinner" and uses spinner dropdown layout from resources

    private void populateSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.spinner));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    //Overriding ON Item Selected listener on the spinner content

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String SelItem = parent.getSelectedItem().toString();
        Toast.makeText(this, "Selected " + SelItem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished( Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}