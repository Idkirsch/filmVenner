package com.example.filmvenner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.jar.JarOutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    JSONObject movietest = new JSONObject();
    ArrayList<Movie> movies;
    ArrayList<MovieItem> exampleList = new ArrayList<>();

    String titleFromJson = "pis";
    String releaseFromJson = "lort";
    String title0;

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

    public JSONObject instantiateJson() throws JSONException {
        movietest.put("Title", "Shrek 2");
        movietest.put("release", "22-22-22");

        return movietest;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        instantiateTestJson();
        callAPI();
        addItems(); //adds cards to recyclerview

        mRecyclerView = v.findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new MovieRecyclerAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }


    public void callAPI() {
        String request = "https://api.themoviedb.org/3/search/movie?api_key=fa302bdb2e93149bd69faa350c178b38&language=en-US&query=avengers&page=1&include_adult=false";
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, request, response -> {


            try {
                JSONObject movieJson = new JSONObject(response);
                JSONArray moviesJson = movieJson.getJSONArray("results");
                movies = Movie.fromJson(moviesJson);
                System.out.println(movies.get(0).getTitle().toString());
                title0 = movies.get(0).getTitle().toString();
                // return response;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> System.out.println("that didnt work"));
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }




    public void addItems() {
        System.out.println("Hallo man");
        MovieItem testItem = new MovieItem(R.drawable.film, titleFromJson, releaseFromJson, "Title");
        MovieItem testItem2 = new MovieItem(R.drawable.film, title0, releaseFromJson, "Title");
        exampleList.add(testItem);
        exampleList.add(testItem2);

    }


    public void instantiateTestJson() {
        try {
            movietest = instantiateJson();
            releaseFromJson = movietest.getString("release").toString();
            titleFromJson = movietest.getString("Title").toString();
            System.out.println("Are you here???");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

