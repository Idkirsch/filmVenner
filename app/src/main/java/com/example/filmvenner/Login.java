package com.example.filmvenner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button login, createUser;
    EditText username, password;
    String expected_username = "test123", expected_password = "1";


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
            if(username.getText().toString().equals(expected_username) && password.getText().toString().equals(expected_password)){
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