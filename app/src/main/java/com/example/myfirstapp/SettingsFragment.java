package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class SettingsFragment extends Fragment {
    private Button edit;
    TextView tv1,tv2;

    Button date_filter, name_filter;
    TextView filter_status;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.settings_fragment, container, false);
        edit = V.findViewById(R.id.save);

        date_filter = (Button) V.findViewById(R.id.date_filter_button);
        name_filter = (Button) V.findViewById(R.id.name_filter_button);

        filter_status = (TextView) V.findViewById(R.id.filterStatus);

        MainActivity activity = (MainActivity) getActivity();
        String s = activity.getStatus();

        if(s != null && !s.equals("")){
            filter_status.setText(s);
        }

        //Added onClickListener to change filter status

        date_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter_status.setText("Date");
                MainActivity activity = (MainActivity) getActivity();
                activity.setStatus(filter_status.getText().toString());

            }
        });

        name_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter_status.setText("Name");
                MainActivity activity = (MainActivity) getActivity();
                activity.setStatus(filter_status.getText().toString());

            }
        });

        String status = filter_status.getText().toString();
        activity.setStatus(filter_status.getText().toString());


        final EditText t1 = V.findViewById(R.id.editfirst);
        final EditText t2 = V.findViewById(R.id.editlast);
        tv1 = V.findViewById(R.id.firstname);
        tv2 = V.findViewById(R.id.lastname);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv1.setText(t1.getText().toString());
                tv2.setText(t2.getText().toString());
                try {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }
            }
        });

        return V;
    }

    private void openProfileAct(){
        Intent intent = new Intent(getActivity(), ChangeProfileActivity.class);
        startActivity(intent);
    }
}