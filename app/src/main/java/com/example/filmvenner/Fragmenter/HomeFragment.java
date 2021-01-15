package com.example.filmvenner.Fragmenter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
    ArrayList<String> venneListe, WantToWatch, Watched;
    ArrayList<String> filmID = new ArrayList<>();
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    Button addFriend, removeFriend;
    String currentUserName = "PippiLangstromp";
    DocumentReference docRef = database.collection("users").document(currentUserName);


    Executor backgroundThread = Executors.newSingleThreadExecutor();
    Handler uiThread = new Handler(Looper.getMainLooper());


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
        addFriend = v.findViewById(R.id.addFriend);
        addFriend.setOnClickListener(this);

        removeFriend = v.findViewById(R.id.removeFriend);
        removeFriend.setOnClickListener(this);


        backgroundThread.execute(() -> {
            try {
                retrieveFriends();
                uiThread.post(() -> {

                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        System.out.println("vennelisten:" + venneListe);


        mRequestQueue = Volley.newRequestQueue(getContext());

        //  callAPI();

        mRecyclerView = v.findViewById(R.id.recyclerviewHome);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);


        // set on click listener
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    public void callAPI(String ID) {

        String req1 = "https://api.themoviedb.org/3/movie/";
        String req2 = "?api_key=fa302bdb2e93149bd69faa350c178b38&language=en-US";

        String request = req1 + ID + req2;
        System.out.println(request);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, request, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject movieJson = response;
                            System.out.println(movieJson);
//                            JSONArray moviesJson = new JSONArray();

                            Movie movie = new Movie();
                            movie = Movie.fromJson(movieJson);

                            String title = movie.getTitle();
                            String imagePath = movie.getmImageResource();
                            String fullImagePath = prefixImage + imagePath;
                            String language = movie.getLanguage();
                            String releaseDate = movie.getRelease();
                            Movie item = new Movie(releaseDate, language, title, fullImagePath);

                            exampleList.add(item);

                            mAdapter = new MovieRecyclerAdapter(exampleList);
                            mRecyclerView.setAdapter(mAdapter);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> System.out.println("couldn't get answer from API in Home Fragment or couldnt populate recyclerview in home"));
        mRequestQueue.add(jsonObjectRequest);
    }


    public void retrieveFriends() {
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

                            }
                        }

                    } else {
                        System.out.println("no such document");
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
                loopGennemVenneliste();
            }

        });

    }


    public void loopGennemVenneliste() {
        System.out.println("vores egen venneliste fra databasen2: " + venneListe);
        ArrayList<String> listForAPI = new ArrayList<>();


        for (String entry : venneListe) {

            DocumentReference docRefToUsersFriend = database.collection("MovieList").document(entry);
            docRefToUsersFriend.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String currentFriend = entry;
                            System.out.println("entry fra vores egen venneliste: " + currentFriend);

                            System.out.println("DocumentSnapshot data: " + document.getData());
                            Map<String, Object> map = document.getData();

                            //for hver ven i listen
                            for (Map.Entry<String, Object> entry : map.entrySet()) {
                                if (entry.getKey().toString().equals("Watched")) {
                                    Watched = (ArrayList<String>) entry.getValue();
                                    for (String entry2 : Watched) {
                                        System.out.println(currentFriend + " har set filmen " + entry2);
                                        //listForAPI.add(entry2);
                                        callAPI(entry2);

                                    }
                                }
                                if (entry.getKey().toString().equals("WantToWatch")) {
                                    Watched = (ArrayList<String>) entry.getValue();
                                    for (String entry2 : Watched) {
                                        System.out.println(currentFriend + " vil gerne se filmen " + entry2);
                                       // listForAPI.add(entry2);
                                        callAPI(entry2);

                                    }
                                }
                            }
                        }
                    }
                }
            });
        }
        System.out.println("listForAPI: " + listForAPI);
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


