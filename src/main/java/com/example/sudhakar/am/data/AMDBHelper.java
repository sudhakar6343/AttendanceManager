package com.example.sudhakar.am.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sudhakar on 9/8/16.
 */
public class AMDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 8;

    static final String DATABASE_NAME = "am.db";


    public AMDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL_CREATE_COURSE_TABLE="CREATE TABLE " + AMContract.CourseEntry.TABLE_NAME +" (" +
                        AMContract.CourseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        AMContract.CourseEntry.COURSE_CODE + " TEXT," +
                        AMContract.CourseEntry.COURSE_NAME + " TEXT NOT NULL," +
                        AMContract.CourseEntry.PRESENT_COLUMN + " TEXT NOT NULL," +
                        AMContract.CourseEntry.ABSENT_COLUMN + " TEXT NOT NULL" + ")";


        String SQL_CREATE_FACULTY_TABLE="CREATE TABLE " + AMContract.FacultyEntry.TABLE_NAME +" (" +
                AMContract.FacultyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                AMContract.FacultyEntry.COURSE_COLUMN + " INTEGER NOT NULL," +
                AMContract.FacultyEntry.NAME_COLUMN + " TEXT," +
                AMContract.FacultyEntry.MOBILE_COLUMN + " TEXT," +
                AMContract.FacultyEntry.EMAIL_COLUMN + " TEXT," +
                AMContract.FacultyEntry.ROOM_COLUMN + " TEXT" + ")";


        sqLiteDatabase.execSQL(SQL_CREATE_COURSE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_FACULTY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AMContract.CourseEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
