package com.example.collabme.status;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.collabme.Activites.LoginActivity;
import com.example.collabme.R;
import com.example.collabme.model.ModelOffers;
import com.example.collabme.model.Modelauth;
import com.example.collabme.pagesForOffers.fragment_mediaContentArgs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class DoneStatusFragment extends Fragment {

    String offerId;
    TextView proposer, status, headline, description, finishDate, price;
    Button paymentBtn;
    FloatingActionButton chatBtn;
    ImageButton editBtn, candidatesBtn;
    CheckBox interestedVerify;
    Spinner profession;
    ImageView logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_done_status, container, false);

        offerId = DoneStatusFragmentArgs.fromBundle(getArguments()).getOfferid();

        proposer = view.findViewById(R.id.fragemnt_done_proposer);
        headline = view.findViewById(R.id.fragemnt_done_headline);
        description = view.findViewById(R.id.fragemnt_done_description);
        finishDate = view.findViewById(R.id.fragemnt_done_finishdate);
        status = view.findViewById(R.id.fragemnt_done_status);
        profession = view.findViewById(R.id.fragemnt_done_profession);
        price = view.findViewById(R.id.fragemnt_done_price);
        interestedVerify = view.findViewById(R.id.fragemnt_done_checkbox);
        editBtn = view.findViewById(R.id.fragemnt_done_editBtn);
        chatBtn = view.findViewById(R.id.fragemnt_done_chatBtn);
        paymentBtn = view.findViewById(R.id.fragemnt_done_payment);
        candidatesBtn = view.findViewById(R.id.fragemnt_done_candidatesBtn);
        logout = view.findViewById(R.id.fragment_done_logoutBtn);


        // Inflate the layout for this fragment

        ModelOffers.instance.getOfferById(offerId, offer -> {
            initSpinnerFooter(offer.getProfession().length, offer.getProfession(), profession);
            headline.setText(offer.getHeadline());
            proposer.setText(offer.getUser());
            description.setText(offer.getDescription());
            finishDate.setText(setValidDate(offer.getFinishDate()));
            status.setText("Done");
            offer.setStatus("Done");
            price.setText(offer.getPrice());
            interestedVerify.setChecked(offer.getIntrestedVerify());
            // In order to change the status in db to done
            ModelOffers.instance.editOffer(offer, new ModelOffers.EditOfferListener() {
                @Override
                public void onComplete(int code) {
                }
            });

        });


        editBtn.setOnClickListener(v -> Navigation.findNavController(v).navigate(DoneStatusFragmentDirections.actionDoneStatusFragmentToEditOfferFragment(offerId)));

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(DoneStatusFragmentDirections.actionDoneStatusFragmentToPaymentFragment());
            }
        });

        candidatesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(DoneStatusFragmentDirections.actionDoneStatusFragmentToCandidatesFragment(offerId));

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modelauth.instance2.logout(new Modelauth.logout() {
                    @Override
                    public void onComplete(int code) {
                        if (code == 200) {
                            toLoginActivity();
                        }
                    }
                });
            }
        });

        return view;
    }

    private String setValidDate(String date){
        String newDate = date.substring(0,2)+"/"+date.substring(2,4)+"/"+date.substring(4);
        return newDate;
    }

    private void initSpinnerFooter(int size, String[] array, Spinner spinner) {
        int tmp = 0;
        for (int j = 0; j < size; j++) {
            if (array[j] != null) {
                tmp++;
            }
        }
        String[] items = new String[tmp];

        for (int i = 0; i < tmp; i++) {
            items[i] = array[i];
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextSize(18);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void toLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}