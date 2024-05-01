package com.jude.mazeyo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolderShop> {

    private Context context;
    private ArrayList<Item> lst;

    public ShopAdapter(Context context, ArrayList<Item> lst) {
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

    }

    @Override
    public int getItemCount() {
        return lst.size();
    }


    public static class ViewHolderShop extends RecyclerView.ViewHolder{

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
