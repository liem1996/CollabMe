package com.example.collabme.Chat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.collabme.Activites.ChatActivity;
import com.example.collabme.R;
import com.example.collabme.model.ModelUsers;
import com.example.collabme.objects.User;

public class ChatFragment extends Fragment {

   String username;


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
        ModelUsers.instance3.getUserConnect(new ModelUsers.getuserconnect() {
            @Override
            public void onComplete(User profile) {
                username = profile.getUsername();
                tochatActivity();
            }
        });

        return view;
    }

    private void tochatActivity() {
        Intent intent = new Intent(getContext(), ChatActivity.class);
        intent.putExtra("name",username);
        startActivity(intent);
        getActivity().finish();
    }
}