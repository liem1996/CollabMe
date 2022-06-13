package com.example.collabme.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.collabme.R;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

public class FacebookActivity extends AppCompatActivity {

    LoginButton facebook;
    TextView info;
    ImageView profileImg;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

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