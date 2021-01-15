package com.example.filmvenner.Aktiviteter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.filmvenner.DAO.User;
import com.example.filmvenner.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private CallbackManager mCallBackManager;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private LoginButton loginButton;
    private AccessTokenTracker accessTokenTracker;
    private static final String TAG = "FacebookAuth";

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

        mFirebaseAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());

        login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(this);

        createUser = (Button) findViewById(R.id.createUserButtonLoginFrag);
        createUser.setOnClickListener(this);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        loginButton = findViewById(R.id.loginFace);
        loginButton.setReadPermissions("email", "public_profile");
        mCallBackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(mCallBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess" + loginResult);
                handleFacebookToken (loginResult.getAccessToken());
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel");


            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError" + error);

            }
        });
            //when authentification is changed such as logged in or out it will update
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    updateUI(user);
                    System.out.println("Hej fra state succes");
                }
                else {
                    updateUI(null);
                }

            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken == null){
                    mFirebaseAuth.signOut();
                }
            }
        };

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
                                     System.out.println("name from database= "+document.get("username"));
//                                    System.out.println("name from inputfield= "+inputUsername);
//                                    System.out.println("the users username is: "+user.getUsername());
//                                    System.out.println("the users email is now set to: "+user.getEmail());
                                    user.setUsername(document.getString("username"));
                                    user.setEmail(document.getString("email"));
                                    //putUserInPreferenceManager();

                                    SharedPreferences.Editor editor = prefMan.edit();

                                    editor.putString("currentUserName", document.getString("username"));
//                                    editor.putString("currentUserName", "HardCodedBrugernavn");
                                    editor.apply();


                                }
                            }else{
                                System.out.println("ingen brugernavne i databasen matcher det inputtede brugernavn");
                            }
                        }else{
                            System.out.println("get failed with "+task.getException());
                        }
                    }
                });

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

            }else {
                username.setText("PippiLangstromp");
                System.out.println("Inputfeltet til brugernavnet er tomt");
            }


//            if(inputUsername.equals(expected_username) && inputPassword.equals(expected_password)){ // checks if input is equal to expected. TODO: get expected password from database
//                System.out.println("the username and password input was equal to the expected ");
//                Intent intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
//            }else{
//                //promptes til at oprette en bruger?
//                username.setText("test123");
//                password.setText("1");
//            }
        }
        if(view == createUser){
            System.out.println("clicked on create user");
            Intent intent = new Intent(this, CreateUser.class);
            startActivity(intent);
        }

    }

    public void handleFacebookToken(AccessToken token) {
        Log.d(TAG, "handleFacebookToken" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d(TAG, "Sign in with credential: successfull");
                    FirebaseUser user = mFirebaseAuth.getCurrentUser();
                    updateUI (user);
                }else {

                    Log.d(TAG, "sign in with credential failure", task.getException());
                    Toast.makeText(Login.this, "Authentification failed", Toast.LENGTH_SHORT).show();
                    updateUI(null);

                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateUI(FirebaseUser user){
        if(user != null) {
            // navnpåTV.setText(user.getDisplayName());
            if(user.getPhotoUrl() != null){

                String photoUrl = user.getPhotoUrl().toString();
                photoUrl = photoUrl + "?type=large";
                //Picasso.get().load(photoUrl).into();// Imageview id på billede skal ind parantes
                System.out.println("PHOTOURL"+photoUrl);

                SharedPreferences.Editor editor = prefMan.edit();

                editor.putString("currentUserPicture", photoUrl);
                editor.apply();
            }
        }
        else{
            // navnpåTV.setText("");
            // navnpåIM.setImageResource(R.drawable.ic_profile)
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(authStateListener);

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