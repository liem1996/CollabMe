package com.example.collabme.status;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.collabme.Activites.LoginActivity;
import com.example.collabme.R;
import com.example.collabme.actionsOnOffers.EditOfferFragmentArgs;
import com.example.collabme.actionsOnOffers.SpinnerAdapter;
import com.example.collabme.model.ModelOffers;
import com.example.collabme.model.ModelPhotos;
import com.example.collabme.model.ModelUsers;
import com.example.collabme.model.Modelauth;
import com.example.collabme.objects.Offer;
import com.example.collabme.objects.User;

import java.util.Arrays;


public class OpenStatusFragment extends Fragment {

    String offerId;
    TextView proposer, status, headline, description, finishDate, price;
    Spinner profession;
    Offer offer2;
    ImageView offerpic;
    ImageView logout;
    ImageButton editBtn, candidatesBtn, backBtn;
    SpinnerAdapter spinnerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offer_open_status, container, false);
        offerId = EditOfferFragmentArgs.fromBundle(getArguments()).getOfferId();
        proposer = view.findViewById(R.id.fragemnt_offerdetails_proposer);
        headline = view.findViewById(R.id.fragemnt_offerdetails_headline);
        description = view.findViewById(R.id.fragemnt_offerdetails_description);
        finishDate = view.findViewById(R.id.fragemnt_offerdetails_finishdate);
        status = view.findViewById(R.id.fragemnt_offerdetails_status);
        profession = view.findViewById(R.id.fragemnt_offerdetails_profession);
        price = view.findViewById(R.id.fragemnt_offerdetails_price);
        editBtn = view.findViewById(R.id.fragemnt_offerdetails_editBtn);

        candidatesBtn = view.findViewById(R.id.fragemnt_offerdetails_candidatesBtn);
        logout = view.findViewById(R.id.fragment_offerdetails_logoutBtn);
        backBtn = view.findViewById(R.id.fragment_offerdetails_backBtn);
        offerpic = view.findViewById(R.id.offer_open_pic);

        ModelOffers.instance.getOfferById(offerId, new ModelOffers.GetOfferListener() {
            @Override
            public void onComplete(Offer offer) {
                ModelPhotos.instance3.getimages(offer.getImage(), new ModelPhotos.getimagesfile() {
                    @Override
                    public void onComplete(Bitmap responseBody) {
                            if (responseBody != null) {
                                offerpic.setImageBitmap(responseBody);
                            }
                        ModelUsers.instance3.getUserConnect(new ModelUsers.getuserconnect() {
                            @Override
                            public void onComplete(User profile) {
                                if (!profile.getUsername().equals(offer.getUser())) {
                                    candidatesBtn.setVisibility(View.GONE);
                                    editBtn.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                });


            }
        });

        editBtn.setOnClickListener(v -> Navigation.findNavController(v).navigate(OpenStatusFragmentDirections.actionGlobalEditOfferFragment(offerId)));
        backBtn.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        ModelOffers.instance.getOfferById(offerId, offer -> {
            if (offer != null) {
                initSpinnerFooter(offer.getProfession().length, offer.getProfession(), profession);
                headline.setText(offer.getHeadline());
                proposer.setText(offer.getUser());
                description.setText(offer.getDescription());
                finishDate.setText(setValidDate(offer.getFinishDate()));
                status.setText(offer.getStatus());
                price.setText(offer.getPrice());
                offer2 = new Offer(description.getText().toString(), headline.getText().toString(), finishDate.getText().toString(), price.getText().toString(), offerId, status.getText().toString(), offer.getProfession(), offer.getUser());
            }
        });

        candidatesBtn.setOnClickListener(new View.OnClickListener() {
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
                        if (code == 200) {
                            toLoginActivity();
                        }
                    }
                });
            }
        });



        return view;
    }

    private String setValidDate(String date) {
        String newDate = date.substring(0, 2) + "/" + date.substring(2, 4) + "/" + date.substring(4);
        return newDate;
    }

    private void initSpinnerFooter(int size, String[] array, Spinner spinner) {
        spinnerAdapter = new SpinnerAdapter(getContext(), Arrays.asList(array));
        spinner.setAdapter(spinnerAdapter);
//        int tmp = 0;
//        for (int j = 0; j < size; j++) {
//            if (array[j] != null) {
//                tmp++;
//            }
//        }
//        String[] items = new String[tmp];
//
//        for (int i = 0; i < tmp; i++) {
//            items[i] = array[i];
//        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ((TextView) parent.getChildAt(0)).setTextSize(18);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
    }

    private void toLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


}