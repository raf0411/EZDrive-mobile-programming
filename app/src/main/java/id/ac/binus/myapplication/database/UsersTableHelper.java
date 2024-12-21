package id.ac.binus.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UsersTableHelper extends SQLiteOpenHelper {
    private static final String TABLE_TITLE = "Users";
    private static final String COL_ID = "userId";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";

    public UsersTableHelper(Context context) {
        super(context, "EZDRIVE_DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTblQuery = "CREATE TABLE " + TABLE_TITLE + " (" +
                COL_ID + " VARCHAR(10) PRIMARY KEY, " +
                COL_USERNAME + " TEXT, " +
                COL_PASSWORD + " TEXT)";
        db.execSQL(createTblQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TITLE);
        onCreate(db);
    }
}
