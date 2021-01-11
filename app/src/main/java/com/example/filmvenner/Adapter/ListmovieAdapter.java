package com.example.filmvenner.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmvenner.DAO.ListmovieItem;
import com.example.filmvenner.R;

import java.util.ArrayList;

public class ListmovieAdapter extends RecyclerView.Adapter<ListmovieAdapter.ListmovieViewHolder> {

    public ArrayList<ListmovieItem> mlistmovieList;


    public static class ListmovieViewHolder extends RecyclerView.ViewHolder{


        public ImageView mImageview;
        public TextView mtextView1;
        public TextView mtextView2;
        // todo tilføj de ekstra ting (Knap, titel)

        public ListmovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageview = itemView.findViewById(R.id.mImageview);
            mtextView1 = itemView.findViewById(R.id.listTV1);
            mtextView2 = itemView.findViewById(R.id.listTV2);
        }

    }

    public ListmovieAdapter(ArrayList<ListmovieItem> listmovieList){

        mlistmovieList = listmovieList;

    }

    @Override
    public void onBindViewHolder(@NonNull ListmovieViewHolder holder, int position) {
        ListmovieItem currentItem = mlistmovieList.get(position);
        holder.mImageview.setImageResource(currentItem.getmImageresource());
        holder.mtextView1.setText(currentItem.getmText1());
        holder.mtextView2.setText(currentItem.getmText2());
    }

    @NonNull
    @Override
    public ListmovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listmovie_item,parent,false);
        ListmovieViewHolder lvh = new ListmovieViewHolder(v);
        return lvh;
    }

    @Override
    public int getItemCount() {
        return mlistmovieList.size();
    }
}
