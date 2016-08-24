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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sudhakar.am.data.AMContract;
import com.example.sudhakar.am.data.contentProvider;

import org.w3c.dom.Text;

/**
 * Created by sudhakar on 10/8/16.
 */
public class FacultyFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private View mView;

    private TextView mName;
    private TextView mEmail;
    private TextView mMobile;
    private TextView mRoom;
    private Long mId;


    private Uri mUri;

    private static final String[] FACULTY_COLUMNS = {

            AMContract.FacultyEntry.TABLE_NAME + "." +AMContract.FacultyEntry._ID,
            AMContract.FacultyEntry.NAME_COLUMN,
            AMContract.FacultyEntry.COURSE_COLUMN,
            AMContract.FacultyEntry.MOBILE_COLUMN,
            AMContract.FacultyEntry.ROOM_COLUMN,
            AMContract.FacultyEntry.EMAIL_COLUMN
    };

    static final int COL_FACULTY_ID = 0;
    static final int COL_COURSE_CODE=2;
    static final int COL_FACULTY_NAME = 1;
    static final int COL_MOBILE = 3;
    static final int COL_ROOM = 4;
    static final int COL_EMAIL=5;

    static final int faculty_loader=6;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUri=getActivity().getIntent().getData();
        mId=AMContract.CourseEntry.getIdFromUri(mUri);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.faculty_layout,container,false);

        ((Button)mView.findViewById(R.id.update_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues values=new ContentValues();
                values.put(AMContract.FacultyEntry.NAME_COLUMN,mName.getText().toString());
                values.put(AMContract.FacultyEntry.MOBILE_COLUMN,mMobile.getText().toString());
                values.put(AMContract.FacultyEntry.ROOM_COLUMN,mRoom.getText().toString());
                values.put(AMContract.FacultyEntry.EMAIL_COLUMN,mEmail.getText().toString());

                int row_id=getActivity().getContentResolver().update( AMContract.FacultyEntry.buildFacultyUri(mId),values,null,null);
                Toast.makeText(getContext(),"Updated",Toast.LENGTH_SHORT).show();
            }
        });

        //Create a loader instance for getting the faculty information
        getActivity().getSupportLoaderManager().initLoader(faculty_loader,null,this);

        return mView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                AMContract.FacultyEntry.buildFacultyUri(mId),
                FACULTY_COLUMNS,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
        mName=((TextView)mView.findViewById(R.id.faculty_name));
        mName.setText(data.getString(COL_FACULTY_NAME));

        mMobile=((TextView)mView.findViewById(R.id.number));
        mMobile.setText(data.getString(COL_MOBILE));

        mEmail=((TextView)mView.findViewById(R.id.email));
        mName.setText(data.getString(COL_EMAIL));

        mRoom=((TextView)mView.findViewById(R.id.Room));
        mRoom.setText(data.getString(COL_ROOM));

        mId=data.getLong(COL_COURSE_CODE);

        return;
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        return;
    }
}
