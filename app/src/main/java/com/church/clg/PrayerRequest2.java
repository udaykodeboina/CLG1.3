package com.church.clg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import butterknife.BindViews;
import butterknife.ButterKnife;

public class PrayerRequest2 extends AppCompatActivity {
    Button send;

    @BindViews({R.id.name, R.id.mobile,R.id.email, R.id.request} )
    List<EditText> editTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_request2);
        //show the title in Action Bar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Prayer Request");
        //show the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ButterKnife.bind(this);

        send=(Button)findViewById(R.id.send);



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating SendMail object

                if (validate(editTexts.get(0)) && validate(editTexts.get(1))&& validateEmail(editTexts.get(2),PrayerRequest2.this)&& validate(editTexts.get(3)) ) {

                    sendEmail();
                }
                else {

                    Toast.makeText(PrayerRequest2.this, "Fill all Details", Toast.LENGTH_SHORT).show();

                }


            }
        });



    }

    public void sendEmail()
    {
        String message ="Name : "+editTexts.get(0).getText().toString().trim() +"\n"+"Mobile : "+editTexts.get(1).getText().toString().trim()+"\n"+"Email ID : "+editTexts.get(2).getText().toString().trim()+"\n"+"Request : "+editTexts.get(3).getText().toString().trim();
        Log.e("Message",""+message);

        // Through Intent

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"clg.london09@gmail.com"}); // to mail id
        email.putExtra(Intent.EXTRA_SUBJECT, "Prayer Request");
        email.putExtra(Intent.EXTRA_TEXT, message);

        //need this to prompts email client only
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

    /*Validations*/

    public static boolean validate(EditText editText) {
        if (editText.getText().toString().trim().length() > 0) {
            return true;
        }
        editText.setError("Should not be Empty");
        editText.requestFocus();
        return false;
    }



    public static boolean validateEmail(EditText editText,Context context) {
        String email = editText.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            editText.setError(context.getString(R.string.err_msg_email));
            editText.requestFocus();
            return false;
        }

        return true;
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
