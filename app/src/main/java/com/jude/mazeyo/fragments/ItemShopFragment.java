package com.jude.mazeyo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.jude.mazeyo.FireBaseServices;
import com.jude.mazeyo.Item;
import com.jude.mazeyo.R;
import com.jude.mazeyo.ShopAdapter;
import com.jude.mazeyo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemShopFragment extends Fragment {

    private FireBaseServices fbs;
    TextView tvMCoin;
    RecyclerView rvSkin;
    ArrayList<Item> iSkins;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ItemShopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemShopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemShopFragment newInstance(String param1, String param2) {
        ItemShopFragment fragment = new ItemShopFragment();
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
        return inflater.inflate(R.layout.fragment_item_shop, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        fbs = FireBaseServices.getInstance();
        tvMCoin = getView().findViewById(R.id.tvMCoinCountShop);
        rvSkin = getView().findViewById(R.id.rvSkinShop);
        iSkins = new ArrayList<Item>();


        if(fbs.getUser() == null){

            fbs.getFirestore().collection("Users").document(fbs.getAuth().getCurrentUser().getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    User user = documentSnapshot.toObject(User.class);
                    fbs.setUser(user);

                    tvMCoin.setText(fbs.getUser().getCoin()+":");

                    // show Coins Amount and Comment and Game Count.
                }
            });

        } else {

            tvMCoin.setText(fbs.getUser().getCoin()+":");
        }


        // this is the item skin in the shop
        iSkins.add(new Item("Black",100,R.color.black));
        iSkins.add(new Item("Light Orange",250,R.color.white_Orange));
        iSkins.add(new Item("Mango",450,R.color.Mango));
        iSkins.add(new Item("Black Bron",500,R.color.Black_Bron));
        iSkins.add(new Item("Bronze",750,R.color.Bronze));
        iSkins.add(new Item("Red Orange",850,R.color.Red_Orange));
        iSkins.add(new Item("Amber",1000,R.color.Amber));
        iSkins.add(new Item("Turquoise Blue", 2500, R.color.Turquoise_Blue));



        rvSkin.setLayoutManager(new LinearLayoutManager(getActivity() , LinearLayoutManager.HORIZONTAL, false));
        rvSkin.setAdapter(new ShopAdapter(getActivity(),iSkins));


    }
}