package com.example.timmy.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class BrainyGameActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 3434;
    private String DB_PATH;
    private String DB_NAME = "MathGame_DB.db";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brainy_game);
        final Context context = this;
        DB_PATH = context.getFilesDir().getPath() +"/db/";
    }
    public void onClickBtnStartSpelling(View v){
        Intent i = new Intent(this, SpellingGame.class);
        startActivityForResult(i, REQUEST_CODE);
    }
    public void onClickBtnStartFlash(View v){
        Intent i = new Intent(this, MathematicGame.class);
        startActivityForResult(i, REQUEST_CODE);
    }
    public void onClickBtnStartShapes(View v){
        Intent i = new Intent(this, MathematicGame.class);
        startActivityForResult(i, REQUEST_CODE);
    }
    public void onClickBtnStartReaction(View v){
        Intent i = new Intent(this, MathematicGame.class);
        startActivityForResult(i, REQUEST_CODE);
    }
    public void onClickBtnStartColors(View v){
        Intent i = new Intent(this, MathematicGame.class);
        startActivityForResult(i, REQUEST_CODE);
    }

}
