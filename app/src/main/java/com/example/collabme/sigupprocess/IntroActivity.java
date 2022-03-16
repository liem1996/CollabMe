package com.example.collabme.sigupprocess;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.collabme.R;
import com.example.collabme.model.Model;
import com.example.collabme.offers.MainActivity;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Model.instance.isSignIn())
            {
                Model.instance.mainThread.post(() -> {
                    toFeedActivity();
                });
            }
            else {
                Model.instance.mainThread.post(() -> {
                    toLoginActivity();
                });
            }


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