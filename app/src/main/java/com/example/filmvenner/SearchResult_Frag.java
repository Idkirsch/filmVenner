package com.example.filmvenner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SearchResult_Frag extends Fragment {

    TextView result;
   // ImageView poster;

    public SearchResult_Frag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search_result_, container, false);
        result = view.findViewById(R.id.TVsearchResult);


//        poster = view.findViewById(R.id.imageViewSearchResult);


        return view;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SearchResultViewModel model = new ViewModelProvider(requireActivity()).get(SearchResultViewModel.class);
        model.getMovie().observe(getViewLifecycleOwner(), item -> {
            System.out.println("Item in resultfrag: "+item);
            //result.setText(item);
            result.setText(item.getTitle()+" "+item.getYear());
            String url = "http://i.imgur.com/DvpvklR.png";
           /* Picasso.with(this).load(url).into(poster);*/

        });
    }



}