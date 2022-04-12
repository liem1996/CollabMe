package com.example.collabme.Activites;

import static android.graphics.Color.rgb;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.ui.NavigationUI;

import com.example.collabme.R;
import com.example.collabme.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    NavController navCtl;
    ActivityMainBinding binding;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavHost navHost = (NavHost)getSupportFragmentManager().findFragmentById(R.id.nav_main);
        navCtl = navHost.getNavController();

        // TODO: 3/13/2022 do not deletee!!!!!
       ActionBar actionBar = getSupportActionBar();
       actionBar.hide();

        NavigationUI.setupActionBarWithNavController(this, navCtl);
        // this is the main activity
        progressBar =findViewById(R.id.mainActivity_progressbar);
        progressBar.setVisibility(View.GONE);
        progressBar.getIndeterminateDrawable().setColorFilter(rgb(132, 80, 160), android.graphics.PorterDuff.Mode.MULTIPLY);

        binding.bottomNavigation.setOnItemSelectedListener(item->{

            switch(item.getItemId()){
                case R.id.nav_home:
                    navCtl.navigate(R.id.homeFragment);
                    break;
                case R.id.nav_search:
                    navCtl.navigate(R.id.fragment_Search);
                    break;
                case R.id.nav_chat:
                    toWhatup();
                    break;
                case R.id.nav_offer:
                    navCtl.navigate(R.id.myOffersFragment);
                    break;
                case R.id.nav_account:
                    navCtl.navigate(R.id.userProfile);
                    break;
            }
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    private void toWhatup() {
        Intent intent = new Intent(this, WhatUpActivity.class);
        startActivity(intent);
        finish();
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        if(!super.onOptionsItemSelected(item)){
//
//            switch (item.getItemId()){
//                case R.id.profiel_fragment:
//                    navCtl.navigate(R.id.action_global_userProfile2);
//
//                    break;
//
//                case R.id.home_fragment:
//
//                    // Model.instance.getUserName(email);
//                    navCtl.navigate(R.id.action_global_homeFragment2);
//                    break;
//
//                case R.id.menu_logout:
//
//                    // Model.instance.getUserName(email);
//                    Modelauth.instance2.logout(new Modelauth.logout() {
//                        @Override
//                        public void onComplete(int code) {
//                            if(code==200) {
//                                toLoginActivity();
//                            }
//                            else{
//                                Toast.makeText(MainActivity.this, "boo boo", Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    });
//                    break;
//
//                case R.id.addoffer:
//                    // Model.instance.getUserName(email);
//                    navCtl.navigate(R.id.action_global_addOfferDetailsFragemnt);
//                    break;
//                case R.id.item_editOffer:
//                    // Model.instance.getUserName(email);
//                    navCtl.navigate(R.id.action_global_editOfferFragment);
//                    break;
//
//                case R.id.myoffers:
//                    // Model.instance.getUserName(email);
//                    navCtl.navigate(R.id.action_global_myOffersFragment);
//                    break;
//
//
//            }
//
//
//        }
//
//        return false;
//    }
}