package com.example.user.firebasepicasso2;

public class Model {

    String title, image, description; //these names must watch with the names in firebase

    //constructor
    public Model(){}

    //getter and setters press Alt+Insert

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
