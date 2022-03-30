package com.example.collabme.status;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.collabme.R;
import com.example.collabme.model.ModelOffers;


public class DoneStatusFragment extends Fragment {

    String offerId;
    TextView proposer,status, headline, description, finishDate, price;
    Button candidates, chat, payment, edit;
    CheckBox interestedVerify;
    Spinner profession;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inprogressfragment, container, false);
        offerId = inprogressfragmentArgs.fromBundle(getArguments()).getOfferId();
        proposer = view.findViewById(R.id.fragemnt_done_proposer);
        headline = view.findViewById(R.id.fragemnt_done_headline);
        description = view.findViewById(R.id.fragemnt_done_description);
        finishDate = view.findViewById(R.id.fragemnt_done_finishdate);
        status = view.findViewById(R.id.fragemnt_done_status);
        profession = view.findViewById(R.id.fragemnt_done_profession);
        price = view.findViewById(R.id.fragemnt_done_price);
        interestedVerify = view.findViewById(R.id.fragemnt_done_checkbox);
        edit = view.findViewById(R.id.fragemnt_done_editBtn);
        chat  = view.findViewById(R.id.fragemnt_done_chatBtn);
        payment  = view.findViewById(R.id.fragemnt_done_payment);
        candidates = view.findViewById(R.id.fragemnt_done_candidatesBtn2);

        // Inflate the layout for this fragment

        ModelOffers.instance.getOfferById(offerId, offer -> {
            initSpinnerFooter(offer.getProfession().length,offer.getProfession(),profession);
            headline.setText(offer.getHeadline());
            proposer.setText(offer.getUser());
            description.setText(offer.getDescription());
            finishDate.setText(offer.getFinishDate());
            status.setText(offer.getStatus());
            price.setText(offer.getPrice());
            interestedVerify.setChecked(offer.getIntrestedVerify());
        });

        edit.setOnClickListener(v -> Navigation.findNavController(v).navigate(DoneStatusFragmentDirections.actionDoneStatusFragmentToEditOfferFragment(offerId)));


        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(DoneStatusFragmentDirections.actionDoneStatusFragmentToPaymentFragment());
            }
        });


        candidates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(DoneStatusFragmentDirections.actionDoneStatusFragmentToCandidatesFragment(offerId));

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
}