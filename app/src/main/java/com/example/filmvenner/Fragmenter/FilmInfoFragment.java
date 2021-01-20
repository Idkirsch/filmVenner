package com.example.filmvenner.Fragmenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import com.example.filmvenner.DAO.DatabaseAccess;
import com.example.filmvenner.DAO.Movie;
import com.example.filmvenner.Adapter.FriendReviewAdapter;
import com.example.filmvenner.DAO.FriendReviewList;
import com.example.filmvenner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
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
    String movieidet;

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ImageButton addToList, addReview;
    TextView film_summary;
    TextView film_title;
    ImageView film_poster;

    private RequestQueue requestQueue;
    Movie movies = new Movie ();
    ArrayList<Movie> example = new ArrayList<> ();
    String currentUser;
    SharedPreferences prefMan;




    FirebaseFirestore database = FirebaseFirestore.getInstance();
    DocumentReference docRef;


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
        Context context = getContext();
        View view = inflater.inflate(R.layout.fragment_film_info2, container, false);

        addToList = view.findViewById(R.id.add_button);
        addToList.setOnClickListener(this);

        addReview = view.findViewById(R.id.add_review);
        addReview.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(getContext());


        SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
        movieidet = pref.getString("currentMovieID", "default");
        System.out.println("Movie Id overført til filminfo "+movieidet);

        docRef = database.collection("Film").document(movieidet);


        System.out.println("movieID from preferencemanager"+ movieidet);

        callAPI(movieidet);
        ImageButton addmovie = view.findViewById(R.id.add_button);
        addmovie.setOnClickListener(this);

        film_summary = view.findViewById(R.id.film_summary);
        film_title= view.findViewById(R.id.film_nama);
        film_poster= view.findViewById(R.id.film_img);

        //addmovie.setOnClickListener(this);

        /*ArrayList<FriendReviewList>  friendReviewList = new ArrayList<>();
        friendReviewList.add(new FriendReviewList(R.drawable.ic_profile, "Louise Nygaard", "jeg synes det her var verdens dårligste film, jeg havde lyst til at græde"));
        friendReviewList.add(new FriendReviewList(R.drawable.ic_profile, "Bent Larsen", "jeg synes det her var verdens bedste fil"));
        friendReviewList.add(new FriendReviewList(R.drawable.ic_profile, "Ole Henriksen", "rtfyguhijgfudtryghjlhlugyiftudryersdtcjvkbhljnkliyuktcrtcfjgvhbjnklbuvtycrfgjvhbjgvkyfcjgvh"));

         */

        requestQueue = Volley.newRequestQueue(getContext());
        retrievereviews();
        mRecyclerview = view.findViewById(R.id.recyclerviewFilmInfo);

        prefMan = getContext().getSharedPreferences("currentUser", Context.MODE_PRIVATE);

        currentUser = prefMan.getString("currentUserName", "default");

        System.out.println( "Username from preferencemanager"+ currentUser);

        mLayoutManager = new LinearLayoutManager(getContext());

        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    System.out.println("listen failed" + error);
                    return;
                }
                if(snapshot != null && snapshot.exists()){
                    System.out.println("current data: "+ snapshot.getData());
                    retrievereviews();
                }
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void callAPI(String movieidet) {
        String request = "https://api.themoviedb.org/3/movie/"+movieidet+"?api_key=fa302bdb2e93149bd69faa350c178b38";

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
                            String prefixImage = "https://image.tmdb.org/t/p/w500";

                                String title = movies.getTitle().toString();
                                film_title.setText(title);
                                String ID = movies.getID().toString();
                                String imagePath = movies.getmImageResource().toString();
                                String releaseDate = movies.getRelease().toString();
                                String language = "language: " + movies.getLanguage().toString();
                                String fullImagePath = prefixImage + imagePath;
                                Picasso.get().load(fullImagePath).into(film_poster);
                                String summary = movies.getSummary ().toString ();
                                System.out.println ("summaryFraAPI"+summary);
                                film_summary.setText (summary);

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

                    if (documentSnapshot.exists()) {
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
        }
        if (v == addReview) {
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
                    addReview(newReview);

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


    public void addReview(String newreview) {
        DocumentReference docRef = database.collection("Film").document(movieidet);


        ArrayList<String> newReviewsRatings = new ArrayList<>();


        Map<String, Object> map = new HashMap<>();

        newReviewsRatings.add(newreview);
        newReviewsRatings.add("*");
        map.put(currentUser, newReviewsRatings);


        docRef.set(map, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("review succesfully altered or written");
            }
        });

    }


    public void showPopup(View v){

        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        popupMenu.getMenuInflater().inflate(R.menu.addmovie_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.AddWatchLater:
                        Toast.makeText(getActivity(), "you added this movie to your 'Want to watch' list", Toast.LENGTH_LONG).show();
                        DocumentReference addWatchLater = db.collection("MovieList").document(currentUser);
                        addWatchLater.update("WantToWatch", FieldValue.arrayUnion(movieidet));
                        break;
                    case R.id.AddWatched:
                        Toast.makeText(getActivity(), "you added this movie to your 'Watched' list", Toast.LENGTH_LONG).show();
                        DocumentReference addWatched = db.collection("MovieList").document(currentUser);
                        addWatched.update("Watched", FieldValue.arrayUnion(movieidet));
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