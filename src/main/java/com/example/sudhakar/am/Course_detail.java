package com.example.sudhakar.am;


import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sudhakar.am.data.AMContract;
import com.example.sudhakar.am.data.contentProvider;

import java.net.URI;

/**
 * Created by sudhakar on 10/8/16.
 */
public class Course_detail extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    /*Uri that we receive from the main activity*/
    private Uri mUri;

    private View mview;

    private Long mId;
    private TextView mName;
    private TextView mCode;
    private TextView mPresent;
    private TextView mAbsent;
    private Button button;

    private final int course_loader=2;

    private static final String[] COURSE_COLUMNS = {

            AMContract.CourseEntry.TABLE_NAME + "." +AMContract.CourseEntry._ID,
            AMContract.CourseEntry.COURSE_CODE,
            AMContract.CourseEntry.COURSE_NAME,
            AMContract.CourseEntry.ABSENT_COLUMN,
            AMContract.CourseEntry.PRESENT_COLUMN
    };

    static final int COL_COURSE_ID = 0;
    static final int COL_COURSE_CODE=1;
    static final int COL_COURSE_NAME = 2;
    static final int COL_ABSENT = 3;
    static final int COL_PRESENT = 4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview=inflater.inflate(R.layout.course_display,container,false);
        mUri=getActivity().getIntent().getData();
        button=(Button)mview.findViewById(R.id.update_button);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                String selection=contentProvider.mCourseSelection;

                ContentValues values=new ContentValues();
                values.put(AMContract.CourseEntry.ABSENT_COLUMN,mAbsent.getText().toString());
                values.put(AMContract.CourseEntry.COURSE_NAME,mName.getText().toString());
                values.put(AMContract.CourseEntry.PRESENT_COLUMN,mPresent.getText().toString());
                values.put(AMContract.CourseEntry.COURSE_CODE,mCode.getText().toString());

                int row_id=getActivity().getContentResolver().update(mUri,values,null,null);
                Toast.makeText(getContext(),"Updated",Toast.LENGTH_SHORT).show();
            }
        });
        //initiating the loader;
        getActivity().getSupportLoaderManager().initLoader(course_loader,null,this);
        return mview;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUri=(Uri)getActivity().getIntent().getData();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(mUri!=null) {

            return new CursorLoader(
                    getActivity(),
                    mUri,
                    COURSE_COLUMNS,
                    null,
                    null,
                    null
            );
        }
        else
            return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
      data.moveToFirst();
       mAbsent=((TextView)mview.findViewById(R.id.absent_count));
        mAbsent.setText(data.getString(COL_ABSENT));

        mPresent=((TextView)mview.findViewById(R.id.present_count));
        mPresent.setText(data.getString(COL_PRESENT));

        mName=((TextView)mview.findViewById(R.id.course_name));
        mName.setText(data.getString(COL_COURSE_NAME));

        mCode=((TextView)mview.findViewById(R.id.codeText));
        mCode.setText(data.getString(COL_COURSE_CODE));

        mId=data.getLong(COL_COURSE_ID);

        return;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        return;
    }
}
