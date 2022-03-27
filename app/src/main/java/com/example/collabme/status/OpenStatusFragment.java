package com.example.collabme.status;

import android.os.Bundle;
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

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.collabme.R;
import com.example.collabme.actionsOnOffers.EditOfferFragmentArgs;
import com.example.collabme.model.ModelOffers;
import com.example.collabme.objects.Offer;


public class OpenStatusFragment extends Fragment {

    String offerId;
    TextView proposer,status, headline, description, finishDate, price;
    Button candidates, chat, upload, edit;
    CheckBox interestedVerify;
    Spinner profession;

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
        upload  = view.findViewById(R.id.fragemnt_offerdetails_uploadBtn);
        candidates = view.findViewById(R.id.fragemnt_offerdetails_candidatesBtn2);

        edit.setOnClickListener(v -> Navigation.findNavController(v).navigate(OpenStatusFragmentDirections.actionGlobalEditOfferFragment(offerId)));

        ModelOffers.instance.getOfferById(offerId, offer -> {
            initSpinnerFooter(offer.getProfession().length,offer.getProfession(),profession);
            headline.setText(offer.getHeadline());
            proposer.setText(offer.getUser());
            description.setText(offer.getDescription());
            finishDate.setText(offer.getFinishDate());
            status.setText("Open");
            price.setText(offer.getPrice());
            interestedVerify.setChecked(offer.getIntrestedVerify());

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