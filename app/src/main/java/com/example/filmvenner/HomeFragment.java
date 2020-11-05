package com.example.filmvenner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);


        ArrayList<ExampleItem> exampleList = new ArrayList<>();

        exampleList.add(new ExampleItem(R.drawable.film, "Line1","Line2"));
        exampleList.add(new ExampleItem(R.drawable.ic_access, "Line3","Line4"));
        exampleList.add(new ExampleItem(R.drawable.ic_adjust, "Line5","Line6"));
        exampleList.add(new ExampleItem(R.drawable.ic_adjust, "Line5","Line6"));
        exampleList.add(new ExampleItem(R.drawable.ic_adjust, "Line5","Line6"));
        exampleList.add(new ExampleItem(R.drawable.ic_adjust, "Line5","Line6"));
        exampleList.add(new ExampleItem(R.drawable.ic_adjust, "Line5","Line6"));
        exampleList.add(new ExampleItem(R.drawable.ic_adjust, "Line5","Line6"));

        mRecyclerView = v.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ExampleAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }
}