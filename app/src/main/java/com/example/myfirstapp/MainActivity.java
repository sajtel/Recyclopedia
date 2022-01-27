package com.example.myfirstapp;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    String username;
    String status_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Retrieving intent broadcast

        username = getIntent().getStringExtra("username");


        BottomNavigationView nav_menu = findViewById(R.id.nav_menu);
        nav_menu.setOnNavigationItemSelectedListener(icon_listener);

        /* In order to display home fragment by default */

        getSupportFragmentManager().beginTransaction().replace(R.id.hold_fragment, new HomeFragment()).commit();


        //Creat an intent for DBHelper to retrieve the status from the main Activtity
        Intent filter_intent = new Intent(MainActivity.this, ScrapDBHelper.class);
        filter_intent.putExtra("status", status_main);
    }

    //Returning status of the filter mode
    public String getStatus(){
        return status_main;
    }

    //Setting status of the filter mode
    public void setStatus(String status)
    {
        Log.i("MAIN ACTIVITY","STATUS: "+status);
        status_main = status;
    }


    public String getUsername(){
        return username;
    }

    /* Creating an navigation menu listener to eventually react to clicks on icons - When the icon listener recognizes the icon, it opens the corresponding fragment session*/

    private BottomNavigationView.OnNavigationItemSelectedListener icon_listener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment icon_select = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nv_home:
                            icon_select= new HomeFragment();
                            break;
                        case R.id.nv_search:
                            icon_select= new SearchFragment();
                            break;
                        case R.id.nv_settings:
                            icon_select= new SettingsFragment();
                            break;
                        case R.id.nv_info:
                            icon_select= new InfoFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.hold_fragment,
                            icon_select).commit();
                    return true; /* We want to show the select the clicked item*/
                }
            };
}
