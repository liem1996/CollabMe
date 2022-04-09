package com.example.collabme.Chat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.collabme.R;

public class ChatFragment extends Fragment {

    Button tmpPayment,chatfragment;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},10);
        }
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        tmpPayment = view.findViewById(R.id.tmp_payment_btn);
        chatfragment = view.findViewById(R.id.chatfragmentbutton);


        tmpPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Navigation.findNavController(view).navigate(ChatFragmentDirections.actionChatFragmentToPaymentFragment());
            }
        });




        chatfragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(ChatFragmentDirections.actionChatFragmentToChatFirstPage("1"));
            }
        });




        return view;
    }
}