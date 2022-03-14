package com.example.collabme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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


public class EditOfferFragment extends Fragment {

    EditText proposer, headline, description, finishDate, status, candidates, price, coupon;
    CheckBox interestedVerify;
    Button chatBtn, uploadBtn;
    Spinner profession;

    String[] oldProfession;
    String oldIdOffer;


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
        uploadBtn = view.findViewById(R.id.fragment_editOffer_uploadBtn);
        chatBtn = view.findViewById(R.id.fragment_editOffer_chatBtn);


        Model.instance.getOfferById(new Model.GetOfferListener() {
            @Override
            public void onComplete(Offer offer) {
                if(offer!=null) {

                    initSpinnerFooter(offer.getProfession().length,offer.getProfession(),profession);
                    oldIdOffer = offer.getIdOffer();
                    oldProfession = offer.getProfession();

                    headline.setText(offer.getHeadline());
                    description.setText(offer.getDescription());
                    finishDate.setText("null");
                    status.setText(offer.getStatus());
                    //profession.setText(offer.getProfession()[0]);
                    //price.setText((int)offer.getPrice());
                    interestedVerify.setChecked(offer.getIntrestedVerify());

                   // proposer.setText(offer.getUser().getUsername());

                }
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOfferDetails();
            }
        });

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

        Offer offer = new Offer(description1,null,headline1,price1,oldIdOffer,status1,oldProfession,null,interestedVerify1);

        Model.instance.editOffer(offer,new Model.EditOfferListener() {
            @Override
            public void onComplete(int code) {
                if(code == 200) {
                    Toast.makeText(getActivity(), "offer details saved", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(), "offer details not saved", Toast.LENGTH_LONG).show();

                }

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