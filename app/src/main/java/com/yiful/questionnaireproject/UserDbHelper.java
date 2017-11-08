package com.yiful.questionnaireproject;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Yifu on 11/5/2017.
 */

public class UserDbHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "my_db";
    static final String TABLE_NAME = "user_password_table";
    static final String ID = "id"; //column 1: id
    static final String USERNAME ="username"; //col 2: username
    static final String PASSWORD = "password"; //col 3: password

    public UserDbHelper(Context context){
        super(context,DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String my_query = "create table " + TABLE_NAME + " (" + ID +" INT, " + USERNAME + " VARCHAR, " + PASSWORD + " vARCHAR)";
        sqLiteDatabase.execSQL(my_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


}
