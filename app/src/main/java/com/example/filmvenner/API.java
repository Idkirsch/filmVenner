package com.example.filmvenner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.filmvenner.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class API {
    /*
    private  RequestQueue mRequestQueue;

    public JSONArray callAPI(String request) {
        mRequestQueue = Volley.newRequestQueue();
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

    }


     */

}
