package id.ac.binus.myapplication.models;

import android.content.Context;

import id.ac.binus.myapplication.database.DatabaseHelper;

public class User {
    private String userId;
    private String username;
    private String password;
    private DatabaseHelper databaseHelper;

    public User() {

    }

    public User(String userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public void getAllUsers(Context context){
        databaseHelper = new DatabaseHelper(context);

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
