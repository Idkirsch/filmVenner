package com.example.filmvenner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

    private List<FilmList> filmLists = new ArrayList<>();
    private RecyclerView recyclerView1, recyclerView2, recyclerView3;
    private Button searchButton;
    private EditText searchField;
    String url ="https://www.omdbapi.com/?s=";
    String apikey ="&apikey=3e1a983d";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);


        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView1 = view.findViewById(R.id.recyclerView1);
        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView3 = view.findViewById(R.id.recyclerView3);


        recyclerView1.setLayoutManager(layoutManager1);
        RecyclerViewAdapter adapter1 = new RecyclerViewAdapter(filmLists, getActivity());
        recyclerView1.setAdapter(adapter1);

        recyclerView2.setLayoutManager(layoutManager2);
        RecyclerViewAdapter adapter2 = new RecyclerViewAdapter(filmLists, getActivity());
        recyclerView2.setAdapter(adapter2);

        recyclerView3.setLayoutManager(layoutManager3);
        RecyclerViewAdapter adapter3 = new RecyclerViewAdapter(filmLists, getActivity());
        recyclerView3.setAdapter(adapter3);

        searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);

        searchField = view.findViewById(R.id.search);


        filmLists.add(new FilmList(R.drawable.film2));
        filmLists.add(new FilmList(R.drawable.film2));
        filmLists.add(new FilmList(R.drawable.film2));
        filmLists.add(new FilmList(R.drawable.film2));
        filmLists.add(new FilmList(R.drawable.film2));
        filmLists.add(new FilmList(R.drawable.film2));
        filmLists.add(new FilmList(R.drawable.film2));


        return view;
    }

    @Override
    public void onClick(View view) {
        System.out.println("clicked search");
        System.out.println(searchField.getText().toString());
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String title = searchField.getText().toString();


        String request = url+title+apikey;
        System.out.println(request);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, request,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //searchField.setText("Response is: "+ response.substring(0,500));

                        //System.out.println("Response is: "+ response.substring(0,500);
                        System.out.println("Response is: "+ response);

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

        //https://developer.android.com/training/volley/simple#java

    }
}

