package id.ac.binus.myapplication.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import id.ac.binus.myapplication.R;
import id.ac.binus.myapplication.database.DatabaseHelper;
import id.ac.binus.myapplication.utils.RandomIDGenerator;

public class User {
    private String userId;
    private byte[] userImg;
    private String username;
    private String password;
    private String phoneNumber;
    private String city;
    private String country;
    private String email;
    private String token;
    private DatabaseHelper db;

    public User() {

    }

    public User(String userId, byte[] userImg, String username, String password, String phoneNumber, String city, String country, String email, String token) {
        this.userId = userId;
        this.userImg = userImg;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.country = country;
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
                System.out.println("user password: " + userPassword);
                String userEmail = users.get(i).getEmail();
                String userPhoneNumber = users.get(i).getPhoneNumber();
                byte[] userImg = users.get(i).getUserImg();
                String userCountry = users.get(i).getCountry();
                String userCity = users.get(i).getCity();
                String userToken = users.get(i).getToken();

                return new User(userID, userImg, userName, userPassword, userPhoneNumber, userCity, userCountry, userEmail, userToken);
            }
        }

        return null;
    }

    public String updateProfile(Context context, String userId, byte[] userImg,
                                String username, String email, String phoneNumber,
                                String city, String country){
        db = new DatabaseHelper(context);

        long result = db.editProfile(userId, userImg, username, email, phoneNumber, city, country);

        if(result != -1){
            return "Profile updated successfully!";
        } else{
            return "Failed to update profile!";
        }
    }

    public String register(Context context, String username, String email, String password){
        db = new DatabaseHelper(context);
        String userID = RandomIDGenerator.generateRandomID();
        SharedPreferences prefs = context.getSharedPreferences("EZDriveApp", Context.MODE_PRIVATE);
        String deviceToken = prefs.getString("token", "NONE");
        byte[] userImg = convertDrawableToByteArray(R.drawable.profile_img, context);

        User user = new User(userID, userImg, username, password, "-", "-", "-", email, deviceToken);
        long result = db.addUser(user);

        if(result != -1){
            return "User registered successfully!";
        } else{
            return "Failed to register user!";
        }
    }

    private byte[] convertDrawableToByteArray(int drawableId, Context context) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
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

    public User getUserByUserId(Context context, String userId){
        db = new DatabaseHelper(context);

        ArrayList<User> users = db.getAllUsers();

        for (int i = 0; i < users.size(); i++){
            Log.e("User: ", users.get(i).getUserId());

            if(users.get(i).getUserId().equals(userId)){
                String userName = users.get(i).getUsername();
                String userID = users.get(i).getUserId();
                String userPassword = users.get(i).getPassword();
                String userEmail = users.get(i).getEmail();
                String userPhoneNumber = users.get(i).getPhoneNumber();
                byte[] userImg = users.get(i).getUserImg();
                String userCountry = users.get(i).getCountry();
                String userCity = users.get(i).getCity();
                String userToken = users.get(i).getToken();

                return new User(userID, userImg, userName, userPassword, userPhoneNumber, userCity, userCountry, userEmail, userToken);
            }
        }

        return null;
    }

    public String getUserId() {
        return userId;
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


    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public byte[] getUserImg() {
        return userImg;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
