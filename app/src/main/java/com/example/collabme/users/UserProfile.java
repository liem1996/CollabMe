package com.example.collabme.users;

import static android.graphics.Color.rgb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.collabme.Activites.LoginActivity;
import com.example.collabme.R;
import com.example.collabme.actionsOnOffers.SpinnerAdapter;
import com.example.collabme.model.ModelPhotos;
import com.example.collabme.model.ModelUsers;
import com.example.collabme.model.Modelauth;
import com.example.collabme.objects.User;
import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.Arrays;
/**
 *
 *  the user profile fragment  -fragmwnt that degin to show the user details for the user which is connected
 *  filling several felied including image to show the user detailes
 *  can edit the user from here-navigate to edit user
 *  can delete a user with delete user button
 */

public class UserProfile extends Fragment {

    TextView usernameType, username, age, followers, postuploads, email, gender;
    Spinner professions, platform;
    ArrayList<String> platformArr, professionsArr, rejectedOffers;
    ImageButton editBtn;
    String[] plat, pref, arrRejected, tmpArr;
    String password;
    Boolean influencer, company;
    ImageView logout, profilepicture;
    ProgressBar progressBar;
    SpinnerAdapter spinnerAdapter;
    String username1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        progressBar = view.findViewById(R.id.userProfile_progressbar);
        progressBar.setVisibility(View.GONE);
        progressBar.getIndeterminateDrawable().setColorFilter(rgb(132, 80, 160), android.graphics.PorterDuff.Mode.MULTIPLY);

        usernameType = view.findViewById(R.id.fragment_userprofile_usernameType);
        username = view.findViewById(R.id.fragment_userprofile_username);
        age = view.findViewById(R.id.fragment_userprofile_age);
        email = view.findViewById(R.id.fragment_userprofile_email);
        gender = view.findViewById(R.id.fragemnt_userprofile_gender);
        platform = view.findViewById(R.id.fragemnt_signup_platform);
        professions = view.findViewById(R.id.fragemnt_signup_proffesions);
        followers = view.findViewById(R.id.fragment_userprofile_followers);
        postuploads = view.findViewById(R.id.fragment_userprofile_postsuploads);
        editBtn = view.findViewById(R.id.fragemnt_userprofile_editBtn);
        logout = view.findViewById(R.id.fragment_userprofile_logoutBtn);
        profilepicture = view.findViewById(R.id.fragment_userprofile_pic);
        username1 = UserProfileArgs.fromBundle(getArguments()).getUsername();
        ModelUsers.instance3.getuserbyusername(username1,new ModelUsers.GetUserByIdListener() {
            @Override
            public void onComplete(User profile) {
                if (profile != null) {
                    ModelPhotos.instance3.getimages(profile.getImage(), new ModelPhotos.getimagesfile() {
                        @Override
                        public void onComplete(Bitmap responseBody) {

                            checkUsernameType(profile);
                            username.setText(profile.getUsername());
                            age.setText(profile.getAge());
                            followers.setText(profile.getFollowers());
                            postuploads.setText(profile.getNumOfPosts());
                            plat = profile.getPlatforms();
                            pref = profile.getProfessions();
                            gender.setText(profile.getSex());
                            email.setText(profile.getEmail());
                            platformArr = ChangeToArray(profile.getPlatforms());
                            professionsArr = ChangeToArray(profile.getProfessions());
                            password = profile.getPassword();
                            influencer = profile.getInfluencer();
                            company = profile.getCompany();
                            rejectedOffers = profile.getRejectedOffers();
                            initSpinnerFooter(platformArr.size(), platformArr, platform);
                            initSpinnerFooter(professionsArr.size(), professionsArr, professions);

                            if (responseBody != null) {
                                profilepicture.setImageBitmap(responseBody);
                            }
                        }
                    });
                }
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                changeRejectedToArray();
                Navigation.findNavController(v).navigate(UserProfileDirections.actionUserProfileToEditProfile2(
                        username.getText().toString(), password, company, influencer, age.getText().toString(), email.getText().toString(), gender.getText().toString(),
                        plat, pref, followers.getText().toString(), postuploads.getText().toString(), arrRejected));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modelauth.instance2.logout(new Modelauth.logout() {
                    @Override
                    public void onComplete(int code) {
                        if (code == 200) {
                            ModelUsers.instance3.setUserConnected(null);
                            LoginManager.getInstance().logOut();
                            toLoginActivity();
                        }
                    }
                });
            }
        });

        return view;
    }

    private void checkUsernameType(User profile) {
        if (profile.getInfluencer() && profile.getCompany()) {
            usernameType.setText("Influencer & Company profile");
            return;
        } else if (profile.getInfluencer()) {
            usernameType.setText("Influencer profile");
            return;
        } else if (profile.getCompany()) {
            usernameType.setText("Company profile");
            return;
        }
    }

    private void toLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void initSpinnerFooter(int size, ArrayList<String> array, Spinner spinner) {
        tmpArr = new String[size];
        for (int i = 0; i < size; i++) {
            if (array.get(i) != null)
                tmpArr[i] = array.get(i);
        }
        spinnerAdapter = new SpinnerAdapter(getContext(), Arrays.asList(tmpArr));
        spinner.setAdapter(spinnerAdapter);
    }

    public ArrayList<String> ChangeToArray(String[] array) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            arrayList.add(array[i]);
        }
        return arrayList;
    }

    public String[] changeRejectedToArray() {
        arrRejected = new String[rejectedOffers.size()];
        for (int i = 0; i < rejectedOffers.size(); i++) {
            arrRejected[i] = rejectedOffers.get(i);
        }
        return arrRejected;
    }
}