package com.church.clg.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.church.clg.Adapter.CustomAdapter;
import com.church.clg.Adapter.GalleryAdapter;
import com.church.clg.R;

import java.util.Objects;

public class GalleryActivity2 extends AppCompatActivity {

    ListView simpleList;

    int flags[] = {R.drawable.pastor1, R.drawable.pastor2,R.drawable.pastor3, R.drawable.pastor4,R.drawable.pastor6, R.drawable.pastor7,R.drawable.pastor8, R.drawable.pastor9,R.drawable.pastor11, R.drawable.pastor10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery2);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Gallery");
        //show the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        simpleList = (ListView) findViewById(R.id.simpleListView);
        GalleryAdapter customAdapter = new GalleryAdapter(getApplicationContext(), flags);
        simpleList.setAdapter(customAdapter);

    }
    //action for back pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
