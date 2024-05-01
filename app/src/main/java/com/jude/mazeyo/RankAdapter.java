package com.jude.mazeyo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewRanking> {

   private Context context;
   private ArrayList<User> list;
   private FireBaseServices fbs;

    public RankAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    public RankAdapter() {
    }

    @NonNull
    @Override
    public RankAdapter.ViewRanking onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.user,parent,false);
        return new ViewRanking(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RankAdapter.ViewRanking holder, int position) {
        User user = list.get(position);
        holder.SetDetails(user);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewRanking extends RecyclerView.ViewHolder{

        private TextView tvRank, tvUsername, tvCount;
        private ImageView ivProfile;

        public ViewRanking(@NonNull View itemView) {
            super(itemView);

            fbs = FireBaseServices.getInstance();
            tvCount = itemView.findViewById(R.id.tvCountUser);
            tvRank = itemView.findViewById(R.id.tvRankUser);
            tvUsername = itemView.findViewById(R.id.tvUserNameUser);
            ivProfile = itemView.findViewById(R.id.ivRankUser);

        }

        void SetDetails (User user){

            tvUsername.setText(user.getUsername());
            tvCount.setText("???");
            tvRank.setText("#???");

            if (fbs.getUser().getPhoto() != null && !fbs.getUser().getPhoto().isEmpty())
            {

                Picasso.get().load(fbs.getUser().getPhoto()).into(ivProfile);

            }

        }

    }
}
