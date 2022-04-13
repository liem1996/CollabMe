package com.example.collabme.Chat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.collabme.R;
import com.hbb20.CountryCodePicker;


public class whatupPage extends Fragment {

    CountryCodePicker countryCodePicker;
    EditText phone, message;
    Button sendbtn;
    String messagestr, phonestr = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_whatup_page, container, false);
        countryCodePicker = view.findViewById(R.id.countryCode);
        phone = view.findViewById(R.id.phoneNo);
        message = view.findViewById(R.id.message);
        sendbtn = view.findViewById(R.id.sendbtn);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                messagestr = message.getText().toString();
                phonestr = phone.getText().toString();

                if (!messagestr.isEmpty() && !phonestr.isEmpty()) {

                    countryCodePicker.registerCarrierNumberEditText(phone);
                    phonestr = countryCodePicker.getFullNumber();

                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone="+phonestr+
                            "&text="+messagestr));
                    startActivity(i);
                    message.setText("");
                    phone.setText("");

                } else {

                    Toast.makeText(getActivity(), "Please fill in the Phone no. and message it can't be empty", Toast.LENGTH_LONG).show();

                }

            }
        });


        // Inflate the layout for this fragment
        return view;
    }
}