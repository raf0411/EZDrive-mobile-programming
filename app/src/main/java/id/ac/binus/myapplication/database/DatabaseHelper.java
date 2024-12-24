package id.ac.binus.myapplication.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import id.ac.binus.myapplication.models.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name and Version
    private static final String DATABASE_NAME = "EZDriveDB";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_USERS = "Users";
    public static final String TABLE_CARS = "Cars";
    public static final String TABLE_BOOKINGS = "Bookings";

    // Table Creation Statements
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    "userId TEXT PRIMARY KEY, " +
                    "username TEXT NOT NULL, " +
                    "email TEXT UNIQUE NOT NULL, " +
                    "password TEXT UNIQUE NOT NULL)";

    private static final String CREATE_TABLE_CARS =
            "CREATE TABLE " + TABLE_CARS + " (" +
                    "carId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "model TEXT NOT NULL, " +
                    "brand TEXT NOT NULL, " +
                    "pricePerDay DECIMAL NOT NULL, " +
                    "availability BOOLEAN NOT NULL)";

    private static final String CREATE_TABLE_BOOKINGS =
            "CREATE TABLE " + TABLE_BOOKINGS + " (" +
                    "bookingId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "userId INTEGER NOT NULL, " +
                    "carId INTEGER NOT NULL, " +
                    "startDate TEXT NOT NULL, " +
                    "endDate TEXT NOT NULL, " +
                    "totalPrice DECIMAL NOT NULL, " +
                    "FOREIGN KEY(userId) REFERENCES " + TABLE_USERS + "(userId), " +
                    "FOREIGN KEY(carId) REFERENCES " + TABLE_CARS + "(carId));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public long addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("userId", user.getUserId());
        cv.put("username", user.getUsername());
        cv.put("email", user.getEmail());
        cv.put("password", user.getPassword());

        long result = db.insert(TABLE_USERS, null, cv);
        db.close();

        return result;
    }

    public ArrayList<User> getAllUsers(){
        ArrayList<User> users = new ArrayList<User>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Users", null);

        if(cursor.moveToFirst()){
            do {
                String userId = cursor.getString(cursor.getColumnIndexOrThrow("userId"));
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));

                users.add(new User(userId, username, email, password));
            } while (cursor.moveToNext());
        }

        return users;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_CARS);
        db.execSQL(CREATE_TABLE_BOOKINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);

        onCreate(db);
    }
}
