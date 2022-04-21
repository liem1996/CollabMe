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
import com.example.collabme.model.ModelUsers;
import com.example.collabme.model.Modelauth;
import com.example.collabme.objects.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class inprogressfragment extends Fragment {
    String offerId;
    TextView proposer, status, headline, description, finishDate, price;
    Button upload;
    FloatingActionButton chatBtn;
    ImageButton editBtn, candidatesBtn, backBtn;
    CheckBox interestedVerify;
    Spinner profession;
    ImageView logout;
    String offerUsername;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inprogressfragment, container, false);
        offerId = inprogressfragmentArgs.fromBundle(getArguments()).getOfferId();
        proposer = view.findViewById(R.id.fragemnt_inprogress_proposer);
        headline = view.findViewById(R.id.fragemnt_inprogress_headline);
        description = view.findViewById(R.id.fragemnt_inprogress_description);
        finishDate = view.findViewById(R.id.fragemnt_inprogress_finishdate);
        status = view.findViewById(R.id.fragment_inprogress_status);
        profession = view.findViewById(R.id.fragment_inprogress_profession);
        price = view.findViewById(R.id.fragemnt_inprogress_price);
        interestedVerify = view.findViewById(R.id.fragemnt_inprogress_checkbox);
        editBtn = view.findViewById(R.id.fragemnt_inprogress_edit);
        chatBtn = view.findViewById(R.id.fragemnt_inprogress_chat);
        upload = view.findViewById(R.id.fragemnt_inprogress_upload);
        logout = view.findViewById(R.id.fragment_inprogress_logoutBtn);
        backBtn = view.findViewById(R.id.fragment_inprogress_backBtn);

        // Inflate the layout for this fragment

        ModelOffers.instance.getOfferById(offerId, offer -> {
            ModelUsers.instance3.getUserConnect(new ModelUsers.getuserconnect() {
                @Override
                public void onComplete(User profile) {
                    if(!profile.getUsername().equals(offer.getUser())){
                        editBtn.setVisibility(View.GONE);
                    }
                    initSpinnerFooter(offer.getProfession().length,offer.getProfession(),profession);
                    headline.setText(offer.getHeadline());
                    proposer.setText(offer.getUser());
                    description.setText(offer.getDescription());
                    finishDate.setText(setValidDate(offer.getFinishDate()));
                    status.setText(offer.getStatus());
                    price.setText(offer.getPrice());
                    interestedVerify.setChecked(offer.getIntrestedVerify());
                    offerUsername = offer.getUser();
                }
            });

        });

        editBtn.setOnClickListener(v -> Navigation.findNavController(v).navigate(inprogressfragmentDirections.actionInprogressfragmentToEditOfferFragment(offerId)));
        backBtn.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(inprogressfragmentDirections.actionInprogressfragmentToFragmentMediaContent(offerId,headline.toString(),price.toString()));
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
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(inprogressfragmentDirections.actionGlobalChatFragmentopen(offerUsername));

            }
        });

        return view;
    }

    private String setValidDate(String date) {
        String newDate = date.substring(0, 2) + "/" + date.substring(2, 4) + "/" + date.substring(4);
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