package com.example.collabme.Activites;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.collabme.R;
import com.example.collabme.model.Modelauth;
/**
 *
 * the intro - the entrace of the app
 * checking if the user is connectd acording to token or is logout already
 * going to login acording to it or the home page
 *  using nav controller and navigation to move from the intro to home or login page
 */
public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           Modelauth.instance2.isSignIn(new Modelauth.islogin() {
                @Override
                public void onComplete(boolean code) {
                    if(code==true){
                        toFeedActivity();
                    }else{
                        toLoginActivity();
                    }
                }
            });
    }

    private void toLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void toFeedActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}