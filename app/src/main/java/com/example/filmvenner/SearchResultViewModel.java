package com.example.filmvenner;

import android.content.ClipData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchResultViewModel extends ViewModel {

    //TODO: change charsequence to something else..... json object?

    private final MutableLiveData<CharSequence> text = new MutableLiveData<CharSequence>();

    public void setText(CharSequence input){
        text.setValue(input);
    }

    public LiveData<CharSequence> getText(){
        return text;
    }


}
