package com.example.filmvenner.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmvenner.DAO.FriendReviewList;
import com.example.filmvenner.R;

import java.util.ArrayList;

public class FriendReviewAdapter extends RecyclerView.Adapter<FriendReviewAdapter.ReviewViewHolder> {
    private ArrayList<FriendReviewList> mFriendReviewList;

    public static  class ReviewViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView textviewFriendName;
        public TextView textviewFriendReview;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.friendProfilePic);
            textviewFriendName = itemView.findViewById(R.id.listTVfriendName);
            textviewFriendReview = itemView.findViewById(R.id.listTVreview);

        }
    }

    public FriendReviewAdapter(ArrayList<FriendReviewList> friendReviewlist) {
        mFriendReviewList = friendReviewlist;
    }


    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.friendreview_item, parent, false);
         ReviewViewHolder rvh = new ReviewViewHolder(v);
         return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        FriendReviewList currentItem = mFriendReviewList.get(position);

        holder.mImageView.setImageResource(currentItem.getimageresource());
        holder.textviewFriendName.setText(currentItem.getTextFriendName());
        holder.textviewFriendReview.setText(currentItem.getTextFriendReview());

    }

    @Override
    public int getItemCount() {
        return mFriendReviewList.size();
    }
}
