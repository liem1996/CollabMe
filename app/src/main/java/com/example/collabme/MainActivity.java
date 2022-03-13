package com.example.collabme;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
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

        // TODO: 3/13/2022 do not deletee!!!!!
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();

        ////


        NavigationUI.setupActionBarWithNavController(this, navCtl);
        // this is the main activity
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        System.out.println("ho");
        if(!super.onOptionsItemSelected(item)){

            switch (item.getItemId()){
                case R.id.profiel_fragment:
                    navCtl.navigate(R.id.action_global_userProfile2);

                    break;

                case R.id.home_fragment:

                    // Model.instance.getUserName(email);
                    navCtl.navigate(R.id.action_global_homeFragment2);
                    break;


            }


        }

        return false;
    }
}