package com.example.filmvenner.Fragmenter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.filmvenner.DAO.Movie;
import com.example.filmvenner.Adapter.FriendReviewAdapter;
import com.example.filmvenner.DAO.FriendReviewList;
import com.example.filmvenner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
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
    ImageView poster;
    TextView movieTitle;
    String currentTitle;
//    String ID = "24428";
    String ID = "464052";
    TextView film_summary;
    private RequestQueue requestQueue;
    Movie movies = new Movie();
    ArrayList<Movie> example = new ArrayList<>();
    private String prefixImage = "https://image.tmdb.org/t/p/w500";
    String summary_1 = "1234";

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    DocumentReference docRef = database.collection("Film").document(ID);


    private String newReview = "";


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

        movieTitle = view.findViewById(R.id.film_nama);
        movieTitle.setText(currentTitle);

        film_summary = view.findViewById(R.id.film_summary);
        film_summary.setText(summary_1);

        poster = view.findViewById(R.id.film_img);



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
                            movieTitle.setText(title);
                            Picasso.get().load(fullImagePath).into(poster);


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



                    if(documentSnapshot.exists()){
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


                            mAdapter = new FriendReviewAdapter(friendReviewList);
                            mRecyclerview.setLayoutManager(mLayoutManager);
                            mRecyclerview.setAdapter(mAdapter);
                        }

                    }

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
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Your review");

            final EditText input = new EditText(getContext());

            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setSingleLine(false);  //add this
            input.setLines(4);
            input.setMaxLines(5);
            input.setGravity(Gravity.LEFT | Gravity.TOP);
            input.setHorizontalScrollBarEnabled(false); //this
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    newReview = input.getText().toString();
                    //addReview(newReview);

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();

        }

    }


    public void addReview(String newreview){
        DocumentReference docRef = database.collection("Film").document(ID);


        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    System.out.println("DocumentSnapshot data: " + document.getData());
                    if(!document.exists()){
                        System.out.println("this movie is not in the database yet");
                    }else{


                        ArrayList<String> currentReviewsRatings = new ArrayList<>();
                        ArrayList<String> newReviewsRatings = new ArrayList<>();

                        String newreview = "test her er et review3";
                        String currentReview = new String();
                        String currentRating = new String();

                        Map<String, Object> map = document.getData();
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            currentReviewsRatings = (ArrayList<String>) entry.getValue();

                            currentReview = currentReviewsRatings.get(0);
                            currentRating = currentReviewsRatings.get(1);
                        }
                        newReviewsRatings.add(newreview);
                        newReviewsRatings.add(currentRating);
                        map.put("current user", newReviewsRatings);
                        // System.out.println("map pippi: " + map);

                        docRef.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                System.out.println("review succesfully altered");
                            }
                        });



                    }

                }
            }
        });
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