package com.example.collabme.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.collabme.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

public class FacebookActivity extends AppCompatActivity {

    LoginButton facebook;
    TextView info;
    ImageView profileImg;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
       // FacebookSdk.sdkInitialize(getApplicationContext());

//        facebook = findViewById(R.id.fragment_login_facebook);
//        info= findViewById(R.id.facebookactivity_info);
//        profileImg = findViewById(R.id.facebookactivity_profile);
//
//        callbackManager = CallbackManager.Factory.create();
//        facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                info.setText("User ID: " + loginResult.getAccessToken().getUserId() + "\n" + "Auth Token: " + loginResult.getAccessToken().getToken());
//                String imageURL = "https://graph.facebook.com/"+loginResult.getAccessToken().getUserId() +"/picture?return_ssl_resources=1";
//                Picasso.get().load(imageURL).into(profileImg);
//                toFeedActivity();
//            }
//
//            @Override
//            public void onCancel() {
//                info.setText("Login attempt canceled.");
//            }
//
//            @Override
//            public void onError(FacebookException e) {
//                info.setText("Login attempt failed.");
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode , resultCode , data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void toFeedActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}