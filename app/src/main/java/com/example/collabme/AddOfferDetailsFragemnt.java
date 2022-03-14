package com.example.collabme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;


public class AddOfferDetailsFragemnt extends Fragment {

    EditText headline,description,finishdate, price;
    TextView status, candidates,profession,proposer;
    Button save;
    CheckBox intrestedVerify;
    Offer offer;
    User userConnected;
    boolean[] selectedLanguage;
    String[] chosenOffers;
    ArrayList<Integer> langList = new ArrayList<>();
    String[] langArray = {"Sport", "Cooking", "Fashion", "Music", "Dance", "Cosmetic", "Travel", "Gaming", "Tech", "Food",
            "Art", "Animals", "Movies", "Photograph", "Lifestyle", "Other"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_offer_details_fragemnt, container, false);

        proposer = view.findViewById(R.id.fragemnt_newoffer_proposer);
        headline = view.findViewById(R.id.fragemnt_newoffer_headline);
        description = view.findViewById(R.id.fragemnt_newoffer_description);
        finishdate = view.findViewById(R.id.fragemnt_newoffer_finishdate);
        status = view.findViewById(R.id.fragment_addoffer_status);
        profession = view.findViewById(R.id.fragment_addoffer_profession);
        candidates = view.findViewById(R.id.fragment_newoffer_candidates);
        price = view.findViewById(R.id.fragemnt_newoffer_price);
        intrestedVerify = view.findViewById(R.id.fragemnt_newoffer_checkbox);
        save = view.findViewById(R.id.fragemnt_newoffer_saveBtn);

        status.setText("Open");

        selectedLanguage = new boolean[langArray.length];

        String uniqueKey = UUID.randomUUID().toString();

        Model.instance.getUserConnect(new Model.getuserconnect() {
            @Override
            public void onComplete(User profile) {
                if(profile!=null) {
                    userConnected = profile;
                }
            }
        });

        proposer.setText("liem");

        profession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // set title
                builder.setTitle("Select Profession:");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(langArray, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
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
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        chosenOffers = new String[langList.size()];

                        // use for loop
                        for (int j = 0; j < langList.size(); j++) {
                            // concat array value
                            stringBuilder.append(langArray[langList.get(j)]);
                            chosenOffers[j]=(langArray[langList.get(j)]); //to check again
                            System.out.println("ko");
                            // check condition
                            if (j != langList.size() - 1) {
                                // When j value not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        profession.setText(stringBuilder.toString());
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
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            // remove all selection
                            selectedLanguage[j] = false;
                            // clear language list
                            langList.clear();
                            // clear text view value
                            profession.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }


        });


        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                offer = new Offer(description.getText().toString(),null, headline.getText().toString(),finishdate.getText().toString(),
                        price.getText().toString(),  uniqueKey,  status.getText().toString(), chosenOffers,  userConnected.getUsername(),
                        intrestedVerify.isChecked());

                Model.instance.addOffer(offer, new Model.addOfferListener() {
                    @Override
                    public void onComplete(int code) {

                        if (code == 200) {
                            //   Model.instance.Login(userConnected.getUsername(), userConnected.getPassword(), code1 -> { });

                            Toast.makeText(getActivity(), "added offer", Toast.LENGTH_LONG).show();

                            Navigation.findNavController(view).navigate(R.id.action_addOfferDetailsFragemnt_to_homeFragment);

                        } else {
                            Toast.makeText(getActivity(), "not add", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        return view;
    }

}

