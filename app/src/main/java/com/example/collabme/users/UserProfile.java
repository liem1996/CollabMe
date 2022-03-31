package com.example.collabme.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.collabme.Activites.LoginActivity;
import com.example.collabme.R;
import com.example.collabme.model.ModelUsers;
import com.example.collabme.model.Modelauth;
import com.example.collabme.objects.User;

import java.util.ArrayList;


public class UserProfile extends Fragment {

    TextView username,age,followers,postuploads;
    Spinner professions,platform;
    ArrayList<String>  platformArr;
    Button chat,createAnOffer,edit;
    ArrayList<String> professionsArr;
    String [] plat;
    String [] pref;
    String password ;
    Boolean influencer1, company1;
    String email1;
    String gender1;
    ImageView logout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        username=view.findViewById(R.id.fragment_userprofile_username);

        age=view.findViewById(R.id.fragment_userprofile_age);
        platform=view.findViewById(R.id.fragemnt_signup_platform);
        professions=view.findViewById(R.id.fragemnt_signup_proffesions);
        followers=view.findViewById(R.id.fragment_userprofile_followers);
        postuploads=view.findViewById(R.id.fragment_userprofile_postsuploads);
        chat=view.findViewById(R.id.fragment_userprofile_chatbtn);
        createAnOffer=view.findViewById(R.id.fragemnt_userprofile_create);
        edit=view.findViewById(R.id.fragemnt_userprofile_edit);
        logout = view.findViewById(R.id.fragment_userprofile_logoutBtn);

         ModelUsers.instance3.getUserConnect(new ModelUsers.getuserconnect() {
            @Override
            public void onComplete(User profile) {
                if(profile!=null) {
                    username.setText(profile.getUsername());
                    followers.setText(profile.getFollowers());
                    postuploads.setText(profile.getNumOfPosts());
                    age.setText(profile.getAge());
                    plat = profile.getPlatforms();
                    pref=profile.getProfessions();
                    gender1=profile.getSex();
                    email1=profile.getEmail();
                    platformArr = ChangeToArray(profile.getPlatforms());
                    professionsArr = ChangeToArray(profile.getProfessions());
                    password=profile.getPassword();
                    influencer1 = profile.getInfluencer();
                    company1 = profile.getInfluencer();
                    initSpinnerFooter(platformArr.size(),platformArr,platform);
                    initSpinnerFooter(professionsArr.size(),professionsArr,professions);
                }

            }
        });



        //need to create chat
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Navigation.findNavController(v).navigate(UserProfileDirections.actionUserProfileToHomeFragment2());
            }
        });
        createAnOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_userProfile_to_addOfferDetailsFragemnt);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Navigation.findNavController(v).navigate(UserProfileDirections.actionUserProfileToEditProfile2(username.getText().toString(),password,company1,influencer1,age.getText().toString(),email1,gender1,plat,pref,followers.getText().toString(),postuploads.getText().toString()));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modelauth.instance2.logout(new Modelauth.logout() {
                        @Override
                        public void onComplete(int code) {
                            if(code==200) {
                                toLoginActivity();
                            }
                            else{

                            }
                        }
                    });
            }
        });



        return view;

    }

    private void toLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void initSpinnerFooter(int size,ArrayList<String> array,Spinner spinner) {
        int tmp = 0;
        for(int j = 0 ; j<size;j++){
            if(array.get(j) != null){
                tmp++;
            }
        }
        String[] items = new String[tmp];

        for(int i = 0 ; i<tmp;i++){
            items[i] = array.get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextSize(25);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    public ArrayList<String> ChangeToArray(String [] array){
        ArrayList<String> arrayList=new ArrayList<>();
        for(int i=0;i<array.length;i++){
            arrayList.add(array[i]);
        }

        return arrayList;
    }


}