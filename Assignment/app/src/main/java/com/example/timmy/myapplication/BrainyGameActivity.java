package com.example.timmy.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BrainyGameActivity extends AppCompatActivity {

    private String DB_PATH;
    private String DB_NAME = "MathGame_DB.db";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brainy_game);
        final Context context = this;
        DB_PATH = context.getFilesDir().getPath() +"/db/";
    }

}
