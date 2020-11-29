package com.example.filmvenner;

import android.content.ClipData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONObject;

public class SearchResultViewModel extends ViewModel {

    //TODO: change charsequence to something else..... json object?

    private final MutableLiveData<JSONObject> movie = new MutableLiveData<JSONObject>();

    public void setMovie(JSONObject input){
        movie.setValue(input);
    }

    public LiveData<JSONObject> getMovie(){

        return movie;
    }


}
