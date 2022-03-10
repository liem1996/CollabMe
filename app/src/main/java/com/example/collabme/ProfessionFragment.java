package com.example.collabme;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfessionFragment extends Fragment implements View.OnClickListener{

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:4000";

    Button continueBtn;

    String username, password, email, gender, age, followers, numOfPosts;
    boolean company, influencer;
    String[] platforms,professions;
    int i = 0;

    Button sport, cooking,fashion, music, dance, cosmetic, travel, gaming, tech, food, art, animals, movies, photograph, other, lifestyle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profession, container, false);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        sport = view.findViewById(R.id.fragemnt_profession_sport);
        sport.setOnClickListener(this);
        cooking = view.findViewById(R.id.fragemnt_profession_cooking);
        cooking.setOnClickListener(this);
        fashion = view.findViewById(R.id.fragemnt_profession_fashion);
        fashion.setOnClickListener(this);
        music = view.findViewById(R.id.fragemnt_profession_music);
        music.setOnClickListener(this);
        dance = view.findViewById(R.id.fragemnt_profession_dance);
        dance.setOnClickListener(this);
        cosmetic = view.findViewById(R.id.fragemnt_profession_cosmetic);
        cosmetic.setOnClickListener(this);
        travel = view.findViewById(R.id.fragemnt_profession_travel);
        travel.setOnClickListener(this);
        gaming = view.findViewById(R.id.fragemnt_profession_gaming);
        gaming.setOnClickListener(this);
        tech = view.findViewById(R.id.fragemnt_profession_tech);
        tech.setOnClickListener(this);
        food = view.findViewById(R.id.fragemnt_profession_food);
        food.setOnClickListener(this);
        art = view.findViewById(R.id.fragemnt_profession_art);
        art.setOnClickListener(this);
        animals = view.findViewById(R.id.fragemnt_profession_animals);
        animals.setOnClickListener(this);
        movies = view.findViewById(R.id.fragemnt_profession_movies);
        movies.setOnClickListener(this);
        photograph = view.findViewById(R.id.fragemnt_profession_photograph);
        photograph.setOnClickListener(this);
        other = view.findViewById(R.id.fragemnt_profession_other);
        other.setOnClickListener(this);
        lifestyle = view.findViewById(R.id.fragemnt_profession_lifestyle);
        lifestyle.setOnClickListener(this);

        professions = new String[16];
        influencer =  ProfessionFragmentArgs.fromBundle(getArguments()).getInfluencer();
        company =  ProfessionFragmentArgs.fromBundle(getArguments()).getCompany();
        username = ProfessionFragmentArgs.fromBundle(getArguments()).getUsername();
        password = ProfessionFragmentArgs.fromBundle(getArguments()).getPassword();
        email =  ProfessionFragmentArgs.fromBundle(getArguments()).getEmail();
        age =ProfessionFragmentArgs.fromBundle(getArguments()).getAge();
        gender = ProfessionFragmentArgs.fromBundle(getArguments()).getGender();
        followers = ProfessionFragmentArgs.fromBundle(getArguments()).getFollowers();
        numOfPosts = ProfessionFragmentArgs.fromBundle(getArguments()).getPostsuploads();
        platforms = ProfessionFragmentArgs.fromBundle(getArguments()).getPlatform();

        continueBtn = view.findViewById(R.id.fragemnt_profession_continuebtn);
        continueBtn.setOnClickListener(v-> handleSighUp());

        return view;
    }

    private void handleSighUp() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("Username", username);
        map.put("Password", password);
        map.put("Email", email);
        map.put("Sex", gender);
        map.put("Age", age);
        map.put("Followers", followers);
        map.put("NumberOfPosts", numOfPosts);
        map.put("Company", company);
        map.put("Influencer", influencer);
        map.put("Profession", professions);
        map.put("Platform", platforms);

        Call<Void> call = retrofitInterface.executeSignup(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Toast.makeText(getActivity(), "sigh up", Toast.LENGTH_LONG).show();

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



    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragemnt_profession_sport:
                professions[i] = "Sport";
                i++;
                break;
            case R.id.fragemnt_profession_animals:
                professions[i] = "Animals";
                i++;
                break;
            case R.id.fragemnt_profession_art:
                professions[i] = "Art";
                i++;
                break;
            case R.id.fragemnt_profession_cooking:
                professions[i] = "Cooking";
                i++;
                break;
            case R.id.fragemnt_profession_fashion:
                professions[i] = "Fashion";
                i++;
                break;
            case R.id.fragemnt_profession_cosmetic:
                professions[i] = "Cosmetic";
                i++;
                break;
            case R.id.fragemnt_profession_music:
                professions[i] = "Music";
                i++;
                break;
            case R.id.fragemnt_profession_dance:
                professions[i] = "Dance";
                i++;
                break;
            case R.id.fragemnt_profession_travel:
                professions[i] = "Travel";
                i++;
                break;
            case R.id.fragemnt_profession_gaming:
                professions[i] = "Gaming";
                i++;
                break;
            case R.id.fragemnt_profession_food:
                professions[i] = "Food";
                i++;
                break;
            case R.id.fragemnt_profession_tech:
                professions[i] = "Tech";
                i++;
                break;
            case R.id.fragemnt_profession_movies:
                professions[i] = "Movies";
                i++;
                break;
            case R.id.fragemnt_profession_photograph:
                professions[i] = "Photograph";
                i++;
                break;
            case R.id.fragemnt_profession_lifestyle:
                professions[i] = "Lifestyle";
                i++;
                break;
            case R.id.fragemnt_profession_other:
                professions[i] = "Other";
                i++;
                break;

        }
    }
}