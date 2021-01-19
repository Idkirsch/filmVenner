package com.example.filmvenner.Fragmenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.filmvenner.DAO.Movie;
import com.example.filmvenner.Adapter.MovieRecyclerAdapter;
import com.example.filmvenner.R;
import com.example.filmvenner.RecyclerItemClickListener;
import com.example.filmvenner.SearchResultViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// TODO: Tilpas denne klasse til Movie istedet for movieItem

public class SearchResult_Frag extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Movie> movies;

    public SearchResult_Frag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search_result_, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerviewSearch);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {
                        System.out.println("clicked on mReyclerview, position = "+position);
                        System.out.println("clicked on mReyclerview, title = "+ movies.get(position).getTitle());
                        System.out.println("clicked on mReyclerview, ID = "+ movies.get(position).getID());

                        String currentIdmRV = movies.get(position).getID();

                        SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("currentMovieID", currentIdmRV);
                        editor.commit();

                        System.out.println("Sideskift fra film");
                        AppCompatActivity activity = (AppCompatActivity)getContext();
                        FilmInfoFragment filmInfo = new FilmInfoFragment();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, filmInfo).addToBackStack(null).commit();


                    }
                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                })
        );

        return view;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SearchResultViewModel model = new ViewModelProvider(requireActivity()).get(SearchResultViewModel.class);

        model.getMovie().observe(getViewLifecycleOwner(), item -> {
            System.out.println("Item in resultfrag: "+item);
            JSONObject movieJson = item;
            JSONArray moviesJson = null;


            try {
                moviesJson = movieJson.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            /**
             * Json Array fra API'en konverteres til et array af objekterne Movie
             * */

            movies = Movie.fromJson(moviesJson);
            System.out.println(movies.get(0).getTitle().toString());
            for(int i=0;i<moviesJson.length();i++){
                String title = movies.get(i).getTitle().toString();
                Movie movie = new Movie(); // TODo
            }

            mAdapter = new MovieRecyclerAdapter(movies);
            mRecyclerView.setAdapter(mAdapter);

        });
    }




}