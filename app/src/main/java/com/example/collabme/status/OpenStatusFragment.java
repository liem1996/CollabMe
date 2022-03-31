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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.collabme.Activites.LoginActivity;
import com.example.collabme.R;
import com.example.collabme.actionsOnOffers.EditOfferFragmentArgs;
import com.example.collabme.model.ModelOffers;
import com.example.collabme.model.Modelauth;
import com.example.collabme.objects.Offer;


public class OpenStatusFragment extends Fragment {

    String offerId;
    TextView proposer,status, headline, description, finishDate, price;
    Button candidates, chat, choosen, edit;
    CheckBox interestedVerify;
    Spinner profession;
    Offer offer2;
    ImageView logout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offer_openstatus, container, false);
        offerId = EditOfferFragmentArgs.fromBundle(getArguments()).getOfferId();
        proposer = view.findViewById(R.id.fragemnt_offerdetails_proposer);
        headline = view.findViewById(R.id.fragemnt_offerdetails_headline);
        description = view.findViewById(R.id.fragemnt_offerdetails_description);
        finishDate = view.findViewById(R.id.fragemnt_offerdetails_finishdate);
        status = view.findViewById(R.id.fragemnt_offerdetails_status);
        profession = view.findViewById(R.id.fragemnt_offerdetails_profession);
        price = view.findViewById(R.id.fragemnt_offerdetails_price);
        interestedVerify = view.findViewById(R.id.fragemnt_offerdetails_checkbox);
        edit = view.findViewById(R.id.fragemnt_offerdetails_editBtn);
        chat  = view.findViewById(R.id.fragemnt_offerdetails_chatBtn);
        choosen  = view.findViewById(R.id.fragemnt_offerdetails_choosenBtn);
        candidates = view.findViewById(R.id.fragemnt_offerdetails_candidatesBtn2);
        logout = view.findViewById(R.id.fragment_offerdetails_logoutBtn);


        edit.setOnClickListener(v -> Navigation.findNavController(v).navigate(OpenStatusFragmentDirections.actionGlobalEditOfferFragment(offerId)));

        ModelOffers.instance.getOfferById(offerId, offer -> {

            initSpinnerFooter(offer.getProfession().length,offer.getProfession(),profession);
            headline.setText(offer.getHeadline());
            proposer.setText(offer.getUser());
            description.setText(offer.getDescription());
            finishDate.setText(offer.getFinishDate());
            status.setText(offer.getStatus());
            price.setText(offer.getPrice());
            interestedVerify.setChecked(offer.getIntrestedVerify());
            offer2=new Offer(description.getText().toString(),headline.getText().toString(),finishDate.getText().toString(),price.getText().toString(),offerId,status.getText().toString(),offer.getProfession(),offer.getUser(),interestedVerify.isChecked());

        });

        choosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offer2.setStatus("inprogress");
                ModelOffers.instance.editOffer(offer2, new ModelOffers.EditOfferListener() {
                    @Override
                    public void onComplete(int code) {
                        if(code==200) {
                            Navigation.findNavController(v).navigate(OpenStatusFragmentDirections.actionOfferDetailsFragmentToInprogressfragment(offerId));
                        }
                    }
                });
            }
        });

        candidates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(OpenStatusFragmentDirections.actionOfferDetailsFragmentToCandidatesFragment(offerId));

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modelauth.instance2.logout(new Modelauth.logout() {
                    @Override
                    public void onComplete(int code) {
                        if(code==200) {
                            toLoginActivity();
                        }
                        else{

                        }
                    }
                });
            }
        });

        return view;
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

    private void toLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}