package com.example.timmy.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DifficultyMenu extends AppCompatActivity {
    private int REQUEST_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_menu);
    }
    public void onClickBtnEasy(View v){
        Intent i = new Intent(this, MathematicGame.class);
        REQUEST_CODE = 1111;
        startActivityForResult(i, REQUEST_CODE);
    }
    public void onClickBtnMedium(View v){
        Intent i = new Intent(this, MathematicGame.class);
        REQUEST_CODE = 2222;
        startActivityForResult(i, REQUEST_CODE);
    }
    public void onClickBtnHard(View v){
        Intent i = new Intent(this, MathematicGame.class);
        REQUEST_CODE = 3333;
        startActivityForResult(i, REQUEST_CODE);
    }
    public void onClickBtnAnyDiff(View v){
        Intent i = new Intent(this, MathematicGame.class);
        REQUEST_CODE = 4444;
        startActivityForResult(i, REQUEST_CODE);
    }
}
