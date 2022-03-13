package com.example.collabme;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.ArrayList;

import retrofit2.Retrofit;


public class UserProfile extends Fragment {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:4000";
    TextView username,age,followers,postuploads, username2, home;
    Spinner professions,platform;
    ArrayList<String>  platformArr;

    ArrayList<String> professionsArr;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        username=view.findViewById(R.id.fragment_userprofile_username);
        username2 = view.findViewById(R.id.fragment_userprofile_username2);
        age=view.findViewById(R.id.fragment_userprofile_age);
        platform=view.findViewById(R.id.fragemnt_signup_platform);
        professions=view.findViewById(R.id.fragemnt_signup_proffesions);
        followers=view.findViewById(R.id.fragment_userprofile_followers);
        postuploads=view.findViewById(R.id.fragment_userprofile_postsuploads);
        home= view.findViewById(R.id.fragment_userprofile_home);
        home.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(UserProfileDirections.actionUserProfileToHomeFragment2());
        });

        Model.instance.getUserConnect(new Model.getuserconnect() {
            @Override
            public void onComplete(User profile) {
                if(profile!=null) {
                    username.setText(profile.getUsername());
                    username2.setText(profile.getUsername());
                    followers.setText(profile.getFollowers());
                    postuploads.setText(profile.getNumOfPosts());
                    age.setText(profile.getAge());
                    platformArr = ChangeToArray(profile.getPlatforms());
                    professionsArr = ChangeToArray(profile.getProfessions());

                    initSpinnerFooter(platformArr.size(),platformArr,platform);
                    initSpinnerFooter(professionsArr.size(),professionsArr,professions);
                }
            }
        });

        return view;

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