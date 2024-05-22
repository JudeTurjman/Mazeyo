package com.jude.mazeyo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jude.mazeyo.objects.FireBaseServices;
import com.jude.mazeyo.objects.ItemOwned;
import com.jude.mazeyo.activities.MainActivity;
import com.jude.mazeyo.adapters.OwnedAdapter;
import com.jude.mazeyo.adapters.OwnedMapAdapter;
import com.jude.mazeyo.R;
import com.jude.mazeyo.objects.User;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private FireBaseServices fbs;
    TextView tvUsername, tvEasy, tvMedium, tvHard, tvComment;
    CardView cvLogout;
    RecyclerView rvOwnedSkin;
    ArrayList<ItemOwned> iOwnedSkins;
    RecyclerView rvOwnedMap;
    ArrayList<ItemOwned> iOwnedMaps;
    ImageView ivSetting, ivImage, ivSLNLogo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        fbs = FireBaseServices.getInstance();
        ivSLNLogo = getView().findViewById(R.id.ivCarSLNLogoProfile);
        ivImage = getView().findViewById(R.id.ivImageProfile);
        tvUsername = getView().findViewById(R.id.tvUsernameProfile);
        ivSetting = getView().findViewById(R.id.ivGoToSettingProfile);
        tvEasy = getView().findViewById(R.id.tvEasyProfile);
        tvMedium = getView().findViewById(R.id.tvMediumProfile);
        tvHard = getView().findViewById(R.id.tvHardProfile);
        tvComment  = getView().findViewById(R.id.tvCommentProfile);
        cvLogout = getView().findViewById(R.id.cvLogoutProfile);
        rvOwnedSkin = getView().findViewById(R.id.rvSkinProfile);
        iOwnedSkins = new ArrayList<ItemOwned>();
        rvOwnedMap = getView().findViewById(R.id.rvMapProfile);
        iOwnedMaps = new ArrayList<ItemOwned>();


        if(fbs.getUser()!=null) {

            User user = fbs.getUser();

            tvUsername.setText(user.getUsername());
            tvComment.setText(user.getComment());
            tvEasy.setText(Integer.toString(user.getEasy()));
            tvMedium.setText(Integer.toString(user.getMedium()));
            tvHard.setText(Integer.toString(user.getHard()));

            if (fbs.getUser().getPhoto() == null || fbs.getUser().getPhoto().isEmpty()) {
                Glide.with(getActivity()).load(R.mipmap.profile_launcher_foreground).into(ivImage);
            }else{
                Glide.with(getActivity()).load(fbs.getUser().getPhoto()).into(ivImage);
            }


            for (int i = 0; i < user.getOwnedSkins().size(); i++){
                if(user.getOwnedSkins().get(i).equals("Red")) iOwnedSkins.add(new ItemOwned("Red",false, R.color.Red));

                if(user.getOwnedSkins().get(i).equals("Black")) iOwnedSkins.add(new ItemOwned("Black", false, R.color.black));

                if(user.getOwnedSkins().get(i).equals("Light Orange"))iOwnedSkins.add(new ItemOwned("Light Orange",false,R.color.white_Orange));

                if(user.getOwnedSkins().get(i).equals("Mango"))iOwnedSkins.add(new ItemOwned("Mango",false,R.color.Mango));

                if(user.getOwnedSkins().get(i).equals("Black Bron"))iOwnedSkins.add(new ItemOwned("Black Bron",false,R.color.Black_Bron));

                if(user.getOwnedSkins().get(i).equals("Bronze"))iOwnedSkins.add(new ItemOwned("Bronze",false,R.color.Bronze));

                if(user.getOwnedSkins().get(i).equals("Red Orange"))iOwnedSkins.add(new ItemOwned("Red Orange",false,R.color.Red_Orange));

                if(user.getOwnedSkins().get(i).equals("Turquoise Blue"))iOwnedSkins.add(new ItemOwned("Turquoise Blue", false, R.color.Turquoise_Blue));

                if(user.getOwnedSkins().get(i).equals("Amber"))iOwnedSkins.add(new ItemOwned("Amber",false,R.color.Amber));

                if(user.getOwnedSkins().get(i).equals("CarSLn Blue")){
                    iOwnedSkins.add(new ItemOwned("CarSLn Blue", false, R.color.Blue700));
                    ivSLNLogo.setVisibility(View.VISIBLE);
                }
            }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            rvOwnedSkin.setLayoutManager(linearLayoutManager);
            OwnedAdapter adapter = new OwnedAdapter(getActivity(),iOwnedSkins);
            rvOwnedSkin.setAdapter(adapter);

            // the map you have
            for (int i = 0; i < user.getOwnedMaps().size(); i++){
                if(user.getOwnedMaps().get(i).equals("Nature")) iOwnedMaps.add(new ItemOwned("Nature",false, R.drawable.map_nature));

                if(user.getOwnedMaps().get(i).equals("Water")) iOwnedMaps.add(new ItemOwned("Water", false, R.drawable.map_water));

                if(user.getOwnedMaps().get(i).equals("Beach"))iOwnedMaps.add(new ItemOwned("Beach",false,R.drawable.map_beach));

                if(user.getOwnedMaps().get(i).equals("Ice"))iOwnedMaps.add(new ItemOwned("Ice",false,R.drawable.map_ice));

                if(user.getOwnedMaps().get(i).equals("Greek Column"))iOwnedMaps.add(new ItemOwned("Greek Column",false,R.drawable.map_ancient_greek_column));

                if(user.getOwnedMaps().get(i).equals("Egyptian Pyramid"))iOwnedMaps.add(new ItemOwned("Egyptian Pyramid",false,R.drawable.map_egyptian_pyramid));

                if(user.getOwnedMaps().get(i).equals("World Map"))iOwnedMaps.add(new ItemOwned("World Map",false,R.drawable.map_world_map));

                if(user.getOwnedMaps().get(i).equals("Church"))iOwnedMaps.add(new ItemOwned("Church", false, R.drawable.map_church_of_the_annunciation));

                if(user.getOwnedMaps().get(i).equals("Al Aqsa Mosque"))iOwnedMaps.add(new ItemOwned("Al Aqsa Mosque",false,R.drawable.map_al_aqsa_mosque));
            }

            rvOwnedMap.setLayoutManager(new LinearLayoutManager(getActivity() , LinearLayoutManager.HORIZONTAL, false));
            rvOwnedMap.setAdapter(new OwnedMapAdapter(getActivity(),iOwnedMaps));
        }

        cvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {GoToLogin();}
        });

        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GOToEditProfile();
            }
        });

    }

    private void GOToEditProfile() {

        BottomNavigationView bnv = ((MainActivity) getActivity()).getBottomNavigationView();
        bnv.setVisibility(View.GONE);

        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new EditProfileFragment());
        ft.commit();

    }

    private void GoToLogin(){

        BottomNavigationView bnv = ((MainActivity) getActivity()).getBottomNavigationView();
        bnv.setVisibility(View.GONE);

        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new LogInFragment());
        ft.commit();
    }
}