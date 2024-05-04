package com.jude.mazeyo;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class MapShopAdapter extends RecyclerView.Adapter<MapShopAdapter.ViewHolderShop> {

    private FireBaseServices fbs;
    private Context context;
    private ArrayList<Item> lst;

    public MapShopAdapter(Context context, ArrayList<Item> lst) {
        this.context = context;
        this.lst = lst;
    }

    @NonNull
    @Override
    public MapShopAdapter.ViewHolderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new ViewHolderShop(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MapShopAdapter.ViewHolderShop holder, int position) {
        Item item = lst.get(position);
        holder.SetDetails(item);
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolderShop extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvName, tvPrice;
        private ImageView ivMap;
        Dialog dialog;

        public ViewHolderShop(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvNameItem);
            tvPrice = itemView.findViewById(R.id.tvPriceItem);
            ivMap = itemView.findViewById(R.id.ivColorItem);

            fbs = FireBaseServices.getInstance();

            dialog = new Dialog(context);
            dialog.setContentView(R.layout.buy_dialog_popup);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
            dialog.setCancelable(false);

            tvPrice.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (fbs.getUser().getCoin() < Integer.parseInt(tvPrice.getText().toString())) {
                Toast.makeText(context, "You don't have enough Mazeyo Coin", Toast.LENGTH_SHORT).show();
            } else {

                if (!dialog.isShowing()) dialog.show();
                Button buy = dialog.findViewById(R.id.btnBuyItem);
                Button exit = dialog.findViewById(R.id.btnExitBuyItem);

                buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        User user = fbs.getUser();
                        user.getOwnedMaps().add(tvName.getText().toString());
                        user.setCoin(user.getCoin() - Integer.parseInt(tvPrice.getText().toString()));

                        fbs.getFirestore().collection("Users").document(fbs.getAuth().getCurrentUser().getEmail()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                fbs.setUser(user);
                            }
                        });

                        dialog.dismiss();
                    }
                });
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        }

        public void SetDetails(Item item) {

            tvName.setText(item.getName());
            tvPrice.setText(String.valueOf(item.getPrice()));
            ivMap.setImageResource(item.getImage());

        }
    }
}