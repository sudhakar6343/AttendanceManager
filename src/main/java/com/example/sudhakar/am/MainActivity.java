package com.example.sudhakar.am;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sudhakar.am.data.AMContract;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private AMAdapter madapter;
    private ListView mlistview;
    private final int loader_constant=1;

    private static final String[] COURSE_COLUMNS = {

            AMContract.CourseEntry.TABLE_NAME + "." +AMContract.CourseEntry._ID,
            AMContract.CourseEntry.COURSE_NAME,
            AMContract.CourseEntry.COURSE_CODE,
            AMContract.CourseEntry.ABSENT_COLUMN,
            AMContract.CourseEntry.PRESENT_COLUMN
    };

    static final int COL_COURSE_ID = 0;
    static final int COL_COURSE_CODE=2;
    static final int COL_COURSE_NAME = 1;
    static final int COL_ABSENT = 3;
    static final int COL_PRESENT = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mlistview=(ListView) findViewById(R.id.list_view);

        //adding onlistviewitem click listener for the listview
        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getBaseContext(),DetailActivity.class);

                Uri mUri=AMContract.CourseEntry.buildCourseUri((long)(i+1));
                intent.setData(mUri);
                startActivity(intent);
            }
        });

        //adding onclick listener to the fab
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),CourseRegister.class);
                startActivity(intent);
            }
        });

        //Initialisting the adapter for the list view;
        madapter=new AMAdapter(getBaseContext(),null);
        mlistview.setAdapter(madapter);

        //initiating the loader;
        getSupportLoaderManager().initLoader(loader_constant,null,this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getBaseContext(),
                AMContract.CourseEntry.CONTENT_URI,
                COURSE_COLUMNS,
                null,
                null,
                null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        madapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        madapter.swapCursor(null);
    }
}
