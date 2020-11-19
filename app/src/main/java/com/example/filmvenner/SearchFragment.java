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
        //"http://www.omdbapi.com/?s=inception&apikey=[yourkey]
        RequestQueue queue = Volley.newRequestQueue(getContext());
        //String url ="https://www.google.com";
       // String url ="https://www.omdbapi.com/?s=inception&apikey=[3e1a983d]";
        String url ="https://www.omdbapi.com/?t=avengers&apikey=3e1a983d";


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //searchField.setText("Response is: "+ response.substring(0,500));
                        System.out.println("Response is: "+ response.substring(0,500));
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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 ////
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.

    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
}
 */