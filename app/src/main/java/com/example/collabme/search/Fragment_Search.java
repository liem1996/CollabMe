package com.example.collabme.search;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.collabme.R;
import com.example.collabme.model.ModelSearch;
import com.example.collabme.objects.Offer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Fragment_Search extends Fragment {

    Offer[] offersFromSearch;
    EditText proposer,headline,todates, toprice, fromdates, fromprice, freeSearch;
    TextView professions;
    Button search;
    ImageView freesearchbutton;
    String proposer1, headline1, todates1,fromdates1,toprice1,freeSearch1,fromprice1;
    boolean[] selectedProfessions = new boolean[16];
    ArrayList<Integer> langList = new ArrayList<>();
    String[] langArray = {"Sport", "Cooking", "Fashion", "Music", "Dance", "Cosmetic", "Travel", "Gaming", "Tech", "Food",
            "Art", "Animals", "Movies", "Photograph", "Lifestyle", "Other"};
    String[] professionArr;
    String[] chosen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        proposer = view.findViewById(R.id.fragment_Search_proposer);
        headline = view.findViewById(R.id.fragment_Search_headline);
        fromdates = view.findViewById(R.id.fragment_Search_fromdates);
        fromprice = view.findViewById(R.id.fragment_Search_fromprice);
        todates = view.findViewById(R.id.fragment_Search_todates);
        toprice = view.findViewById(R.id.fragment_Search_toprice);
        search = view.findViewById(R.id.fragment_Search_button_search);
        professions = view.findViewById(R.id.fragment_Search_profession);
        freeSearch = view.findViewById(R.id.fragment_Search_freesearch);
        freesearchbutton = view.findViewById(R.id.fragment_search_freesearc_btn);

        professions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // set title
                builder.setTitle("Select Professions:");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(langArray, selectedProfessions, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position in lang list
                            langList.add(i);
                            // Sort array list
                            Collections.sort(langList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            langList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder;
                        // Initialize string builder
                        stringBuilder = new StringBuilder();
                        chosen = new String[langList.size()];

                        // use for loop
                        for (int j = 0; j < langList.size(); j++) {
                            // concat array value

                            stringBuilder.append(langArray[langList.get(j)]);
                            chosen[j] = (langArray[langList.get(j)]); //to check again

                            // check condition
                            if (j != langList.size() - 1) {
                                // When j value not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        professions.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedProfessions.length; j++) {
                            // remove all selection
                            selectedProfessions[j] = false;
                            // clear language list
                            langList.clear();
                            // clear text view value
                            professions.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }

        });

        freesearchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               searchAcordingtoParamters();
                ModelSearch.instance.getOfferFromFreeSearch(freeSearch1, new ModelSearch.getOfferFromFreeSearchListener() {
                    @Override
                    public void onComplete(List<Offer> offers) {
                        offersFromSearch = offers.toArray(new Offer[0]);
                        Navigation.findNavController(view).navigate(Fragment_SearchDirections.actionFragmentSearchToFragmentSearchResults(
                                offersFromSearch));
                    }
                });


            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAcordingtoParamters();
                ModelSearch.instance.getOfferFromSpecificSearch(null,headline1,fromdates1,todates1,fromprice1,toprice1,
                        chosen, proposer1, new ModelSearch.getOfferFromSpecificSearchListener() {
                    @Override
                    public void onComplete(List<Offer> offers) {
                        offersFromSearch = offers.toArray(new Offer[0]);
                        Navigation.findNavController(view).navigate(Fragment_SearchDirections.actionFragmentSearchToFragmentSearchResults(
                                offersFromSearch));
                    }
                });


            }
        });

        return view;
    }
// TODO to add descruption field ------------------!!!!!!!!!!!!!!!!!!!!!!!!!!

    public void searchAcordingtoParamters() {
         proposer1 = proposer.getText().toString();
         headline1 = headline.getText().toString();
         todates1 = todates.getText().toString();
         fromdates1 = fromdates.getText().toString();
         toprice1 = toprice.getText().toString();
         fromprice1 = fromprice.getText().toString();
         freeSearch1 = freeSearch.getText().toString();
    }

}