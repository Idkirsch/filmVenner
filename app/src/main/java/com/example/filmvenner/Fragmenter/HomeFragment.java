package com.example.filmvenner.Fragmenter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.filmvenner.DAO.DatabaseAccess;
import com.example.filmvenner.DAO.Movie;
import com.example.filmvenner.DAO.MovieItem;
import com.example.filmvenner.Adapter.MovieRecyclerAdapter;
import com.example.filmvenner.DAO.User;
import com.example.filmvenner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private  RequestQueue mRequestQueue;
    ArrayList<Movie> movies;
    ArrayList<Movie> exampleList = new ArrayList<>();
    DatabaseAccess db = new DatabaseAccess();
    User user = new User();

//
//    FirebaseFirestore database = FirebaseFirestore.getInstance();
//    CollectionReference usersDB = database.collection("users");


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        mRequestQueue = Volley.newRequestQueue(getContext());

        callAPI();

        mRecyclerView = v.findViewById(R.id.recyclerviewHome);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);

        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    public void callAPI() {
        String request = "https://api.themoviedb.org/3/search/movie?api_key=fa302bdb2e93149bd69faa350c178b38&language=en-US&query=avengers&page=1&include_adult=false";

        //StringRequest stringRequest = new StringRequest(Request.Method.GET, request,new Response.Listener<String>()
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, request, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject movieJson = response;
                            JSONArray moviesJson = movieJson.getJSONArray("results");

                            movies = Movie.fromJson(moviesJson);
                            System.out.println(movies.get(0).getTitle().toString());
                            // title0 = movies.get(0).getTitle().toString();

                            for (int i = 0; i < moviesJson.length(); i++) {
                                String title = movies.get(i).getTitle().toString();
                                Movie item = new Movie("releasedate", "_", title, "https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg");
                                exampleList.add(item);
                            }
                            mAdapter = new MovieRecyclerAdapter(getContext(),exampleList); // exampleList:     ArrayList<MovieItem>
                                                                                // her mangler vi context som argument
                            mRecyclerView.setAdapter(mAdapter);

                            //addItems();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, error -> System.out.println("couldn't get answer from API in Home Fragment or couldnt populate recyclerview in home"));
        mRequestQueue.add(jsonObjectRequest);
    }


//    public void retrieveData(){
//        DocumentReference docRef = database.collection("users").document("TEST");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//                    DocumentSnapshot document = task.getResult();
//                    if(document.exists()){
//                        System.out.println("DocumentSnapshot data: "+ document.getData());
//                    }else{
//                        System.out.println("no such document");
//                    }
//                }else{
//                    System.out.println("get failed with "+task.getException());
//                }
//            }
//        });
//
//    }



}

