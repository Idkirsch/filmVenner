package com.example.filmvenner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<FilmList> filmLists;
    private Context context;

    public RecyclerViewAdapter(List<FilmList> filmLists, Context context) {
        this.filmLists = filmLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FilmList filmList=filmLists.get(position);
        holder.image.setImageResource(filmList.getImage());

    }

    @Override
    public int getItemCount() {
        return filmLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
        }
    }
}
