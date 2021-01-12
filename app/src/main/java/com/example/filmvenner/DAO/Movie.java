package com.example.filmvenner.DAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// model of movieclass
public class Movie {

    private String title;
    private String release;
    private String overview;



    // decodes movie json into movie model object
    public static Movie fromJson(JSONObject jsonObject){
        Movie m = new Movie();
        try{
            m.title = jsonObject.getString("title");
            m.release = jsonObject.getString("release_date");
            m.overview = jsonObject.getString("overview");
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
        return m;
    }




    //decodes array of Movie json into Movie model objects
    public static ArrayList<Movie> fromJson(JSONArray jsonArray){
        JSONObject movieJson;
        ArrayList<Movie> movies = new ArrayList<Movie>(jsonArray.length());
        // Process each result in json array, decode and convert to movie object
        for(int i = 0; i<jsonArray.length(); i++){
            try{
                movieJson = jsonArray.getJSONObject(i);
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }

            Movie movie = Movie.fromJson(movieJson);
            if(movie!=null){
                movies.add(movie);
            }
        }
        return movies;
    }







    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getOverview(){ return overview;}
}







// source: https://guides.codepath.com/android/converting-json-to-models#fetching-json-results