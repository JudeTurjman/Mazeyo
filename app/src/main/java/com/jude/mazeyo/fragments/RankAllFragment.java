package com.jude.mazeyo.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.Usage;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.jude.mazeyo.FireBaseServices;
import com.jude.mazeyo.R;
import com.jude.mazeyo.RankAdapter;
import com.jude.mazeyo.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RankAllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RankAllFragment extends Fragment {

    private FireBaseServices fbs;
    TextView tvSeeEasy, tvSeeMedium, tvSeeHard, tvSeeDaily;
    RecyclerView rvEasy, rvMedium, rvHard, rvDaily;
    ArrayList<User> listEasy, listMedium, listHard, listDaily, topListEasy, topListMedium, topListHard, topListDaily;
    RankAdapter adapterEasy, adapterMedium, adapterHard, adapterDaily;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RankAllFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RankAllFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RankAllFragment newInstance(String param1, String param2) {
        RankAllFragment fragment = new RankAllFragment();
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
        return inflater.inflate(R.layout.fragment_rank_all, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        fbs = FireBaseServices.getInstance();
        tvSeeEasy = getView().findViewById(R.id.tvSeeAllEasyRankAll);
        tvSeeMedium = getView().findViewById(R.id.tvSeeAllMediumRankAll);
        tvSeeHard = getView().findViewById(R.id.tvSeeAllHardRankAll);
        tvSeeDaily = getView().findViewById(R.id.tvSeeAllDailyRankAll);
        rvEasy = getView().findViewById(R.id.rvEasyRankAll);
        rvMedium = getView().findViewById(R.id.rvMediumRankAll);
        rvHard = getView().findViewById(R.id.rvHardRankAll);
        rvDaily = getView().findViewById(R.id.rvDailyRankAll);
        listEasy = new ArrayList<User>();
        listMedium = new ArrayList<User>();
        listHard = new ArrayList<User>();
        listDaily = new ArrayList<User>();
        topListEasy = new ArrayList<User>();
        topListMedium = new ArrayList<User>();
        topListHard = new ArrayList<User>();
        topListDaily = new ArrayList<User>();
        rvEasy.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMedium.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvHard.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDaily.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterEasy = new RankAdapter(getActivity(),listEasy,"Easy");
        adapterMedium = new RankAdapter(getActivity(),listMedium,"Medium" );
        adapterHard = new RankAdapter(getActivity(),listHard,"Hard" );
        adapterDaily = new RankAdapter(getActivity(),listDaily,"Daily" );

        FirebaseFirestore db = fbs.getFirestore();

        db.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (DocumentSnapshot dataSnapshot: queryDocumentSnapshots.getDocuments()){

                    User user = dataSnapshot.toObject(User.class);

                    listEasy.add(user);
                    listMedium.add(user);
                    listHard.add(user);
                    listDaily.add(user);

                }

                Collections.sort(listEasy, new Comparator<User>() {
                    @Override
                    public int compare(User user1, User user2) {
                        // Compare based on the EasyCounter
                        return Integer.compare(user2.getEasy(), user1.getEasy());
                    }
                });
                for (int i = 0; i < 3 ; i++){
                    topListEasy.add(listEasy.get(i));
                }
                Collections.sort(listMedium, new Comparator<User>() {
                    @Override
                    public int compare(User user1, User user2) {
                        // Compare based on the EasyCounter
                        return Integer.compare(user2.getMedium(), user1.getMedium());
                    }
                });
                for (int i = 0; i < 3 ; i++){
                    topListMedium.add(listMedium.get(i));
                }
                Collections.sort(listHard, new Comparator<User>() {
                    @Override
                    public int compare(User user1, User user2) {
                        // Compare based on the EasyCounter
                        return Integer.compare(user2.getHard(), user1.getHard());
                    }
                });
                for (int i = 0; i < 3 ; i++){
                    topListHard.add(listHard.get(i));
                }
                Collections.sort(listDaily, new Comparator<User>() {
                    @Override
                    public int compare(User user1, User user2) {
                        // Compare based on the EasyCounter
                        return Integer.compare(user2.getDailyCount(), user1.getDailyCount());
                    }
                });
                for (int i = 0; i < 3 ; i++){
                    topListDaily.add(listDaily.get(i));
                }


                SettingFrame();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"Couldn't load the Ranking", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void SettingFrame(){

        rvEasy.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMedium.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvHard.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDaily.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterEasy = new RankAdapter(getActivity(),topListEasy,"Easy");
        adapterMedium = new RankAdapter(getActivity(),topListMedium,"Medium" );
        adapterHard = new RankAdapter(getActivity(),topListHard,"Hard" );
        adapterDaily = new RankAdapter(getActivity(),topListDaily,"Daily" );
        rvEasy.setAdapter(adapterEasy);
        rvMedium.setAdapter(adapterMedium);
        rvHard.setAdapter(adapterHard);
        rvDaily.setAdapter(adapterDaily);
    }

}