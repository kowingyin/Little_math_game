package com.example.timmy.myapplication;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.Toast;

public class BarChart extends Activity {
    SQLiteDatabase db;

    private String DB_PATH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        final Context context = this;
        DB_PATH = context.getFilesDir().getPath() +"/db/";
        String DB_NAME = "MathGame_DB.db";
        db = SQLiteDatabase.openDatabase(DB_PATH+ DB_NAME, null, SQLiteDatabase.OPEN_READONLY);
        //db = SQLiteDatabase.openOrCreateDatabase(getApplicationContext().getFilesDir().getPath(), null);
        Cursor cursor = db.rawQuery("SELECT * FROM GamesLog;", null);
        String[] gameDatetime = new String[cursor.getCount()];
        int[] correctCount = new int[cursor.getCount()];
        int count = 0;

        while (cursor.moveToNext()){
            gameDatetime[count] = cursor.getString(cursor.getColumnIndex("playDate")) + " " + cursor.getString(cursor.getColumnIndex("playTime"));
            correctCount[count] = cursor.getInt(cursor.getColumnIndex("correctCount"));
            count++;
        }

        setContentView(new Panel(this, "Games Result", gameDatetime, correctCount));
    }

    public void test(){
        db = SQLiteDatabase.openDatabase("/data/data/com.example.timmy.myapplication/MG_DB", null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM GamesLog;", null);


    }


    class Panel extends View {
        public Panel(Context context, String title, String[] gameDatetime, int[] correctCount) {
            super(context);
            this.title = title;
            this.gameDatetime = gameDatetime;
            this.correctCount = correctCount;

        }

        public String title;
        public String[] gameDatetime;
        public int[] correctCount;


        @Override
        public void onDraw(Canvas c) {
            super.onDraw(c);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);

            // Make the entire canvas in white
            paint.setColor(Color.WHITE);
            c.drawPaint(paint);

            // Draw the title
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(40);
            paint.setTypeface(Typeface.SERIF);
            c.drawText(title, 30, 50, paint);

            int vertSpace = 60;
            boolean odd = true;
            String label;

            float[] percent = new float[gameDatetime.length];
            for(int i = 0; i < gameDatetime.length; i++){
                percent[i] = ((float) correctCount[i]/10)*(getWidth() - 20);
            }



            paint.setTextSize(30);
            for (int i = 0; i < gameDatetime.length; i++) {
                // Draw the label
                paint.setColor(Color.BLACK);
                paint.setTypeface(Typeface.SERIF);
                c.drawText(gameDatetime[i], (10)*2, vertSpace*2, paint);

                vertSpace += 10;

                // Draw the bar
                if(odd){
                    odd = false;
                    paint.setColor(Color.RED);
                }
                else{
                    odd = true;
                    paint.setColor(Color.GREEN);
                }

                if(correctCount[i] > 0){
                    c.drawRect((10)*3, vertSpace*2, percent[i], (vertSpace + 18)*2, paint);
                    paint.setColor(Color.BLACK);
                    paint.setTypeface(Typeface.SERIF);

                    c.drawText(correctCount[i]+"", (15)*3, (vertSpace+13)*2, paint);

                }
                else{
                    paint.setColor(Color.BLACK);
                    paint.setTypeface(Typeface.SERIF);
                    c.drawText(correctCount[i]+"", (15)*3, (vertSpace+13)*2, paint);
                }
                vertSpace += 40;
            }
        }
    }

}
