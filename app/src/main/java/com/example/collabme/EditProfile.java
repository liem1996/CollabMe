package com.example.collabme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class EditProfile extends Fragment {
    TextView username,age,followers,postuploads;
    Spinner professions,platform;
    ArrayList<String> platformArr;
    Button chat,edit,cancel,uploadphoto,delete;
    ArrayList<String> professionsArr;
    String [] plat;
    String [] pref;
    String password ;
    Boolean influencer1, company1;
    String email1;
    String gender1;
    String username1;
    String age1;
    String Followers;
    String posts;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        username=view.findViewById(R.id.fragment_edituser_username2);

        age=view.findViewById(R.id.fragment_edituser_age);
        platform=view.findViewById(R.id.fragemnt_edituser_platform);
        professions=view.findViewById(R.id.fragemnt_edituser_proffesions);
        followers=view.findViewById(R.id.fragment_edituser_followers);
        postuploads=view.findViewById(R.id.fragment_edituser_postsuploads);
        delete=view.findViewById(R.id.fragemnt_edituser_delete);
        uploadphoto=view.findViewById(R.id.fragemnt_edituser_upload);
        edit=view.findViewById(R.id.fragemnt_edituser_save);
        cancel =view.findViewById(R.id.fragemnt_edituser_cancel);

        username1 = EditProfileArgs.fromBundle(getArguments()).getUsername();
        password = EditProfileArgs.fromBundle(getArguments()).getPassword();
        email1 = EditProfileArgs.fromBundle(getArguments()).getEmail();
        gender1 = EditProfileArgs.fromBundle(getArguments()).getGender();
        age1 = EditProfileArgs.fromBundle(getArguments()).getAge();
        Followers = EditProfileArgs.fromBundle(getArguments()).getFollowers();
        plat = EditProfileArgs.fromBundle(getArguments()).getPlatform();
        pref = EditProfileArgs.fromBundle(getArguments()).getProfession();
        posts = EditProfileArgs.fromBundle(getArguments()).getPostsuploads();
        influencer1 = EditProfileArgs.fromBundle(getArguments()).getInfluencer();
        company1 = EditProfileArgs.fromBundle(getArguments()).getCompany();

        username.setText(username1);
        age.setText(age1);
       // platform.
        //professions
        followers.setText(Followers);
        postuploads.setText(posts);
        //delete
        uploadphoto.setText(posts);
        //edit
        //cancel








        return view;
        // Inflate the layout for this fragment
    }
}