package com.jude.mazeyo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class OwnedMapAdapter extends RecyclerView.Adapter<OwnedMapAdapter.ViewHolderOwned> {

    private Context context;
    private ArrayList<ItemOwned> lst;
    private FireBaseServices fbs;

    public OwnedMapAdapter(Context context, ArrayList<ItemOwned> lst) {
        fbs = FireBaseServices.getInstance();
        this.context = context;
        this.lst = lst;
    }

    @NonNull
    @Override
    public OwnedMapAdapter.ViewHolderOwned onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new ViewHolderOwned(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnedMapAdapter.ViewHolderOwned holder, int position) {

        ItemOwned itemOwned = lst.get(position);
        holder.SetDetails(itemOwned);

        holder.llBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = fbs.getUser();
                String PreOwned = user.getInMap();
                user.setInMap(holder.tvName.getText().toString());
                fbs.getFirestore().collection("Users").document(fbs.getAuth().getCurrentUser().getEmail()).update("inMap",holder.tvName.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "You chang your Use Color", Toast.LENGTH_SHORT).show();
                        holder.tvOwned.setText("Use Now");
                        holder.llBtn.setBackgroundResource(R.drawable.card_view_out_lines);
                        for(int i = 0 ; i < lst.size(); i++){
                            if (lst.get(i).getName().equals(PreOwned)) {
                                ItemOwned itemOwned = lst.get(i);
                                holder.ChangeDetails(itemOwned);
                            }
                        }
                        fbs.setUser(user);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Try to chang your Use Color agan!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolderOwned extends RecyclerView.ViewHolder {

        private TextView tvName, tvOwned;
        private ImageView ivColor;
        private LinearLayout llBtn;

        public ViewHolderOwned(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvNameItem);
            tvOwned = itemView.findViewById(R.id.tvPriceItem);
            ivColor = itemView.findViewById(R.id.ivColorItem);
            llBtn = itemView.findViewById(R.id.llBtnItem);

        }

        void SetDetails(ItemOwned itemOwned) {

            String ColorName = itemOwned.getName();
            tvName.setText(ColorName);
            ivColor.setImageResource(itemOwned.getImage());
            if (fbs.getUser().getInMap().equals(ColorName)) {
                tvOwned.setText("Use Now");
                llBtn.setBackgroundResource(R.drawable.card_view_out_lines);
            } else {
                tvOwned.setText("Owned");
                llBtn.setBackgroundResource(R.drawable.card_view_shadow);
            }

        }

        void ChangeDetails(ItemOwned itemOwned) {

            tvOwned.setText("Owned");
            llBtn.setBackgroundResource(R.drawable.card_view_shadow);

        }
    }
}
