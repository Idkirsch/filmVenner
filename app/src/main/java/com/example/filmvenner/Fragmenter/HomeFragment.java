package com.example.filmvenner.Fragmenter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.filmvenner.DAO.Movie;
import com.example.filmvenner.DAO.MovieItem;
import com.example.filmvenner.Adapter.MovieRecyclerAdapter;
import com.example.filmvenner.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //    APIqueue api = new APIqueue();
    private  RequestQueue mRequestQueue;
  //  RequestQueue mRequestQueue = APIqueue.getInstance(getContext()).getRequestQueue();

    // JSONObject movietest = new JSONObject();
    ArrayList<Movie> movies;
    ArrayList<MovieItem> exampleList = new ArrayList<>();


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        mRequestQueue = Volley.newRequestQueue(getContext());

        callAPI();


        mRecyclerView = v.findViewById(R.id.recyclerviewHome);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);

        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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

                            movies = Movie.fromJson(moviesJson);
                            System.out.println(movies.get(0).getTitle().toString());
                            // title0 = movies.get(0).getTitle().toString();

                            for (int i = 0; i < moviesJson.length(); i++) {
                                String title = movies.get(i).getTitle().toString();
                                MovieItem item = new MovieItem(R.drawable.film, "_", "release date", title);
                                exampleList.add(item);
                            }
                            mAdapter = new MovieRecyclerAdapter(exampleList);
                            mRecyclerView.setAdapter(mAdapter);
                            //addItems();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, error -> System.out.println("that didnt work"));
        // Add the request to the RequestQueue.
        mRequestQueue.add(jsonObjectRequest);
        //APIqueue.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);

    }

}

