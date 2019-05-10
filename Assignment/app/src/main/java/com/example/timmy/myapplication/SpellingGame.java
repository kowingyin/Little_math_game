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
import java.util.*;
import java.io.*;

import java.util.Timer;

public class SpellingGame extends AppCompatActivity {
    private TextView tvIns, tvTimer, tvScore;
    private Button Button2,Button3,Button4,Button5;
    int time = 0, score = 0, combo = 0, timelimit = 10;
    int correct, place, count = 0, wrong, ansCount = 0;
    int pressed1 = 0, pressed2 = 0, pressed3 = 0, pressed4 = 0;
    int corrCount, wrongCount;
    int[] usedAns = new int[10];
    int[] usedWrong = new int[30];
    String[] ans = {"revision","practice","motivate","enough","photograph","trousers", "achieve", "convenient", "memorize", "datum"};
    String[] wrongAns = {"dicttionary", "explanasion", "requirment", "tatoo", "convienient" , "achievemet" , "distint" , "brige" , "contant" , "ebout" , "suden", "eferyday" , "questsion" , "meen" , "fathar" , "earthquick" , "forgut" , "asume" , "eyasr" , "srand" ,
            "saterday", "faburary" , "decryse" , "incryse", "supplai", "photosinthsis", "elephent", "astroaunt", "recieve", "preposision"};
    int timeRec = 0;
    Timer tm;

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
        tm = new Timer();
        init();

    }
    public void init(){

        if (count == 10)
            count = 0;
        pressed1 = 0;
        pressed2 = 0;
        pressed3 = 0;
        pressed4 = 0;
        ansCount = 0;
        Random rnd = new Random();
        corrCount = 0;
        correct = rnd.nextInt(10);
        while (corrCount < count)
        {
            if (correct == usedAns[corrCount])
            {
                correct = rnd.nextInt(10);
                corrCount = 0;
            }
            else corrCount++;
        }
        usedAns[count] = correct;
        place = rnd.nextInt(4);
        for (int j = 0; j < 3; j++)
        {
            wrong = rnd.nextInt(30);
            wrongCount = 0;
            while (wrongCount < 3 * count + j)
            {
                if (wrong == usedWrong[wrongCount])
                {
                    wrong = rnd.nextInt(30);
                    wrongCount = 0;
                }
                else wrongCount++;
            }
            usedWrong[3 * count + j] = wrong;
        }

        switch (place)
        {
            case 0:
                Button2.setText(ans[correct]);
                Button3.setText(wrongAns[usedWrong[3 * count]]);
                Button4.setText(wrongAns[usedWrong[3 * count + 1]]);
                Button5.setText(wrongAns[usedWrong[3 * count + 2]]);
                break;
            case 1:
                Button2.setText(wrongAns[usedWrong[3 * count]]);
                Button3.setText(ans[correct]);
                Button4.setText(wrongAns[usedWrong[3 * count + 1]]);
                Button5.setText(wrongAns[usedWrong[3 * count + 2]]);
                break;
            case 2:
                Button2.setText(wrongAns[usedWrong[3 * count]]);
                Button3.setText(wrongAns[usedWrong[3 * count + 1]]);
                Button4.setText(ans[correct]);
                Button5.setText(wrongAns[usedWrong[3 * count + 2]]);
                break;
            case 3:
                Button2.setText(wrongAns[usedWrong[3 * count]]);
                Button3.setText(wrongAns[usedWrong[3 * count + 1]]);
                Button4.setText(wrongAns[usedWrong[3 * count + 2]]);
                Button5.setText(ans[correct]);
                break;
        }
        count++;
    }
    public void onClickBtn2(View v){
        if (place != 0)
        {
            if (pressed1 == 0)
            {
                pressed1 = 1;
                ansCount++;
            }
            if (ansCount == 3)
            {
                combo++;
                score++;
                tvScore.setText("Score: " + score);
                init();
            }
            else combo = 0;
            if (combo == 3)
                timelimit += 2;
        }else init();
    }
    public void onClickBtn3(View v){
        if (place != 1)
        {
            if (pressed2 == 0)
            {
                pressed2 = 1;
                ansCount++;
            }
            if (ansCount == 3)
            {
                combo++;
                score++;
                tvScore.setText("Score: " + score);
                init();
            }
            else combo = 0;
            if (combo == 3)
                timelimit += 2;
        }
        else init();
    }
    public void onClickBtn4(View v){
        if (place != 2)
        {
            if (pressed3 == 0)
            {
                pressed3 = 1;
                ansCount++;
            }
            if (ansCount == 3)
            {
                combo++;
                score++;
                tvScore.setText("Score: " + score);
                init();
            }
            else combo = 0;
            if (combo == 3)
                timelimit += 2;
        }
        else init();
    }
    public void onClickBtn5(View v){
        if (place != 3)
        {
            if (pressed4 == 0)
            {
                pressed4 = 1;
                ansCount++;
            }
            if (ansCount == 3)
            {
                combo++;
                score++;
                tvScore.setText("Score: " + score);
                init();
            }
            else combo = 0;
            if (combo == 3)
                timelimit += 2;
        }
        else init();
    }
}
