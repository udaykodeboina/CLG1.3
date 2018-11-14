package com.church.clg.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.church.clg.R;

import com.church.clg.SendMail;

import java.util.Objects;

public class PrayerRequestActivity extends AppCompatActivity {
    Button send;
    EditText name,mobile,email,request;

    String name1,mobile1,email1,request1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_request2);
        //show the title in Action Bar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Prayer Request");
        //show the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        send=(Button)findViewById(R.id.send);
        name=(EditText)findViewById(R.id.name);
        mobile=(EditText)findViewById(R.id.mobile);
        email=(EditText)findViewById(R.id.email);
        request=(EditText)findViewById(R.id.request);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating SendMail object

                name1=name.getText().toString().trim();
                mobile1=mobile.getText().toString().trim();
                email1=email.getText().toString().trim();
                request1=request.getText().toString().trim();



                String message ="Name : "+name1 +"\n"+"Mobile : "+mobile1+"\n"+"Email ID : "+email1+"\n"+"Request : "+request1;
                Log.e("Message",""+message);

                SendMail sm = new SendMail(PrayerRequestActivity.this, "udaykiran447.k@gmail.com", "Prayer Request", message);
                //Executing sendmail to send email
                sm.execute();



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
