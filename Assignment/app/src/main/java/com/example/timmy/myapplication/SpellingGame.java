package com.example.timmy.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;

public class SpellingGame extends AppCompatActivity {
    private TextView tvIns, tvTimer, tvScore;
    private Button Button2,Button3,Button4,Button5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling_game);
        Button2 = (Button) findViewById(R.id.button2);
        Button3 = (Button) findViewById(R.id.button3);
        Button4 = (Button) findViewById(R.id.button4);
        Button5 = (Button) findViewById(R.id.button5);
        tvTimer = (TextView) findViewById(R.id.tvTimer);
        tvScore = (TextView) findViewById(R.id.tvScore);
        Timer tm = new Timer();
    }
    public void onClickBtn2(View v){

    }
    public void onClickBtn3(View v){

    }
    public void onClickBtn4(View v){

    }
    public void onClickBtn5(View v){

    }
}
