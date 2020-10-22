package com.example.filmvenner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*
     Her i denne blok kan vi skrive vigtig info til hinanden.

    Jeg har lavet en meget simpel main activity, hvor der bare er én knap som hedder "videre".
    Nede i onClick metoden kan man ændre hvilken aktivitet, knappen skal tage dig hen til.

    Tjek lige den her fil ud: app > res > values > colors.xml
    Her kan man gemme sine egne farver under et navn, og bruge de farver i sit layout.
    Hvis alle gør dét, bliver det rigtig nemt og hurtigt at ændre farveskalaen til sidst ;-)
    Jeg har lavet et par stykker, og I skal være velkomne til at ændre dem eller ændre navngivningen

     */


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
            // her kan man ændre hvilken side knappen tager dig hen til
            Intent login = new Intent(this, Login.class);
            this.startActivity(login);
        }

    }
}