package com.example.filmvenner.DAO;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DatabaseAccess {

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    CollectionReference usersDB = database.collection("users");

    private String name;


    public void retrieveData(){
        DocumentReference docRef = database.collection("users").document("TEST");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        System.out.println("DocumentSnapshot data: "+ document.getData());
                        System.out.println("document get name: "+ document.get("name"));
                        name = document.get("name").toString();
                    }else{
                        System.out.println("no such document");
                    }
                }else{
                    System.out.println("get failed with "+task.getException());
                }
            }
        });

    }

}
