package com.example.filmvenner.Aktiviteter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.filmvenner.Aktiviteter.CreateUser;
import com.example.filmvenner.Aktiviteter.MainActivity;
import com.example.filmvenner.DAO.User;
import com.example.filmvenner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button login, createUser;
    EditText username, password;
    String expected_username = "test123", expected_password = "1";
    User user = new User();

    /*
    *Creating an instance of the database in order to check if the username that the user types is in fact in the database.
    */
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    /*
     * Instantiaing the preferencemanager to be able to reach the user to other fragments or activities
     */
     SharedPreferences prefMan;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefMan = this.getSharedPreferences("currentUser", Context.MODE_PRIVATE);

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


            if(!inputUsername.isEmpty()){

                DocumentReference docRef = database.collection("users").document(inputUsername);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                if(document.getString("username").equals(inputUsername)){
                                    System.out.println("the username equals the username from database");
//                                    System.out.println("name from database= "+document.get("username"));
//                                    System.out.println("name from inputfield= "+inputUsername);
//                                    System.out.println("the users username is: "+user.getUsername());
//                                    System.out.println("the users email is now set to: "+user.getEmail());
                                    user.setUsername(document.getString("username"));
                                    user.setEmail(document.getString("email"));
                                    putUserInPreferenceManager();

                                }
                            }else{
                                System.out.println("ingen brugernavne i databasen matcher det inputtede brugernavn");
                            }
                        }else{
                            System.out.println("get failed with "+task.getException());
                        }
                    }
                });

            }else {
                System.out.println("Inputfeltet til brugernavnet er tomt");
            }


            if(inputUsername.equals(expected_username) && inputPassword.equals(expected_password)){ // checks if input is equal to expected. TODO: get expected password from database
                System.out.println("the username and password input was equal to the expected ");
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


    public void putUserInPreferenceManager(){
        SharedPreferences.Editor editor = prefMan.edit();
        ExclusionStrategy ex = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return false;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };

        /*
        * Transforming the user object to Json to be able to sent it through the preference manager
        * */

        Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
        String userJson = gson.toJson(user);
        editor.putString("user",userJson);
        editor.commit();


//        System.out.println("user: "+userJson);
    }






}