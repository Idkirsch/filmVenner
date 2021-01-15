package com.example.filmvenner.DAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// model of movieclass
public class Movie {

    private String release, language,title,mImageResource, friend, summary;

    public Movie (String release, String language, String title, String mImageResource, String friend, String summary){
        this.language = language;
        this.mImageResource = mImageResource;
        this.release = release;
        this.title = title;
        this.summary = summary;
        this.friend = friend;
    }

    public Movie (){ }

    // decodes movie json into movie model object
    public static Movie fromJson(JSONObject jsonObject){
        Movie m = new Movie();
        try{
            m.title = jsonObject.getString("title");
            m.release = jsonObject.getString("release_date");
            m.language = jsonObject.getString("original_language");
            m.mImageResource = jsonObject.getString("poster_path");
            m.summary = jsonObject.getString ("overview");
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

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setRelease(String release) {
        this.release = release;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public void setmImageResource(String mImageResource) {
        this.mImageResource = mImageResource;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLanguage() {
        return language;
    }
    public String getmImageResource() {
        return mImageResource;
    }
    public String getRelease() {
        return release;
    }
    public String getTitle() {
        return title;
    }
    public String getSummary() {
        return summary;
    }


}







// source: https://guides.codepath.com/android/converting-json-to-models#fetching-json-results