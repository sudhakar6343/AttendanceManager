package com.example.sudhakar.am;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by sudhakar on 9/8/16.
 */
public class AMAdapter extends CursorAdapter {

    public AMAdapter(Context context, Cursor c) {
        super(context, c);
    }


    public class placeHolder {
        private TextView Coursename;
        private TextView Attendance;
        private TextView Absent;
        private TextView Percentage;

        public placeHolder(View view) {
            Coursename=(TextView) view.findViewById(R.id.courseText);
            Attendance=(TextView) view.findViewById(R.id.presentText);
            Absent=(TextView) view.findViewById(R.id.absentText);
            Percentage=(TextView) view.findViewById(R.id.percentageText);
        }
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_item_view,viewGroup,false);
        placeHolder holder=new placeHolder(view);
        view.setTag(holder);
        return view;
    }

    public double getPercentage(String a,String b) {
        double d;
        double present=Double.parseDouble(a);
        double absent=Double.parseDouble(b);

        d=Math.round((present)/(present+absent)*100);
        return d;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String a,b;
        a=cursor.getString(MainActivity.COL_PRESENT);
        b=cursor.getString(MainActivity.COL_ABSENT);
        placeHolder holder=(placeHolder) view.getTag();
        holder.Coursename.setText(cursor.getString(MainActivity.COL_COURSE_NAME));
        holder.Attendance.setText(a);
        holder.Absent.setText(b);
        holder.Percentage.setText(""+getPercentage(a,b));

    }
}
