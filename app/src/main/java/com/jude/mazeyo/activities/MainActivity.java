package com.jude.mazeyo.activities;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    FireBaseServices fbs;
    private BottomNavigationView bnv;
    Dialog dialog;

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
            fbs.setCurrentPage("Home");
            GoToHome();

        }else{

            bnv.setVisibility(View.GONE);
            fbs.setCurrentPage("Login");
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

    // The Back Pressed thing _______________:)__________________
    @Override
    public void onBackPressed() {

        String wherePage = fbs.getCurrentPage();
        if(!wherePage.equals("")){
            if (wherePage.equals("Home")){
                dialog = new Dialog(this);
                dialog.setContentView(R.layout.exit_app_dialog_popup);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
                dialog.show();

                Button exit = dialog.findViewById(R.id.btnExitApp);
                Button stay = dialog.findViewById(R.id.btnExitExitApp);
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finishAffinity();
                        dialog.dismiss();
                    }
                });
                stay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            } else if (wherePage.equals("Login")){
                if (fbs.getAuth().getCurrentUser()!=null){
                    bnv.setSelectedItemId(R.id.nav_home);
                    bnv.setVisibility(View.VISIBLE);
                    GoToHome();
                }
                else {
                    dialog = new Dialog(this);
                    dialog.setContentView(R.layout.exit_app_dialog_popup);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
                    dialog.show();

                    Button exit = dialog.findViewById(R.id.btnExitApp);
                    Button stay = dialog.findViewById(R.id.btnExitExitApp);
                    exit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finishAffinity();
                            dialog.dismiss();
                        }
                    });
                    stay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }

            } else if (wherePage.equals("Shop") || wherePage.equals("Profile") || wherePage.equals("RankAll")) {
                bnv.setSelectedItemId(R.id.nav_home);
                GoToHome();

            } else if (wherePage.equals("Game")) {
                if (!fbs.getDifficulty().equals("DailyPlay")){
                    dialog = new Dialog(this);
                    dialog.setContentView(R.layout.exit_game_dialog_popup);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
                    dialog.show();

                    Button exit = dialog.findViewById(R.id.btnExitGame);

                    exit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            bnv.setVisibility(View.VISIBLE);
                            GoToHome();
                        }
                    });
                }

            } else if (wherePage.equals("RankSpecific")) {
                bnv.setVisibility(View.VISIBLE);
                bnv.setSelectedItemId(R.id.nav_rank);
                GoToRankAll();

            } else if (wherePage.equals("EditProfile")) {
                EditText etUnameEdit, etNoteEdit;
                etUnameEdit = findViewById(R.id.etEditUserNameEditProfile);
                etNoteEdit = findViewById(R.id.etEditNoteEditProfile);

                if(!fbs.getUser().getUsername().equals(etUnameEdit.getText().toString()) || !fbs.getUser().getComment().equals(etNoteEdit.getText().toString())){

                    dialog = new Dialog(this);
                    dialog.setContentView(R.layout.exit_edit_profile_dialog_popup);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
                    dialog.show();

                    Button exit = dialog.findViewById(R.id.btnExitEditProfile);

                    exit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            bnv.setVisibility(View.VISIBLE);
                            bnv.setSelectedItemId(R.id.nav_profile);
                            GoToProfile();
                        }
                    });

                }else {
                    bnv.setVisibility(View.VISIBLE);
                    bnv.setSelectedItemId(R.id.nav_profile);
                    GoToProfile();
                }

            } else if (wherePage.equals("SignUp") || wherePage.equals("ForgotPass")) {
                GoToLogin();

            }
            else {
                super.onBackPressed();
            }
        }

    }

    public void GoToHome() {
        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new HomeFragment());
        fbs.setCurrentPage("Home");
        ft.commit();
    }

    private void GoToShop() {

        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new ItemShopFragment());
        fbs.setCurrentPage("Shop");
        ft.commit();
    }

    private void GoToProfile() {

        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new ProfileFragment());
        fbs.setCurrentPage("Profile");
        ft.commit();
    }

    private void GoToLogin() {

        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new LogInFragment());
        fbs.setCurrentPage("Login");
        ft.commit();
    }

    private void GoToRankAll() {

        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new RankAllFragment());
        fbs.setCurrentPage("RankAll");
        ft.commit();
    }

    public BottomNavigationView getBottomNavigationView(){
        return bnv;
    }

}