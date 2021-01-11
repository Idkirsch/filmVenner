package com.example.filmvenner.Fragmenter;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.example.filmvenner.Aktiviteter.ProfileToWatch;
import com.example.filmvenner.Aktiviteter.ProfileWatched;
import com.example.filmvenner.R;
import com.example.filmvenner.Aktiviteter.Settings;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button b1;
    Button watchedButton;
    Button watchButton;
    Button reviewButton;
    ImageButton settingsButton;
    ImageButton editButton;
    ImageView profilePic;
    TextView userNameText;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button b1 = (Button )view.findViewById(R.id.ratedbutton);
        b1.setOnClickListener(this);
        Button watchedButton = (Button)view.findViewById(R.id.watchedbutton);
        watchedButton.setOnClickListener(this);
        Button watchButton = (Button)view.findViewById(R.id.towatchbutton);
        watchButton.setOnClickListener(this);
        Button reviewedButton = (Button)view.findViewById(R.id.reviewedbutton);
        reviewedButton.setOnClickListener(this);
        ImageButton settingsButton = (ImageButton) view.findViewById(R.id.imageButtonSetting);
        settingsButton.setOnClickListener(this);
        ImageButton editButton = (ImageButton)view.findViewById(R.id.imageButtonEdit);
        editButton.setOnClickListener(this);

        ImageView profilePic = (ImageView)view.findViewById(R.id.imageProfilePic);
        profilePic.setOnClickListener(this);
        TextView userNameText = (TextView)view.findViewById(R.id.ProfileNameTV);


        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ratedbutton:
                startActivity(new Intent(getActivity(), ProfileRated.class));
                break;

            case R.id.watchedbutton:
                startActivity(new Intent(getActivity(), ProfileWatched.class));
                break;

            case R.id.towatchbutton:
                startActivity(new Intent(getActivity(), ProfileToWatch.class));
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


