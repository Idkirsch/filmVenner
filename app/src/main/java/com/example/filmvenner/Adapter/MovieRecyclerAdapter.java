package com.example.filmvenner.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmvenner.DAO.MovieItem;
import com.example.filmvenner.R;

import java.util.ArrayList;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ExampleViewHolder> {
    private ArrayList<MovieItem> mExampleList;


    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageview;
        public TextView mName, mTV2, mTitle;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageview = itemView.findViewById(R.id.imageView1);
            mName = itemView.findViewById(R.id.Name);
            mTV2 = itemView.findViewById(R.id.textView2);
            mTitle = itemView.findViewById(R.id.title);
        }
    }

    public MovieRecyclerAdapter(ArrayList<MovieItem> exampleList){
        mExampleList = exampleList;
    }





    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        MovieItem currentItem = mExampleList.get(position);

        holder.mImageview.setImageResource(currentItem.getImageResource());
        holder.mName.setText(currentItem.getName());
        holder.mTV2.setText(currentItem.getText2());
        holder.mTitle.setText(currentItem.getTitle());

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

}