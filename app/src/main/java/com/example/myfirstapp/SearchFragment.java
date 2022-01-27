package com.example.myfirstapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SearchFragment extends Fragment {
    SearchView searchText;
    ScrapListItemAdapter mAdapter;
    RecyclerView recyclerView;
    ArrayList<Scraps> scrapsList;
    ScrapDBHelper database_helper;
    TextView searchFrag;

    /*Overriding onCreateView layout*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.search_fragment, container, false);

        recyclerView = (RecyclerView)V.findViewById(R.id.recyclerview);
        database_helper = new ScrapDBHelper(V.getContext());
        searchFrag = (TextView) V.findViewById(R.id.searchfragText);
        getScraps();

        searchText = V.findViewById(R.id.search_icon);
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                search(s,V);
                return false;
            }
        });
        return V;
    }

    //Applying search method on the arraylist containing scrap types returned from our TABLE_NAME, recognizes scrap based on scrap name and user_id and then
    private void search(String s,View V){
        ArrayList<Scraps> searchScrapsList = new ArrayList<>();
        if(!s.equals("")) {
            for (int i = 0; i < scrapsList.size(); i++) {
                if (scrapsList.get(i).getName().toLowerCase().contains(s.toLowerCase()) || scrapsList.get(i).getIdentification().toLowerCase().contains(s.toLowerCase()) ) {
                    searchScrapsList.add(scrapsList.get(i));
                }
            }
        }

        //Setting  recyclerview in Search fragment invisible if nothing is inputted in the search field
        if(searchScrapsList.size()>0){
            searchFrag.setVisibility(View.INVISIBLE);
        }
        else{
            searchFrag.setVisibility(View.VISIBLE);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(V.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ScrapListItemAdapter adapter = new ScrapListItemAdapter(V.getContext(),this.getActivity(), searchScrapsList);
        recyclerView.setAdapter(adapter);
    }
    //populating arraylist from dbHelper and called on mainactivity to retrieve the filter status
    private void getScraps() {

        MainActivity activity = (MainActivity) getActivity();
        String status = activity.status_main;
        scrapsList = new ArrayList<>(database_helper.getScraps(status));
    }

}

