package com.jude.mazeyo.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.jude.mazeyo.objects.FireBaseServices;
import com.jude.mazeyo.R;
import com.jude.mazeyo.adapters.RankAdapter;
import com.jude.mazeyo.objects.User;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RankSpecificFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RankSpecificFragment extends Fragment {

    private FireBaseServices fbs;
    TextView tvRankName;
    ImageView ivGoBack;
    RecyclerView rvSpecific;
    ArrayList<User> listSpecific;
    RankAdapter adapterSpecific;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RankSpecificFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RankSpecificFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RankSpecificFragment newInstance(String param1, String param2) {
        RankSpecificFragment fragment = new RankSpecificFragment();
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
        return inflater.inflate(R.layout.fragment_rank_specific, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        fbs = FireBaseServices.getInstance();
        ivGoBack = getView().findViewById(R.id.ivGoBackRankSpecific);
        tvRankName = getView().findViewById(R.id.tvRankSpecific);
        rvSpecific = getView().findViewById(R.id.rvRankSpecific);
        listSpecific = new ArrayList<User>();

        FirebaseFirestore db = fbs.getFirestore();
        db.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (DocumentSnapshot dataSnapshot: queryDocumentSnapshots.getDocuments()){

                    User user = dataSnapshot.toObject(User.class);

                    listSpecific.add(user);
                }

                if (fbs.getSeeRank() != null){
                    if (fbs.getSeeRank().equals("Easy")){
                        adapterSpecific = new RankAdapter(getActivity(),listSpecific,"Easy");
                        tvRankName.setText("Easy Ranking");

                        listSpecific.sort(new Comparator<User>() {
                            @Override
                            public int compare(User user1, User user2) {
                                // Compare based on the EasyCounter
                                return Integer.compare(user2.getEasy(), user1.getEasy());
                            }
                        });
                        SettingFrame("Easy");

                    } else if (fbs.getSeeRank().equals("Medium")) {
                        adapterSpecific = new RankAdapter(getActivity(),listSpecific,"Medium");
                        tvRankName.setText("Medium Ranking");

                        listSpecific.sort(new Comparator<User>() {
                            @Override
                            public int compare(User user1, User user2) {
                                // Compare based on the MediumCounter
                                return Integer.compare(user2.getMedium(), user1.getMedium());
                            }
                        });
                        SettingFrame("Medium");

                    } else if (fbs.getSeeRank().equals("Hard")) {
                        adapterSpecific = new RankAdapter(getActivity(),listSpecific,"Hard");
                        tvRankName.setText("Hard Ranking");

                        listSpecific.sort(new Comparator<User>() {
                            @Override
                            public int compare(User user1, User user2) {
                                // Compare based on the HardCounter
                                return Integer.compare(user2.getHard(), user1.getHard());
                            }
                        });
                        SettingFrame("Hard");

                    }else if (fbs.getSeeRank().equals("Daily")) {
                        adapterSpecific = new RankAdapter(getActivity(),listSpecific,"Daily");
                        tvRankName.setText("Daily Ranking");

                        listSpecific.sort(new Comparator<User>() {
                            @Override
                            public int compare(User user1, User user2) {
                                // Compare based on the DailyCounter
                                return Integer.compare(user2.getDailyCount(), user1.getDailyCount());
                            }
                        });
                        SettingFrame("Daily");

                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"Couldn't load the Ranking", Toast.LENGTH_LONG).show();
            }
        });

        ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbs.setSeeRank("No Ranking");
                GoToRankAll();
            }
        });
    }

    private void GoToRankAll() {

        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new RankAllFragment());
        ft.commit();
    }
    private void SettingFrame(String SpecificRankingName){

        rvSpecific.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterSpecific = new RankAdapter(getActivity(),listSpecific,SpecificRankingName);
        rvSpecific.setAdapter(adapterSpecific);
    }

}