package com.jude.mazeyo;

import android.annotation.SuppressLint;
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

public class OwnedAdapter extends RecyclerView.Adapter<OwnedAdapter.ViewHolderOwned> {

    private Context context;
    private ArrayList<ItemOwned> lst;

    public OwnedAdapter(Context context, ArrayList<ItemOwned> lst) {
        this.context = context;
        this.lst = lst;
    }

    @NonNull
    @Override
    public OwnedAdapter.ViewHolderOwned onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new OwnedAdapter.ViewHolderOwned(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnedAdapter.ViewHolderOwned holder, @SuppressLint("RecyclerView") int position) {

        ItemOwned itemOwned = lst.get(position);
        holder.SetDetails(itemOwned);

    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolderOwned extends RecyclerView.ViewHolder implements View.OnClickListener  {

        private TextView tvName, tvOwned;
        private ImageView ivColor;
        private LinearLayout llBtn;
        private FireBaseServices fbs;

        public ViewHolderOwned(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvNameItem);
            tvOwned = itemView.findViewById(R.id.tvPriceItem);
            ivColor = itemView.findViewById(R.id.ivColorItem);
            llBtn = itemView.findViewById(R.id.llBtnItem);

            fbs = FireBaseServices.getInstance();

            tvOwned.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            fbs.getFirestore().collection("Users").document(fbs.getAuth().getCurrentUser().getEmail()).update("inUse",tvName.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context, "You chang your Use Color", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Try to chang your Use Color agan!", Toast.LENGTH_SHORT).show();
                }
            });

        }


        void SetDetails (ItemOwned itemOwned){

            String ColorName = itemOwned.getName();
            tvName.setText(ColorName);
            ivColor.setImageResource(itemOwned.getImage());
            if(fbs.getUser().getInUse().equals(ColorName)){
                tvOwned.setText("Use Now");
                llBtn.setBackgroundResource(R.drawable.card_view_out_lines);
            }
            else {
                tvOwned.setText("Owned");
                llBtn.setBackgroundResource(R.drawable.card_view_shadow);
            }


        }
    }
}
