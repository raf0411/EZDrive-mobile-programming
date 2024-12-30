package id.ac.binus.myapplication.controllers;

import android.content.Context;

import id.ac.binus.myapplication.models.User;

public class UserController {
    User user = new User();

    public UserController() {
    }

    public User getUserByUsername(Context context, String username){
        return user.getUserByUsername(context, username);
    }

    public String validateUserLogin(Context context, String username, String password){
        if(username.isEmpty()){
            return "Username cannot be empty!";
        } else if(password.isEmpty()){
            return "Password cannot be empty!";
        } else {
            return user.login(context, username, password);
        }
    }

    public String validateUserRegister(Context context, String username, String email, String password, String confirmPassword){
        String message;

        if(username.isEmpty()){
            message = "Username can't be empty!";
            return message;
        } else if(username.length() < 3){
            message = "Username must at least be 3 characters!";
            return message;
        } else if(email.isEmpty()){
            message = "Email can't be empty!";
            return message;
        } else if(!email.contains("@")){
            message = "Email must be a valid one!";
            return message;
        } else if(!email.endsWith(".com")){
            message = "Email must be a valid one!";
            return message;
        } else if (password.isEmpty()){
            message = "Password can't be empty!";
            return message;
        } else if (password.length() < 5){
            message = "Password must at least be 5 characters!";
            return message;
        } else if (!confirmPassword.equals(password)) {
            message = "Confirm password must match password!";
            return message;
        } else {
            return user.register(context, username, email, password);
        }
    }
}
