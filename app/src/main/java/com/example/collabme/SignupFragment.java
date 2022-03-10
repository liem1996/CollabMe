package com.example.collabme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupFragment extends Fragment {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:4000";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        return view;
    }

    private void handleSighUp() {
        View view = getLayoutInflater().inflate(R.layout.fragment_signup, null);
        Button register = view.findViewById(R.id.fragemnt_signup_continuebtn);
        EditText username = view.findViewById(R.id.fragemnt_signup_username);
        EditText password = view.findViewById(R.id.fragemnt_signup_email);
        EditText age = view.findViewById(R.id.fragemnt_signup_age);
        //EditText gender = view.findViewById(R.id.fragemnt_signup_gender);
        CheckBox company = view.findViewById(R.id.fragemnt_signup_comapny);
        CheckBox influencer = view.findViewById(R.id.fragment_signup_influencer);
        EditText professions = view.findViewById(R.id.fragemnt_signup_age);
        EditText platforms = view.findViewById(R.id.fragemnt_signup_age);
        EditText followers = view.findViewById(R.id.fragemnt_signup_age);
        EditText numOfPosts = view.findViewById(R.id.fragemnt_signup_age);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("Username", username.getText().toString());
                map.put("Password", password.getText().toString());
                //map.put("Sex", null);
                map.put("Age", age.getText().toString());
                map.put("Followers", null);
                map.put("NumOfPosts", null);
                map.put("Company", company.isChecked());
                map.put("Influencer", influencer.isChecked());
                map.put("Profession", null);
                map.put("Platform", null);

                Call<Void> call = retrofitInterface.executeSignup(map);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            Toast.makeText(getActivity(), "sigh up", Toast.LENGTH_LONG).show();

                        } else if (response.code() == 400) {
                            Toast.makeText(getActivity(), "not sighup", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }

}