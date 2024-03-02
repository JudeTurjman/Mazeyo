package com.jude.mazeyo.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jude.mazeyo.MainActivity;
import com.jude.mazeyo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameDalyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameDalyFragment extends Fragment {

    CardView cvHome;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GameDalyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameDalyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameDalyFragment newInstance(String param1, String param2) {
        GameDalyFragment fragment = new GameDalyFragment();
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
        return inflater.inflate(R.layout.fragment_game_play, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        cvHome = getView().findViewById(R.id.cvGotoHomeMedium);

        cvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {GoToHome();}
        });

    }

    public void GoToHome(){

        BottomNavigationView bnv = ((MainActivity) getActivity()).getBottomNavigationView();
        bnv.setVisibility(View.VISIBLE);

        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new HomeFragment());
        ft.commit();
    }
}