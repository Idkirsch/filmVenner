package com.example.filmvenner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

    }

    @Override
    public void onClick(View view) {
        if(view == videre){
            Intent videre = new Intent(this, Login.class);
            this.startActivity(videre);
        }
        //Intent navHome = new Intent(this, WordpressActivity.class);
        //  this.startActivity(navHome);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener
            navListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item)
        {
            // By using switch we can easily get
            // the selected fragment
            // by using there id.
            switch (item.getItemId()) {
                case R.id.home:
                 //   Intent videre = new Intent(this, Home.class);
                  //  this.startActivity(home);
                    System.out.println("pressed home");
                    break;
                case R.id.search:
                    System.out.println("pressed searhc");
                    break;
                case R.id.profile:
                    System.out.println("pressed profile");
                    break;
            }
            return true;
        }
    };
}