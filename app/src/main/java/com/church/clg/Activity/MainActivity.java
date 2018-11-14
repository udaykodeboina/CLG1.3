package com.church.clg.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.church.clg.Adapter.CustomAdapter;
import com.church.clg.Model.ImageModel;
import com.church.clg.R;
import com.church.clg.Adapter.SlidingImage_Adapter;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{
    GridView simpleGrid;
    int logos[] = {R.drawable.aboutus_final, R.drawable.videos2, R.drawable.prayer_final, R.drawable.gallery1,
            R.drawable.calendar1, R.drawable.contact_3};
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;

    private int[] myImageList = new int[]{R.drawable.pastor1, R.drawable.slide,
            R.drawable.pastor3};
    public static final String TAG = "eyaldebug";


    public ArrayList<String> photoURLs = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleGrid = (GridView) findViewById(R.id.simpleGridView); // init GridView









        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), logos);
        simpleGrid.setAdapter(customAdapter);
        // implement setOnItemClickListener event on GridView
        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set an Intent to Another Activity
                if(position==0) {
                    Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
                    startActivity(intent); // start Intent
                }

                if(position==1) {
                    Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                    startActivity(intent); // start Intent
                }
                if(position==2) {
                    Intent intent = new Intent(MainActivity.this,PrayerRequest2.class);
                    startActivity(intent); // start Intent


                }

                if(position==3) {
                    Intent intent = new Intent(MainActivity.this,GalleryActivity2.class);
                    startActivity(intent); // start Intent
                }


                if(position==4) {
                    Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                    startActivity(intent); // start Intent
                }

                if(position==5) {
                    Intent intent = new Intent(MainActivity.this, ContactUsActivity.class);
                    startActivity(intent); // start Intent
                }
                if(position==6) {
                    Intent intent = new Intent(MainActivity.this,MeetingsActivity.class);
                    startActivity(intent); // start Intent
                }


            }
        });



        imageModelArrayList = new ArrayList();
        imageModelArrayList = populateList();
        init();




    }

    private ArrayList<ImageModel> populateList(){

        ArrayList<ImageModel> list = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }

        return list;
    }

    private void init() {

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(MainActivity.this,imageModelArrayList));

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES =imageModelArrayList.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }




}
