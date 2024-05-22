package com.jude.mazeyo.adapters;

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
import com.jude.mazeyo.objects.FireBaseServices;
import com.jude.mazeyo.objects.ItemOwned;
import com.jude.mazeyo.R;
import com.jude.mazeyo.objects.User;

import java.util.ArrayList;

public class OwnedAdapter extends RecyclerView.Adapter<OwnedAdapter.ViewHolderOwned> {

    private Context context;
    private ArrayList<ItemOwned> lst;
    private FireBaseServices fbs;

    public OwnedAdapter(Context context, ArrayList<ItemOwned> lst) {
        fbs = FireBaseServices.getInstance();
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

        holder.llBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = fbs.getUser();
                String PreOwned = user.getInUse();
                user.setInUse(holder.tvName.getText().toString());
                fbs.getFirestore().collection("Users").document(fbs.getAuth().getCurrentUser().getEmail()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "You change your Used Color", Toast.LENGTH_SHORT).show();
                        fbs.setUser(user);

                        notifyDataSetChanged();
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

    public class ViewHolderOwned extends RecyclerView.ViewHolder{

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

        void ChangeDetails(ItemOwned itemOwned) {

            tvOwned.setText("Owned");
            llBtn.setBackgroundResource(R.drawable.card_view_shadow);

        }
    }
}
