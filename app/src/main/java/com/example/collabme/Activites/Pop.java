package com.example.collabme.Activites;

import static com.example.collabme.objects.MyApplication.getContext;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.collabme.R;
import com.example.collabme.model.ModelUsers;
import com.example.collabme.model.Modelauth;
import com.example.collabme.objects.User;

public class Pop extends Activity {

    EditText username, email, newpassword;
    TextView yourepassis, newpasstext;
    Button send, change;
    String username1, email1;
    ImageView grayback;

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
        newpassword = view.findViewById(R.id.fragment_pop_newpassword);
        newpasstext = view.findViewById(R.id.fragment_pop_newpasstext);
        change = view.findViewById(R.id.fragemnt_pop_change);
        grayback = view.findViewById(R.id.imageView9_grayback);

        yourepassis.setVisibility(View.GONE);
        newpassword.setVisibility(View.GONE);
        newpasstext.setVisibility(View.GONE);
        change.setVisibility(View.GONE);
        grayback.setVisibility(View.GONE);

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
                        } else {
                            yourepassis.setVisibility(View.VISIBLE);
                            newpassword.setVisibility(View.VISIBLE);
                            newpasstext.setVisibility(View.VISIBLE);
                            change.setVisibility(View.VISIBLE);
                            grayback.setVisibility(View.VISIBLE);

                            change.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (!newpassword.getText().toString().equals(""))
                                    {
                                        if (profile!=null) {
                                            profile.setPassword(newpassword.getText().toString());
                                            ModelUsers.instance3.editUserWithoutAuth(profile, new ModelUsers.editUserWithoutAuthListener() {
                                                @Override
                                                public void onComplete(int code) {
                                                    if (code == 200) {
                                                        Toast.makeText(Pop.this, "Password changed successfully", Toast.LENGTH_LONG).show();
                                                        return;
                                                    } else {
                                                        Toast.makeText(Pop.this, "Password didn't changed successfully", Toast.LENGTH_LONG).show();
                                                        return;
                                                    }
                                                }
                                            });
                                        }
                                        else {
                                                Toast.makeText(Pop.this, "Profile doesn't exist", Toast.LENGTH_LONG).show();
                                                return;
                                            }
                                    }
                                    else{
                                        Toast.makeText(Pop.this, "Nothing was entered", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                }

                            });


                        }
                    }
                });
            }
        });
    }
}
