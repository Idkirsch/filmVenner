package com.example.filmvenner.Fragmenter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.filmvenner.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilmInfoFragment extends Fragment implements View.OnClickListener {

    ImageButton addToList;
    TextView title;
    String currentTitle = "Moana";
    String movieID = "277834";


    FirebaseFirestore database = FirebaseFirestore.getInstance();
    DocumentReference docRef = database.collection("MovieList").document("PippiLangstromp");



    public void ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_film_info, container, false);

        addToList = (ImageButton) view.findViewById(R.id.add_to_list_button);
        addToList.setOnClickListener(this);



        title = view.findViewById(R.id.film_nama);
        title.setText(currentTitle);

        return view;
    }

    @Override
    public void onClick(View v) {

        if(v == addToList){
            System.out.println("klikkede på add to list knap");
            docRef.update("WantToWatch", FieldValue.arrayUnion(movieID));
        }
    }
}