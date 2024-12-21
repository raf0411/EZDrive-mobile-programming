package id.ac.binus.myapplication.controllers;

import android.content.Context;

import id.ac.binus.myapplication.models.User;

public class UserController {
    User user = new User();

    public UserController() {
    }

    public String validateUserLogin(String username, String password, Context context){
        if(username.isEmpty()){
            return "Username cannot be empty!";
        } else if(password.isEmpty()){
            return "Password cannot be empty!";
        } else {
            user.getAllUsers(context);
        }

        return null;
    }
}
