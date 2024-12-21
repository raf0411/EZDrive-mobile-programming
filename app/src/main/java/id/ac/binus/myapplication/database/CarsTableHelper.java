package id.ac.binus.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CarsTableHelper extends SQLiteOpenHelper {
    private static final String TABLE_TITLE = "Cars";
    private static final String COL_ID = "carId";
    private static final String COL_BRAND = "brand";
    private static final String COL_MODEL = "model";
    private static final String COL_PRICE_PER_DAY = "pricePerDay";
    private static final String COL_AVAILABILITY = "availability";
    private static final String COL_LOCATION = "location";
    private static final String COL_DESCRIPTION = "description";
    private static final String COL_SEATS = "seats";
    private static final String COL_TRANSMISSION = "transmission";
    private static final String COL_RULES = "rules";

    public CarsTableHelper(Context context) {
        super(context, "EZDRIVE_DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTblQuery = "CREATE TABLE " + TABLE_TITLE + " (" +
                COL_ID + " VARCHAR(10) PRIMARY KEY, " +
                COL_BRAND + " TEXT, " +
                COL_MODEL + " TEXT," +
                COL_PRICE_PER_DAY + " DECIMAL, " +
                COL_AVAILABILITY + " BOOLEAN, " +
                COL_LOCATION + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_SEATS + " INTEGER, " +
                COL_TRANSMISSION + " TEXT, " +
                COL_RULES + " TEXT)";

        db.execSQL(createTblQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TITLE);
        onCreate(db);
    }
}
