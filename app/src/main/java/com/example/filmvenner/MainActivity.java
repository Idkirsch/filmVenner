package com.example.filmvenner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button videre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videre = (Button) findViewById(R.id.buttonVidere);
        videre.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(view == videre){
            Intent login = new Intent(this, Login.class);
            this.startActivity(login);
        }

    }
}