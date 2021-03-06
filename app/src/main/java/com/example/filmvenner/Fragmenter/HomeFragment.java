package com.example.filmvenner.Fragmenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.filmvenner.Aktiviteter.Settings;
import com.example.filmvenner.Aktiviteter.skalSlettes;
import com.example.filmvenner.DAO.DatabaseAccess;
import com.example.filmvenner.DAO.Movie;
import com.example.filmvenner.Adapter.MovieRecyclerAdapter;
import com.example.filmvenner.DAO.User;
import com.example.filmvenner.R;
import com.example.filmvenner.RecyclerItemClickListener;
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
//    ArrayList<Movie> movies;
    ArrayList<Movie> exampleList = new ArrayList<>();
    DatabaseAccess db = new DatabaseAccess();
    // User user = new User();

   SharedPreferences prefMan;

    private String prefixImage = "https://image.tmdb.org/t/p/w500";
    ArrayList<String> venneListe, WantToWatch, Watched;
//    ArrayList<String> filmID = new ArrayList<>();
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    Button addFriend, removeFriend, videre;

    String currentUserName = new String();
//        String currentUserName = "PippiLangstromp";


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
        Context hostAct = getActivity();


        Button videre = (Button) v.findViewById(R.id.videre);
        videre.setOnClickListener(this);

        prefMan= hostAct.getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        String usernameFromPrefMan = prefMan.getString("currentUserName", "default");
        currentUserName = usernameFromPrefMan;

        retrieveFriends();

//        backgroundThread.execute(() -> {
//            try {
//                uiThread.post(() -> {
//
//                });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });

        System.out.println("vennelisten:" + venneListe); // = null


        mRequestQueue = Volley.newRequestQueue(getContext());

        //  callAPI();

        mRecyclerView = v.findViewById(R.id.recyclerviewHome);
        mRecyclerView.setHasFixedSize(true);


        mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        System.out.println("clicked on reyclerview, position = "+position);
                        System.out.println("clicked on recyclerview, title = "+exampleList.get(position).getTitle());

                        // preferencemanager (eller send titel med over til nyt fragment på en anden måde)
                        // ovre i nyt fragment: kald API med titlen

                        String currentIdRVhome = exampleList.get(position).getID();

                        SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("currentMovieID", currentIdRVhome);
                        editor.commit();

                        System.out.println("Sideskift fra home til filmside");
                        AppCompatActivity activity = (AppCompatActivity)getContext();
                        FilmInfoFragment filmInfo = new FilmInfoFragment();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, filmInfo).addToBackStack(null).commit();

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );


        // set on click listener
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    public void callAPI(String ID, String friendsName, String action) {

        String req1 = "https://api.themoviedb.org/3/movie/";
        String req2 = "?api_key=fa302bdb2e93149bd69faa350c178b38&language=en-US";

       // String friendsName = "vens navn";

        String request = req1 + ID + req2;
//        System.out.println(request);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, request, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject movieJson = response;
//                            System.out.println(movieJson);
//                            JSONArray moviesJson = new JSONArray();

                            Movie movie = new Movie();
                            movie = Movie.fromJson(movieJson);

                            String title = movie.getTitle();
                            String imagePath = movie.getmImageResource();
                            String fullImagePath = prefixImage + imagePath;
                            String language = movie.getLanguage();
                            String releaseDate = movie.getRelease();
                            Movie item = new Movie(releaseDate, language, title, fullImagePath, friendsName+action, "summary",ID);

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
        DocumentReference docRef = database.collection("users").document(currentUserName);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                      //  System.out.println("DocumentSnapshot data: " + document.getData());

                        Map<String, Object> map = document.getData();

                        for (Map.Entry<String, Object> entry : map.entrySet()) {
//                            System.out.println("entry: " + entry.getValue().toString());
                            //System.out.println("type of entry: "+entry.getValue());
                            if (entry.getKey().toString().equals("Friends")) {
//                                System.out.println("Her er vennelisten");
                                venneListe = (ArrayList<String>) entry.getValue();
//                                System.out.println("vores egen venneliste: " + venneListe);

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
//        System.out.println("vores egen venneliste fra databasen2: " + venneListe);

        ArrayList<String> listForAPI = new ArrayList<>();

        if(venneListe != null) {
            for (String entry : venneListe) {

                DocumentReference docRefToUsersFriend = database.collection("MovieList").document(entry);
                docRefToUsersFriend.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String currentFriend = entry;
//                            System.out.println("entry fra vores egen venneliste: " + currentFriend);

//                            System.out.println("DocumentSnapshot data: " + document.getData());
                                Map<String, Object> map = document.getData();

                                //for hver ven i listen
                                for (Map.Entry<String, Object> entry : map.entrySet()) {
                                    if (entry.getKey().toString().equals("Watched")) {
                                        Watched = (ArrayList<String>) entry.getValue();
                                        for (String entry2 : Watched) {
                                            System.out.println(currentFriend + " har set filmen " + entry2);
                                            String action = " har set denne film";
//                                        listForAPI.add(entry2);
                                            callAPI(entry2, currentFriend, action);

                                        }
                                    }
                                    if (entry.getKey().toString().equals("WantToWatch")) {
                                        Watched = (ArrayList<String>) entry.getValue();
                                        for (String entry2 : Watched) {
//                                        System.out.println(currentFriend + " vil gerne se filmen " + entry2);
                                            String action = " vil gerne se denne film";
                                            // listForAPI.add(entry2);
                                            callAPI(entry2, currentFriend, action);

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
    }


    @Override
    public void onClick(View view) {

        if(view == videre){
            startActivity(new Intent(getActivity(), Settings.class));
            System.out.println("someone clicked");



        }

    }
}


