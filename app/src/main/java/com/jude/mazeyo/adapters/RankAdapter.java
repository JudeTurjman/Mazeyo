package com.jude.mazeyo.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jude.mazeyo.objects.FireBaseServices;
import com.jude.mazeyo.R;
import com.jude.mazeyo.objects.User;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewRanking> {

   private Context context;
   private ArrayList<User> list;
   private final FireBaseServices fbs = FireBaseServices.getInstance();
   private String factor;
    Dialog dialog;


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

        holder.cvSeeTheUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(context);
                dialog.setContentView(R.layout.user_popup);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
                dialog.show();


                ImageView ivSLNLogoPopUP = dialog.findViewById(R.id.ivCarSLNLogoUserPopUp);
                ImageView ivProfilePhoto = dialog.findViewById(R.id.ivImageUserPopUp);
                TextView tvUserName = dialog.findViewById(R.id.tvUsernameUserPopUp);
                TextView tvComment = dialog.findViewById(R.id.tvCommentUserPopUp);
                TextView tvEasy = dialog.findViewById(R.id.tvEasyUserPopUp);
                TextView tvMedium = dialog.findViewById(R.id.tvMediumUserPopUp);
                TextView tvHard = dialog.findViewById(R.id.tvHardUserPopUp);
                Button exit = dialog.findViewById(R.id.btnExitUserPopUp);

                if (user.getPhoto() == null || user.getPhoto().isEmpty()) {
                    Glide.with(context).load(R.mipmap.profile_launcher_foreground).into(ivProfilePhoto);
                }else{
                    Glide.with(context).load(user.getPhoto()).into(ivProfilePhoto);
                }

                tvUserName.setText(user.getUsername());
                tvComment.setText(user.getComment());
                tvEasy.setText(String.valueOf(user.getEasy()));
                tvMedium.setText(String.valueOf(user.getMedium()));
                tvHard.setText(String.valueOf(user.getHard()));

                // put the CarSLN logo in the profile photo "SLN"
                boolean haveSLN = false;
                for (int i = 0; i < user.getOwnedSkins().size(); i++){
                    if(user.getOwnedSkins().get(i).equals("CarSLn Blue")){
                        ivSLNLogoPopUP.setVisibility(View.VISIBLE);
                        haveSLN = true;
                    }
                }
                if (!haveSLN)ivSLNLogoPopUP.setVisibility(View.GONE);
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewRanking extends RecyclerView.ViewHolder{

        private TextView tvRank, tvUsername, tvCount;
        private ImageView ivProfile, ivSLNLogo;
        private CardView cvRank, cvSeeTheUser;

        public ViewRanking(@NonNull View itemView) {
            super(itemView);

            tvCount = itemView.findViewById(R.id.tvCountUser);
            tvRank = itemView.findViewById(R.id.tvRankUser);
            tvUsername = itemView.findViewById(R.id.tvUserNameUser);
            ivSLNLogo = itemView.findViewById(R.id.ivCarSLNLogoUser);
            ivProfile = itemView.findViewById(R.id.ivRankUser);
            cvRank = itemView.findViewById(R.id.cvRankUser);
            cvSeeTheUser = itemView.findViewById(R.id.cvSeeTheUser);

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

            if (user.getPhoto() == null || user.getPhoto().isEmpty()) {
                Glide.with(context).load(R.mipmap.profile_launcher_foreground).into(ivProfile);
            }else{
                Glide.with(context).load(user.getPhoto()).into(ivProfile);
            }

            // game is game
            if (factor.equals("Easy")) tvCount.setText(String.valueOf(user.getEasy()));
            else if (factor.equals("Medium")) tvCount.setText(String.valueOf(user.getMedium()));
            else if (factor.equals("Hard")) tvCount.setText(String.valueOf(user.getHard()));
            else if (factor.equals("Daily")) tvCount.setText(String.valueOf(user.getDailyCount()));

            // put the CarSLN logo in the profile photo "SLN"
            for (int i = 0; i < user.getOwnedSkins().size(); i++){
                if(user.getOwnedSkins().get(i).equals("CarSLn Blue")){
                    ivSLNLogo.setVisibility(View.VISIBLE);
                }
            }


        }
    }
}
