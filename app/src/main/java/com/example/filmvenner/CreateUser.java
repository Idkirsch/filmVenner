package com.example.filmvenner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateUser extends AppCompatActivity implements View.OnClickListener {

    User test_user = new User();
    EditText email, username, password, confirmPassword;
    Button create;

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


    }

    @Override
    public void onClick(View view) {
        if(view == create){


            if(FieldsHasValues()){
                System.out.println("yes, the fields have values");
                //TODO: this is where to save the user in the database
                test_user.setname(username.getText().toString());
                test_user.setEmail(email.getText().toString());
                test_user.setPassword(password.getText().toString());

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else{
                System.out.println("no, the fields contain nothing or wrong values:-(");
            }

        }
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