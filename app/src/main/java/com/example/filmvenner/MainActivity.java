package com.example.filmvenner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayDeque;
import java.util.Deque;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    BottomNavigationView bottomNavigationView;
    Deque<Integer> integerDeque = new ArrayDeque<>(4);
    boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        //ad home frag in deque
        integerDeque.push(R.id.home);

        //set home as default fragment
        loadFragment(new HomeFragment());
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        //get selected item id
                        int id = item.getItemId();
                        //check condition
                        if(integerDeque.contains(id)){
                            //when deque list contains selected id
                            //check condition
                            if(id ==R.id.home){
                                // when selected is home
                                //check condition
                                if(integerDeque.size() != 1){
                                    if(flag){
                                        //when flag is true, add home fragment in deque list
                                        integerDeque.addFirst(R.id.home);
                                        //set flag to false
                                        flag = false;
                                    }
                                }
                            }
                            //Remove selected id from deque list
                            integerDeque.remove(id);
                        }
                        //push selected id in dqueqlist
                        integerDeque.push(id);
                        //load fragment
                        loadFragment(getFragment(item.getItemId()));
                        return true;
                    }
                }
        );

    }

    private Fragment getFragment(int itemId) {
        switch(itemId){
            case R.id.home:
                //set checked home frag
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                //return home frag
                return new HomeFragment();
            case R.id.search:
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                return new SearchFragment();
            case R.id.profile:
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                return new ProfileFragment();
            case R.id.chat:
                bottomNavigationView.getMenu().getItem(3).setChecked(true);
                return new ChatFragment();

        }
        //set checked default fome frag
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        return new HomeFragment();
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment,fragment,fragment.getClass().getSimpleName()).commit();
    }

    @Override
    public void onBackPressed() {
        //pop to previous fragment
        integerDeque.pop();
        if(!integerDeque.isEmpty()){
            //when list in not empty
            loadFragment(getFragment(integerDeque.peek()));
        }else{
            //when deque is empty
            finish();
        }
    }

    @Override
    public void onClick(View view) {

    }
}