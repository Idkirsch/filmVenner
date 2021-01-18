package com.example.filmvenner.Aktiviteter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.filmvenner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class skalSlettes extends AppCompatActivity {
    String MovieID = "372058";
    TextView reviewView;

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    DocumentReference docRef = database.collection("Film").document(MovieID);
    // CollectionReference collectionReference = database.collection("Film");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skal_slettes);


        reviewView = findViewById(R.id.reviewFromDB);

        addReview();

    }


    public void retrievereviews() {
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    System.out.println("DocumentSnapshot data: " + documentSnapshot.getData());

                    Map<String, Object> map = documentSnapshot.getData();
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
//                        System.out.println("entrykey: " + entry.getKey());
//                        System.out.println("entryvalue: " + entry.getValue());
                        ArrayList<String> reviewsRatings = (ArrayList<String>) entry.getValue();
//                        System.out.println("reviwsratings : " + reviewsRatings);
                        String review = reviewsRatings.get(0);
                        String rating = reviewsRatings.get(1);
                        System.out.println("review: " + review);
                        System.out.println("rating: " + rating);
                        //add to recyclerview
                    }
                }
            }
        });
    }

    public void addReview() {
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    System.out.println("DocumentSnapshot data: " + documentSnapshot.getData());
                    ArrayList<String> currentReviewsRatings = new ArrayList<>();
                    ArrayList<String> newReviewsRatings = new ArrayList<>();

                    String newreview = "test her er et review3";
                    String currentReview = new String();
                    String currentRating = new String();


                    Map<String, Object> map = documentSnapshot.getData();

                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        currentReviewsRatings = (ArrayList<String>) entry.getValue();

                        currentReview = currentReviewsRatings.get(0);
                        currentRating = currentReviewsRatings.get(1);


                    }

                    newReviewsRatings.add(newreview);
                    newReviewsRatings.add(currentRating);


                    map.put("Pis", newReviewsRatings);
                    System.out.println("map pippi: " + map);

                    docRef.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            System.out.println("review succesfully altered");
                        }
                    });

                }
            }
        });
    }


}


