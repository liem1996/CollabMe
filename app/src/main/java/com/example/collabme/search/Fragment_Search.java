package com.example.collabme.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.collabme.R;


public class Fragment_Search extends Fragment {

    EditText proposer,price,headline,dates;
    TextView proffesions;
    Button search;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_offer_details_fragemnt, container, false);
        proposer = view.findViewById(R.id.fragmentSearch_proposer);
        price = view.findViewById(R.id.fragmentSearch_price);
        headline = view.findViewById(R.id.fragmentSearch_headline);
        dates = view.findViewById(R.id.fragmentSearch_dates);
        proffesions = view.findViewById(R.id.fragmentSearch_professions);
        search = view.findViewById(R.id.fragmentSearch_button_search);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               searchAcordingtoParamters();
            }
        });


        return view;
    }

    public void searchAcordingtoParamters() {
        String proposer1=proposer.getText().toString();



    }
}