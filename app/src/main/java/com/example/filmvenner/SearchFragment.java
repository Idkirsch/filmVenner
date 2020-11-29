package com.example.filmvenner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class SearchFragment extends Fragment implements View.OnClickListener {

    private SearchResultViewModel viewModel;

  //  String url = "https://www.omdbapi.com/?t=";
  //  String apikey = "&apikey=3e1a983d";
    String apiKeyFromTMDB = "fa302bdb2e93149bd69faa350c178b38";
    String query1 = "https://api.themoviedb.org/3/search/movie?api_key=fa302bdb2e93149bd69faa350c178b38&language=en-US&query=";
    String query2;
    String query3 = "&page=1&include_adult=false";
    private Button searchButton;
    private EditText searchField;
    SearchResult_Frag fragmentResult = new SearchResult_Frag();
    Movie movie = new Movie();
    ArrayList<Movie> movies;
    JSONObject movietest = new JSONObject();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);
        searchField = view.findViewById(R.id.search);
        addRecyclerFragment(); // This method does the getChildFragmentManager() stuff.
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void addRecyclerFragment() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction childFragManager = fragmentManager.beginTransaction();
        SearchRecycler_frag recycler_frag = new SearchRecycler_frag();
        childFragManager.add(R.id.nestedFragment_Search, recycler_frag);
        childFragManager.addToBackStack("recyclerfrag");
        childFragManager.commit();
    }

    public void addSearchResultFrag() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction childFragManager = fragmentManager.beginTransaction();
        childFragManager.replace(R.id.nestedFragment_Search, fragmentResult);
        childFragManager.addToBackStack("result_frag");
        childFragManager.commit();
    }

    public void instantiateJson() throws JSONException {
        movietest.put("Title", "shrek");
        movietest.put("Year", "2000");
    }


    @Override
    public void onClick(View view) {
        System.out.println("clicked search");
        System.out.println(searchField.getText().toString());

        String title = searchField.getText().toString();


        if (!title.isEmpty()) {
            query2 = title;
            callAPI();
            viewModel = new ViewModelProvider(requireActivity()).get(SearchResultViewModel.class);
            addSearchResultFrag(); // instantiates new fragment where search result is displayed

        } else {
            System.out.println("search field is empty ");

        }
    }


    public void callAPI() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
       // String request = "https://api.themoviedb.org/3/search/movie?api_key=fa302bdb2e93149bd69faa350c178b38&language=en-US&query=avengers&page=1&include_adult=false";
        String request = query1+query2+query3;

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
                            viewModel.setMovie(response); //transfers some text into viewmodel to be used in child fragment

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, error -> System.out.println("that didnt work"));
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
}



