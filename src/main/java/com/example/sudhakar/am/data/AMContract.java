package com.example.sudhakar.am.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by sudhakar on 9/8/16.
 */
public class AMContract{

    /*Authority of the content */
    public static final String CONTENT_AUTHORITY = "com.example.sudhakar.am";

    /*Base Content Uri*/
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /*Path for the course table */
    public static final String PATH_COURSE ="course";

    public static final String PATH_FACULTY ="faculty";

    /*Inner table that provides the table content for the course table*/
    public static final class CourseEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COURSE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSE;

        //Table name
        public static final String TABLE_NAME="course";

        //Course code
        public static final String COURSE_CODE="code";

        //cOURSE NAME
        public static final String COURSE_NAME="name";

        //Attendance column name
        public static final String PRESENT_COLUMN="present";

        //Absent column
        public static final String ABSENT_COLUMN="absent";

        public static Uri buildCourseUri(long _id) {
            return ContentUris.withAppendedId(CONTENT_URI,_id);
        }

        public static long getIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }

    }

    /*Inner table for the faculty table*/
    public static final class FacultyEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FACULTY).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FACULTY;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FACULTY;

        /*Path for the course table */
        public static final String PATH_COURSE ="faculty";

        //Table name
        public static final String TABLE_NAME="faculty";

        //Course id
        public static final String COURSE_COLUMN="course";

        //Course code
        public static final String MOBILE_COLUMN="mobile";

        //cOURSE NAME
        public static final String NAME_COLUMN="name";

        //Attendance column name
        public static final String ROOM_COLUMN="room";

        //Absent column
        public static final String EMAIL_COLUMN="email";

        public static Uri buildFacultyUri(long _id) {
            return ContentUris.withAppendedId(CONTENT_URI,_id);
        }

        public static long getIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }
    }
}
