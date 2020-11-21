package com.example.filmvenner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// model of movieclass
public class Movie {

    private String title;
    private int year;
    private String rated;
    private String released;
    private String runtime;
    private String genre;
    private String director;
    private String writer;
    private String actors;
    private String plot;
    private String language;
    private String country;
    private String awards;
    private String poster;
  //  private ArrayList<Rating> ratings;
    private int metascore;
    private double imdbRating;
    private double imdbVotes;
    private String imdbID;
    private String type;
    private String DVD;
    private String boxOffice;
    private String production;
    private String website;
    private boolean response;

    // TODO: add the rest of the values from json object




    // decodes movie json into movie model object
    public static Movie fromJson(JSONObject jsonObject){
        Movie m = new Movie();
        try{
            m.title = jsonObject.getString("Title");
            m.year = jsonObject.getInt("Year");
            m.rated = jsonObject.getString("Rated");
            m.released = jsonObject.getString("Released");
            m.runtime = jsonObject.getString("Runtime");
            m.genre = jsonObject.getString("Genre");
            m.director = jsonObject.getString("Director");
            m.writer = jsonObject.getString("Write");
            m.actors = jsonObject.getString("Actors");
            m.plot = jsonObject.getString("Plot");
            m.language = jsonObject.getString("Language");
            m.country = jsonObject.getString("Country");
            m.awards = jsonObject.getString("Awards");
            m.poster = jsonObject.getString("Poster");
            m.metascore = jsonObject.getInt("Metascore");
            m.imdbRating = jsonObject.getDouble("imdbRating");
            m.imdbVotes = jsonObject.getDouble("imdbVotes");
            m.imdbID = jsonObject.getString("imdbID");
            m.type = jsonObject.getString("Type");
            m.DVD = jsonObject.getString("DVD");
            m.boxOffice = jsonObject.getString("BoxOffice");
            m.production = jsonObject.getString("Production");
            m.website = jsonObject.getString("Website");
            m.response = jsonObject.getBoolean("Response");

            System.out.println("succeded in decoding json into model");
            // TODO : also add remaining values
        }catch (JSONException e){
            e.printStackTrace();
            System.out.println("a mistake happened in decoding json into model");
            return null;
        }
        return m;
    }

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

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getMetascore() {
        return metascore;
    }

    public void setMetascore(int metascore) {
        this.metascore = metascore;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public double getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(double imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDVD() {
        return DVD;
    }

    public void setDVD(String DVD) {
        this.DVD = DVD;
    }

    public String getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(String boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
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