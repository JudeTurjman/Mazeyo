package com.jude.mazeyo.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.jude.mazeyo.FireBaseServices;
import com.jude.mazeyo.Item;
import com.jude.mazeyo.MainActivity;
import com.jude.mazeyo.R;
import com.jude.mazeyo.ShopAdapter;
import com.jude.mazeyo.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private FireBaseServices fbs;
    TextView tvUsername, tvEasy, tvMedium, tvHard, tvComment;
    CardView cvLogout;
    RecyclerView rvSkin;
    ArrayList<Item> iSkin;

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
        tvUsername = getView().findViewById(R.id.tvUsernameProfile);
        tvEasy = getView().findViewById(R.id.tvEasyProfile);
        tvMedium = getView().findViewById(R.id.tvMediumProfile);
        tvHard = getView().findViewById(R.id.tvHardProfile);
        tvComment  = getView().findViewById(R.id.tvCommentProfile);
        cvLogout = getView().findViewById(R.id.cvLogoutProfile);
        rvSkin = getView().findViewById(R.id.rvSkinProfile);
        iSkin = new ArrayList<Item>();


        if(fbs.getUser()!=null) {

            tvUsername.setText(fbs.getUser().getUsername());
            tvComment.setText(fbs.getUser().getComment());
            tvEasy.setText(Integer.toString(fbs.getUser().getEasy()));
            tvMedium.setText(Integer.toString(fbs.getUser().getMedium()));
            tvHard.setText(Integer.toString(fbs.getUser().getHard()));

            //iSkin.add(new Item("Black",100, Color.RED));
            //
            //
            //            rvSkin.setLayoutManager(new LinearLayoutManager(getActivity() , LinearLayoutManager.HORIZONTAL, false));
            //            rvSkin.setAdapter(new ShopAdapter(getActivity(),iSkin));
        }

        cvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {GoToLogin();}
        });

    }

    private void GoToLogin(){

        BottomNavigationView bnv = ((MainActivity) getActivity()).getBottomNavigationView();
        bnv.setVisibility(View.GONE);

        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new LogInFragment());
        ft.commit();
    }
}