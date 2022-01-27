package com.example.myfirstapp;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    /*Overriding onCreateView layout that fragment inflates*/
    private FloatingActionButton button;


    ScrapListItemAdapter mAdapter;

    RecyclerView recyclerView;
    ArrayList<Scraps> scrapList;
    ScrapDBHelper database_helper;
    TextView noReminder, welcomeUser;

    Toolbar toolbar;

    //RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.home_fragment, container, false);
        button = (FloatingActionButton) V.findViewById(R.id.btn_postScrap);
        recyclerView = (RecyclerView) V.findViewById(R.id.recyclerview);
        database_helper = new ScrapDBHelper(V.getContext());
        noReminder = (TextView) V.findViewById(R.id.no_reminder_text);
        welcomeUser = (TextView) V.findViewById(R.id.welcomeUser);

        // Getting homefragment activity and setting text to that of the username variable declared in the main activity
        MainActivity activity = (MainActivity) getActivity();
        String name = activity.getUsername();
        welcomeUser.setText("Hello, " + name);

        // To populate recyclerview
        populateRecyclerView(V);

        //Set On Click Listener for the button to open PostScrap Activity
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPostAct();
            }
        });

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                database_helper.DeleteScrap(scrapList.get(position).getId());
                Toast.makeText(V.getContext(), "Scrap Deleted", Toast.LENGTH_SHORT).show();
                populateRecyclerView(V);

            }

            // Display the background view
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            }
        };

        // Attach the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        return V;
    }

    //Method to Populate RecyclerView in Homefragment and to

    private void populateRecyclerView(View v) {
        MainActivity activity = (MainActivity) getActivity();
        String status = activity.status_main;
        Log.i("HOME FRAGMENT", "status:"+status);

        scrapList = new ArrayList<>(database_helper.getScraps(status));

        if(scrapList.size() > 0) {
            noReminder.setVisibility(View.INVISIBLE);
        }
        else {
            noReminder.setVisibility(View.INVISIBLE);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ScrapListItemAdapter itemAdapter =new ScrapListItemAdapter(v.getContext(), this.getActivity(), scrapList);
        recyclerView.setAdapter(itemAdapter);
    }


    //Create an intent to get Post Scrap Activity

    private void openPostAct(){
        Intent intent = new Intent(getActivity(), PostScrapActivity.class);
        startActivity(intent);
    }

}
