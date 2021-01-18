package com.example.filmvenner.DAO;

public class FilmList {
    private String image;
    private String title;
    private String ID;

    public FilmList(String image, String title, String ID) {
        this.image = image;
        this.title = title;
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}