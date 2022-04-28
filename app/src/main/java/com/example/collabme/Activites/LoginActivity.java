package com.example.collabme.Activites;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.ui.NavigationUI;

import com.example.collabme.R;
/**
 *
 * the login - the user access to the app
 * entering the user details
 * using nav controller and navigation to move from the login to home
 *
 */

public class LoginActivity extends AppCompatActivity {
    NavController navCtl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        NavHost navHost = (NavHost) getSupportFragmentManager().findFragmentById(R.id.login_navhost);
        navCtl = navHost.getNavController();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        NavigationUI.setupActionBarWithNavController(this, navCtl);
    }
}