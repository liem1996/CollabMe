package com.example.collabme.Chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.collabme.R;

public class ChatFragment extends Fragment {

    Button tmpPayment;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_chat_first_page, container, false);

        tmpPayment = view.findViewById(R.id.tmp_payment_btn);

        tmpPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Navigation.findNavController(view).navigate(ChatFragmentDirections.actionChatFragmentToPaymentFragment());
            }
        });
        return view;
    }
}