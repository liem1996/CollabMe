package com.example.collabme;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {

    EditText username, password;
    Button login;
    TextView signup;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:4000";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        username = view.findViewById(R.id.fragment_login_username);
        password = view.findViewById(R.id.fragment_login_password);

        login = view.findViewById(R.id.fragment_login_loginbtn);
        login.setOnClickListener(v -> handleLogin());


        signup = view.findViewById(R.id.fragment_login_newuser);
        signup.setOnClickListener(v -> handleSighUp());

        return view;
    }


    private void handleLogin() {
        View view = getLayoutInflater().inflate(R.layout.fragment_login, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view).show();

        HashMap<String, String> map = new HashMap<>();

        map.put("Username", username.getText().toString());
        map.put("Password", password.getText().toString());

        Call<User> call = retrofitInterface.executeLogin(map);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    Toast.makeText(getActivity(), "great!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();
                    return;


                } else if (response.code() == 404) {
                    Toast.makeText(getActivity(), "Wrong Credentials", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }

        });


    }

    private void handleSighUp() {
        View view = getLayoutInflater().inflate(R.layout.fragment_signup, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view).show();
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