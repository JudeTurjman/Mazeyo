package com.jude.mazeyo.adapters;

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
import com.jude.mazeyo.objects.FireBaseServices;
import com.jude.mazeyo.objects.Item;
import com.jude.mazeyo.R;
import com.jude.mazeyo.objects.User;

import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolderShop> {

    private Context context;
    private ArrayList<Item> lst;
    private FireBaseServices fbs;
    Dialog dialog;


    public ShopAdapter(Context context, ArrayList<Item> lst) {
        fbs = FireBaseServices.getInstance();
        this.context = context;
        this.lst = lst;
    }

    @NonNull
    @Override
    public ViewHolderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new ViewHolderShop(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderShop holder, int position) {

        Item item = lst.get(position);
        holder.SetDetails(item);

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.buy_dialog_popup);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
        dialog.setCancelable(false);

        holder.tvPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fbs.getUser().getCoin() < Integer.parseInt(holder.tvPrice.getText().toString())){
                    Toast.makeText(context, "You don't have enough Mazeyo Coin", Toast.LENGTH_SHORT).show();
                }
                else {

                    if(!dialog.isShowing()) dialog.show();
                    Button buy = dialog.findViewById(R.id.btnBuyItem);
                    Button exit = dialog.findViewById(R.id.btnExitBuyItem);

                    buy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            User user = fbs.getUser();
                            user.getOwnedSkins().add(holder.tvName.getText().toString());
                            user.setCoin(user.getCoin() - Integer.parseInt(holder.tvPrice.getText().toString()));

                            fbs.getFirestore().collection("Users").document(fbs.getAuth().getCurrentUser().getEmail()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    fbs.setUser(user);

                                    // todo: delete the item that the user pays...
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
        });

    }

    @Override
    public int getItemCount() {
        return lst.size();
    }


    public class ViewHolderShop extends RecyclerView.ViewHolder{

        private TextView tvName, tvPrice;
        private ImageView ivColor;

        public ViewHolderShop(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvNameItem);
            tvPrice = itemView.findViewById(R.id.tvPriceItem);
            ivColor = itemView.findViewById(R.id.ivColorItem);

        }

        void SetDetails (Item item){

            tvName.setText(item.getName());
            tvPrice.setText(String.valueOf(item.getPrice()));
            ivColor.setImageResource(item.getImage());

        }

    }
}
