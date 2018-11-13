package com.church.clg.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.church.clg.R;

import java.util.Objects;

public class ContactUsActivity extends AppCompatActivity {
    TextView webiste;
    TextView mobile,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        //show the title in Action Bar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Contact Us");
        //show the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        webiste=(TextView)findViewById(R.id.webiste);
        email=(TextView)findViewById(R.id.email1);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent email1 = new Intent(Intent.ACTION_SEND);
                email1.putExtra(Intent.EXTRA_EMAIL, new String[]{"arul@clg.church"}); // to mail id
                email1.putExtra(Intent.EXTRA_SUBJECT, "Prayer Request");


                //need this to prompts email client only
                email1.setType("message/rfc822");

                startActivity(Intent.createChooser(email1, "Choose an Email client :"));

            }
        });
        mobile=(TextView)findViewById(R.id.mobile);
        webiste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.clg.church/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + "+44 (0) 75544 46363"));//change the number

                startActivity(i);
            }
        });
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

    //initialize the back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
