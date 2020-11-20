package com.example.filmvenner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.filmvenner.data.model.FilmList;
import com.example.filmvenner.ui.login.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements View.OnClickListener {

    private SearchResultViewModel viewModel;

    String url ="https://www.omdbapi.com/?t=";
    String apikey ="&apikey=3e1a983d";
    private Button searchButton;
    private EditText searchField;
    SearchResult_Frag fragmentResult = new SearchResult_Frag();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);


        searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);

        searchField = view.findViewById(R.id.search);

        addRecyclerFragment(); // This method does the getChildFragmentManager() stuff.



        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);





    }

    public void addRecyclerFragment(){
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction childFragManager = fragmentManager.beginTransaction();
        SearchRecycler_frag recycler_frag = new SearchRecycler_frag();
        childFragManager.add(R.id.nestedFragment_Search, recycler_frag);
        childFragManager.addToBackStack("recyclerfrag");
        childFragManager.commit();
    }

    public void addSearchResultFrag(){
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction childFragManager = fragmentManager.beginTransaction();
        childFragManager.replace(R.id.nestedFragment_Search, fragmentResult);
        childFragManager.addToBackStack("result_frag");
        childFragManager.commit();
    }


    @Override
    public void onClick(View view) {
        System.out.println("clicked search");
        System.out.println(searchField.getText().toString());
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String title = searchField.getText().toString();


        viewModel = new ViewModelProvider(requireActivity()).get(SearchResultViewModel.class);

        viewModel.setText("Hejsa");


        if(!title.isEmpty()) {
            String request = url+title+apikey;
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, request,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            //searchField.setText("Response is: "+ response.substring(0,500));

                            System.out.println("Response is: " + response.substring(0, 500));
                            addSearchResultFrag();
                            System.out.println("should add result frag");
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //textView.setText("That didn't work!");
                    System.out.println("that didnt work");
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);

        }else{
            System.out.println("search field is empty ");
        }
        //https://developer.android.com/training/volley/simple#java

    }



}

