package com.example.collabme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import retrofit2.Retrofit;


public class UserProfile extends Fragment {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:4000";
    TextView username,age,gender,proffesions,followers,postuploads,platform;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        username=view.findViewById(R.id.fragment_userprofile_username);
        age=view.findViewById(R.id.fragment_userprofile_age);
      //  gender=view.findViewById(R.id.fragment_userprofile_);
        followers=view.findViewById(R.id.fragment_userprofile_followers);
        postuploads=view.findViewById(R.id.fragment_userprofile_postsuploads);
        Model.instance.getUserConnect(new Model.getuserconnect() {
            @Override
            public void onComplete(User profile) {
                if(profile!=null) {
                    username.setText(profile.getUsername());
                    followers.setText(profile.getFollowers());
                    postuploads.setText(profile.getNumOfPosts());
                    age.setText(profile.getAge());
                }
            }
        });

        return view;

    }


}