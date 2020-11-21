package com.example.filmvenner;

import android.content.ClipData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchResultViewModel extends ViewModel {

    //TODO: change charsequence to something else..... json object?

    private final MutableLiveData<Movie> movie = new MutableLiveData<Movie>();

    public void setMovie(Movie input){

        movie.setValue(input);
    }

    public LiveData<Movie> getMovie(){

        return movie;
    }


}
