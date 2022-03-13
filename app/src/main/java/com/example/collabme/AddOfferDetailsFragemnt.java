package com.example.collabme;

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
import java.util.UUID;


public class AddOfferDetailsFragemnt extends Fragment {

    EditText proposer,headline,description,finishdate,status,profession, price;
  //  Spinner candidates;
    Button  save;
    CheckBox intrestedVerify;
    Offer offer;
    ArrayList<String> candidatesArr;
    User userConnected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_offer_details_fragemnt, container, false);

        proposer = view.findViewById(R.id.fragemnt_newoffer_proposer);
        headline = view.findViewById(R.id.fragemnt_newoffer_headline);
        description = view.findViewById(R.id.fragemnt_newoffer_description);
        finishdate = view.findViewById(R.id.fragemnt_newoffer_finishdate);
        status = view.findViewById(R.id.fragemnt_newoffer_status);
        profession = view.findViewById(R.id.fragemnt_newoffer_profession);
      //  candidates = view.findViewById(R.id.fragment_newoffer_candidates);
        price = view.findViewById(R.id.fragemnt_newoffer_price);
        intrestedVerify = view.findViewById(R.id.fragemnt_newoffer_checkbox);
        save = view.findViewById(R.id.fragemnt_newoffer_saveBtn);

//        initSpinnerFooter(candidatesArr.size(),candidatesArr,candidates);

        String uniqueKey = UUID.randomUUID().toString();

        Model.instance.getUserConnect(new Model.getuserconnect() {
            @Override
            public void onComplete(User profile) {
                if(profile!=null) {
                    userConnected = profile;
                }
            }
        });

       save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               offer = new Offer(description.getText().toString(),null, headline.getText().toString(),
                       price.getText().toString(),  uniqueKey,  status.getText().toString(),  null,  userConnected,
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

    private void initSpinnerFooter(int size, ArrayList<String> array, Spinner spinner) {
        int tmp = 0;
        for(int j = 0 ; j<size;j++){
            if(array.get(j) != null){
                tmp++;
            }
        }
        String[] items = new String[tmp];

        for(int i = 0 ; i<tmp;i++){
            items[i] = array.get(i);
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