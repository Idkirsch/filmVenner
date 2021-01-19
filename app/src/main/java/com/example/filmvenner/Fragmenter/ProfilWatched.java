package com.example.filmvenner.Fragmenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.filmvenner.Adapter.MovieRecyclerAdapter;
import com.example.filmvenner.DAO.DatabaseAccess;
import com.example.filmvenner.DAO.Movie;
import com.example.filmvenner.R;
import com.example.filmvenner.RecyclerItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.CollectionReference;
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
 * Use the {@link ProfilWatched#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilWatched extends Fragment {

    private RecyclerView mRecyclerview;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RequestQueue mRequestqueue;
    Movie listmovieItems = new Movie();
    ArrayList<Movie> viewList = new ArrayList<>();
    ArrayList<String> idList = new ArrayList<>();
    ArrayList<String> watchedList = new ArrayList<>();


    DatabaseAccess db = new DatabaseAccess();
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    DocumentReference docRef = database.collection("MovieList").document("PippiLangstromp"); // database adgang til den liste vi skal bruge




    public static ProfilWatched newInstance(String param1, String param2) {
        ProfilWatched fragment = new ProfilWatched();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pga vi arbejder i fragments, skrives logik i onCreateView.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil_watched, container, false);

        retrieveData();

        mRequestqueue = Volley.newRequestQueue(getContext());




//        System.out.println("watchedlist før for loop" + watchedList);
//        int i=0;
//        for (String id : watchedList){
//            String currentMovieID = watchedList.get(i);
//            callAPI(currentMovieID);
//            System.out.println("Current moview id from for loop: " +currentMovieID);
//            i++;
//
//        }


//        ArrayList<ListmovieItem> listmovieItems = new ArrayList<>();

//        listmovieItems.add(new ListmovieItem(R.drawable.filmtestpic, "Linje 1watched", "Linje 2w"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));
//        listmovieItems.add(new Movie("noget","noget","noget","https://image.tmdb.org/t/p/w500/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"));


        mRecyclerview = view.findViewById(R.id.recyclerviewWatched);
        //mRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setAdapter(mAdapter);

        mRecyclerview.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), mRecyclerview, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        System.out.println("clicked on reyclerview, position = "+position);
                        System.out.println("clicked on recyclerview, title = "+viewList.get(position).getTitle());

                        // preferencemanager (eller send titel med over til nyt fragment på en anden måde)
                        // ovre i nyt fragment: kald API med titlen

                        String currentIdRVwatched = viewList.get(position).getID();

                        SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("currentMovieID", currentIdRVwatched);
                        editor.commit();

                        System.out.println("Sideskift fra watched til filmside");
                        AppCompatActivity activity = (AppCompatActivity)getContext();
                        FilmInfoFragment filmInfo = new FilmInfoFragment();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, filmInfo).addToBackStack(null).commit();

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
        return view;
    }

    public void callAPI(String movieId) {
        String key = "fa302bdb2e93149bd69faa350c178b38";
        String request = "https://api.themoviedb.org/3/movie/"+movieId+"?api_key=fa302bdb2e93149bd69faa350c178b38"; // get movie by id

        //StringRequest stringRequest = new StringRequest(Request.Method.GET, request,new Response.Listener<String>()
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, request, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject movieJson = response;
//                            JSONArray moviesJson = movieJson.getJSONArray("results");
                            System.out.println("Json from response, movieJson"+movieJson);

                            listmovieItems = Movie.fromJson(movieJson);
                            System.out.println(listmovieItems.getTitle().toString());
                            // title0 = movies.get(0).getTitle().toString();
                            String prefixImage = "https://image.tmdb.org/t/p/w500";

                                String title = listmovieItems.getTitle().toString();
                                String ID = listmovieItems.getID().toString();
                                String imagePath = listmovieItems.getmImageResource().toString();
                                String fullImagePath  = prefixImage + imagePath;
                                String release = listmovieItems.getRelease();
                                String language = listmovieItems.getLanguage();
//                                System.out.println(fullImagePath);
                                Movie item = new Movie(release, language, title, fullImagePath, null, "",ID);
                                viewList.add(item);


                            mAdapter = new MovieRecyclerAdapter(viewList);


//                            System.out.println("context fra Home "+ getContext());
//                            System.out.println("parentcontext fra Home "+ getParentFragment().getContext());


                            mRecyclerview.setAdapter(mAdapter);

                            //addItems();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }, error -> System.out.println("no answer from API in watched Fragment or couldnt populate recyclerview in watched"));
        mRequestqueue.add(jsonObjectRequest);
        System.out.println("JASON 1 moview" + jsonObjectRequest);
    }

    public void retrieveData() {
        System.out.println("retrieve called");
        //init docref
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                System.out.println("Oncomplete called");
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("Doc exists :) DocumentSnapshot data: " + document.getData());

                        Map<String, Object> map = document.getData();

                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            System.out.println("entry: " + entry.getValue().toString());
                            //System.out.println("type of entry: "+entry.getValue());
                            if (entry.getKey().toString().equals("Watched")) {
                                System.out.println("Her er Watched");
                                watchedList = (ArrayList<String>) entry.getValue(); // array over strings med film id'er
                                System.out.println("vores egen watchedliste: " + watchedList);
                                loopGennemWatchedliste();
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

    public void loopGennemWatchedliste() {
        System.out.println("loop called");

        for (String entry : watchedList) {
            System.out.println("entry fra watchedliste: " + entry);
            callAPI(entry);

            ;

            /**
             * Her kan man gøre noget for hver enkelt film (String) der er tilføjet til watchedListen.
             * */
        }
        System.out.println("Fuld liste af id'er"+idList);

    }
}