package com.example.sudhakar.am;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sudhakar.am.data.AMContract;

/**
 * Created by sudhakar on 9/8/16.
 */
public class CourseRegister extends AppCompatActivity {

    private String mcourse_name;
    private String mabsent_count;
    private String mpresent_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_entry);
        ((Button) findViewById(R.id.save_button)).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcourse_name=((TextView)findViewById(R.id.course_name)).getText().toString();
                mabsent_count=((TextView)findViewById(R.id.absent_count)).getText().toString();
                mpresent_count=((TextView)findViewById(R.id.present_count)).getText().toString();

                if(mcourse_name==null) {
                    Toast.makeText(getBaseContext(),"Course name should not be empty",Toast.LENGTH_LONG).show();
                }
                else if(mabsent_count==null) {
                    Toast.makeText(getBaseContext(),"Absent count should not be empty",Toast.LENGTH_LONG).show();
                }
                else if(mpresent_count==null) {
                    Toast.makeText(getBaseContext(),"Present count should not be empty",Toast.LENGTH_LONG).show();
                }
                else {
                    try {
                        Integer.parseInt(mabsent_count);
                    }
                    catch(NumberFormatException e) {
                        Toast.makeText(getBaseContext(),"Count should  be a integer",Toast.LENGTH_LONG).show();
                        return;
                    }

                    try {
                        Integer.parseInt(mpresent_count);
                    }
                    catch(NumberFormatException e) {
                        Toast.makeText(getBaseContext(),"Count should  be a integer",Toast.LENGTH_LONG).show();
                        return;
                    }

                    ContentValues values=new ContentValues();
                    values.put(AMContract.CourseEntry.ABSENT_COLUMN,mabsent_count);
                    values.put(AMContract.CourseEntry.COURSE_NAME,mcourse_name);
                    values.put(AMContract.CourseEntry.PRESENT_COLUMN,mpresent_count);
                    Uri uri=getBaseContext().getContentResolver().insert(AMContract.CourseEntry.CONTENT_URI,values);

                    values.clear();
                    values.put(AMContract.FacultyEntry.COURSE_COLUMN,AMContract.FacultyEntry.getIdFromUri(uri));
                    uri=getBaseContext().getContentResolver().insert(AMContract.FacultyEntry.CONTENT_URI,values);
                    Log.v("lksjdfsdfsdf        sdf",Long.toString(AMContract.FacultyEntry.getIdFromUri(uri)));
                    Intent intent=new Intent(getBaseContext(),MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        return;
    }

}
