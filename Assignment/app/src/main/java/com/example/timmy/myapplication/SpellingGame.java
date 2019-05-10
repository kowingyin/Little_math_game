package com.example.timmy.myapplication;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.*;
import java.io.*;
import java.lang.*;
//import java.util.Timer;

public class SpellingGame extends AppCompatActivity {
    private TextView tvIns, tvTimer, tvScore;
    private Button Button2,Button3,Button4,Button5;
    public int timeleft = 10000, score = 0, combo = 0;
    public int timelimit = 10000;
    int correct, place, count = 0, wrong, ansCount = 0;
    int pressed1 = 0, pressed2 = 0, pressed3 = 0, pressed4 = 0;
    int corrCount, wrongCount;
    int[] usedAns = new int[10];
    int[] usedWrong = new int[30];
    String[] ans = {"revision","practice","motivate","enough","photograph","trousers", "achieve", "convenient", "memorize", "datum"};
    String[] wrongAns = {"dicttionary", "explanasion", "requirment", "tatoo", "convienient" , "achievemet" , "distint" , "brige" , "contant" , "ebout" , "suden", "eferyday" , "questsion" , "meen" , "fathar" , "earthquick" , "forgut" , "asume" , "eyasr" , "srand" ,
            "saterday", "faburary" , "decryse" , "incryse", "supplai", "photosinthsis", "elephent", "astroaunt", "recieve", "preposision"};
    int timeRec = 0;
    //Timer tm;

    //gyroscope
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private SensorEventListener gyroscopeEventListener;
    private CountDownTimer cdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling_game);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if(gyroscopeSensor == null){
            Toast.makeText(this,"The device has no gyroscope!", Toast.LENGTH_SHORT).show();
            finish();
        }
        gyroscopeEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event.values[0]>1.0f && event.values[1]>1.0f){
                    Button5.performClick();
                    Button5.setPressed(true);
                    Button5.invalidate();
                    Button5.setPressed(false);
                    Button5.invalidate();
                }
                if(event.values[0]<-1.0f && event.values[1]>1.0f){
                    Button3.performClick();
                    Button3.setPressed(true);
                    Button3.invalidate();
                    Button3.setPressed(false);
                    Button3.invalidate();
                }
                if(event.values[0]>1.0f && event.values[1]<-1.0f){
                    Button4.performClick();
                    Button4.setPressed(true);
                    Button4.invalidate();
                    Button4.setPressed(false);
                    Button4.invalidate();
                }
                if(event.values[0]<-1.0f && event.values[1]<-1.0f){
                    Button2.performClick();
                    Button2.setPressed(true);
                    Button2.invalidate();
                    Button2.setPressed(false);
                    Button2.invalidate();
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        Button2 = (Button) findViewById(R.id.button2);
        Button3 = (Button) findViewById(R.id.button3);
        Button4 = (Button) findViewById(R.id.button4);
        Button5 = (Button) findViewById(R.id.button5);
        tvTimer = (TextView) findViewById(R.id.tvTimer);
        tvScore = (TextView) findViewById(R.id.tvScore);
        tvScore.setText("Score: 0");

        cdt = new CountDownTimer(timelimit,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeleft-=1000;
                //millisUntilFinished = timeleft;
                tvTimer.setText("Time remaining (s) : "+ millisUntilFinished/1000+" seconds.");

            }

            @Override
            public void onFinish() {
                tvTimer.setText("Time over!");
                finish();
            }
        }.start();
        init();

    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(gyroscopeEventListener,gyroscopeSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(gyroscopeEventListener);
    }
    @Override
    public void finish(){
        super.finish();
        Toast.makeText(this,"Game over! you scored : "+ score, Toast.LENGTH_LONG).show();
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
        if (place != 0) {
            if (pressed1 == 0) {
                pressed1 = 1;
                ansCount++;
            }
            if (ansCount == 3) {
                combo++;
                score++;
                tvScore.setText("Score: " + score);
                init();
            } else {
                combo = 0;
            }
            if (combo == 3) {
                timeleft += 5000;
                cdt.cancel();
                cdt.onTick(timeleft);
                cdt.start();
            }
        }else {
            init();
        }
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
            if (combo == 3){
                timeleft += 5000;
                cdt.cancel();
                cdt.onTick(timeleft);
                cdt.start();
            }
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
            if (combo == 3){
                timeleft += 5000;
                cdt.cancel();
                cdt.onTick(timeleft);
                cdt.start();
            }
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
            if (combo == 3){
                timeleft += 5000;
                cdt.cancel();
                cdt.onTick(timeleft);
                cdt.start();
            }
        }
        else init();
    }
}
