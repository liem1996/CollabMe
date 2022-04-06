package com.example.collabme.users;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.collabme.Activites.LoginActivity;
import com.example.collabme.R;
import com.example.collabme.model.ModelUsers;
import com.example.collabme.model.Modelauth;
import com.example.collabme.objects.User;

import java.util.ArrayList;
import java.util.Collections;


public class EditProfile extends Fragment {
    TextView platform, professions, usernameType;
    EditText username, age, followers, postuploads, email, gender;
    Button saveBtn, deleteBtn;
    String[] platformArr;
    String[] professionArr;
    String password;
    Boolean influencer1, company1;
    String email1, gender1, username1, age1, Followers, posts;
    boolean[] selectedProfessions = new boolean[16];
    boolean[] selectedPlatforms = new boolean[5];
    ArrayList<Integer> langList = new ArrayList<>();
    ArrayList<Integer> langList2 = new ArrayList<>();
    ImageView logout;
    ImageButton galleryBtn, cameraBtn, cancelBtn;

    String[] chosen, chosen2;

    User user;
    String[] langArray = {"Sport", "Cooking", "Fashion", "Music", "Dance", "Cosmetic", "Travel", "Gaming", "Tech", "Food",
            "Art", "Animals", "Movies", "Photograph", "Lifestyle", "Other"};
    String[] langArray2 = {"youtube", "facebook", "tiktok", "instagram", "twitter"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        usernameType = view.findViewById(R.id.fragment_edituser_usernameType);
        username = view.findViewById(R.id.fragment_edituser_username);
        age = view.findViewById(R.id.fragment_edituser_age);
        professions = view.findViewById(R.id.fragment_edituser_profession);
        followers = view.findViewById(R.id.fragment_edituser_followers);
        postuploads = view.findViewById(R.id.fragment_edituser_postsuploads);
        platform = view.findViewById(R.id.fragment_edituser_platform);
        email = view.findViewById(R.id.fragment_edituser_email);
        gender = view.findViewById(R.id.fragemnt_edituser_gender);
        galleryBtn = view.findViewById(R.id.fragment_edituser_galleryBtn);
        cameraBtn = view.findViewById(R.id.fragment_edituser_cameraBtn);
        deleteBtn = view.findViewById(R.id.fragemnt_edituser_deleteBtn);
        saveBtn = view.findViewById(R.id.fragemnt_edituser_saveBtn);
        cancelBtn = view.findViewById(R.id.fragment_edituser_cancelBtn);
        logout = view.findViewById(R.id.fragment_edituser_logoutBtn);

        username1 = EditProfileArgs.fromBundle(getArguments()).getUsername();
        password = EditProfileArgs.fromBundle(getArguments()).getPassword();
        email1 = EditProfileArgs.fromBundle(getArguments()).getEmail();
        gender1 = EditProfileArgs.fromBundle(getArguments()).getGender();
        age1 = EditProfileArgs.fromBundle(getArguments()).getAge();
        Followers = EditProfileArgs.fromBundle(getArguments()).getFollowers();
        platformArr = EditProfileArgs.fromBundle(getArguments()).getPlatform();
        professionArr = EditProfileArgs.fromBundle(getArguments()).getProfession();
        posts = EditProfileArgs.fromBundle(getArguments()).getPostsuploads();
        influencer1 = EditProfileArgs.fromBundle(getArguments()).getInfluencer();
        company1 = EditProfileArgs.fromBundle(getArguments()).getCompany();

        updateUsernameType(influencer1, company1);
        username.setText(username1);
        age.setText(age1);
        //professions
        followers.setText(Followers);
        postuploads.setText(posts);
        //platform
        email.setText(email1);
        email.setEnabled(false);
        gender.setText(gender1);
        gender.setEnabled(false);


        //deleteBtn
        cancelBtn.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        //TODO:: add functionality for camera and gallery image button
        //edit
        //cancel

        professions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // set title
                builder.setTitle("Select Professions:");

                // set dialog non cancelable
                builder.setCancelable(false);
                int m = 0;

                for (int k = 0; k < langArray.length; k++) {
                    for (int h = 0; h < professionArr.length; h++) {
                        if (professionArr[h] != null) {
                            if (professionArr[h].equals(langArray[k])) {
                                if (!langList.contains(k)) {

                                    langList.add(k);
                                    Collections.sort(langList);
                                    selectedProfessions[k] = true;

                                }
                            }
                        }
                    }
                }


                builder.setMultiChoiceItems(langArray, selectedProfessions, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position in lang list
                            langList.add(i);
                            // Sort array list
                            Collections.sort(langList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            langList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder;
                        // Initialize string builder
                        stringBuilder = new StringBuilder();
                        chosen = new String[langList.size()];

                        // use for loop
                        for (int j = 0; j < langList.size(); j++) {
                            // concat array value

                            stringBuilder.append(langArray[langList.get(j)]);
                            chosen[j] = (langArray[langList.get(j)]); //to check again

                            // check condition
                            if (j != langList.size() - 1) {
                                // When j value not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        professions.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedProfessions.length; j++) {
                            // remove all selection
                            selectedProfessions[j] = false;
                            // clear language list
                            langList.clear();
                            // clear text view value
                            professions.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }

        });

        platform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());

                // set title
                builder2.setTitle("Select Platforms:");

                // set dialog non cancelable
                builder2.setCancelable(false);
                int m = 0;

                for (int km = 0; km < langArray2.length; km++) {
                    for (int he = 0; he < platformArr.length; he++) {
                        if (platformArr[he] != null) {
                            if (platformArr[he].equals(langArray2[km])) {
                                langList2.add(km);
                                Collections.sort(langList2);
                                selectedPlatforms[km] = true;
                            }

                        }
                    }
                }

                builder2.setMultiChoiceItems(langArray2, selectedPlatforms, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position in lang list
                            langList2.add(i);
                            // Sort array list
                            Collections.sort(langList2);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            langList2.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder2;
                        // Initialize string builder
                        stringBuilder2 = new StringBuilder();
                        chosen2 = new String[langList2.size()];

                        // use for loop
                        for (int j = 0; j < langList2.size(); j++) {
                            // concat array value

                            stringBuilder2.append(langArray2[langList2.get(j)]);
                            chosen2[j] = (langArray2[langList2.get(j)]); //to check again

                            System.out.println("ko");
                            // check condition
                            if (j != langList2.size() - 1) {
                                // When j value not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder2.append(", ");
                            }
                        }
                        // set text on textView
                        platform.setText(stringBuilder2.toString());
                    }
                });

                builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder2.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedPlatforms.length; j++) {
                            // remove all selection
                            selectedPlatforms[j] = false;
                            // clear language list
                            langList2.clear();
                            // clear text view value
                            platform.setText("");
                        }
                    }
                });
                // show dialog
                builder2.show();
            }

        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User(gender1, password, email1, username.getText().toString(),
                        age.getText().toString(), followers.getText().toString(), postuploads.getText().toString(),
                        company1, influencer1, chosen, chosen2);

                ModelUsers.instance3.EditUser(user, new ModelUsers.EditUserListener() {
                    @Override
                    public void onComplete(int code) {
                        if (code == 200) {
                            Toast.makeText(getActivity(), "user changes saved", Toast.LENGTH_LONG).show();
                            Navigation.findNavController(v).navigateUp();
                        } else {
                            Toast.makeText(getActivity(), "user changes not saved", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modelauth.instance2.logout(new Modelauth.logout() {
                    @Override
                    public void onComplete(int code) {
                        if (code == 200) {
                            toLoginActivity();
                        } else {

                        }
                    }
                });
            }
        });

        return view;
        // Inflate the layout for this fragment
    }

    private void toLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void updateUsernameType(Boolean influencer, Boolean company) {
        if (influencer && company) {
            usernameType.setText("Influencer & Company profile");
            return;
        }
        if (influencer) {
            usernameType.setText("Influencer profile");
            return;
        }
        if (company) {
            usernameType.setText("Company profile");
            return;
        }
    }
}