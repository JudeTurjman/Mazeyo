package com.jude.mazeyo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.jude.mazeyo.fragments.HomeFragment;
import com.jude.mazeyo.fragments.ItemShopFragment;
import com.jude.mazeyo.fragments.LogInFragment;
import com.jude.mazeyo.fragments.ProfileFragment;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    FireBaseServices fbs;
    private BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To Make the App not Flip.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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

                return true;
            }
        });

    }

    //todo: this for reset the daily play

    private void GoToHome() {

        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new HomeFragment());
        ft.commit();
    }

    private void GoToShop() {

        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new ItemShopFragment());
        ft.commit();
    }

    private void GoToProfile() {

        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new ProfileFragment());
        ft.commit();
    }

    private void GoToLogin() {

        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new LogInFragment());
        ft.commit();
    }

    public BottomNavigationView getBottomNavigationView(){
        return bnv;
    }

}