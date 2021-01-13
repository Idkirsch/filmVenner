package com.example.filmvenner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmvenner.DAO.Movie;
import com.example.filmvenner.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ExampleViewHolder> {
    private ArrayList<Movie> mExampleList;
    private Context mContext;


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

//    public MovieRecyclerAdapter(Context context,ArrayList<Movie> exampleList){
//        mExampleList = exampleList;
//        mContext = context;
//    }

    public MovieRecyclerAdapter(ArrayList<Movie> exampleList){
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
        Movie currentItem = mExampleList.get(position);

        Picasso.get().load(currentItem.getmImageResource()).into(holder.mImageview);
        holder.mName.setText(currentItem.getLanguage());
        holder.mTV2.setText(currentItem.getRelease());
        holder.mTitle.setText(currentItem.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("someone clicked something in a recyclerview, they clicked on: "+ holder.mTitle.getText());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

}
