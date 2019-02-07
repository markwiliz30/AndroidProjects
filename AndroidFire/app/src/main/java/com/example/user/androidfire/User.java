package com.example.user.androidfire;

public class User {

    String userId;
    String userName;
    String userType;

    public User(){

    }

    public User(String userId, String userName, String userType) {
        this.userId = userId;
        this.userName = userName;
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserType() {
        return userType;
    }
}
