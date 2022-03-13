package com.example.collabme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import retrofit2.Retrofit;


public class UserProfile extends Fragment {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:4000";
    TextView username,age,followers,postuploads;
    Spinner proffesions,platform;
    ArrayList<String>  platform1;

    ArrayList<String> proffesions1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        username=view.findViewById(R.id.fragment_userprofile_username);
        age=view.findViewById(R.id.fragment_userprofile_age);
        platform=view.findViewById(R.id.fragemnt_signup_platform);
        proffesions=view.findViewById(R.id.fragemnt_signup_proffesions);
        followers=view.findViewById(R.id.fragment_userprofile_followers);
        postuploads=view.findViewById(R.id.fragment_userprofile_postsuploads);
        Model.instance.getUserConnect(new Model.getuserconnect() {
            @Override
            public void onComplete(User profile) {
                if(profile!=null) {
                    username.setText(profile.getUsername());
                    followers.setText(profile.getFollowers());
                    postuploads.setText(profile.getNumOfPosts());
                    age.setText(profile.getAge());
                    platform1 = ChangeToArray(profile.getPlatforms());
                    proffesions1 = ChangeToArray(profile.getProfessions());

                    initSpinnerFooter(platform1.size(),platform1,platform);
                    initSpinnerFooter(proffesions1.size(),proffesions1,proffesions);

                }
            }
        });

        return view;

    }

    private void initSpinnerFooter(int size,ArrayList<String> array,Spinner spinner) {
        String[] items = new String[size];

        for(int i = 0 ; i<size;i++){
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
            public void onNothingSelected(AdapterView<?> parent) {


            }
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