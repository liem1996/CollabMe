package com.example.collabme.TheSighUPProcess;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.collabme.Activites.MainActivity;
import com.example.collabme.R;
import com.example.collabme.model.Modelauth;
import com.example.collabme.objects.User;

import java.util.Arrays;


public class ProfessionFragment extends Fragment implements View.OnClickListener{

     Button continueBtn, backBtn;

    String username, password, email, gender, age, followers, numOfPosts;
    boolean company, influencer;
    String[] platforms,professions;
    int i = 0;
    int index=0;
    Bitmap bitmap;


    Button sport, cooking,fashion, music, dance, cosmetic, travel, gaming, tech, food, art, animals, movies, photograph, other, lifestyle;


    private void toFeedActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

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

        backBtn = view.findViewById(R.id.fragemnt_profession_backbtn);

        professions = new String[16];
        influencer =  ProfessionFragmentArgs.fromBundle(getArguments()).getInfluencer();
        company =  ProfessionFragmentArgs.fromBundle(getArguments()).getCompany();
        username = ProfessionFragmentArgs.fromBundle(getArguments()).getUsername();
        password = ProfessionFragmentArgs.fromBundle(getArguments()).getPassword();
        email =  ProfessionFragmentArgs.fromBundle(getArguments()).getEmail();
        age = ProfessionFragmentArgs.fromBundle(getArguments()).getAge();
        gender = ProfessionFragmentArgs.fromBundle(getArguments()).getGender();
        followers = ProfessionFragmentArgs.fromBundle(getArguments()).getFollowers();
        numOfPosts = ProfessionFragmentArgs.fromBundle(getArguments()).getPostsuploads();
        platforms = ProfessionFragmentArgs.fromBundle(getArguments()).getPlatform();
        bitmap = ProfessionFragmentArgs.fromBundle(getArguments()).getBitmap();

        User user = new User(gender,password,email,username,age,followers,numOfPosts,company,influencer,professions,platforms);

        continueBtn = view.findViewById(R.id.fragemnt_profession_continuebtn);


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                ModelPhotos.instance3.uploadImage(bitmap, getActivity(), new ModelPhotos.PostProfilePhoto() {
                    @Override
                    public void onComplete(Uri uri) {
                        user.setImage(uri);
                    }
                });

