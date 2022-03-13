package com.example.collabme;

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

import retrofit2.Retrofit;

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

        username = view.findViewById(R.id.fragment_login_username);
        password = view.findViewById(R.id.fragment_login_password);

        login = view.findViewById(R.id.fragment_login_loginbtn);
        login.setOnClickListener(v -> Model.instance.Login(username.getText().toString(),password.getText().toString(), new Model.loginListener() {
            @Override
            public void onComplete(int code) {
                if(code==200) {
                    Navigation.findNavController(view).navigate(R.id.action_global_addOfferDetailsFragemnt);
                    //Toast.makeText(getActivity(), "yess", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(), "boo", Toast.LENGTH_LONG).show();
                }
            }
        }));
        signup = view.findViewById(R.id.fragment_login_newuser);
        signup.setOnClickListener(v -> handleSighUp());

        return view;
    }




    private void handleSighUp() {

        Navigation.findNavController(view).navigate(R.id.action_fragment_login_to_signupFragment2);
    }
}