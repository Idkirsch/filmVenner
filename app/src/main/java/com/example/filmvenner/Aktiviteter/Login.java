package com.example.filmvenner.Aktiviteter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.filmvenner.Aktiviteter.CreateUser;
import com.example.filmvenner.Aktiviteter.MainActivity;
import com.example.filmvenner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button login, createUser;
    EditText username, password;
    String expected_username = "test123", expected_password = "1";

    FirebaseFirestore database = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(this);

        createUser = (Button) findViewById(R.id.createUserButtonLoginFrag);
        createUser.setOnClickListener(this);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
    }

    @Override
    public void onClick(View view) {
        if(view == login){
            String inputUsername = username.getText().toString();
            System.out.println("input username is: "+inputUsername);
            String inputPassword = password.getText().toString();


            //check if name MARIE is in document "username"  in collection users.
            // if it is, print it in console


            //DocumentReference docRef = database.collection("users").document(inputUsername);

            DocumentReference docRef = database.collection("users").document("BrugernavnET");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot document = task.getResult();
                        if(document.exists()){
                            if(document.getString("username").equals(inputUsername)){
                                System.out.println("the username equals the username from database");
                                System.out.println("name from database= "+document.get("username"));
                                System.out.println("name from inputfield= "+inputUsername);
                            }

                        }else{
                            System.out.println("no such document");
                        }
                    }else{
                        System.out.println("get failed with "+task.getException());
                    }
                }
            });


            if(inputUsername.equals(expected_username) && inputPassword.equals(expected_password)){ // checks if input is equal to expected. TODO: get expected password from database
                System.out.println("yess man ");
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else{
                //promptes til at oprette en bruger?
                username.setText("test123");
                password.setText("1");
            }
        }
        if(view == createUser){
            System.out.println("clicked on create user");
            Intent intent = new Intent(this, CreateUser.class);
            startActivity(intent);
        }

    }
}