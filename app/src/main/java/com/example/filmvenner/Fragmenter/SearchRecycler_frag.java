package com.example.filmvenner.Fragmenter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.filmvenner.Adapter.MovieRecyclerAdapter;
import com.example.filmvenner.DAO.FilmList;
import com.example.filmvenner.DAO.Movie;
import com.example.filmvenner.R;
import com.example.filmvenner.Adapter.RecyclerViewAdapter;
import com.example.filmvenner.RecyclerItemClickListener;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;

// implements View.OnClickListener
public class SearchRecycler_frag extends Fragment{

    private ArrayList<FilmList> genre = new ArrayList<>();
    private ArrayList<FilmList> popular = new ArrayList<>();
    private ArrayList<FilmList> recent = new ArrayList<>();
    private RequestQueue mRequestQueue;
    String key = "fa302bdb2e93149bd69faa350c178b38";
    ArrayList<Movie> movies;
    ArrayList<FilmList> exampleList = new ArrayList<>();
    private String prefixImage = "https://image.tmdb.org/t/p/w500";

    LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
    LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
    //    MovieRecyclerAdapter adapter1 = new MovieRecyclerAdapter(genre);
//    MovieRecyclerAdapter adapter2 = new MovieRecyclerAdapter(popular);
//    MovieRecyclerAdapter adapter3 = new MovieRecyclerAdapter(recent);

//public class SearchRecycler_frag extends Fragment {


    private RecyclerView recyclerView1, recyclerView2, recyclerView3;
    private TextView textview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_recycler_frag, container, false);


        recyclerView1 = view.findViewById(R.id.recyclerView1);
        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView3 = view.findViewById(R.id.recyclerView3);



        recyclerView1.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView1, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        System.out.println("clicked on reyclerview, position = "+position);
                        System.out.println("clicked on reyclerview, title = "+ exampleList.get(position).getTitle());
                        System.out.println("clicked on reyclerview, ID = "+ exampleList.get(position).getID());

                        System.out.println("Sideskift fra film");
                        AppCompatActivity activity = (AppCompatActivity)getContext();
                        FilmInfoFragment filmInfo = new FilmInfoFragment();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, filmInfo).addToBackStack(null).commit();

                        //  System.out.println("clicked on recyclerview, title = "+exampleList.get(position).getID());

                        // preferencemanager (eller send titel med over til nyt fragment på en anden måde)
                        // ovre i nyt fragment: kald API med titlen

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );


        mRequestQueue = Volley.newRequestQueue(getContext());
        System.out.println("calling API");
        callAPI();


//        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(layoutManager1);

//        recyclerView1.setLayoutManager(layoutManager1);
//      MovieRecyclerAdapter adapter1 = new MovieRecyclerAdapter(genre);
//        recyclerView1.setAdapter(adapter1);

//        recyclerView2.setLayoutManager(layoutManager2);
//        MovieRecyclerAdapter adapter2 = new MovieRecyclerAdapter(popular);
//        recyclerView2.setAdapter(adapter2);

//        recyclerView3.setLayoutManager(layoutManager3);
//        MovieRecyclerAdapter adapter3 = new MovieRecyclerAdapter(recent);
//        recyclerView3.setAdapter(adapter3);





//        genre.add(new FilmList(R.drawable.film));


        return view;
    }

    // ? vil skift side til film_info leyout ved at trykke på recycleViews
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.recyclerView1:
//
//                break;
//
//            case R.id.recyclerView2:
//
//
//            case R.id.recyclerView3:
//
//                break;
//        }
//    }

    public void callAPI() {
        String requestPopular = "https://api.themoviedb.org/3/movie/popular?api_key=fa302bdb2e93149bd69faa350c178b38&language=en-US&page=1";
        String requestTopRated = "https://api.themoviedb.org/3/movie/top_rated?api_key=fa302bdb2e93149bd69faa350c178b38&language=en-US&page=1";


        System.out.println("in Call API method");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, requestPopular, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                System.out.println("in onResponse");
                try {
                    JSONObject movieJson = response;
                    JSONArray moviesJson = movieJson.getJSONArray("results");



//                    System.out.println(" convertin from json to movies");
                    movies = Movie.fromJson(moviesJson);

                    for (int i = 0; i < moviesJson.length(); i++) {
                        String imagePath = movies.get(i).getmImageResource().toString();
                        String fullImagePath = prefixImage + imagePath;
                        String title = movies.get(i).getTitle();
                        String ID = movies.get(i).getID();
//                        System.out.println("full image path: " + fullImagePath);
                        //FilmList item = new FilmList(imagePath);
                        FilmList item = new FilmList(fullImagePath,title,ID);

                        exampleList.add(item);
                    }

                    recyclerView1.setLayoutManager(layoutManager1);
                    RecyclerViewAdapter adapter1 = new RecyclerViewAdapter(exampleList, getActivity());
                    recyclerView1.setAdapter(adapter1);

//                    System.out.println("exampleList: "+exampleList);
//                    System.out.println("attaching adapters");



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> System.out.println(" couldnt't get answer from API in search recycler frag or couldnt populate views"));
        mRequestQueue.add(jsonObjectRequest);
    }





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


