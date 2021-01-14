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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.filmvenner.Adapter.MovieRecyclerAdapter;
import com.example.filmvenner.Adapter.RecyclerViewAdapter;
import com.example.filmvenner.DAO.DatabaseAccess;
import com.example.filmvenner.DAO.Movie;
import com.example.filmvenner.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilmInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilmInfoFragment extends Fragment implements View.OnClickListener {

    ImageButton addToList;
    TextView title;
    String currentTitle = "Moana";
    String movieID = "277834";

    private RequestQueue requestQueue;
    ArrayList<Movie> movies;
    ArrayList<Movie> example = new ArrayList<> ();
    DatabaseAccess db = new DatabaseAccess ();
    ArrayList<String> filmSummary;
    TextView film_summary;
    private String prefixImage = "https://image.tmdb.org/t/p/w500";

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    DocumentReference docRef = database.collection("MovieList").document("PippiLangstromp");

    public FilmInfoFragment() {
        // Required empty public constructor
    }

    public static FilmInfoFragment newInstance(String param1, String param2) {
        FilmInfoFragment fragment = new FilmInfoFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_film_info, container, false);

        addToList = (ImageButton) view.findViewById(R.id.add_to_list_button);
        addToList.setOnClickListener(this);

        title = view.findViewById(R.id.film_nama);
        title.setText(currentTitle);
        requestQueue = Volley.newRequestQueue(getContext());

        callAPI();

        film_summary = view.findViewById(R.id.film_info);

        return view;
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
                                String summary = movies.get (i).getSummary ().toString ();
                                Movie item = new Movie(releaseDate, language, title, fullImagePath, summary);
                                example.add(item);
                            }
                            //addItems();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> System.out.println("couldn't get answer from API in Home Fragment or couldnt populate recyclerview in home"));
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {

        if(v == addToList){
            System.out.println("klikkede på add to list knap");
            docRef.update("WantToWatch", FieldValue.arrayUnion(movieID));
        }
    }
}