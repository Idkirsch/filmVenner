package com.example.filmvenner.Fragmenter;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.filmvenner.Aktiviteter.ProfileEdit;
import com.example.filmvenner.Aktiviteter.ProfileRated;
import com.example.filmvenner.Aktiviteter.Settings;
import com.example.filmvenner.DAO.FilmList;
import com.example.filmvenner.R;
import com.example.filmvenner.Adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class SearchRecycler_frag extends Fragment implements View.OnClickListener {

    private List<FilmList> filmLists = new ArrayList<>();
    private RecyclerView recyclerView1, recyclerView2, recyclerView3;


    private TextView textview;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_recycler_frag, container, false);


        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView1 = view.findViewById(R.id.recyclerView1);
        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView3 = view.findViewById(R.id.recyclerView3);


        recyclerView1.setLayoutManager(layoutManager1);
        RecyclerViewAdapter adapter1 = new RecyclerViewAdapter(filmLists, getActivity());
        recyclerView1.setAdapter(adapter1);

        recyclerView2.setLayoutManager(layoutManager2);
        RecyclerViewAdapter adapter2 = new RecyclerViewAdapter(filmLists, getActivity());
        recyclerView2.setAdapter(adapter2);

        recyclerView3.setLayoutManager(layoutManager3);
        RecyclerViewAdapter adapter3 = new RecyclerViewAdapter(filmLists, getActivity());
        recyclerView3.setAdapter(adapter3);


        filmLists.add(new FilmList(R.drawable.film));
        filmLists.add(new FilmList(R.drawable.film));
        filmLists.add(new FilmList(R.drawable.film));
        filmLists.add(new FilmList(R.drawable.film));
        filmLists.add(new FilmList(R.drawable.film));
        filmLists.add(new FilmList(R.drawable.film));
        filmLists.add(new FilmList(R.drawable.film));


        return view;
    }

    // ? vil skift side til film_info leyout ved at trykke på recycleViews
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recyclerView1:
                //startActivity(new Intent (getActivity(), FilmInfoFragment.class));
                /*System.out.println("klikkede på recykler 1");
                FragmentManager fragmentManager7 = getChildFragmentManager();
                FragmentTransaction fragmentTransaction7 = fragmentManager7.beginTransaction();

                FilmInfoFragment filmInfo = new FilmInfoFragment();
                fragmentTransaction7.add(R.id.nestedFragment_Search, filmInfo);


                fragmentTransaction7.commit();*/

                break;

            case R.id.recyclerView2:
                startActivity(new Intent (getActivity(), FilmInfoFragment.class));


            case R.id.recyclerView3:
                startActivity(new Intent (getActivity(), FilmInfoFragment.class));

                break;
        }
    }
}