package com.callparent.areyoubusy;


import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.callparent.areyoubusy.database.DBAdapter;
import com.callparent.areyoubusy.fragment.ProfileFragment;
import com.callparent.areyoubusy.fragment.RankFragment;
import com.callparent.areyoubusy.fragment.SettingFragment;
import com.callparent.areyoubusy.fragment.StatsFragment;


public class MainActivity extends AppCompatActivity {

    public static final int FRAGMENT_ONE = 0;
    public static final int FRAGMENT_TWO = 1;
    public static final int FRAGMENT_THREE = 2;
    public static final int FRAGMENT_FOUR = 3;


    Toolbar toolbar;
    ImageView imageView_profile;
    ImageView imageView_rank;
    ImageView imageView_stats;
    ImageView imageView_setting;



    public static DBAdapter db;

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new DBAdapter(this);
        db.open();

        toolbar = (Toolbar) findViewById(R.id.mainActivity_toolbar);
        imageView_profile = (ImageView) findViewById(R.id.mainActivity_profile);
        imageView_rank = (ImageView) findViewById(R.id.mainActivity_rank);
        imageView_stats = (ImageView) findViewById(R.id.mainActivity_stats);
        imageView_setting = (ImageView) findViewById(R.id.mainActivity_setting);
        mViewPager = (ViewPager) findViewById(R.id.mainActivity_fragment);

        setClickListener();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch(position) {
                    case FRAGMENT_ONE :
                        imageView_profile.setBackgroundResource(R.drawable.main_1_on);
                        imageView_rank.setBackgroundResource(R.drawable.main_2_off);
                        imageView_stats.setBackgroundResource(R.drawable.main_3_off);
                        imageView_setting.setBackgroundResource(R.drawable.main_4_off);
                        break;
                    case FRAGMENT_TWO :
                        imageView_profile.setBackgroundResource(R.drawable.main_1_off);
                        imageView_rank.setBackgroundResource(R.drawable.main_2_on);
                        imageView_stats.setBackgroundResource(R.drawable.main_3_off);
                        imageView_setting.setBackgroundResource(R.drawable.main_4_off);
                        break;
                    case FRAGMENT_THREE :
                        imageView_profile.setBackgroundResource(R.drawable.main_1_off);
                        imageView_rank.setBackgroundResource(R.drawable.main_2_off);
                        imageView_stats.setBackgroundResource(R.drawable.main_3_on);
                        imageView_setting.setBackgroundResource(R.drawable.main_4_off);
                        break;
                    case FRAGMENT_FOUR :
                        imageView_profile.setBackgroundResource(R.drawable.main_1_off);
                        imageView_rank.setBackgroundResource(R.drawable.main_2_off);
                        imageView_stats.setBackgroundResource(R.drawable.main_3_off);
                        imageView_setting.setBackgroundResource(R.drawable.main_4_on);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setSupportActionBar(toolbar);

    }
    ////통화기록 가져오기

    void setClickListener() {
        imageView_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(FRAGMENT_ONE);
            }
        });

        imageView_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(FRAGMENT_TWO);
            }
        });

        imageView_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(FRAGMENT_THREE);
            }
        });

        imageView_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(FRAGMENT_FOUR);
            }
        });
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;

            switch(position) {
                case FRAGMENT_ONE :
                    fragment = new ProfileFragment();
                    break;
                case FRAGMENT_TWO :
                    fragment = new RankFragment();
                    break;
                case FRAGMENT_THREE :
                    fragment = new StatsFragment();
                    break;
                case FRAGMENT_FOUR :
                    fragment = new SettingFragment();
                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
            return rootView;
        }
    }

}
