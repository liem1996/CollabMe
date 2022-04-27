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
    boolean goodsign = true;
    String[] platform = new String[5];

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
        backBtn.setOnClickListener(v -> Navigation.findNavController(view).navigate(socialmediaDirections.actionGlobalSignupFragment2(null,null,null)));
        countinue.setOnClickListener(v -> toProfession());

        return view;
    }

    private void toProfession() {
        int i = 0;


        if (instegram.isChecked()) {
            platform[i] = "instagram";
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

        if (checkValidDate()) {
            progressBar.setVisibility(View.VISIBLE);
            Navigation.findNavController(view).navigate(socialmediaDirections.actionSocialmediaToProfessionFragment(username1, password1, influencer1,
                    company1, email1, age1, selectedGender, platform, followers.getText().toString(), posts.getText().toString(), bitmap));
        }
    }
    public boolean checkValidDate() {
        if (followersCheck.isEmpty() || !(followers.getText().toString().matches("^[1-9]{1}(?:[0-9])*?$"))) {
            followers.setError("Your followers is required");
            return false;

        } else if (platform[0]== null && platform[1] == null &&
                platform[2]== null && platform[3] == null && platform[4] == null) {
            Toast.makeText(getContext(), "Your platform is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (postsCheck.isEmpty() || !(posts.getText().toString().matches("^[1-9]{1}(?:[0-9])*?$"))) {
            posts.setError("Your posts/uploads is required");
            goodsign = false;
            return false;
        }
        return  true;

    }
}
