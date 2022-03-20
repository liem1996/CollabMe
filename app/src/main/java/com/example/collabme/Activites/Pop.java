package com.example.collabme.Activites;

import static com.example.collabme.objects.MyApplication.getContext;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.collabme.R;
import com.example.collabme.model.Modelauth;
import com.example.collabme.objects.User;

public class Pop extends Activity {

    EditText username, email;
    TextView yourepassis, password;
    Button send;
    String username1, email1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_popup_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (height * .8));
        getWindow().setBackgroundDrawableResource(R.drawable.picc);

        ViewGroup view = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        username = view.findViewById(R.id.fragment_pop_username);
        email = view.findViewById(R.id.fragment_pop_email);
        send = view.findViewById(R.id.fragemnt_pop_sendBtn);
        yourepassis = view.findViewById(R.id.fragment_pop_yourpasswordis);
        password = view.findViewById(R.id.fragment_pop_password);

        yourepassis.setVisibility(View.GONE);
        password.setVisibility(View.GONE);

        checks();

    }

    public void checks(){
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username1 = username.getText().toString();
                email1 = email.getText().toString();
                Modelauth.instance2.getUserByUserNameInSignIn(username1, new Modelauth.getUserByUserNameInSignIn() {
                    @Override
                    public void onComplete(User profile) {

                        if (profile == null) {
                            Toast.makeText(getContext(), "This user doesn't exist", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (!profile.getEmail().equals(email1)) {
                            Toast.makeText(getContext(), "This email doesn't belong to this user", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (username1.equals("") || email1.equals("")) {
                            Toast.makeText(getContext(), "You have to fill both fields", Toast.LENGTH_SHORT).show();
                            return;
                        } else
                        {
                            password.setText(profile.getPassword());
                            yourepassis.setVisibility(View.VISIBLE);
                            password.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }
}
