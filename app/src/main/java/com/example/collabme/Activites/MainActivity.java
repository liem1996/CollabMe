package com.example.collabme.Activites;

import static android.graphics.Color.rgb;

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
import com.example.collabme.model.ModelUsers;
import com.example.collabme.objects.User;

/**
 *
 * the MainActivity - the main for the offers,user pages
 * the bottome menu to navigate to the diffrent page - profile,home,chat,myoffers and waiting offers
 *
 */
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
                    navCtl.navigate(R.id.whatupPage);
                    break;
                case R.id.nav_offer:
                    navCtl.navigate(R.id.myOffersFragment);
                    break;
                case R.id.nav_account:
                    ModelUsers.instance3.getUserConnect(new ModelUsers.getuserconnect() {
                        @Override
                        public void onComplete(User profile) {
                            Bundle bundle = new Bundle();
                            bundle.putString("username",profile.getUsername());
                            bundle.putString("password",profile.getPassword());
                            bundle.putBoolean("company",profile.getCompany());
                            bundle.putBoolean("influencer",profile.getInfluencer());
                            bundle.putString("age",profile.getAge());
                            bundle.putString("email",profile.getEmail());
                            bundle.putString("gender",profile.getSex());
                            bundle.putStringArray("platform",profile.getPlatforms());
                            bundle.putStringArray("profession",profile.getProfessions());
                            bundle.putString("followers",profile.getFollowers());
                            bundle.putString("postsuploads",profile.getNumOfPosts());
                            navCtl.navigate(R.id.userProfile,bundle);
                        }
                    });

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
}