package com.example.collabme.TheSighUPProcess;

import static android.graphics.Color.rgb;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.collabme.R;


public class socialmedia extends Fragment {

    String username1, password1, email1, age1, selectedGender, followersCheck, postsCheck;
    CheckBox instegram, twitter, tiktok, facebook, youtube;
    EditText followers, posts;
    Boolean influencer1, company1;
    Button countinue;
    ImageButton backBtn;
    View view;
    Bitmap bitmap;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_socialmedia, container, false);
        progressBar = view.findViewById(R.id.socialMedia_progressbar);
        progressBar.setVisibility(View.GONE);
        progressBar.getIndeterminateDrawable().setColorFilter(rgb(132, 80, 160), android.graphics.PorterDuff.Mode.MULTIPLY);

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
        influencer1 = socialmediaArgs.fromBundle(getArguments()).getInfluencer();
        company1 = socialmediaArgs.fromBundle(getArguments()).getCompany();
        email1 = socialmediaArgs.fromBundle(getArguments()).getEmail();
        age1 = socialmediaArgs.fromBundle(getArguments()).getAge();
        bitmap = ProfessionFragmentArgs.fromBundle(getArguments()).getBitmap();

        selectedGender = socialmediaArgs.fromBundle(getArguments()).getGender();

        backBtn = view.findViewById(R.id.fragment_socialmedia_backBtn);
        backBtn.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_socialmedia_to_signupFragment2));
        countinue.setOnClickListener(v -> toProfession());

        return view;
    }

    private void toProfession() {
        String[] platform = new String[5];
        int i = 0;


        if (instegram.isChecked()) {
            platform[i] = "instegram";
            i++;
        }
        if (twitter.isChecked()) {
            platform[i] = "twitter";
            i++;
        }
        if (facebook.isChecked()) {
            platform[i] = "facebook";
            i++;
        }
        if (tiktok.isChecked()) {
            platform[i] = "tiktok";
            i++;
        }
        if (youtube.isChecked()) {
            platform[i] = "youtube";
            i++;
        }

        followersCheck = followers.getText().toString();
        postsCheck = posts.getText().toString();
        if(followersCheck.isEmpty() || !(followers.getText().toString().matches("^[1-9]{1}(?:[0-9])*?$"))){
            followers.setError("Your followers is required");
            return;

        }else if(platform.length ==0 || platform == null){
            Toast.makeText(getContext(), "Your platform is required", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(postsCheck.isEmpty() && !isInteger(postsCheck)){
            posts.setError("Your posts/uploads is required");
            return;
        } else {
            progressBar.setVisibility(View.VISIBLE);

            Navigation.findNavController(view).navigate(socialmediaDirections.actionSocialmediaToProfessionFragment(username1, password1, influencer1,
                    company1, email1, age1, selectedGender, platform, followers.getText().toString(), posts.getText().toString(), bitmap));
        }
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}