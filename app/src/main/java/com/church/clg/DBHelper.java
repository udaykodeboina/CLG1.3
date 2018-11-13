package com.church.clg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "StepUP";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "STEPUP_DATABASE";
    private Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StepValues stepValues=new StepValues(context);

        db.execSQL("CREATE TABLE IF NOT EXISTS "+stepValues.GRADE_TABLE+" (sno INTEGER PRIMARY KEY AUTOINCREMENT,"
                +stepValues.GRADE_ID+" TEXT UNIQUE,"

                +stepValues.GRADE_IMAGE+" TEXT)");


    }






    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        StepValues stepValues=new StepValues(context);
        db.execSQL("DROP TABLE IF EXISTS "+stepValues.GRADE_TABLE);

        onCreate(db);
    }



    public void insertGrade(String grade_id,String grade_image){
        StepValues stepValues=new StepValues(context);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(stepValues.GRADE_ID,grade_id);

        values.put(stepValues.GRADE_IMAGE,grade_image);
        if(row_count("SELECT count(*) FROM "+stepValues.GRADE_TABLE+" where "+stepValues.GRADE_ID+"='"+grade_id+"'")==0)
            db.insert(stepValues.GRADE_TABLE, null, values) ;
        db.close();
    }

    public Map<String,List<String>> getGrade(String s){
        StepValues stepValues=new StepValues(context);
        List<String> grade_id=new ArrayList<>();

        List<String> grade_image=new ArrayList<>();
        List<String> grade_count=new ArrayList<>();
        Map<String,List<String>> map =new HashMap();
        SQLiteDatabase dbh = this.getWritableDatabase();
        Cursor result=dbh.rawQuery(s,null);
        if(result.moveToFirst()){
            do{

                grade_id.add(result.getString(result.getColumnIndex(stepValues.GRADE_ID)));

                grade_image.add(result.getString(result.getColumnIndex(stepValues.GRADE_IMAGE)));
            }while (result.moveToNext());
        }
        map.put("grade_id",grade_id);

        map.put("grade_image",grade_image);
        map.put("grade_count",grade_count);
        return map;
    }





    public int row_count(String s) {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.longForQuery(db, s, null);
    }









}
