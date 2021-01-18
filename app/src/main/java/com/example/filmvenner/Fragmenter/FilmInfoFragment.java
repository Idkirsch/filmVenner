package com.example.filmvenner.Fragmenter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;
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
import com.example.filmvenner.Adapter.FriendReviewAdapter;
import com.example.filmvenner.DAO.FriendReviewList;
import com.example.filmvenner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
 * Use the {@link FilmInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilmInfoFragment extends Fragment implements View.OnClickListener {
    private RecyclerView mRecyclerview;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ImageButton addToList, addReview;
    TextView title;
    String currentTitle = "Moana";
    String ID = "24428";
    TextView film_summary;
    private RequestQueue requestQueue;
    Movie movies = new Movie();
    ArrayList<Movie> example = new ArrayList<>();
    private String prefixImage = "https://image.tmdb.org/t/p/w500";
    String summary_1 = "1234";

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    DocumentReference docRef = database.collection("Film").document(ID);



//    ArrayList<String> filmSummary;
//    ArrayList<String> film_id = new ArrayList<> ();
//    DocumentReference docRef = database.collection("MovieList").document("PippiLangstromp");
//    DatabaseAccess databaseAccess = new DatabaseAccess ();
//    String movieID = "277834";
//    FirebaseFirestore database = FirebaseFirestore.getInstance();


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

        View view = inflater.inflate(R.layout.fragment_film_info2, container, false);

        addToList = view.findViewById(R.id.add_button);
        addToList.setOnClickListener(this);

        addReview = view.findViewById(R.id.add_review);
        addReview.setOnClickListener(this);

        ImageButton addmovie = view.findViewById(R.id.add_button);
        addmovie.setOnClickListener(this);

        title = view.findViewById(R.id.film_nama);
        title.setText(currentTitle);

        film_summary = view.findViewById(R.id.film_summary);
        film_summary.setText(summary_1);


        requestQueue = Volley.newRequestQueue(getContext());


        callAPI(ID);
        retrievereviews();





        mRecyclerview = view.findViewById(R.id.recyclerviewFilmInfo);

        mLayoutManager = new LinearLayoutManager(getContext());


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void callAPI(String ID) {
        String request = "https://api.themoviedb.org/3/movie/" + ID + "?api_key=fa302bdb2e93149bd69faa350c178b38";

        //StringRequest stringRequest = new StringRequest(Request.Method.GET, request,new Response.Listener<String>()
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, request, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject movieJson = response;
                            //JSONArray moviesJson = movieJson.getJSONArray("results");

                            movies = Movie.fromJson(movieJson);
                            System.out.println(movies.getTitle().toString());

                            String title = movies.getTitle().toString();
                            String ID = movies.getTitle().toString();
                            String imagePath = movies.getmImageResource().toString();
                            String releaseDate = movies.getRelease().toString();
                            String language = "language: " + movies.getLanguage().toString();
                            String fullImagePath = prefixImage + imagePath;
                            String summary = movies.getSummary().toString();
                            System.out.println("summaryFraAPI" + summary);
                            film_summary.setText(summary);

                            Movie item = new Movie(releaseDate, language, title, fullImagePath, "friendhere", summary, ID);
                            example.add(item);

                            //addItems();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> System.out.println("couldn't get answer from API in Home Fragment or couldnt populate recyclerview in home"));
        requestQueue.add(jsonObjectRequest);
    }



    public void retrievereviews() {
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    System.out.println("DocumentSnapshot data: " + documentSnapshot.getData());
                    ArrayList<FriendReviewList> friendReviewList = new ArrayList<>();

                    Map<String, Object> map = documentSnapshot.getData();
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
//                        System.out.println("entrykey: " + entry.getKey());
//                        System.out.println("entryvalue: " + entry.getValue());
                        ArrayList<String> reviewsRatings = (ArrayList<String>) entry.getValue();
//                        System.out.println("reviwsratings : " + reviewsRatings);
                        String review = reviewsRatings.get(0);
                        String rating = reviewsRatings.get(1);
                        System.out.println("review: " + review);
                        System.out.println("rating: " + rating);
                        //add to recyclerview
                        friendReviewList.add(new FriendReviewList(R.drawable.ic_profile, entry.getKey(), review));

                    }
                    mAdapter = new FriendReviewAdapter(friendReviewList);
                    mRecyclerview.setLayoutManager(mLayoutManager);
                    mRecyclerview.setAdapter(mAdapter);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {

        if (v == addToList) {
            showPopup(v);
        }if (v == addReview) {
            System.out.println("clicked on add review");
        }

    }


    public void showPopup(View v) {

        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        popupMenu.getMenuInflater().inflate(R.menu.addmovie_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.AddWatchLater:
                        Toast.makeText(getActivity(), "you added this movie to your 'Want to watch' list", Toast.LENGTH_LONG).show();
                        DocumentReference addWatchLater = db.collection("MovieList").document("Sukkerknald");
                        addWatchLater.update("WantToWatch", FieldValue.arrayUnion("347158"));
                        break;
                    case R.id.AddWatched:
                        Toast.makeText(getActivity(), "you added this movie to your 'Watched' list", Toast.LENGTH_LONG).show();
                        DocumentReference addWatched = db.collection("MovieList").document("PippiLangstromp");
                        addWatched.update("Watched", FieldValue.arrayUnion("487555"));
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        popupMenu.show();

    }


}