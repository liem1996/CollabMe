package com.example.collabme.TheSighUPProcess;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.collabme.R;


public class socialmedia extends Fragment {

    CheckBox instegram, twitter,tiktok,facebook,youtube;
    Button countinue, back;
    EditText followers, posts;
    String username1;
    String password1, email1, age1,selectedGender;
    View view;
    Boolean influencer1, company1;
    String followersCheck, postsCheck;
    Bitmap bitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_socialmedia, container, false);

        instegram = view.findViewById(R.id.fragment_socialmedia_instagram);
        twitter = view.findViewById(R.id.fragment_socialmedia_twitter);
        facebook = view.findViewById(R.id.fragment_socialmedia_facebook);
        tiktok = view.findViewById(R.id.fragment_socialmedia_tiktok);
        youtube = view.findViewById(R.id.fragment_socialmedia_youtube);
        countinue = view.findViewById(R.id.fragment_socialmedia_continue);

        followers = view.findViewById(R.id.fragment_socialmedia_followers);
        posts = view.findViewById(R.id.fragment_socialmedia_postsuploads);

        username1 = socialmediaArgs.fromBundle(getArguments()).getUsername();
        password1 = socialmediaArgs.fromBundle(getArguments()).getPassword();
        influencer1 =  socialmediaArgs.fromBundle(getArguments()).getInfluencer();
        company1 =  socialmediaArgs.fromBundle(getArguments()).getCompany();
        email1 =  socialmediaArgs.fromBundle(getArguments()).getEmail();
        age1 =socialmediaArgs.fromBundle(getArguments()).getAge();
        bitmap = ProfessionFragmentArgs.fromBundle(getArguments()).getBitmap();

        selectedGender = socialmediaArgs.fromBundle(getArguments()).getGender();

        back = view.findViewById(R.id.fragment_socialmedia_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Navigation.findNavController(view).navigate(R.id.action_socialmedia_to_signupFragment2);

            }
        });
        countinue.setOnClickListener(v -> toProfession());

        return view;
    }

    private void toProfession() {
        String[] platform = new String[5];
        int i=0;


        if(instegram.isChecked()){
            platform[i] = "instegram";
            i++;

        } if (twitter.isChecked()){
            platform[i] = "twitter";
            i++;

        } if(facebook.isChecked()){
            platform[i] = "facebook";
            i++;

        } if (tiktok.isChecked()){
            platform[i] = "tiktok";
            i++;

        } if(youtube.isChecked()){
            platform[i] = "youtube";
            i++;
        }

        followersCheck = followers.getText().toString();
        postsCheck = posts.getText().toString();

        if((!followersCheck.equals(""))&& !isInteger(followersCheck))
        {
            Toast.makeText(getContext(), "Your followers field is not an integer", Toast.LENGTH_SHORT).show();
            return;
        }
        else if((!postsCheck.equals(""))&& !isInteger(postsCheck))
        {
            Toast.makeText(getContext(), "Your posts/uploads field is not an integer", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            Navigation.findNavController(view).navigate(socialmediaDirections.actionSocialmediaToProfessionFragment(username1, password1, influencer1,
                    company1, email1, age1, selectedGender, platform, followers.getText().toString(), posts.getText().toString(),bitmap));

        }
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }



}