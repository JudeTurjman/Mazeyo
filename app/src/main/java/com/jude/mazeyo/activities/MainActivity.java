package com.jude.mazeyo.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.jude.mazeyo.R;
import com.jude.mazeyo.fragments.HomeFragment;
import com.jude.mazeyo.fragments.ItemShopFragment;
import com.jude.mazeyo.fragments.LogInFragment;
import com.jude.mazeyo.fragments.ProfileFragment;
import com.jude.mazeyo.fragments.RankAllFragment;
import com.jude.mazeyo.objects.FireBaseServices;

public class MainActivity extends AppCompatActivity {

    FireBaseServices fbs;
    private BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To Make the App not Flip.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // To hide "Mazeyo" on the top.
        getSupportActionBar().hide();

        fbs = FireBaseServices.getInstance();
        bnv= findViewById(R.id.BottomNavigationView);


        if(fbs.getAuth().getCurrentUser()!=null){

            bnv.setVisibility(View.VISIBLE);
            GoToHome();

        }else{

            bnv.setVisibility(View.GONE);
            GoToLogin();

        }


        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.nav_home) {
                    GoToHome();
                }
                if(item.getItemId() == R.id.nav_profile){
                    GoToProfile();
                }
                if(item.getItemId() == R.id.nav_shop){
                    GoToShop();
                }
                if(item.getItemId() == R.id.nav_rank){
                    GoToRankAll();
                }

                return true;
            }
        });

    }

    public void GoToHome() {

        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new HomeFragment());
        ft.commit();
    }

    private void GoToShop() {

        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new ItemShopFragment());
        ft.addToBackStack("HomeFragment");
        ft.commit();
    }

    private void GoToProfile() {

        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new ProfileFragment());
        ft.addToBackStack("HomeFragment");
        ft.commit();
    }

    private void GoToLogin() {

        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new LogInFragment());
        ft.addToBackStack("HomeFragment");
        ft.commit();
    }

    private void GoToRankAll() {

        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new RankAllFragment());
        ft.addToBackStack("HomeFragment");
        ft.commit();
    }

    public BottomNavigationView getBottomNavigationView(){
        return bnv;
    }

}