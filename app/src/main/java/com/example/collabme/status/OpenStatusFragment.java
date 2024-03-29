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
import com.facebook.login.LoginManager;

import java.util.Arrays;
/**
 * the open stage offer fragmenrt - inclused :
 * the status after the proposer open the offer and waits to candidte to love its offer
 * the first status
 * lead to the candidate page for the proposer to choose a candidate from list of users
 * after choosing candidate going to inprogress status
 */

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

        ModelOffers.instance.getOfferById(offerId, offer -> {
            if (offer != null) {
                initSpinnerFooter(offer.getProfession().length, offer.getProfession(), profession);
                headline.setText(offer.getHeadline());
                proposer.setText(offer.getUser());
                description.setText(offer.getDescription());
                if(String.valueOf(offer.getFinishDate()).length() == 7){
                    String tmp = String.valueOf(offer.getFinishDate());
                    tmp = "0" + tmp;
                    finishDate.setText(setValidDate(tmp));
                }else finishDate.setText(setValidDate(String.valueOf(offer.getFinishDate())));
                status.setText(offer.getStatus());
                price.setText(String.valueOf(offer.getPrice()));
            }
        });

        editBtn.setOnClickListener(v -> Navigation.findNavController(v).navigate(OpenStatusFragmentDirections.actionGlobalEditOfferFragment(offerId)));
        backBtn.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        candidatesBtn.setOnClickListener(v -> Navigation.findNavController(v).navigate(OpenStatusFragmentDirections.actionOfferDetailsFragmentToCandidatesFragment(offerId)));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modelauth.instance2.logout(new Modelauth.logout() {
                    @Override
                    public void onComplete(int code) {
                        if (code == 200) {
                            ModelUsers.instance3.setUserConnected(null);
                            LoginManager.getInstance().logOut();
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

    }

    private void toLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}