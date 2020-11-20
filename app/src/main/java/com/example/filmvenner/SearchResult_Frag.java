package com.example.filmvenner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SearchResult_Frag extends Fragment {


    public SearchResult_Frag() {
        // Required empty public constructor
    }

/*
    public static SearchResult_Frag newInstance(String param1, String param2) {
        SearchResult_Frag fragment = new SearchResult_Frag();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_result_, container, false);
    }

/*
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("Item1: ");
        SearchResultViewModel model = new ViewModelProvider(requireActivity()).get(SearchResultViewModel.class);
        System.out.println("Item1: ");
        model.getText().observe(getViewLifecycleOwner(), item -> {
            System.out.println("Item: "+item);
        });
    }

 */

}