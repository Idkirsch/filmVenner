package com.example.filmvenner.Fragmenter;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.filmvenner.DAO.FilmList;
import com.example.filmvenner.DAO.Movie;
import com.example.filmvenner.R;
import com.example.filmvenner.Adapter.RecyclerViewAdapter;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;


public class SearchRecycler_frag extends Fragment implements View.OnClickListener {

    private List<FilmList> genre = new ArrayList<>();
    private List<FilmList> popular = new ArrayList<>();
    private List<FilmList> recent = new ArrayList<>();
    private RequestQueue mRequestQueue;
    String key = "fa302bdb2e93149bd69faa350c178b38";
    ArrayList<FilmList> movies;




    private RecyclerView recyclerView1, recyclerView2, recyclerView3;


    private TextView textview;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_recycler_frag, container, false);


        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView1 = view.findViewById(R.id.recyclerView1);
        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView3 = view.findViewById(R.id.recyclerView3);


        mRequestQueue = Volley.newRequestQueue(getContext());
        callAPI();


        recyclerView1.setLayoutManager(layoutManager1);
        RecyclerViewAdapter adapter1 = new RecyclerViewAdapter(genre, getActivity());
        recyclerView1.setAdapter(adapter1);

        recyclerView2.setLayoutManager(layoutManager2);
        RecyclerViewAdapter adapter2 = new RecyclerViewAdapter(popular, getActivity());
        recyclerView2.setAdapter(adapter2);

        recyclerView3.setLayoutManager(layoutManager3);
        RecyclerViewAdapter adapter3 = new RecyclerViewAdapter(recent, getActivity());
        recyclerView3.setAdapter(adapter3);





        genre.add(new FilmList(R.drawable.film));
        genre.add(new FilmList(R.drawable.film));
        genre.add(new FilmList(R.drawable.film));
        genre.add(new FilmList(R.drawable.film));
        genre.add(new FilmList(R.drawable.film));
        genre.add(new FilmList(R.drawable.film));
        genre.add(new FilmList(R.drawable.film));
        genre.add(new FilmList(R.drawable.film));
        genre.add(new FilmList(R.drawable.film));

        popular.add(new FilmList(R.drawable.checkmark));
        popular.add(new FilmList(R.drawable.checkmark));
        popular.add(new FilmList(R.drawable.checkmark));
        popular.add(new FilmList(R.drawable.checkmark));
        popular.add(new FilmList(R.drawable.checkmark));
        popular.add(new FilmList(R.drawable.checkmark));
        popular.add(new FilmList(R.drawable.checkmark));
        popular.add(new FilmList(R.drawable.checkmark));
        popular.add(new FilmList(R.drawable.checkmark));

        recent.add(new FilmList(R.drawable.filmrulle));
        recent.add(new FilmList(R.drawable.filmrulle));
        recent.add(new FilmList(R.drawable.filmrulle));
        recent.add(new FilmList(R.drawable.filmrulle));
        recent.add(new FilmList(R.drawable.filmrulle));
        recent.add(new FilmList(R.drawable.filmrulle));
        recent.add(new FilmList(R.drawable.filmrulle));
        recent.add(new FilmList(R.drawable.filmrulle));

        return view;
    }

    // ? vil skift side til film_info leyout ved at trykke på recycleViews
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recyclerView1:
                //startActivity(new Intent (getActivity(), FilmInfoFragment.class));
                /*System.out.println("klikkede på recykler 1");
                FragmentManager fragmentManager7 = getChildFragmentManager();
                FragmentTransaction fragmentTransaction7 = fragmentManager7.beginTransaction();

                FilmInfoFragment filmInfo = new FilmInfoFragment();
                fragmentTransaction7.add(R.id.nestedFragment_Search, filmInfo);


                fragmentTransaction7.commit();*/

                break;

            case R.id.recyclerView2:
                startActivity(new Intent (getActivity(), FilmInfoFragment.class));


            case R.id.recyclerView3:
                startActivity(new Intent (getActivity(), FilmInfoFragment.class));

                break;
        }
    }

    public void callAPI(){
        String requestPopular = "https://api.themoviedb.org/3/movie/popular?api_key=fa302bdb2e93149bd69faa350c178b38&language=en-US&page=1";
        String requestTopRated = "https://api.themoviedb.org/3/movie/top_rated?api_key=fa302bdb2e93149bd69faa350c178b38&language=en-US&page=1";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method GET, requestPopular, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject movieJson = response;
                    JSONArray moviesJson = movieJson.getJSONArray("results");

                    movies = Movie.fromJson(moviesJson);

                    for(int i = 0; i < moviesJson.length(); i++){
                        String imagePath = movies.get(i).getmImageResource().toString();
                    }


                    /**
                     * Jeg skal bruge en metode der er i klassen Movie,
                     * Men det her adapter tager ikke Movies, det tager FilmLists..
                     *
                     *
                     * */


                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
        )


















//        ArrayList<String> requests = new ArrayList<>();
//        requests.add(requestPopular);
//        requests.add(requestTopRated);
//
//        for (String request:requests
//             ) {
//            /**
//             * For each request in the arraylist of requests to the API, make a new call
//             * */
//
//
//        }
//


    }


}