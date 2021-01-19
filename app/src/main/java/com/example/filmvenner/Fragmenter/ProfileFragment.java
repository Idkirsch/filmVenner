package com.example.filmvenner.Fragmenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {


//    Executor backgroundThread = Executors.newSingleThreadExecutor();
//    Handler uiThread = new Handler(Looper.getMainLooper());

    Button b1;
    Button watchedButton;
    Button watchButton;
    Button reviewButton;
    ImageButton settingsButton;
    ImageButton editButton;
//    DatabaseAccess db = new DatabaseAccess();
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    SharedPreferences prefMan;
    ImageView profilePic;
    TextView userNameText;


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
        Context context = getContext();

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
        ImageView profilePic = (ImageView)view.findViewById(R.id.imageProfilePic);profilePic.setOnClickListener(this);
        prefMan = context.getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        System.out.println("prefman get all: "+prefMan.getAll());

        String usernameFromPrefMan = prefMan.getString("currentUserName", "default");

        System.out.println( "Username from preferencemanager"+ usernameFromPrefMan);
        String userpicFromPrefMan = prefMan.getString("currentUserPicture", "default");
        System.out.println( "Usernpicture from preferencemanager"+ userpicFromPrefMan);


      //  Picasso.get().load(userpicFromPrefMan).into(profilePic);

        profilename.setText(usernameFromPrefMan);


//        DocumentReference docRef = database.collection("users").document("TEST");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//                    DocumentSnapshot document = task.getResult();
//                    if(document.exists()){
//                        System.out.println("DocumentSnapshot data: "+ document.getData());
//                        System.out.println("document get name: "+ document.get("name"));
//                        profilename.setText(document.getString("name"));
//                        //create map to save data in
//                        //Map<String, Object> user = document.getData();
//
//                    }else{
//                        System.out.println("no such document");
//                    }
//                }else{
//                    System.out.println("get failed with "+task.getException());
//                }
//            }
//        });


        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ratedbutton:
                startActivity(new Intent(getActivity(), ProfileRated.class));
                break;

            case R.id.watchedbutton:
//                startActivity(new Intent(getActivity(), ProfileWatched.class));
                System.out.println("klikkede på to watched button");

                FragmentManager fragmentManager2 = getChildFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();

                ProfilWatched watched = new ProfilWatched();
                fragmentTransaction2.add(R.id.frameLayout_for_something, watched);


                fragmentTransaction2.commit();
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
//                startActivity(new Intent(getActivity(), ProfileReviewed.class));
                System.out.println("klikkede på to reviewed button");

                FragmentManager fragmentManager3 = getChildFragmentManager();
                FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();

                ProfilReviewed reviewed = new ProfilReviewed();
                fragmentTransaction3.add(R.id.frameLayout_for_something, reviewed);

                fragmentTransaction3.commit();
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


