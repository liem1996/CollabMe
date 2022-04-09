package com.example.collabme.Chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.collabme.R;
import com.example.collabme.model.ModelChat;


public class ChatConnectionFragment extends Fragment {
    private String Nickname ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_connection, container, false);
        Nickname= ChatConnectionFragmentArgs.fromBundle(getArguments()).getUsername();
        ModelChat.instance2.ChatFuctionWithSocke(Nickname, new ModelChat.ListentoSocke() {
            @Override
            public void onComplete(int code) {
                Toast.makeText(getActivity(), "yass", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}