package com.example.filmvenner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = (Button) findViewById(R.id.loginButton);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == button){
            Intent intent = new Intent(this, CreateUser.class);
            startActivity(intent);


        }

    }
}