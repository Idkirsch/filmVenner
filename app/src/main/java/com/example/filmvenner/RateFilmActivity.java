package com.example.filmvenner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RateFilmActivity extends AppCompatActivity {
    private String user, movie_title;
    private int rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_film);
    }

    public RateFilmActivity(String user, int rate, String movie_title) {
        this.user = user;
        this.rate = rate;
        this.movie_title = movie_title;
    }

    public String getUser(){ return user;}
    public String getMovie_title(){ return movie_title;}
    public int getRate(){ return rate;}
}