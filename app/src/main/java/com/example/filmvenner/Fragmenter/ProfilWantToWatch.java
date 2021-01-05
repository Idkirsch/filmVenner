package com.example.filmvenner.Fragmenter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.filmvenner.Adapter.ListmovieAdapter;
import com.example.filmvenner.Adapter.RecyclerViewAdapter;
import com.example.filmvenner.DAO.ListmovieItem;
import com.example.filmvenner.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilWantToWatch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilWantToWatch extends Fragment {

    private RecyclerView mRecyclerview;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pga vi arbejder i fragments, skrives logik i onCreateView.
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil_want_to_watch, container, false);

        // Inflate the layout for this fragment
        ArrayList<ListmovieItem> listmovieItems = new ArrayList<>();

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

        mRecyclerview = view.findViewById(R.id.recyclerviewWantToWatch);
        mRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ListmovieAdapter(listmovieItems); //todo If problems, check here

        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setAdapter(mAdapter);
        return view;
    }
























    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfilWantToWatch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilWantToWatch.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilWantToWatch newInstance(String param1, String param2) {
        ProfilWantToWatch fragment = new ProfilWantToWatch();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }




}