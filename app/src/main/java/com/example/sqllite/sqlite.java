package com.example.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class sqlite extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "POC.db";
    public static final String TABLE_NAME = "user";

    public static final String NAME = "name";
    public static final String EMAIL_ID = "email_id";
    public static final String PASSWORD = "password";
    public static final String PHONE_NO = "phone_no";
    public static final String COLLEGE = "college";
    public static final String CITY = "city";
    public static final String STATE = "state";
    public static final String COUNTRY = "country";

    public static final String TABLE_NAME2 = "speech";
    public static final String SPEECH_ID = "speech_id";
    public static final String SPEECH_TEXT = "speech_text";

    public sqlite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME2 + " (SPEECH_ID INTEGER PRIMARY KEY AUTOINCREMENT ,SPEECH_TEXT TEXT )");
        db.execSQL("create table " + TABLE_NAME + " (NAME TEXT ,EMAIL_ID TEXT ,PASSWORD TEXT ,PHONE_NO INTEGER ,COLLEGE TEXT ,CITY TEXT ,STATE TEXT ,COUNTRY TEXT )");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
    }

    boolean insertData(String name, String email, String password, String phone, String college, String city, String state, String country) {
        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(EMAIL_ID, email);
        contentValues.put(PASSWORD, password);
        contentValues.put(PHONE_NO, phone);
        contentValues.put(COLLEGE, college);
        contentValues.put(CITY, city);
        contentValues.put(STATE, state);
        contentValues.put(COUNTRY, country);


        long success1 = db1.insert(TABLE_NAME, null, contentValues);
        if (success1 == -1) {
            return false;
        } else {
            return true;
        }

    }

    boolean insert(String speech_text) {
        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();


        contentValues1.put(SPEECH_TEXT, speech_text);


        long success2 = db1.insert(TABLE_NAME2, null, contentValues1);
        if (success2 == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean checkUser(String email, String password) {
        String[] columns = {NAME};

        SQLiteDatabase db1 = getReadableDatabase();
        String selection = EMAIL_ID + "=?" + " and " + PASSWORD + "=?";
        String[] selection_args = {email, password};
        Cursor cur = db1.query(TABLE_NAME, columns, selection, selection_args, null, null, null);

        int count = cur.getCount();
        cur.close();
        db1.close();

        if (count > 0) {
            return true;

        } else {
            return false;
        }

    }

    public boolean checkEmail(String email) {
        String[] columns = {NAME};

        SQLiteDatabase db1 = getReadableDatabase();
        String selection = EMAIL_ID + "=?";
        String[] selection_args = {email};
        Cursor cur = db1.query(TABLE_NAME, columns, selection, selection_args, null, null, null);

        int count = cur.getCount();
        cur.close();
        db1.close();

        if (count > 0) {
            return true;

        } else {
            return false;
        }

    }

    public Cursor ViewData() {
        SQLiteDatabase db1 = this.getReadableDatabase();

        String strquery1 = "select * from speech where SPEECH_ID=1";
        Cursor cursor = db1.rawQuery(strquery1, null);
        return cursor;
    }

    public Cursor ViewName(String email) {

        String email_user = '"' + email + '"';
        SQLiteDatabase db1 = this.getReadableDatabase();
        String strquery = "select name from user where EMAIL_ID=" + email_user;
        Cursor cursor1 = db1.rawQuery(strquery, null);
        return cursor1;
    }

    public boolean UpdatePassword(String email, String passe) {

        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues2 = new ContentValues();
        String Email3=email;
        contentValues2.put(PASSWORD, passe);
        long success= db1.update(TABLE_NAME, contentValues2, "EMAIL_ID =?", new String[]{Email3});
        if(success==-1){
            return false;
        }
        else
            return true;
    }
}




