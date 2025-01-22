package com.example.test3.Services.DataBaseServices;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    final String LOG_TAG = "myLogs";
    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table MyOrder ("
                + "id integer primary key autoincrement,"+"productid integer,"
                +"type integer"+ ");");
        db.execSQL("create table User ("+"id integer primary key autoincrement, "+"Name text, "+"Level integer, "+"userid integer"+")");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }
}