                 */
                Modelauth.instance2.sighup(user, new Modelauth.signupListener() {
                    @Override
                    public void onComplete(int code) {
                        if(code==200){
                            Modelauth.instance2.Login(username, password, new Modelauth.loginListener() {
                                @Override
                                public void onComplete(int code) {
                                    toFeedActivity();
                                    Toast.makeText(getActivity(),"Welcome to Collab Me!", Toast.LENGTH_LONG).show();
                                }
                            });

                        }else{
                            Toast.makeText(getActivity(), "Sign up went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_professionFragment_to_socialmedia);
            }
        });

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
                    i--;
                    sport.setBackgroundColor(sport.getContext().getResources().getColor(R.color.purple));
                    professions[index] = null;
                }else {
                    sport.setBackgroundColor(Color.DKGRAY);
                    professions[i] = "Sport";
                    i++;
                }
                break;
            case R.id.fragemnt_profession_animals:
                index= indexOfValue("Animals");
                if(index!=-1){
                    animals.setBackgroundColor(animals.getContext().getResources().getColor(R.color.purple));
                    professions[index] = null;
                    i--;
                }else {
                    animals.setBackgroundColor(Color.DKGRAY);

                    professions[i] = "Animals";
                    i++;
                }
                break;
            case R.id.fragemnt_profession_art:
                index= indexOfValue("Art");
                if(index!=-1){
                    art.setBackgroundColor(art.getContext().getResources().getColor(R.color.purple));
                    professions[index] = null;
                    i--;
                }else {
                    art.setBackgroundColor(Color.DKGRAY);
                    professions[i] = "Art";
                    i++;
                }
                break;
            case R.id.fragemnt_profession_cooking:
                index= indexOfValue("Cooking");
                if(index!=-1){
                    cooking.setBackgroundColor(cooking.getContext().getResources().getColor(R.color.purple));
                    professions[index] = null;
                    i--;
                }else {
                    cooking.setBackgroundColor(Color.DKGRAY);

                    professions[i] = "Cooking";
                    i++;
                }

                break;
            case R.id.fragemnt_profession_fashion:
                index= indexOfValue("Fashion");
                if(index!=-1){
                    fashion.setBackgroundColor(fashion.getContext().getResources().getColor(R.color.purple));

                    professions[index] = null;
                    i--;
                }else {
                    fashion.setBackgroundColor(Color.DKGRAY);

                    professions[i] = "Fashion";
                    i++;
                }

                break;
            case R.id.fragemnt_profession_cosmetic:
                index= indexOfValue("Cosmetic");
                if(index!=-1){
                    cosmetic.setBackgroundColor(cosmetic.getContext().getResources().getColor(R.color.purple));

                    professions[index] = null;
                    i--;
                }else {
                    cosmetic.setBackgroundColor(Color.DKGRAY);

                    professions[i] = "Cosmetic";
                i++;
                }

                break;
            case R.id.fragemnt_profession_music:
                index= indexOfValue("Music");
                if(index!=-1){
                    music.setBackgroundColor(music.getContext().getResources().getColor(R.color.purple));

                    professions[index] = null;
                    i--;
                }else {
                    music.setBackgroundColor(Color.DKGRAY);

                    professions[i] = "Music";
                    i++;
                }
                break;
            case R.id.fragemnt_profession_dance:
                index= indexOfValue("Dance");
                if(index!=-1){
                    dance.setBackgroundColor(dance.getContext().getResources().getColor(R.color.purple));

                    professions[index] = null;
                    i--;
                }else {
                    dance.setBackgroundColor(Color.DKGRAY);

                    professions[i] = "Dance";
                    i++;
                }

                break;
            case R.id.fragemnt_profession_travel:
                index= indexOfValue("Travel");
                if(index!=-1){
                    travel.setBackgroundColor(travel.getContext().getResources().getColor(R.color.purple));

                    professions[index] = null;
                    i--;
                }else {
                    travel.setBackgroundColor(Color.DKGRAY);

                    professions[i] = "Travel";
                    i++;
                }
                break;
            case R.id.fragemnt_profession_gaming:
                index= indexOfValue("Gaming");
                if(index!=-1){
                    gaming.setBackgroundColor(gaming.getContext().getResources().getColor(R.color.purple));

                    professions[index] = null;
                    i--;
                }else {
                    gaming.setBackgroundColor(Color.DKGRAY);

                    professions[i] = "Gaming";
                    i++;
                }

                break;
            case R.id.fragemnt_profession_food:
                index= indexOfValue("Food");
                if(index!=-1){
                    food.setBackgroundColor(food.getContext().getResources().getColor(R.color.purple));

                    professions[index] = null;
                    i--;
                }else {
                    food.setBackgroundColor(Color.DKGRAY);

                    professions[i] = "Food";
                    i++;
                }

                break;
            case R.id.fragemnt_profession_tech:
                index= indexOfValue("Tech");
                if(index!=-1){
                    tech.setBackgroundColor(tech.getContext().getResources().getColor(R.color.purple));

                    professions[index] = null;
                    i--;
                }else {
                    tech.setBackgroundColor(Color.DKGRAY);

                    professions[i] = "Tech";
                    i++;
                }
                break;
            case R.id.fragemnt_profession_movies:
                index= indexOfValue("Movies");
                if(index!=-1){
                    movies.setBackgroundColor(movies.getContext().getResources().getColor(R.color.purple));

                    professions[index] = null;
                    i--;
                }else {
                    movies.setBackgroundColor(Color.DKGRAY);

                    professions[i] = "Movies";
                    i++;
                }
                break;
            case R.id.fragemnt_profession_photograph:
                index= indexOfValue("Photograph");
                if(index!=-1){
                    photograph.setBackgroundColor(photograph.getContext().getResources().getColor(R.color.purple));

                    professions[index] = null;
                    i--;
                }else {
                    photograph.setBackgroundColor(Color.DKGRAY);

                    professions[i] = "Photograph";
                    i++;
                }
                break;
            case R.id.fragemnt_profession_lifestyle:
                index= indexOfValue("Lifestyle");
                if(index!=-1){
                    lifestyle.setBackgroundColor(lifestyle.getContext().getResources().getColor(R.color.purple));

                    professions[index] = null;
                    i--;
                }else {
                    lifestyle.setBackgroundColor(Color.DKGRAY);

                    professions[i] = "Lifestyle";
                    i++;
                }

                break;
            case R.id.fragemnt_profession_other:
                index= indexOfValue("Other");
                if(index!=-1){
                    other.setBackgroundColor(other.getContext().getResources().getColor(R.color.purple));

                    professions[index] = null;
                    i--;
                }else {
                    other.setBackgroundColor(Color.DKGRAY);

                    professions[i] = "Other";
                    i++;
                }

                break;

        }
    }
}