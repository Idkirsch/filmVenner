package com.example.filmvenner;

public class User {
    private String name;
    private String password;
    private String email;

    public User() {
        name = "test123";
        password ="1";
    }


    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }


    public String getname() { return name; }

    public void setname(String name) { this.name = name; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


