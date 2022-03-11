package com.example.collabme;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Arrays;

import retrofit2.Retrofit;


public class ProfessionFragment extends Fragment implements View.OnClickListener{

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:4000";

    Button continueBtn;

    String username, password, email, gender, age, followers, numOfPosts;
    boolean company, influencer;
    String[] platforms,professions;
    int i = 0;
    int index=0;

    Button sport, cooking,fashion, music, dance, cosmetic, travel, gaming, tech, food, art, animals, movies, photograph, other, lifestyle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profession, container, false);


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

        User user = new User(gender,username,password,email,age,followers,numOfPosts,company,influencer,professions,platforms);

        continueBtn = view.findViewById(R.id.fragemnt_profession_continuebtn);
        continueBtn.setOnClickListener(v-> Model.instance.sighup(user, new Model.sighup() {
            @Override
            public void onComplete(int code) {
                if(code==200){
                    Toast.makeText(getActivity(),"yes", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(), "no", Toast.LENGTH_LONG).show();
                }
            }
        }));

        return view;
    }



    public int indexOfValue(String proffesion){
        int getindex = Arrays.asList(professions).indexOf(proffesion);
        return getindex;
    }



    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragemnt_profession_sport:
                index= indexOfValue("Sport");
                if(index!=-1){
                    sport.setBackgroundColor(Color.DKGRAY);
                    professions[index] = null;
                }else {
                    sport.setBackgroundColor(sport.getContext().getResources().getColor(R.color.purple));
                    professions[i] = "Sport";
                }
                i++;
                break;
            case R.id.fragemnt_profession_animals:
                index= indexOfValue("Animals");
                if(index!=-1){
                    animals.setBackgroundColor(Color.DKGRAY);
                    professions[index] = null;
                }else {
                    animals.setBackgroundColor(animals.getContext().getResources().getColor(R.color.purple));
                    professions[i] = "Animals";
                }
                i++;
                break;
            case R.id.fragemnt_profession_art:
                index= indexOfValue("Art");
                if(index!=-1){
                    art.setBackgroundColor(Color.DKGRAY);
                    professions[index] = null;
                }else {
                    art.setBackgroundColor(art.getContext().getResources().getColor(R.color.purple));
                    professions[i] = "Art";
                }
                i++;
                break;
            case R.id.fragemnt_profession_cooking:
                index= indexOfValue("Cooking");
                if(index!=-1){
                    cooking.setBackgroundColor(Color.DKGRAY);
                    professions[index] = null;
                }else {
                    cooking.setBackgroundColor(cooking.getContext().getResources().getColor(R.color.purple));
                    professions[i] = "Cooking";
                }
                i++;
                break;
            case R.id.fragemnt_profession_fashion:
                index= indexOfValue("Fashion");
                if(index!=-1){
                    fashion.setBackgroundColor(Color.DKGRAY);
                    professions[index] = null;
                }else {
                    fashion.setBackgroundColor(fashion.getContext().getResources().getColor(R.color.purple));
                    professions[i] = "Fashion:";
                }
                i++;
                break;
            case R.id.fragemnt_profession_cosmetic:
                index= indexOfValue("Cosmetic");
                if(index!=-1){
                    cosmetic.setBackgroundColor(Color.DKGRAY);
                    professions[index] = null;
                }else {
                    cosmetic.setBackgroundColor(cosmetic.getContext().getResources().getColor(R.color.purple));
                    professions[i] = "Cosmetic";
                }
                i++;
                break;
            case R.id.fragemnt_profession_music:
                index= indexOfValue("Music");
                if(index!=-1){
                    music.setBackgroundColor(Color.DKGRAY);
                    professions[index] = null;
                }else {
                    music.setBackgroundColor(music.getContext().getResources().getColor(R.color.purple));
                    professions[i] = "Music";
                }
                i++;
                break;
            case R.id.fragemnt_profession_dance:
                index= indexOfValue("Dance");
                if(index!=-1){
                    dance.setBackgroundColor(Color.DKGRAY);
                    professions[index] = null;
                }else {
                    dance.setBackgroundColor(dance.getContext().getResources().getColor(R.color.purple));
                    professions[i] = "Dance";
                }
                i++;
                break;
            case R.id.fragemnt_profession_travel:
                index= indexOfValue("Travel");
                if(index!=-1){
                    travel.setBackgroundColor(Color.DKGRAY);
                    professions[index] = null;
                }else {
                    travel.setBackgroundColor(travel.getContext().getResources().getColor(R.color.purple));
                    professions[i] = "Travel";
                }
                i++;
                break;
            case R.id.fragemnt_profession_gaming:
                index= indexOfValue("Gaming");
                if(index!=-1){
                    gaming.setBackgroundColor(Color.DKGRAY);
                    professions[index] = null;
                }else {
                    gaming.setBackgroundColor(gaming.getContext().getResources().getColor(R.color.purple));
                    professions[i] = "Gaming";
                }
                i++;

                break;
            case R.id.fragemnt_profession_food:
                index= indexOfValue("Food");
                if(index!=-1){
                    food.setBackgroundColor(Color.DKGRAY);
                    professions[index] = null;
                }else {
                    food.setBackgroundColor(food.getContext().getResources().getColor(R.color.purple));
                    professions[i] = "Food";
                }
                i++;
                break;
            case R.id.fragemnt_profession_tech:
                index= indexOfValue("Tech");
                if(index!=-1){
                    tech.setBackgroundColor(Color.DKGRAY);
                    professions[index] = null;
                }else {
                    tech.setBackgroundColor(tech.getContext().getResources().getColor(R.color.purple));
                    professions[i] = "Tech";
                }
                i++;
                break;
            case R.id.fragemnt_profession_movies:
                index= indexOfValue("Movies");
                if(index!=-1){
                    movies.setBackgroundColor(Color.DKGRAY);
                    professions[index] = null;
                }else {
                    movies.setBackgroundColor(movies.getContext().getResources().getColor(R.color.purple));
                    professions[i] = "Movies";
                }
                i++;
                break;
            case R.id.fragemnt_profession_photograph:
                index= indexOfValue("Photograph");
                if(index!=-1){
                    photograph.setBackgroundColor(Color.DKGRAY);
                    professions[index] = null;
                }else {
                    photograph.setBackgroundColor(photograph.getContext().getResources().getColor(R.color.purple));
                    professions[i] = "Photograph";
                }
                i++;
                break;
            case R.id.fragemnt_profession_lifestyle:
                index= indexOfValue("Lifestyle");
                if(index!=-1){
                    lifestyle.setBackgroundColor(Color.DKGRAY);
                    professions[index] = null;
                }else {
                    lifestyle.setBackgroundColor(lifestyle.getContext().getResources().getColor(R.color.purple));
                    professions[i] = "Lifestyle";
                }
                i++;
                break;
            case R.id.fragemnt_profession_other:
                index= indexOfValue("Other");
                if(index!=-1){
                    other.setBackgroundColor(Color.DKGRAY);
                    professions[index] = null;
                }else {
                    other.setBackgroundColor(other.getContext().getResources().getColor(R.color.purple));
                    professions[i] = "Other";
                }
                i++;
                break;

        }
    }
}