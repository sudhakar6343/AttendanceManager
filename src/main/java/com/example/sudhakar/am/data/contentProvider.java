package com.example.sudhakar.am.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by sudhakar on 9/8/16.
 */
public class contentProvider extends ContentProvider {

    /*OPENDBHELPER FOR EASE OF ACCESS OF THE DATABASE*/
    AMDBHelper mDbhelper;

    /*Uri to get all the rows in the course entry */
    private final static int COURSE=100;
    private final static int FACULTY=103;
    private final static int COURSE_WITH_ID=101;
    private final static int FACULTY_WITH_ID=102;

    /*Uri matcher used by this content provider*/
    private static final UriMatcher mUriMatcher=buildUriMatcher();

    //Selection statement based on course name
    public static final String mCourseSelection =
            AMContract.CourseEntry.TABLE_NAME+
                    "." + AMContract.CourseEntry._ID + " = ? ";

    //selection statement based on faculty course id
    public static final String mFacultySelection =
            AMContract.FacultyEntry.TABLE_NAME+
                    "." + AMContract.FacultyEntry.COURSE_COLUMN + " = ? ";

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = AMContract.CONTENT_AUTHORITY;
        matcher.addURI(authority,AMContract.PATH_COURSE,COURSE);
        matcher.addURI(authority,AMContract.PATH_FACULTY,FACULTY);
        matcher.addURI(authority,AMContract.PATH_COURSE+"/*",COURSE_WITH_ID);
        matcher.addURI(authority,AMContract.PATH_FACULTY+"/*",FACULTY_WITH_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mDbhelper=new AMDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        int match=mUriMatcher.match(uri);
        Cursor returnCursor=null;
        String[] string=new String[1];
        switch(match) {
            case COURSE:
                returnCursor = mDbhelper.getReadableDatabase().query(
                        AMContract.CourseEntry.TABLE_NAME,
                        strings,
                        s,
                        strings1,
                        null,
                        null,
                        s1
                );

                break;
            case COURSE_WITH_ID:
                string[0]=Long.toString(AMContract.CourseEntry.getIdFromUri(uri));
                returnCursor=mDbhelper.getReadableDatabase().query(
                        AMContract.CourseEntry.TABLE_NAME,
                        strings,
                        mCourseSelection,
                        string,
                        null,
                        null,
                        s1
                );
                break;

            case FACULTY_WITH_ID:
                string[0]=Long.toString(AMContract.FacultyEntry.getIdFromUri(uri));
                returnCursor=mDbhelper.getReadableDatabase().query(
                        AMContract.FacultyEntry.TABLE_NAME,
                        strings,
                        mFacultySelection,
                        string,
                        null,
                        null,
                        s1
                );
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);


        }
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int match=mUriMatcher.match(uri);
        switch(match) {
            case COURSE:
                return AMContract.CourseEntry.CONTENT_TYPE;
            case FACULTY:
                return AMContract.FacultyEntry.CONTENT_TYPE;
            case COURSE_WITH_ID:
                return AMContract.CourseEntry.CONTENT_ITEM_TYPE;
            case FACULTY_WITH_ID:
                return AMContract.FacultyEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int match=mUriMatcher.match(uri);
        Uri returnUri=null;
        long _id;
        switch(match) {
            case COURSE:
                _id=mDbhelper.getWritableDatabase().insert(
                        AMContract.CourseEntry.TABLE_NAME,null,contentValues);
                returnUri=AMContract.CourseEntry.buildCourseUri(_id);
                break;

            case FACULTY:

                _id=mDbhelper.getWritableDatabase().insert(
                        AMContract.FacultyEntry.TABLE_NAME,null,contentValues);
                returnUri=AMContract.FacultyEntry.buildFacultyUri(_id);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        final int match = mUriMatcher.match(uri);
        int rowsUpdated;
        String[] string=new String[1];
        switch (match) {
            case COURSE:

                rowsUpdated = mDbhelper.getWritableDatabase().update(AMContract.CourseEntry.TABLE_NAME, contentValues, s,
                        strings);
                break;

            case COURSE_WITH_ID:
                string[0]=Long.toString(AMContract.CourseEntry.getIdFromUri(uri));
                rowsUpdated = mDbhelper.getWritableDatabase().update(AMContract.CourseEntry.TABLE_NAME, contentValues, mCourseSelection,
                        string);
                break;

            case FACULTY_WITH_ID:
                string[0]=Long.toString(AMContract.FacultyEntry.getIdFromUri(uri));
                rowsUpdated = mDbhelper.getWritableDatabase().update(AMContract.FacultyEntry.TABLE_NAME, contentValues, mFacultySelection,
                        string);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
