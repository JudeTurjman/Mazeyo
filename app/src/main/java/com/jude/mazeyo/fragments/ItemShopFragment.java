package com.jude.mazeyo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.jude.mazeyo.FireBaseServices;
import com.jude.mazeyo.Item;
import com.jude.mazeyo.R;
import com.jude.mazeyo.ShopAdapter;
import com.jude.mazeyo.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemShopFragment extends Fragment {

    private FireBaseServices fbs;
    TextView tvMCoin, tvItemPrice, tvColorName;
    RecyclerView rvSkin;
    ArrayList<Item> iSkins;
    boolean Black = false, LightOrange = false,Mango = false,Bronze = false, RedOrange = false,TurquoiseBlue = false,BlackBron = false,Amber = false,CarSLnBlue = false;



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
        tvItemPrice = getView().findViewById(R.id.tvPriceItem);
        tvColorName = getView().findViewById(R.id.tvNameItem);



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

        User user = fbs.getUser();

        // this is the item skin in the shop
        for (int i = 0; i < user.getOwnedSkins().size(); i++) {
            if (user.getOwnedSkins().get(i).equals("Black")) {
                Black = true;
            }
        }
        if(!Black)iSkins.add(new Item("Black", 100, R.color.black));

        for (int i = 0; i < user.getOwnedSkins().size(); i++) {
            if (user.getOwnedSkins().get(i).equals("Light Orange")) {
                LightOrange = true;
            }
        }
        if(!LightOrange)iSkins.add(new Item("Light Orange",250,R.color.white_Orange));

        for (int i = 0; i < user.getOwnedSkins().size(); i++) {
            if (user.getOwnedSkins().get(i).equals("Mango")) {
                Mango = true;
            }
        }
        if(!Mango)iSkins.add(new Item("Mango",500,R.color.Mango));

        for (int i = 0; i < user.getOwnedSkins().size(); i++) {
            if (user.getOwnedSkins().get(i).equals("Black Bron")) {
                BlackBron = true;
            }
        }
        if(!BlackBron)iSkins.add(new Item("Black Bron",1000,R.color.Black_Bron));

        for (int i = 0; i < user.getOwnedSkins().size(); i++) {
            if (user.getOwnedSkins().get(i).equals("Bronze")) {
                Bronze = true;
            }
        }
        if(!Bronze)iSkins.add(new Item("Bronze",5000,R.color.Bronze));

        for (int i = 0; i < user.getOwnedSkins().size(); i++) {
            if (user.getOwnedSkins().get(i).equals("Red Orange")) {
                RedOrange = true;
            }
        }
        if(!RedOrange)iSkins.add(new Item("Red Orange",12000,R.color.Red_Orange));

        for (int i = 0; i < user.getOwnedSkins().size(); i++) {
            if (user.getOwnedSkins().get(i).equals("Turquoise Blue")) {
                TurquoiseBlue = true;
            }
        }
        if(!TurquoiseBlue)iSkins.add(new Item("Turquoise Blue", 25000, R.color.Turquoise_Blue));

        for (int i = 0; i < user.getOwnedSkins().size(); i++) {
            if (user.getOwnedSkins().get(i).equals("Amber")) {
                Amber = true;
            }
        }
        if(!Amber)iSkins.add(new Item("Amber",62000,R.color.Amber));

        for (int i =0 ; i<user.getOwnedSkins().size(); i++) {
            if (user.getOwnedSkins().get(i).equals("CarSLn Blue")) {
                CarSLnBlue = true;
            }
        }
        if(!CarSLnBlue)iSkins.add(new Item("CarSLn Blue", 88000, R.drawable.blue700_carsln));



        rvSkin.setLayoutManager(new LinearLayoutManager(getActivity() , LinearLayoutManager.HORIZONTAL, false));
        rvSkin.setAdapter(new ShopAdapter(getActivity(),iSkins));


    }
}