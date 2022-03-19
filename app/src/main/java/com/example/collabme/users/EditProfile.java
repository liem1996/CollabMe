package com.example.collabme.users;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.collabme.model.Model;
import com.example.collabme.model.User;
import com.example.collabme.R;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;


public class EditProfile extends Fragment {
    TextView platform, professions;
    EditText username, age, followers, postuploads;
    Button chat, save, cancel, uploadphoto, delete;
    String[] platformArr;
    String[] professionArr;
    String password;
    Boolean influencer1, company1;
    String email1, gender1, username1, age1, Followers, posts;
    boolean[] selectedProfessions = new boolean[16];
    boolean[] selectedPlatforms = new boolean[5];
    ArrayList<Integer> langList = new ArrayList<>();
    ArrayList<Integer> langList2 = new ArrayList<>();

    String[] chosen, chosen2;

    User user;
    String[] langArray = {"Sport", "Cooking", "Fashion", "Music", "Dance", "Cosmetic", "Travel", "Gaming", "Tech", "Food",
            "Art", "Animals", "Movies", "Photograph", "Lifestyle", "Other"};
    String[] langArray2 = {"youtube", "facebook", "tiktok", "instagram", "twitter"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        username = view.findViewById(R.id.fragment_edituser_username2);
        age = view.findViewById(R.id.fragment_edituser_age);
        platform = view.findViewById(R.id.fragment_edituser_platform);
        professions = view.findViewById(R.id.fragment_edituser_profession);
        followers = view.findViewById(R.id.fragment_edituser_followers);
        postuploads = view.findViewById(R.id.fragment_edituser_postsuploads);
        delete = view.findViewById(R.id.fragemnt_edituser_delete);
        uploadphoto = view.findViewById(R.id.fragemnt_edituser_upload);
        save = view.findViewById(R.id.fragemnt_edituser_save);
        cancel = view.findViewById(R.id.fragemnt_edituser_cancel);

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

        username.setText(username1);
        age.setText(age1);
        // platform.
        //professions
        followers.setText(Followers);
        postuploads.setText(posts);
        //delete
        uploadphoto.setText(posts);
        //edit
        //cancel

     //   String[] pro1 = thedialog(professions, langArray, professionArr, selectedProfessions);
     //   String[] pro2 = thedialog(platform, langArray2, platformArr, selectedPlatforms);

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
                        if (professionArr[h]!=null) {
                            if (professionArr[h].equals(langArray[k])) {
                                langList.add(k);
                                Collections.sort(langList);
                                selectedProfessions[k] = true;
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

                            System.out.println("ko");
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

                            if (platformArr[he].equals(langArray2[km])) {
                                langList2.add(km);
                                Collections.sort(langList2);
                                selectedPlatforms[km] = true;
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User(gender1, password, email1,username.getText().toString(),
                        age.getText().toString(),followers.getText().toString(),postuploads.getText().toString(),
                        company1,influencer1,chosen, chosen2);

                Model.instance.EditUser(user,new Model.EditUserListener() {
                    @Override
                    public void onComplete(int code) {
                        if(code == 200) {
                            Toast.makeText(getActivity(), "user changes saved", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getActivity(), "user changes not saved", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });

        return view;
        // Inflate the layout for this fragment
    }

/////////////////////////////////////////////////////////

    /*

    public String[] thedialog(TextView textView, String[] lang, String[] theArr, boolean[] selected) {

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // set title
                builder.setTitle("Select:");

                // set dialog non cancelable
                builder.setCancelable(false);
                int m = 0;

                    for (int k = 0; k < lang.length; k++) {
                        for (int h = 0; h < theArr.length; h++) {
                            if (theArr[h]!=null) {
                                if (theArr[h].equals(lang[k])) {
                                    langList.add(k);
                                    Collections.sort(langList);
                                    selected[k] = true;
                                }
                            }
                        }
                    }


                builder.setMultiChoiceItems(lang, selected, new DialogInterface.OnMultiChoiceClickListener() {

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

                                stringBuilder.append(lang[langList.get(j)]);
                                chosen[j] = (lang[langList.get(j)]); //to check again

                            System.out.println("ko");
                            // check condition
                            if (j != langList.size() - 1) {
                                // When j value not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        textView.setText(stringBuilder.toString());
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
                        for (int j = 0; j < selected.length; j++) {
                            // remove all selection
                            selected[j] = false;
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
        return chosen;


    }
    */
    ////////////////////////////////////////////////////////////////////////////////
}