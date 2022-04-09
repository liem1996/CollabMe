package com.example.collabme.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.collabme.Activites.ChatActivity;
import com.example.collabme.R;


public class ChatFirstPage extends Fragment {
    private Button btn;
    private EditText nickname;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_chat_first_page, container, false);
        btn = view.findViewById(R.id.nicname_button) ;
        nickname = view.findViewById(R.id.nickname);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the nickname is not empty go to chatbox activity and add the nickname to the intent extra
                if(!nickname.getText().toString().isEmpty()){
                    tochatActivity();
                }
            }
        });
        return view;
    }

    private void tochatActivity() {
        Intent intent = new Intent(getContext(), ChatActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}