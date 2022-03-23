package com.example.collabme.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.ui.NavigationUI;

import com.example.collabme.ChatFragment;
import com.example.collabme.HomeOffers.HomeFragment;
import com.example.collabme.HomeOffers.MyOffersFragment;
import com.example.collabme.R;
import com.example.collabme.databinding.ActivityMainBinding;
import com.example.collabme.model.Modelauth;
import com.example.collabme.search.Fragment_Search;
import com.example.collabme.users.UserProfile;

public class MainActivity extends AppCompatActivity {
    NavController navCtl;
    ActivityMainBinding binding;
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

        ////


        NavigationUI.setupActionBarWithNavController(this, navCtl);
        // this is the main activity

        binding.bottomNavigation.setOnItemSelectedListener(item->{

            switch(item.getItemId()){
                case R.id.nav_home:
                    navCtl.navigate(R.id.homeFragment);
                    break;
                case R.id.nav_search:
                    navCtl.navigate(R.id.fragment_Search);
                    break;
                case R.id.nav_chat:
                    navCtl.navigate(R.id.chatFragment);
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

    private void toLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
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