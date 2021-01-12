package com.example.filmvenner.Aktiviteter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.filmvenner.DAO.User;
import com.example.filmvenner.R;
import com.example.filmvenner.DAO.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateUser extends AppCompatActivity implements View.OnClickListener {

    User test_user = new User();
    EditText email, username, password, confirmPassword;
    Button create;
    private static final String KEY_NAME = "username";
    private static final String KEY_EMAIL = "email";


    //database
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    CollectionReference users = database.collection("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        create = (Button) findViewById(R.id.createUserButton);
        create.setOnClickListener(this);

        email = (EditText) findViewById(R.id.createEmail);
        username  = (EditText) findViewById(R.id.createUsername);
        password = (EditText) findViewById(R.id.createPassword);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);

        FirebaseApp.initializeApp(this);

    }

    @Override
    public void onClick(View view) {
        if(view == create){
            String usrname = username.getText().toString();
            String mail = email.getText().toString();

            if(FieldsHasValues()){
                System.out.println("yes, the fields have values");


                addUser(usrname,mail,usrname);

                test_user.setUsername(username.getText().toString());
                test_user.setEmail(email.getText().toString());
                test_user.setPassword(password.getText().toString());

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else{
                System.out.println("no, the fields contain nothing or wrong values:-(");
            }

        }
    }

    public void addUser(String name, String email, String documentName){
        Map<String,Object> user = new HashMap<>();
        user.put(KEY_NAME, name);
        user.put(KEY_EMAIL, email);
        users.document(documentName).set(user);

        database.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        System.out.println( "DocumentSnapshot added with ID :"+documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error adding document "+e);
                    }
                });

    }



// checks if all fields are OK
    public boolean FieldsHasValues(){
        if(checkName() && checkEmail() && checkPasswords()){
            return true;
        }
        return false;
    }

//checks if name is longer than 2 letters
    public boolean checkName(){
        String input = username.getText().toString();
        if (input.length() < 2){
            username.setHint("please, longer than 2 letters");
        }else{
            //make check if it already exists
            // username is accepted
            return true;
        }
        return false;
    }

    public boolean checkEmail(){
        String input = email.getText().toString();
        if (input.length() < 2){
            username.setHint("please, longer than 2 letters");
        }else{
            //make check if it already exists
            // username is accepted
            return true;
        }
        return false;
    }

    public boolean checkPasswords(){
        String input = password.getText().toString();
        String confirm = confirmPassword.getText().toString();
        if (input.length() < 2){
            username.setHint("please, longer than 2 letters");
        }else{
            //make check if it already exists
            // username is accepted
            if(input.equals(confirm)){
                return true;
            }else{
                System.out.println("passwords matcher ikke");
            }
        }
        return false;
    }
}