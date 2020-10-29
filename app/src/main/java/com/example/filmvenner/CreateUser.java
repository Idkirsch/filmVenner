package com.example.filmvenner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateUser extends AppCompatActivity implements View.OnClickListener {

    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        create = (Button) findViewById(R.id.createUserButton);
        create.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == create){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}