package com.tayara.abdulrahman.telephoneguide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "database.db";
    private final String TABLE_NAME = "TelephoneGuide";
    private static DataBase dataBaseSingleton = null;
    private static Context mContext;
    private DataBase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static DataBase getInstance() {
        if (dataBaseSingleton == null) {
            dataBaseSingleton = new DataBase(mContext);
        }
        return dataBaseSingleton;
    }
    public static void setContext(Context context) {
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Table In DataBase
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "FirstName TEXT ,LastName TEXT ,TelephoneNumber TEXT,MobileNumber TEXT ," +
                "BirthDay TEXT , Address TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Check If Table Is Already Created
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertNewItem(Contact newItem) {
        SQLiteDatabase sql = this.getWritableDatabase();
        ContentValues contentValues = insertData(newItem);
        long result = sql.insert(TABLE_NAME, null, contentValues);
        return (result != -1);

    }

    public Cursor getAllItems() {
        SQLiteDatabase sql = this.getWritableDatabase();
        Cursor cursor = sql.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }

    public Cursor search(String tag) {
        String newTag = "'" + tag + "'";
        SQLiteDatabase sql = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                "FirstName = " + newTag + " OR " +
                "LastName = " + newTag + " OR " +
                "MobileNumber = " + newTag + " OR " +
                "TelephoneNumber = " + newTag;
        Cursor cursor = sql.rawQuery(query, null);
        return cursor;
    }

    public boolean deleteItem(int id) {
        SQLiteDatabase sql = this.getWritableDatabase();
        int result = sql.delete(TABLE_NAME, ("id = " + id), null);
        return (result > 0);
    }

    public boolean updateItem(Contact contact) {
        SQLiteDatabase sql = this.getWritableDatabase();
        ContentValues contentValues = insertData(contact);
        int result = sql.update(TABLE_NAME, contentValues, ("id = " + contact.getId()), null);
        return (result > 0);
    }

    private ContentValues insertData(Contact item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("FirstName", item.getFirstName());
        contentValues.put("LastName", item.getLastName());
        contentValues.put("TelephoneNumber", item.getTelephoneNumber());
        contentValues.put("MobileNumber", item.getMobileNumber());
        contentValues.put("BirthDay", item.getBirthDay());
        contentValues.put("Address", item.getAddress());
        return contentValues;
    }
}
