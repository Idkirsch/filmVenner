package com.example.filmvenner.Fragmenter;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.filmvenner.Aktiviteter.ProfileEdit;
import com.example.filmvenner.Aktiviteter.ProfileRated;
import com.example.filmvenner.Aktiviteter.ProfileReviewed;
import com.example.filmvenner.Aktiviteter.ProfileWatched;
import com.example.filmvenner.DAO.DatabaseAccess;
import com.example.filmvenner.R;
import com.example.filmvenner.Aktiviteter.Settings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {


    Button b1;
    Button watchedButton;
    Button watchButton;
    Button reviewButton;
    ImageButton settingsButton;
    ImageButton editButton;
    DatabaseAccess db = new DatabaseAccess();
    private TextView profilename;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button b1 = (Button) view.findViewById(R.id.ratedbutton);
        b1.setOnClickListener(this);
        Button watchedButton = (Button) view.findViewById(R.id.watchedbutton);
        watchedButton.setOnClickListener(this);
        Button watchButton = (Button) view.findViewById(R.id.towatchbutton);
        watchButton.setOnClickListener(this);
        Button reviewedButton = (Button) view.findViewById(R.id.reviewedbutton);
        reviewedButton.setOnClickListener(this);
        ImageButton settingsButton = (ImageButton) view.findViewById(R.id.imageButtonSetting);
        settingsButton.setOnClickListener(this);
        ImageButton editButton = (ImageButton) view.findViewById(R.id.imageButtonEdit);
        editButton.setOnClickListener(this);

        profilename = view.findViewById(R.id.ProfileNameTV);
        profilename.setText("HEjsa");

        db.retrieveData();


        return view;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ratedbutton:
                startActivity(new Intent(getActivity(), ProfileRated.class));
                break;

            case R.id.watchedbutton:
                startActivity(new Intent(getActivity(), ProfileWatched.class));
                break;

            case R.id.towatchbutton:

                System.out.println("klikkede på to watch button");

                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ProfilWantToWatch wantToWatch = new ProfilWantToWatch();
                fragmentTransaction.add(R.id.frameLayout_for_something, wantToWatch);


                fragmentTransaction.commit();

                break;

            case R.id.reviewedbutton:
                startActivity(new Intent(getActivity(), ProfileReviewed.class));
                break;

            case R.id.imageButtonSetting:
                startActivity(new Intent(getActivity(), Settings.class));
                break;

            case R.id.imageButtonEdit:
                startActivity(new Intent(getActivity(), ProfileEdit.class));
                break;
        }

    }


}


