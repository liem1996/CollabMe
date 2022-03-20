package com.example.collabme.actionsOnOffers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.collabme.R;
import com.example.collabme.model.ModelOffers;
import com.example.collabme.model.ModelUsers;
import com.example.collabme.objects.Offer;
import com.example.collabme.objects.User;

import java.util.ArrayList;
import java.util.Collections;


public class EditOfferFragment extends Fragment {

    EditText  headline, description, finishDate, status, price, coupon;
    TextView proposer,profession;
    CheckBox interestedVerify;
    Button candidates, saveBtn;
    String[] professionArr;
    String[] oldProfession;
    String oldIdOffer, oldProposer, offerId;
    ArrayList<Integer> langList = new ArrayList<>();
    String[] langArray = {"Sport", "Cooking", "Fashion", "Music", "Dance", "Cosmetic", "Travel", "Gaming", "Tech", "Food",
            "Art", "Animals", "Movies", "Photograph", "Lifestyle", "Other"};
    boolean[] selectedProfessions = new boolean[16];
    String[] chosen;
    String[] newProfession;



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
        candidates = view.findViewById(R.id.fragment_editOffer_candidatesBtn);
        price = view.findViewById(R.id.fragment_editOffer_price);
        interestedVerify = view.findViewById(R.id.fragment_editOffer_checkbox);
        saveBtn = view.findViewById(R.id.fragment_editOffer_saveBtn);
        newProfession = new String[16];
        offerId = EditOfferFragmentArgs.fromBundle(getArguments()).getOfferId();

        ModelOffers.instance.getOfferById(offerId, offer -> {
            if(offer!=null) {
                //initSpinnerFooter(offer.getProfession().length,offer.getProfession(),profession);
                oldIdOffer = offer.getIdOffer();
                oldProfession = offer.getProfession();
                oldProposer = offer.getUser();
                String[] strings = oldProfession;
                StringBuffer sb = new StringBuffer();
                for(int i = 0; i < strings.length; i++) {
                    sb.append(strings[i]);
                    if(i<strings.length-1) {
                        sb.append(", ");
                    }
                }
                String str = sb.toString();

                profession.setText(str);
                headline.setText(offer.getHeadline());
                description.setText(offer.getDescription());
                finishDate.setText(offer.getFinishDate());
                status.setText(offer.getStatus());
                price.setText(offer.getPrice());
                interestedVerify.setChecked(offer.getIntrestedVerify());
                ModelUsers.instance3.getUserConnect(new ModelUsers.getuserconnect() {
                    @Override
                    public void onComplete(User profile) {
                        if(profile!=null) {
                            proposer.setText(profile.getUsername());
                        }
                    }
                });
//                Log.d("TAG", " user id:   "+ (String) offer.getUser());
//                ModelUsers.instance3.getUserById(offer.getUser(), profile -> {
//                    if(profile!=null) {
//                        proposer.setText(profile.getUsername());
//                    }
//                });
                professionArr = offer.getProfession();

        }
    });
        profession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                int m = 0;

                for (int k = 0; k < langArray.length; k++) {
                    for (int h = 0; h < professionArr.length; h++) {
                        if (professionArr[h]!=null) {
                            if (professionArr[h].equals(langArray[k])) {
                                if(!langList.contains(k)) {
                                    langList.add(k);
                                    Collections.sort(langList);
                                    selectedProfessions[k] = true;
                                }
                            }
                        }
                    }
                }


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
                        profession.clearComposingText();
                        professionArr = chosen;

                        // use for loop
                        for (int j = 0; j < langList.size(); j++) {
                            // concat array value

                            stringBuilder.append(langArray[langList.get(j)]);
                            chosen[j] = (langArray[langList.get(j)]); //to check again

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
                        for (int j = 0; j < selectedProfessions.length; j++) {
                            // remove all selection
                            selectedProfessions[j] = false;
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


        candidates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(EditOfferFragmentDirections.actionEditOfferFragmentToCandidatesFragment(offerId));
            }
        });
        saveBtn.setOnClickListener(v -> saveOfferDetails());

        return view;
    }

    private void saveOfferDetails() {
        newProfession = chosen;
        String headline1 = headline.getText().toString();
        String description1 = description.getText().toString();
        String finishDate1 = finishDate.getText().toString();
        String status1 = status.getText().toString();
        //String[] profession1 = profession.getText().toString();
        String price1 = price.getText().toString();
        //String candidates1 = candidates.getText().toString();
        //String coupon1 = coupon.getText().toString();
        boolean interestedVerify1 = interestedVerify.isChecked();
        Offer offer = new Offer(description1,null,headline1,finishDate1,price1,oldIdOffer,status1,newProfession,null,interestedVerify1);

        Log.d("TAG","new Offer : "+offer);
        ModelOffers.instance.editOffer(offer, code -> {
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
}