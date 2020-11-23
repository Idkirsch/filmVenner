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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class SearchFragment extends Fragment implements View.OnClickListener {

    private SearchResultViewModel viewModel;

    String url ="https://www.omdbapi.com/?t=";
    String apikey ="&apikey=3e1a983d";
    private Button searchButton;
    private EditText searchField;
    SearchResult_Frag fragmentResult = new SearchResult_Frag();
    Movie movie = new Movie();
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

    public void addRecyclerFragment(){
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction childFragManager = fragmentManager.beginTransaction();
        SearchRecycler_frag recycler_frag = new SearchRecycler_frag();
        childFragManager.add(R.id.nestedFragment_Search, recycler_frag);
        childFragManager.addToBackStack("recyclerfrag");
        childFragManager.commit();
    }

    public void addSearchResultFrag(){
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
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String title = searchField.getText().toString();

        viewModel = new ViewModelProvider(requireActivity()).get(SearchResultViewModel.class);

        if(!title.isEmpty()) {
            String request = url+title+apikey;
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, request, response -> {


                //TODO: only add new frag if current frag isn't already the result frag
                addSearchResultFrag(); // instantiates new fragment where search result is displayed
              //  viewModel.setMovie(response); //transfers some text into viewmodel to be used in child fragment

                try {
                    System.out.println("request = " + request);
                    JSONObject movieJson = new JSONObject(response);
                    System.out.println("Response is: " + response);
                    Movie movie1 = movie.fromJson(movieJson);
                    System.out.println("Movie1: "+movie1.getTitle());
                    viewModel.setMovie(movie1);

                   // JSONArray array = movieJson.getJSONArray("movies");
/*
                    for(int i = 0; i<array.length();i++){
                        JSONObject object1 = array.getJSONObject(i);
                        System.out.println(object1);
                        // do something to each json here.
                    }

 */
                }catch(JSONException e){
                    e.printStackTrace();
                    System.out.println("something went wrong when trying to get the json object movie");
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("that didnt work");
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);

        }else{
            System.out.println("search field is empty ");
        }
        //https://developer.android.com/training/volley/simple#java
    }
}


/*
                try {
                    instantiateJson();
                    System.out.println("succeeded to instantiate json test object");
                    Movie shrek = movie.fromJson(movietest); //converting test object into
                    System.out.println("title: "+shrek.getTitle());
                    System.out.println("year: "+shrek.getYear());
                    viewModel.setMovie(shrek);
                    System.out.println("viewmodel now has shrek");
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("failed to instantiate json test object");
                }

 */
