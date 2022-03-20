package com.example.collabme.TheSighUPProcess;

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

import com.example.collabme.Activites.Pop;
import com.example.collabme.R;
import com.example.collabme.model.Modelauth;
import com.example.collabme.Activites.MainActivity;

public class LoginFragment extends Fragment {

    EditText username, password;
    Button login;
    TextView signup, forgotpassword;
    View view;

    private void toFeedActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        username = view.findViewById(R.id.fragment_login_username);
        password = view.findViewById(R.id.fragment_login_password);
        forgotpassword = view.findViewById(R.id.fragment_login_forgotpass);

        login = view.findViewById(R.id.fragment_login_loginbtn);
        login.setOnClickListener(v -> Modelauth.instance2.Login(username.getText().toString(),password.getText().toString(), new Modelauth.loginListener() {
            @Override
            public void onComplete(int code) {
                if(code==200) {
                    toFeedActivity();

                }
                else{
                    Toast.makeText(getActivity(), "Login succeded! Welcome to CollabMe!", Toast.LENGTH_LONG).show();
                }
            }
        }));
        signup = view.findViewById(R.id.fragment_login_newuser);
        signup.setOnClickListener(v -> handleSighUp());

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext() , Pop.class));
            }
        });

        return view;
    }


    private void handleSighUp() {

        Navigation.findNavController(view).navigate(R.id.action_fragment_login_to_signupFragment2);
    }
}