package com.example.collabme.offers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.collabme.R;

public class PaymentFragment extends Fragment {

    TextView cardNumber, expDate, cvv, id, name, offer, bankAccount;
    Button backBtn, submit;

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

        return view;

    }
}