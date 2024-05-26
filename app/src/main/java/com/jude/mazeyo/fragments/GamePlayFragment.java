package com.jude.mazeyo.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jude.mazeyo.objects.FireBaseServices;
import com.jude.mazeyo.activities.MainActivity;
import com.jude.mazeyo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GamePlayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GamePlayFragment extends Fragment {

    FireBaseServices fbs;
    CardView cvHome;
    Dialog dialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GamePlayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GamePlayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GamePlayFragment newInstance(String param1, String param2) {
        GamePlayFragment fragment = new GamePlayFragment();
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

        fbs =   FireBaseServices.getInstance();
        cvHome = getView().findViewById(R.id.cvGotoHomeMedium);

        if (fbs.getDifficulty().equals("DailyPlay")){

            cvHome.setVisibility(View.GONE);
        }else {
            cvHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!fbs.getDifficulty().equals("DailyPlay")){
                        dialog = new Dialog(getActivity());
                        dialog.setContentView(R.layout.exit_game_dialog_popup);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
                        dialog.show();

                        Button exit = dialog.findViewById(R.id.btnExitGame);

                        exit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                GoToHome();
                            }
                        });
                    }
                }
            });
        }
    }

    public void GoToHome(){

        BottomNavigationView bnv = ((MainActivity) getActivity()).getBottomNavigationView();
        bnv.setVisibility(View.VISIBLE);

        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new HomeFragment());
        ft.commit();
    }
}