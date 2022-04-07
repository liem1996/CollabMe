package com.example.collabme.pagesForOffers;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.collabme.Activites.LoginActivity;
import com.example.collabme.R;
import com.example.collabme.model.Modelauth;

public class PaymentFragment extends Fragment {

    TextView cardNumber, expDate, cvv, id, name, offer, bankAccount;
    Button backBtn, submit;
    ImageView logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        cardNumber = view.findViewById(R.id.fragment_payment_card_number_et);
        expDate = view.findViewById(R.id.fragment_payment_exp_et);
        cvv = view.findViewById(R.id.fragment_payment_cvv_et);
        id = view.findViewById(R.id.fragment_payment_id_et);
        name = view.findViewById(R.id.fragment_payment_name_et);
        offer = view.findViewById(R.id.fragment_payment_offer_et);
        bankAccount = view.findViewById(R.id.fragment_payment_bank_account_et);
        logout =view.findViewById(R.id.fragment_payment_logoutBtn);

        backBtn = view.findViewById(R.id.fragment_payment_back_btn);
        submit = view.findViewById(R.id.fragment_payment_submit_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to "MyOfferFragment" - change status to close
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
                    }
                });
            }
        });

        return view;

    }

    private void toLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}