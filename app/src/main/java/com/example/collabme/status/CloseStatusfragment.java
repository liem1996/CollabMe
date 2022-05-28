package com.example.collabme.status;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.collabme.Activites.LoginActivity;
import com.example.collabme.R;
import com.example.collabme.actionsOnOffers.EditOfferFragmentDirections;
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
 * the close stage offer fragmenrt - inclused :
 * the status after a payment
 * comes after status done
 * the end of the offer
 * delete opertion is excisting for an offer
 */

public class CloseStatusfragment extends Fragment {

    String offerId;
    TextView proposer, status, headline, description, finishDate, price;
    Button delete;
    Spinner profession;
    ImageView logout;
    Offer offer1;
    ImageView offerpic;
    SpinnerAdapter spinnerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offer_close_status, container, false);
        offerId = inprogressfragmentArgs.fromBundle(getArguments()).getOfferId();
        proposer = view.findViewById(R.id.fragemnt_close_proposer);
        headline = view.findViewById(R.id.fragemnt_close_headline);
        description = view.findViewById(R.id.fragemnt_close_description);
        finishDate = view.findViewById(R.id.fragemnt_close_finishdate);
        status = view.findViewById(R.id.fragemnt_close_status);
        profession = view.findViewById(R.id.fragemnt_close_profession);
        price = view.findViewById(R.id.fragemnt_close_price);
        offerpic = view.findViewById(R.id.offer_close_pic2);
        logout = view.findViewById(R.id.fragment_close_logoutBtn);
        delete = view.findViewById(R.id.fragemnt_close_delete);


        // Inflate the layout for this fragment

        ModelOffers.instance.getOfferById(offerId, offer -> {
            offer1 = offer;
            initSpinnerFooter(offer.getProfession().length, offer.getProfession(), profession);
            headline.setText(offer.getHeadline());
            proposer.setText(offer.getUser());
            description.setText(offer.getDescription());
            if(String.valueOf(offer.getFinishDate()).length() == 7){
                String tmp = String.valueOf(offer.getFinishDate());
                tmp = "0" + tmp;
                finishDate.setText(setValidDate(tmp));
            }else finishDate.setText(setValidDate(String.valueOf(offer.getFinishDate())));
            status.setText("Close");
            offer.setStatus("Close");
            price.setText(String.valueOf(offer.getPrice()));

            // In order to change the status in db to close
            ModelOffers.instance.editOffer(offer, new ModelOffers.EditOfferListener() {
                @Override
                public void onComplete(int code) {
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
                                        delete.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                    });

                }
            });

        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Delete Offer");
                builder.setMessage("Are you sure you want to delete this offer?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ModelOffers.instance.getOfferById(offerId, new ModelOffers.GetOfferListener() {
                                    @Override
                                    public void onComplete(Offer offer) {
                                        ModelOffers.instance.deleteoffer(offer, new ModelOffers.deleteoffer() {
                                            @Override
                                            public void onComplete() {
                                                Toast.makeText(getActivity(), "offer deleted", Toast.LENGTH_LONG).show();
                                                Navigation.findNavController(v).navigate(EditOfferFragmentDirections.actionGlobalHomeFragment2(offerId));

                                            }
                                        });
                                    }
                                });
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


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