package com.example.collabme;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    NavController navCtl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavHost navHost = (NavHost)getSupportFragmentManager().findFragmentById(R.id.nav_main);
        navCtl = navHost.getNavController();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        NavigationUI.setupActionBarWithNavController(this, navCtl);
        // this is the main activity
    }
}