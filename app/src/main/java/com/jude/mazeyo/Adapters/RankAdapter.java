package com.jude.mazeyo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jude.mazeyo.FireBaseServices;
import com.jude.mazeyo.R;
import com.jude.mazeyo.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewRanking> {

   private Context context;
   private ArrayList<User> list;
   private final FireBaseServices fbs = FireBaseServices.getInstance();
   private String factor;

    public RankAdapter(Context context, ArrayList<User> list, String factor) {
        this.context = context;
        this.list = list;
        this.factor = factor;
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
        holder.SetDetails(user,position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewRanking extends RecyclerView.ViewHolder{

        private TextView tvRank, tvUsername, tvCount;
        private ImageView ivProfile;
        private CardView cvRank;

        public ViewRanking(@NonNull View itemView) {
            super(itemView);

            tvCount = itemView.findViewById(R.id.tvCountUser);
            tvRank = itemView.findViewById(R.id.tvRankUser);
            tvUsername = itemView.findViewById(R.id.tvUserNameUser);
            ivProfile = itemView.findViewById(R.id.ivRankUser);
            cvRank = itemView.findViewById(R.id.cvRankUser);

        }

        void SetDetails (User user, int position){

            tvUsername.setText(user.getUsername());
            tvRank.setText("#" + String.valueOf(position+1));
            if (position+1 == 1){
                cvRank.setBackgroundResource(R.drawable.rank_first);
            } else if (position+1 == 2) {
                cvRank.setBackgroundResource(R.drawable.rank_second);
            }else if (position+1 == 3) {
                cvRank.setBackgroundResource(R.drawable.rank_third);
            }

            if (fbs.getUser().getPhoto() != null && !fbs.getUser().getPhoto().isEmpty())
            {

                Picasso.get().load(fbs.getUser().getPhoto()).into(ivProfile);

            }

            // game is game
            if (factor.equals("Easy")) tvCount.setText(String.valueOf(user.getEasy()));
            else if (factor.equals("Medium")) tvCount.setText(String.valueOf(user.getMedium()));
            else if (factor.equals("Hard")) tvCount.setText(String.valueOf(user.getHard()));
            else if (factor.equals("Daily")) tvCount.setText(String.valueOf(user.getDailyCount()));



        }
    }
}
