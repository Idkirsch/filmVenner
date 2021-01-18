package com.example.filmvenner.Aktiviteter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.filmvenner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class skalSlettes extends AppCompatActivity {
    String MovieID = "372058";

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    DocumentReference docRef = database.collection("Film").document(MovieID);
    // CollectionReference collectionReference = database.collection("Film");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skal_slettes);


        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    System.out.println("DocumentSnapshot data: " + documentSnapshot.getData());

                }
            }
        });
    }
}


// docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//@Override
//public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//        if(task.isSuccessful()){
//        DocumentSnapshot document = task.getResult();
//        if(document.exists()){
//        System.out.println("DocumentSnapshot data: "+ document.getData());
//        System.out.println("document get name: "+ document.get("name"));
//        name = document.getString("name");
//
//        //create map to save data in
//        //Map<String, Object> user = document.getData();
//
//        }else{
//        System.out.println("no such document");
//        }
//        }else{
//        System.out.println("get failed with "+task.getException());
//        }
//        }
//        });
//
//        }