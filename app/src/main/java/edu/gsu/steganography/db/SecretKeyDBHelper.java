package edu.gsu.steganography.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import edu.gsu.steganography.model.SecretKey;

public class SecretKeyDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "steganography.db";

    private static final String ID_COLUMN_NAME = "ID";
    private static final String NAME_COLUMN_NAME = "NAME";
    private static final String KEY_COLUMN_NAME = "KEY_VALUE";
    private static final String TABLE_NAME = "SECRET_KEY";


    public SecretKeyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + ID_COLUMN_NAME + " VARCHAR NOT NULL PRIMARY KEY," + NAME_COLUMN_NAME + " VARCHAR, " + KEY_COLUMN_NAME + " VARCHAR);");
        //db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public SecretKey getKey(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where "+ID_COLUMN_NAME+"= ?", new String[]{id});
        res.moveToFirst();
        while (!res.isAfterLast()) {
            String name = res.getString(res.getColumnIndex(NAME_COLUMN_NAME));
            String key = res.getString(res.getColumnIndex(KEY_COLUMN_NAME));
            return new SecretKey(id, name, key);
        }
        return null;
    }

    public boolean updateKey(SecretKey key) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_COLUMN_NAME, key.getId());
        contentValues.put(NAME_COLUMN_NAME, key.getName());
        contentValues.put(KEY_COLUMN_NAME, key.getKey());
        db.update(TABLE_NAME, contentValues, ID_COLUMN_NAME +" = ? ", new String[]{key.getId()});
        return true;
    }

    public boolean insertKey(SecretKey key) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_COLUMN_NAME, key.getId());
        contentValues.put(NAME_COLUMN_NAME, key.getName());
        contentValues.put(KEY_COLUMN_NAME, key.getKey());
        long insert = db.insert(TABLE_NAME, "", contentValues);
        return true;
    }

    public boolean deleteKey(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID_COLUMN_NAME+" = ? ", new String[]{id});
        return true;
    }

    public List<SecretKey> getAllKeys() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<SecretKey> result = new ArrayList<>();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            String id = res.getString(res.getColumnIndex(ID_COLUMN_NAME));
            String name = res.getString(res.getColumnIndex(NAME_COLUMN_NAME));
            String key = res.getString(res.getColumnIndex(KEY_COLUMN_NAME));
            result.add(new SecretKey(id, name, key));
            res.moveToNext();
        }
        return result;
    }
}
