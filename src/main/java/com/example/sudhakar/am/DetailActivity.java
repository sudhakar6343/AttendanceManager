package com.example.sudhakar.am;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;

/**
 * Created by sudhakar on 10/8/16.
 */
public class DetailActivity extends AppCompatActivity {

    private int NUMBER_OF_FRAGMENTS=2;
    private String tabTitles[] = new String[] { "Course", "Faculty"};
    private SlidePageAdapter madapter;
    private ViewPager mPager;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        mPager=(ViewPager) findViewById(R.id.pager);

        //create an instance of fragment adapter
        madapter=new SlidePageAdapter(getSupportFragmentManager());
        //attach the adapter to the viewpager
        mPager.setAdapter(madapter);

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(mPager);
    }



    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class SlidePageAdapter extends FragmentStatePagerAdapter {

        public SlidePageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0) {
                return new Course_detail();
            }
            else if(position==1)
                return new FacultyFragment();
            else
                return null;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return NUMBER_OF_FRAGMENTS;
        }


    }
}
