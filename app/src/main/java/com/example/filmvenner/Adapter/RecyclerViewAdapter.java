package com.example.filmvenner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmvenner.DAO.FilmList;
import com.example.filmvenner.Fragmenter.FilmInfoFragment;
import com.example.filmvenner.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<FilmList> filmLists;
    private Context context;

    public RecyclerViewAdapter(List<FilmList> filmLists, Context context) {
        this.filmLists = filmLists;
       // this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent,false);

        ViewHolder vHolder = new ViewHolder(view);

        vHolder.item_film.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Test click"+ String.valueOf(vHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
                System.out.println("Sideskift fra film");
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                FilmInfoFragment filmInfo = new FilmInfoFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, filmInfo).addToBackStack(null).commit();


            }
        });

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FilmList filmList=filmLists.get(position);
        Picasso.get().load(filmList.getImage()).into(holder.image);

        //holder.image.setImageResource(filmList.getImage());

    }

    @Override
    public int getItemCount() {
        return filmLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private RelativeLayout item_film;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            item_film = (RelativeLayout) itemView.findViewById(R.id.film_item_id);
        }
    }
}
