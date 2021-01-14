package com.example.filmvenner.Fragmenter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.filmvenner.Adapter.FriendReviewAdapter;
import com.example.filmvenner.DAO.FriendReviewList;
import com.example.filmvenner.R;

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

        ArrayList<FriendReviewList>  friendReviewList = new ArrayList<>();
        friendReviewList.add(new FriendReviewList(R.drawable.ic_profile, "Mads Nygaard", "jeg synes det her var verdens dårligste film, jeg havde lyst til at græde"));
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

    }
}