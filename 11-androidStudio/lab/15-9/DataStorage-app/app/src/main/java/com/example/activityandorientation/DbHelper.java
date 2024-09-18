package com.example.activityandorientation;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MsgData.db";
    private static final String TABLE_NAME    = "user";
    private static final String COLUMN_PHONE  = "phone";
    private static final String COLUMN_MESSAGE= "message";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_PHONE + " TEXT,"
                + COLUMN_MESSAGE + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String phone, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_MESSAGE, message);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }

    public Cursor getPhoneByMessage(String message) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME,
                new String[]{COLUMN_PHONE},  // Column to return (phone)
                COLUMN_MESSAGE + "=?",        // WHERE clause
                new String[]{message},        // WHERE arguments (message)
                null, null, null);
    }
}
