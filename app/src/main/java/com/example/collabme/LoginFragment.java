package com.example.collabme;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
    View view;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:4000";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
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

        Navigation.findNavController(view).navigate(R.id.action_fragment_login_to_signupFragment2);
    }
}