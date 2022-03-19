package com.example.collabme.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.collabme.R;


public class Fragment_Search extends Fragment {

    EditText category,age,gender,followers,posts,platforms;
    Button search;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_offer_details_fragemnt, container, false);
        category = view.findViewById(R.id.fragmentSearch_category);
        age = view.findViewById(R.id.fragmentSearch_age);
        gender = view.findViewById(R.id.fragmentSearch_gender);
        followers = view.findViewById(R.id.fragmentSearch_followers);
        posts = view.findViewById(R.id.fragmentSearch_posts);
        platforms = view.findViewById(R.id.fragmentSearch_platform);
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
        String followers1=followers.getText().toString();



    }
}