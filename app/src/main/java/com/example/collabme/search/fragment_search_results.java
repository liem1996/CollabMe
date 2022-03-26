package com.example.collabme.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.collabme.R;
import com.example.collabme.users.EditProfileArgs;


public class fragment_search_results extends Fragment {

    String freeSearch1, proposer1, fromprice1, toprice1, headline1,fromdates1, todates1;
    String[] chosen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);

        freeSearch1 = fragment_search_resultsArgs.fromBundle(getArguments()).getFreeSearch();
        chosen = fragment_search_resultsArgs.fromBundle(getArguments()).getProfessions();
        proposer1 = fragment_search_resultsArgs.fromBundle(getArguments()).getFreeSearch();
        fromdates1 = fragment_search_resultsArgs.fromBundle(getArguments()).getFreeSearch();
        fromprice1 = fragment_search_resultsArgs.fromBundle(getArguments()).getFreeSearch();
        toprice1 = fragment_search_resultsArgs.fromBundle(getArguments()).getFreeSearch();
        headline1 = fragment_search_resultsArgs.fromBundle(getArguments()).getFreeSearch();
        todates1 = fragment_search_resultsArgs.fromBundle(getArguments()).getFreeSearch();


        return view;
    }
}