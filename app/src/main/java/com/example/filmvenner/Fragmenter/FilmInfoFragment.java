package com.example.filmvenner.Fragmenter;

import android.os.Bundle;

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

import com.example.filmvenner.Adapter.FriendReviewAdapter;
import com.example.filmvenner.DAO.FriendReviewList;
import com.example.filmvenner.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilmInfoFragment extends Fragment implements View.OnClickListener {
    private RecyclerView mRecyclerview;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public FilmInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilmInfoFragment newInstance(String param1, String param2) {
        FilmInfoFragment fragment = new FilmInfoFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_film_info, container, false);


        ImageButton addmovie = (ImageButton) view.findViewById(R.id.add_button);
        addmovie.setOnClickListener(this);

        ArrayList<FriendReviewList>  friendReviewList = new ArrayList<>();
        friendReviewList.add(new FriendReviewList(R.drawable.ic_profile, "Louise Nygaard", "jeg synes det her var verdens dårligste film, jeg havde lyst til at græde"));
        friendReviewList.add(new FriendReviewList(R.drawable.ic_profile, "Bent Larsen", "jeg synes det her var verdens bedste fil"));
        friendReviewList.add(new FriendReviewList(R.drawable.ic_profile, "Ole Henriksen", "rtfyguhijgfudtryghjlhlugyiftudryersdtcjvkbhljnkliyuktcrtcfjgvhbjnklbuvtycrfgjvhbjgvkyfcjgvh"));

        mRecyclerview = view.findViewById(R.id.recyclerviewFilmInfo);

        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new FriendReviewAdapter(friendReviewList);

        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setAdapter(mAdapter);




        return  view;
    }

    @Override
    public void onClick(View v) {

        showPopup(v);

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