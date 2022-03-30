package com.example.collabme.status;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.collabme.R;


public class inprogressfragment extends Fragment {
    String offerId;
    TextView proposer,status, headline, description, finishDate, price;
    Button candidates, chat, upload, edit;
    CheckBox interestedVerify;
    Spinner profession;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inprogressfragment, container, false);
        offerId = OpenStatusFragmentArgs.fromBundle(getArguments()).getOfferId();
        proposer = view.findViewById(R.id.fragemnt_inprogress_proposer);
        headline = view.findViewById(R.id.fragemnt_inprogress_headline);
        description = view.findViewById(R.id.fragemnt_inprogress_description);
        finishDate = view.findViewById(R.id.fragemnt_inprogress_finishdate);
        status = view.findViewById(R.id.fragment_inprogress_status);
        profession = view.findViewById(R.id.fragment_inprogress_profession);
        price = view.findViewById(R.id.fragemnt_inprogress_price);
        interestedVerify = view.findViewById(R.id.fragemnt_inprogress_checkbox);
        edit = view.findViewById(R.id.fragemnt_inprogress_edit);
        chat  = view.findViewById(R.id.fragemnt_inprogress_chat);
        upload  = view.findViewById(R.id.fragemnt_inprogress_upload);
        candidates = view.findViewById(R.id.fragemnt_inprogress_candidate);

        // Inflate the layout for this fragment


        edit.setOnClickListener(v -> Navigation.findNavController(v).navigate(inprogressfragmentDirections.actionInprogressfragmentToEditOfferFragment(offerId)));

        return view;
    }
}