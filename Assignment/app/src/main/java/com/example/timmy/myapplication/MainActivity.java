package com.example.timmy.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.*;

public class MainActivity extends Activity {
    private static final int REQUEST_CODE = 3434;
    private Button btnStart;

    private SQLiteDatabase db;
    private String sql;
    private Cursor cursor = null;

    private String DB_PATH;
    private String DB_NAME = "MathGame_DB.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        final Context context = this;
        DB_PATH = context.getFilesDir().getAbsolutePath() +"/db/";
        System.out.println(DB_PATH);
        initialDB();
    }

    public void onClickBtnStart(View v){
        Intent i = new Intent(this, MathematicGame.class);
        startActivityForResult(i, REQUEST_CODE);
    }
    public void onClickBtnQL(View v){
        Intent i = new Intent(this, ShowQuestionsLog.class);
        startActivityForResult(i, REQUEST_CODE);
    }
    public void onClickBtnGL(View v){
        Intent i = new Intent(this, ShowGamesLog.class);
        startActivityForResult(i, REQUEST_CODE);
    }
    public void onClickBtnGR(View v){
        Intent i = new Intent(this, BarChart.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    public void initialDB() {
        File dir=new File(DB_PATH);
        if(!dir.exists()){
            dir.mkdirs();
        }
        try {
            System.out.println(DB_PATH+DB_NAME);

            //create or open database
            db = SQLiteDatabase.openOrCreateDatabase(DB_PATH+DB_NAME, null);

            sql = "CREATE TABLE IF NOT EXISTS QuestionsLog (questionNo INT PRIMARY KEY , question TEXT , answer TEXT , yourAnswer TEXT );";
            db.execSQL(sql);

            sql = "CREATE TABLE IF NOT EXISTS GamesLog (gameNo INT PRIMARY KEY , playDate TEXT , playTime TEXT , duration INT , correctCount INT);";
            db.execSQL(sql);
            db.close();

        }
        catch (SQLiteException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
