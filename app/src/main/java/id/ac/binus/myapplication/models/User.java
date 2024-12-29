package id.ac.binus.myapplication.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import id.ac.binus.myapplication.database.DatabaseHelper;
import id.ac.binus.myapplication.utils.RandomIDGenerator;
import id.ac.binus.myapplication.views.LoginView;

public class User {
    private String userId;
    private String username;
    private String password;
    private String email;
    private String token;
    private DatabaseHelper db;

    public User() {

    }

    public User(String userId, String username, String password, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.token = token;
    }

    public User(String userId, String username, String password, String email, String token) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.token = token;
    }

    public User getUserByUsername(Context context, String username){
        db = new DatabaseHelper(context);

        ArrayList<User> users = db.getAllUsers();

        for (int i = 0; i < users.size(); i++){
            Log.e("User: ", users.get(i).getUsername());

            if(users.get(i).getUsername().equals(username)){
                String userName = users.get(i).getUsername();
                String userID = users.get(i).getUserId();
                String userPassword = users.get(i).getPassword();
                String userEmail = users.get(i).getEmail();

                return new User(userID, userName, userPassword, userEmail);
            }
        }

        return null;
    }

    public String register(Context context, String username, String email, String password){
        db = new DatabaseHelper(context);
        String userID = RandomIDGenerator.generateRandomID();
        SharedPreferences prefs = context.getSharedPreferences("EZDriveApp", Context.MODE_PRIVATE);
        String deviceToken = prefs.getString("token", "NONE");
        User user = new User(userID, username, email, password, deviceToken);
        long result = db.addUser(user);

        if(result != -1){
            return "User registered successfully!";
        } else{
            return "Failed to register user!";
        }
    }

    public String login(Context context, String username, String password){
        User user = getUserByUsername(context, username);

        if (user == null){
            return "User does not exists!";
        } else if(!user.getPassword().equals(password)){
            return "Password is incorrect!";
        } else {
            Toast.makeText(context, "Welcome " + user.getUsername() + "!", Toast.LENGTH_SHORT).show();
            return "Login Success!";
        }
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
