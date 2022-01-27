package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChangeProfileActivity extends AppCompatActivity {

    private static SQLiteDatabase Database;
    EditText t1,t2,t3;
    String s1;
    String s2;
    String s3;
    Button previous;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        save = findViewById(R.id.save);
        previous = (Button) findViewById(R.id.Back);
        t1 = findViewById(R.id.ed1);
        t2 = findViewById(R.id.ed2);
        t3 = findViewById(R.id.ed3);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s1 = t1.getText().toString();
                s2 = t2.getText().toString();
                s3 = t3.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("firstname",s1);
                bundle.putString("lastname",s2);
                bundle.putString("email",s3);
                SettingsFragment settingsFragment = new SettingsFragment();
                settingsFragment.setArguments(bundle);
                ChangeProfileActivity.this.finish();
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeProfileActivity.this.finish();
            }
        });


    }

    public void tofrag(String a, String b, String c){
        ChangeProfileActivity.this.finish();
    }
}