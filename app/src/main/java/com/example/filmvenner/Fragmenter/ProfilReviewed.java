package com.example.filmvenner.Fragmenter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.filmvenner.Adapter.ListmovieAdapter;
import com.example.filmvenner.DAO.ListmovieItem;
import com.example.filmvenner.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilReviewed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilReviewed extends Fragment {


    private RecyclerView mRecyclerview;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;




    public static ProfilReviewed newInstance(String param1, String param2) {
        ProfilReviewed fragment = new ProfilReviewed();
        Bundle args = new Bundle();

        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pga vi arbejder i fragments, skrives logik i onCreateView.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil_reviewed, container, false);

        ArrayList<ListmovieItem> listmovieItems = new ArrayList<>();

        listmovieItems.add(new ListmovieItem(R.drawable.filmtestpic, "Linje reviewed", "Linje 2r"));
        listmovieItems.add(new ListmovieItem(R.drawable.filmtestpic, "Linje 1", "Linje 2"));
        listmovieItems.add(new ListmovieItem(R.drawable.filmtestpic, "Linje 1", "Linje 2"));
        listmovieItems.add(new ListmovieItem(R.drawable.filmtestpic, "Linje 1", "Linje 2"));
        listmovieItems.add(new ListmovieItem(R.drawable.filmtestpic, "Linje 1", "Linje 2"));
        listmovieItems.add(new ListmovieItem(R.drawable.filmtestpic, "Linje 1", "Linje 2"));
        listmovieItems.add(new ListmovieItem(R.drawable.filmtestpic, "Linje 1", "Linje 2"));
        listmovieItems.add(new ListmovieItem(R.drawable.filmtestpic, "Linje 1", "Linje 2"));
        listmovieItems.add(new ListmovieItem(R.drawable.filmtestpic, "Linje 1", "Linje 2"));
        listmovieItems.add(new ListmovieItem(R.drawable.filmtestpic, "Linje 1", "Linje 2"));
        listmovieItems.add(new ListmovieItem(R.drawable.filmtestpic, "Linje 1", "Linje 2"));
        listmovieItems.add(new ListmovieItem(R.drawable.filmtestpic, "Linje 1", "Linje 2"));
        listmovieItems.add(new ListmovieItem(R.drawable.filmtestpic, "Linje 1", "Linje 2"));
        listmovieItems.add(new ListmovieItem(R.drawable.filmtestpic, "Linje 1", "Linje 2"));
        listmovieItems.add(new ListmovieItem(R.drawable.filmtestpic, "Linje 1", "Linje 2"));
        listmovieItems.add(new ListmovieItem(R.drawable.filmtestpic, "Linje 1", "Linje 2"));
        listmovieItems.add(new ListmovieItem(R.drawable.filmtestpic, "Linje 1", "Linje 2"));
        listmovieItems.add(new ListmovieItem(R.drawable.filmtestpic, "Linje 1", "Linje 2"));
        listmovieItems.add(new ListmovieItem(R.drawable.filmtestpic, "Linje 1", "Linje 2"));
        listmovieItems.add(new ListmovieItem(R.drawable.filmtestpic, "Linje 1", "Linje 2"));

        mRecyclerview = view.findViewById(R.id.recyclerviewReviewed);
        //mRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());

        mAdapter = new ListmovieAdapter(listmovieItems);

        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setAdapter(mAdapter);
        return view;
    }
}