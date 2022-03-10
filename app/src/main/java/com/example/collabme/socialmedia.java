package com.example.collabme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class socialmedia extends Fragment {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:4000";
    CheckBox instegram, twitter,tiktok,facebook,youtube;
    Button countinue;
    EditText followers, posts;
    String username;
    String password;
    View view;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_socialmedia, container, false);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        instegram = view.findViewById(R.id.fragment_socialmedia_instagram);
        twitter = view.findViewById(R.id.fragment_socialmedia_twitter);
        facebook = view.findViewById(R.id.fragment_socialmedia_facebook);
        tiktok = view.findViewById(R.id.fragment_socialmedia_tiktok);
        youtube = view.findViewById(R.id.fragment_socialmedia_youtube);

        followers = view.findViewById(R.id.fragment_socialmedia_followers);
        posts = view.findViewById(R.id.fragment_socialmedia_postsuploads);
        countinue.setOnClickListener(v -> handleSighUp());
        username = socialmediaArgs.fromBundle(getArguments()).getUsername();
        password = socialmediaArgs.fromBundle(getArguments()).getPassword();


        return view;
    }

    private void handleSighUp() {
        String [] platform = new String[5];
        int i=0;


        if(instegram.isChecked()){
            platform[i] = "instegram";
            i++;

        }else if (twitter.isChecked()){
            platform[i] = "twitter";
            i++;

        }else if(facebook.isChecked()){
            platform[i] = "facebook";
            i++;

        }else if (tiktok.isChecked()){
            platform[i] = "tiktok";
            i++;

        }else if(youtube.isChecked()){
            platform[i] = "youtube";
            i++;
        }



        HashMap<String, Object> map = new HashMap<>();
        map.put("Platform", platform);
        map.put("Password", password);
        map.put("Username", username);
        map.put("Followers", followers.getText().toString());
        map.put("NumberOfPosts", posts.getText().toString());

        Call<Void> call = retrofitInterface.executeSignup(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Toast.makeText(getActivity(), "yay", Toast.LENGTH_LONG).show();

                } else if (response.code() == 400) {
                    Toast.makeText(getActivity(), "not sighup", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}