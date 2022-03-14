package com.example.collabme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class EditOfferFragment extends Fragment {

    EditText  headline, description, finishDate, status, candidates, price, coupon;
    TextView proposer,profession;
    CheckBox interestedVerify;
    Button chatBtn, saveBtn;

    String[] oldProfession;
    String oldIdOffer, oldProposer;
    ArrayList<Integer> langList = new java.util.ArrayList<>();
    String[] langArray = {"Sport", "Cooking", "Fashion", "Music", "Dance", "Cosmetic", "Travel", "Gaming", "Tech", "Food",
            "Art", "Animals", "Movies", "Photograph", "Lifestyle", "Other"};
    String[] chosenOffers;
    boolean[] selectedLanguage= new boolean[langArray.length];
    String[] chosen;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_edit_offer, container, false);
        proposer = view.findViewById(R.id.fragment_editOffer_proposer);
        headline = view.findViewById(R.id.fragment_editOffer_headline);
        description = view.findViewById(R.id.fragment_editOffer_description);
        finishDate = view.findViewById(R.id.fragment_editOffer_finishdate);
        status = view.findViewById(R.id.fragment_editOffer_status);
        profession = view.findViewById(R.id.fragment_editOffer_profession);
        //candidates = view.findViewById(R.id.fragment_editOffer_candidates);
        price = view.findViewById(R.id.fragment_editOffer_price);
        //coupon = view.findViewById(R.id.fragment_editOffer_coupon);
        interestedVerify = view.findViewById(R.id.fragment_editOffer_checkbox);
        saveBtn = view.findViewById(R.id.fragment_editOffer_saveBtn);


        Model.instance.getOfferById(new Model.GetOfferListener() {
            @Override
            public void onComplete(Offer offer) {
                if(offer!=null) {
                    //initSpinnerFooter(offer.getProfession().length,offer.getProfession(),profession);
                    oldIdOffer = offer.getIdOffer();
                    oldProfession = offer.getProfession();
                    oldProposer = offer.getUser();
                    String[] strings = thedialog(profession,langArray,oldProfession,selectedLanguage);
                    //profession.setText(strings.toString());
                    headline.setText(offer.getHeadline());
                    description.setText(offer.getDescription());
                    finishDate.setText(offer.getFinishDate());
                    status.setText(offer.getStatus());
                    price.setText(offer.getPrice());
                    interestedVerify.setChecked(offer.getIntrestedVerify());

                    Log.d("TAG", " user id:   "+ (String) offer.getUser());
                    Model.instance.getUserById(offer.getUser(), profile -> {
                        if(profile!=null) {
                            proposer.setText(profile.getUsername());
                        }
                    });

                }
            }
        });


        saveBtn.setOnClickListener(v -> saveOfferDetails());

        return view;
    }

    private void saveOfferDetails() {

        String headline1 = headline.getText().toString();
        String description1 = description.getText().toString();
        String finishDate1 = finishDate.getText().toString();
        String status1 = status.getText().toString();
        //String[] profession1 = profession.getText().toString();
        String price1 = price.getText().toString();
        //String candidates1 = candidates.getText().toString();
        //String coupon1 = coupon.getText().toString();
        boolean interestedVerify1 = interestedVerify.isChecked();

        Offer offer = new Offer(description1,null,headline1,finishDate1,price1,oldIdOffer,status1,oldProfession,null,interestedVerify1);

        Log.d("TAG","new Offer : "+offer);
        Model.instance.editOffer(offer, code -> {
            if(code == 200) {
                Toast.makeText(getActivity(), "offer details saved", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getActivity(), "offer details not saved", Toast.LENGTH_LONG).show();

            }

        });
    }

    private void initSpinnerFooter(int size, String[] array, Spinner spinner) {
        int tmp = 0;
        for(int j = 0 ; j<size;j++){
            if(array[j] != null){
                tmp++;
            }
        }
        String[] items = new String[tmp];

        for(int i = 0 ; i<tmp;i++){
            items[i] = array[i];
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextSize(25);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    public String[] thedialog(TextView textView, String[] lang, String[] theArr, boolean[] selected) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // set title
                builder.setTitle("Select:");

                // set dialog non cancelable
                builder.setCancelable(false);
                int m = 0;

                for (int k = 0; k < theArr.length; k++) {
                    for (int h = 0; h < lang.length; h++) {
                        if (theArr[k].equals(lang[h])) {
                            langList.add(h);
                            Collections.sort(langList);
                            selected[h] = true;
                        }
                    }
                }

                builder.setMultiChoiceItems(lang, selected, new DialogInterface.OnMultiChoiceClickListener() {

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
                        chosen = new String[langList.size()];

                        // use for loop
                        for (int j = 0; j < langList.size(); j++) {
                            // concat array value
                            stringBuilder.append(lang[langList.get(j)]);
                            chosen[j] = (lang[langList.get(j)]); //to check again
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
                        for (int j = 0; j < selected.length; j++) {
                            // remove all selection
                            selected[j] = false;
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

        return chosen;
    }

    }