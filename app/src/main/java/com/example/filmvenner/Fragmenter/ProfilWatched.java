package com.example.filmvenner.Fragmenter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.filmvenner.Adapter.MovieRecyclerAdapter;
import com.example.filmvenner.DAO.Movie;
import com.example.filmvenner.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilWatched#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilWatched extends Fragment {

    private RecyclerView mRecyclerview;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RequestQueue mRequestqueue;
    ArrayList<Movie> listmovieItems = new ArrayList<>();
    ArrayList<Movie> viewList = new ArrayList<>();


    public static ProfilWatched newInstance(String param1, String param2) {
        ProfilWatched fragment = new ProfilWatched();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pga vi arbejder i fragments, skrives logik i onCreateView.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil_watched, container, false);

        mRequestqueue = Volley.newRequestQueue(getContext());

        callAPI();

//        ArrayList<ListmovieItem> listmovieItems = new ArrayList<>();

//        listmovieItems.add(new ListmovieItem(R.drawable.filmtestpic, "Linje 1watched", "Linje 2w"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));


        mRecyclerview = view.findViewById(R.id.recyclerviewWatched);
        //mRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setAdapter(mAdapter);
        return view;
    }

    public void callAPI() {
        String request = "https://api.themoviedb.org/3/search/movie?api_key=fa302bdb2e93149bd69faa350c178b38&language=en-US&query=avengers&page=1&include_adult=false";

        //StringRequest stringRequest = new StringRequest(Request.Method.GET, request,new Response.Listener<String>()
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, request, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject movieJson = response;
                            JSONArray moviesJson = movieJson.getJSONArray("results");

                            listmovieItems = Movie.fromJson(moviesJson);
                            System.out.println(listmovieItems.get(0).getTitle().toString());
                            // title0 = movies.get(0).getTitle().toString();
                            String prefixImage = "https://image.tmdb.org/t/p/w500";


                            for (int i = 0; i < moviesJson.length(); i++) {
                                String title = listmovieItems.get(i).getTitle().toString();
                                String imagePath = listmovieItems.get(i).getmImageResource().toString();
                                String fullImagePath  = prefixImage + imagePath;
                                String release = listmovieItems.get(i).getRelease();
                                String language = listmovieItems.get(i).getLanguage();
//                                System.out.println(fullImagePath);
                                Movie item = new Movie(release, language, title, fullImagePath, null, "");
                                viewList.add(item);
                            }

                            mAdapter = new MovieRecyclerAdapter(viewList);


//                            System.out.println("context fra Home "+ getContext());
//                            System.out.println("parentcontext fra Home "+ getParentFragment().getContext());


                            mRecyclerview.setAdapter(mAdapter);

                            //addItems();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, error -> System.out.println("couldn't get answer from API in Home Fragment or couldnt populate recyclerview in home"));
        mRequestqueue.add(jsonObjectRequest);
    }
}