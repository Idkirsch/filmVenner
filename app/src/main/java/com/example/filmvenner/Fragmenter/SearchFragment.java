package com.example.filmvenner.Fragmenter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.filmvenner.DAO.Movie;
import com.example.filmvenner.R;
import com.example.filmvenner.SearchResultViewModel;
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


public class SearchFragment extends Fragment implements View.OnClickListener {

    private SearchResultViewModel viewModel;

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore database = FirebaseFirestore.getInstance();

  //  String url = "https://www.omdbapi.com/?t=";
  //  String apikey = "&apikey=3e1a983d";
    String apiKeyFromTMDB = "fa302bdb2e93149bd69faa350c178b38";
    String query1 = "https://api.themoviedb.org/3/search/movie?api_key=fa302bdb2e93149bd69faa350c178b38&language=en-US&query=";
    String query2;
    String query3 = "&page=1&include_adult=false";
    private ImageButton searchButton, addFriend;
    private EditText searchField;
    SearchResult_Frag fragmentResult = new SearchResult_Frag();
    Movie movie = new Movie();
    ArrayList<Movie> movies;
    JSONObject movietest = new JSONObject();

    String B = new String();
    ArrayList<String> friendListe;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);

        searchField = view.findViewById(R.id.search);

        addFriend = (ImageButton) view.findViewById (R.id.friendButton);
        addFriend.setOnClickListener (this);
        addRecyclerFragment(); // This method does the getChildFragmentManager() stuff.

        addFriend();


        return view;
    }

    private void addFriend() {
        //init docref
        DocumentReference docRef = database.collection("users").document("B");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot> () {
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
                                System.out.println("Her er friendlisten");
                                friendListe = (ArrayList<String>) entry.getValue();
//                                System.out.println("vores egen friendListe: " + friendListe);

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void addRecyclerFragment() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction childFragManager = fragmentManager.beginTransaction();
        SearchRecycler_frag recycler_frag = new SearchRecycler_frag();
        childFragManager.add(R.id.nestedFragment_Search, recycler_frag);
        childFragManager.addToBackStack("recyclerfrag");
        childFragManager.commit();
    }

    public void addSearchResultFrag() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction childFragManager = fragmentManager.beginTransaction();
        childFragManager.replace(R.id.nestedFragment_Search, fragmentResult);
        childFragManager.addToBackStack("result_frag");
        childFragManager.commit();
    }

    public void instantiateJson() throws JSONException {
        movietest.put("Title", "shrek");
        movietest.put("Year", "2000");
    }


    @Override
    public void onClick(View view) {

        System.out.println("clicked search");
        System.out.println(searchField.getText().toString());

        String title = searchField.getText().toString();


        if (!title.isEmpty()) {
            query2 = title;
            callAPI();
            viewModel = new ViewModelProvider(requireActivity()).get(SearchResultViewModel.class);
            addSearchResultFrag(); // instantiates new fragment where search result is displayed

        } else {
            System.out.println("search field is empty ");

        } if (view == addFriend) {
            showPopup(view);
        }
    }

    private void callAPI() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        // String request = "https://api.themoviedb.org/3/search/movie?api_key=fa302bdb2e93149bd69faa350c178b38&language=en-US&query=avengers&page=1&include_adult=false";

        String request = query1+query2+query3;

        //StringRequest stringRequest = new StringRequest(Request.Method.GET, request,new Response.Listener<String>()
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, request, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(request);
                        try {
                            JSONObject movieJson = response;
                            JSONArray moviesJson = movieJson.getJSONArray("results");
                            movies = Movie.fromJson(moviesJson);

                            viewModel.setMovie(response); //transfers some text into viewmodel to be used in child fragment

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, error -> System.out.println("that didnt work"));
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    private void showPopup(View view) {
        PopupMenu popupMenu = new PopupMenu (getActivity (), view);
        popupMenu.getMenuInflater ().inflate (R.menu.addfriend_menu, popupMenu.getMenu ());

        popupMenu.setOnMenuItemClickListener (new PopupMenu.OnMenuItemClickListener () {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId ()) {
                    case R.id.AddFriend:
                        Toast.makeText (getActivity (), "you added this friend to your 'friend' list", Toast.LENGTH_LONG).show ();
                        DocumentReference addFriend = database.collection ("users").document ("B");
                        addFriend.update ("Friends", FieldValue.arrayUnion ("newFriend"));
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        addFriend ();
        popupMenu.show ();
    }

}



