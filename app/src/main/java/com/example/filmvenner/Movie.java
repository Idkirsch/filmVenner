package com.example.filmvenner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// model of movieclass
public class Movie {

    private String title;
    private int year;
   /*
    private String rated;
    private String runtime;
    private ArrayList<String> genre;
    private String director;
    private String writer;
    */
    // TODO: add the rest of the values from json object


    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    // decodes movie json into movie model object
    public static Movie fromJson(JSONObject jsonObject){
        Movie m = new Movie();
        try{
            m.title = jsonObject.getString("Title");
            m.year = jsonObject.getInt("Year");
            System.out.println("succeded in decoding json into model");
            // TODO : also add remaining values
        }catch (JSONException e){
            e.printStackTrace();
            System.out.println("a mistake happened in decoding json into model");
            return null;
        }
        return m;
    }
    //TODO: later, alter method to be able to convert an array of movies into an array of movie objects
}



































/*
public class Business {
  // ...fields and getters
  // ...fromJson for an object

  // Decodes array of business json results into business model objects
  public static ArrayList<Business> fromJson(JSONArray jsonArray) {
      JSONObject businessJson;
      ArrayList<Business> businesses = new ArrayList<Business>(jsonArray.length());
      // Process each result in json array, decode and convert to business object
      for (int i=0; i < jsonArray.length(); i++) {
          try {
          	businessJson = jsonArray.getJSONObject(i);
          } catch (Exception e) {
              e.printStackTrace();
              continue;
          }

          Business business = Business.fromJson(businessJson);
          if (business != null) {
          	businesses.add(business);
          }
      }

      return businesses;
  }
}
 */