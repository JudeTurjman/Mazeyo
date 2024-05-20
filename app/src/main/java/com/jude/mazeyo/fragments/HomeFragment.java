package com.jude.mazeyo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.jude.mazeyo.objects.FireBaseServices;
import com.jude.mazeyo.activities.MainActivity;
import com.jude.mazeyo.R;
import com.jude.mazeyo.objects.User;

import java.util.Calendar;
import java.util.Date;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private FireBaseServices fbs;
    CardView cvEasy, cvMedium, cvHard, cvDaily;
    ImageView ivProfile, ivSLNLogo;
    TextView tvMCoin, tvDailyCount;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        fbs = FireBaseServices.getInstance();
        ivSLNLogo = getView().findViewById(R.id.ivCarSLNLogoHome);
        cvEasy = getView().findViewById(R.id.cvEasyHome);
        cvMedium = getView().findViewById(R.id.cvMediumHome);
        cvHard = getView().findViewById(R.id.cvHardHome);
        cvDaily = getView().findViewById(R.id.cvDailyHome);
        ivProfile = getView().findViewById(R.id.ivProfileHome);
        Glide.with(this).load(R.mipmap.profile_launcher_foreground).transform(new CropCircleTransformation()).into(ivProfile);
        tvMCoin = getView().findViewById(R.id.tvMCoinCountHome);
        tvDailyCount = getView().findViewById(R.id.tvDailyCountHome);
        fbs.setDifficulty("NoGame");

        if(fbs.getUser() == null){
            fbs.getFirestore().collection("Users").document(fbs.getAuth().getCurrentUser().getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                 public void onSuccess(DocumentSnapshot documentSnapshot) {

                    User user = documentSnapshot.toObject(User.class);
                    fbs.setUser(user);

                    tvMCoin.setText(Integer.toString(user.getCoin())+":");

                    // reset the user (if have played the daily play or Not) after that reset the user dailyCount...

                    long date1 = user.getDatePlay().getTime() + (24 * 60 * 60 * 1000);
                    Date newDate = Calendar.getInstance().getTime();
                    long date2 = newDate.getTime();

                    if (date1 < date2){
                        date1 += (24 * 60 * 60 * 1000);
                        if (user.getDidDaily() && date1 >= date2){
                            user.setDidDaily(false);
                            user.setDatePlay(Calendar.getInstance().getTime());
                        }
                        else {
                            user.setDailyCount(0);
                            user.setDidDaily(false);
                        }
                    }

                    tvDailyCount.setText(Integer.toString(user.getDailyCount()));


                    // show Coins Amount and Comment and Game Count.
                    // and show daily played in a row count.

                    // put the CarSLN logo in the profile photo "SLN"
                    for (int i = 0; i < user.getOwnedSkins().size(); i++){
                        if(user.getOwnedSkins().get(i).equals("CarSLn Blue")){
                            ivSLNLogo.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

        } else {

            tvMCoin.setText(Integer.toString(fbs.getUser().getCoin())+":");

            // reset the user (if have played the daily play or Not) after that reset the user dailyCount...

            long date1 = fbs.getUser().getDatePlay().getTime() + (24 * 60 * 60 * 1000);
            Date newDate = Calendar.getInstance().getTime();
            long date2 = newDate.getTime();

            if (date1 < date2){
                date1 += (24 * 60 * 60 * 1000);
                if (fbs.getUser().getDidDaily() && date1 >= date2){
                    fbs.getUser().setDidDaily(false);
                    fbs.getUser().setDatePlay(Calendar.getInstance().getTime());
                }
                else {
                    fbs.getUser().setDailyCount(0);
                    fbs.getUser().setDidDaily(false);
                }
            }
            tvDailyCount.setText(Integer.toString(fbs.getUser().getDailyCount()));

            for (int i = 0; i < fbs.getUser().getOwnedSkins().size(); i++){
                if(fbs.getUser().getOwnedSkins().get(i).equals("CarSLn Blue")){
                    ivSLNLogo.setVisibility(View.VISIBLE);
                }
            }

        }

        cvEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToEasy();
            }
        });
        cvMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToMedium();
            }
        });
        cvHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToHard();
            }
        });
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToProfile();
                ((MainActivity) getActivity()).getBottomNavigationView().setSelectedItemId(R.id.nav_profile);
            }
        });


        cvDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!fbs.getUser().getDidDaily())
                    GoTODailyPlay();
                else
                    Toast.makeText(getActivity(), "You Played today, you can't play again now", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void GoToProfile(){
        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new ProfileFragment());
        ft.commit();
    }




    public void GoToEasy(){

        BottomNavigationView bnv = ((MainActivity) getActivity()).getBottomNavigationView();
        bnv.setVisibility(View.GONE);

        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new GamePlayFragment());
        ft.commit();

        // set the difficulty to Easy mode
        fbs.setDifficulty("Easy");
    }
    public void GoToMedium(){

        BottomNavigationView bnv = ((MainActivity) getActivity()).getBottomNavigationView();
        bnv.setVisibility(View.GONE);

        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new GamePlayFragment());
        ft.commit();

        // set the difficulty to Medium mode
        fbs.setDifficulty("Medium");
    }
    public void GoToHard(){

        BottomNavigationView bnv = ((MainActivity) getActivity()).getBottomNavigationView();
        bnv.setVisibility(View.GONE);

        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new GamePlayFragment());
        ft.commit();

        // set the difficulty to Hard mode
        fbs.setDifficulty("Hard");
    }
    public void GoTODailyPlay(){

        BottomNavigationView bnv = ((MainActivity) getActivity()).getBottomNavigationView();
        bnv.setVisibility(View.GONE);

        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new GamePlayFragment());
        ft.commit();

        // set the difficulty to Daily mode
        fbs.setDifficulty("DailyPlay");
    }
}