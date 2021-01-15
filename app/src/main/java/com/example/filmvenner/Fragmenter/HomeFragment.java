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
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.filmvenner.DAO.DatabaseAccess;
import com.example.filmvenner.DAO.Movie;
import com.example.filmvenner.Adapter.MovieRecyclerAdapter;
import com.example.filmvenner.DAO.User;
import com.example.filmvenner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RequestQueue mRequestQueue;
    ArrayList<Movie> movies;
    ArrayList<Movie> exampleList = new ArrayList<>();
    DatabaseAccess db = new DatabaseAccess();
    // User user = new User();
    private String prefixImage = "https://image.tmdb.org/t/p/w500";
    ArrayList<String> venneListe;
    ArrayList<String> filmID = new ArrayList<>();
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    Button addFriend, removeFriend;
    String currentUserName = "PippiLangstromp";
    DocumentReference docRef = database.collection("users").document(currentUserName);


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

//
//        filmID.add("299534");
//        filmID.add("24428");
//        filmID.add("299536");


        addFriend = v.findViewById(R.id.addFriend);
        addFriend.setOnClickListener(this);

        removeFriend = v.findViewById(R.id.removeFriend);
        removeFriend.setOnClickListener(this);

        retrieveData();

        mRequestQueue = Volley.newRequestQueue(getContext());

        callAPI();

        mRecyclerView = v.findViewById(R.id.recyclerviewHome);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);

        //   System.out.println("vores egen venneliste fra databasen: "+venneListe);


        // set on click listener
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

                            for (int i = 0; i < moviesJson.length(); i++) {
                                String title = movies.get(i).getTitle().toString();
                                String imagePath = movies.get(i).getmImageResource().toString();
                                String releaseDate = movies.get(i).getRelease().toString();
                                String language = "language: " + movies.get(i).getLanguage().toString();
                                String fullImagePath = prefixImage + imagePath;
                                Movie item = new Movie(releaseDate, language, title, fullImagePath);
                                exampleList.add(item);
                            }
                            mAdapter = new MovieRecyclerAdapter(exampleList);
                            mRecyclerView.setAdapter(mAdapter);
                            //addItems();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> System.out.println("couldn't get answer from API in Home Fragment or couldnt populate recyclerview in home"));
        mRequestQueue.add(jsonObjectRequest);
    }


    public void retrieveData() {
        //init docref
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("DocumentSnapshot data: " + document.getData());

                        Map<String, Object> map = document.getData();

                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            System.out.println("entry: " + entry.getValue().toString());
                            //System.out.println("type of entry: "+entry.getValue());
                            if (entry.getKey().toString().equals("Friends")) {
                                System.out.println("Her er vennelisten");
                                venneListe = (ArrayList<String>) entry.getValue();
                                System.out.println("vores egen venneliste: " + venneListe);
                                loopGennemVenneliste();
                            }
                        }
                    } else {
                        System.out.println("no such document");
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            }
        });

    }

    public void loopGennemVenneliste() {
        System.out.println("vores egen venneliste fra databasen2: " + venneListe);

        for (String entry : venneListe) {
            System.out.println("entry fra vores egen venneliste: " + entry);

            /**
             * Her kan man gøre noget for hver enkelt bruger (String) der er tilføjet til vennelisten.
             * */
        }

    }


    @Override
    public void onClick(View view) {
        if (view == addFriend) {
            System.out.println("clicked on add friend button");
            docRef.update("Friends", FieldValue.arrayUnion("newFriend2"));
        }
        if (view == removeFriend) {
            System.out.println("clicked on add friend button");
            docRef.update("Friends", FieldValue.arrayRemove("Marie"));
        }
//https://firebase.google.com/docs/firestore/manage-data/add-data#update_elements_in_an_array

    }
}

