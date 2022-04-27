package com.example.collabme.status;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.collabme.Activites.LoginActivity;
import com.example.collabme.R;
import com.example.collabme.actionsOnOffers.SpinnerAdapter;
import com.example.collabme.model.ModelOffers;
import com.example.collabme.model.ModelPhotos;
import com.example.collabme.model.ModelUsers;
import com.example.collabme.model.Modelauth;
import com.example.collabme.objects.User;

import java.util.Arrays;


public class inprogressfragment extends Fragment {
    String offerId;
    TextView proposer, status, headline, description, finishDate, price;
    Button upload;

    ImageButton editBtn, candidatesBtn, backBtn;
    ImageView offerpic;
    Spinner profession;
    ImageView logout;
    String offerUsername;
    SpinnerAdapter spinnerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offer_inprogress_status, container, false);
        offerId = inprogressfragmentArgs.fromBundle(getArguments()).getOfferId();
        proposer = view.findViewById(R.id.fragemnt_inprogress_proposer);
        headline = view.findViewById(R.id.fragemnt_inprogress_headline);
        description = view.findViewById(R.id.fragemnt_inprogress_description);
        finishDate = view.findViewById(R.id.fragemnt_inprogress_finishdate);
        status = view.findViewById(R.id.fragment_inprogress_status);
        profession = view.findViewById(R.id.fragment_inprogress_profession);
        price = view.findViewById(R.id.fragemnt_inprogress_price);
        offerpic = view.findViewById(R.id.offer_inprogress_pic2);
        editBtn = view.findViewById(R.id.fragemnt_inprogress_edit);

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
                    ModelPhotos.instance3.getimages(offer.getImage(), new ModelPhotos.getimagesfile() {
                        @Override
                        public void onComplete(Bitmap responseBody) {
                            if (responseBody != null) {
                                offerpic.setImageBitmap(responseBody);
                            }
                            initSpinnerFooter(offer.getProfession().length,offer.getProfession(),profession);
                            headline.setText(offer.getHeadline());
                            proposer.setText(offer.getUser());
                            description.setText(offer.getDescription());
                            finishDate.setText(setValidDate(offer.getFinishDate()));
                            status.setText(offer.getStatus());
                            price.setText(String.valueOf(offer.getPrice()));

                            offerUsername = offer.getUser();
                        }
                    });

                }
            });

        });

        editBtn.setOnClickListener(v -> Navigation.findNavController(v).navigate(inprogressfragmentDirections.actionInprogressfragmentToEditOfferFragment(offerId)));
        backBtn.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price2= Integer.parseInt(price.getText().toString());
                Navigation.findNavController(v).navigate(inprogressfragmentDirections.actionInprogressfragmentToFragmentMediaContent(offerId,headline.toString(),price2));
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