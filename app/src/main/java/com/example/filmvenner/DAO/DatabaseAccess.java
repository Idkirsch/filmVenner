package com.example.filmvenner.DAO;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class DatabaseAccess {

    FirebaseFirestore database = FirebaseFirestore.getInstance();


    private String name;

    public String getName() {
        return name;
    }

    public DatabaseAccess() {
    }

    public void retrieveData(){
        //når man skal have fat i et bestemt document
        DocumentReference docRef = database.collection("users").document("TEST");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        System.out.println("DocumentSnapshot data: "+ document.getData());
                        System.out.println("document get name: "+ document.get("name"));
                        name = document.getString("name");

                        //create map to save data in
                        //Map<String, Object> user = document.getData();

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
