package com.example.timmy.myapplication;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ShowGamesLog extends Activity {
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_games_log);

        Cursor cursor = null;

        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.example.timmy.myapplication/MG_DB", null, SQLiteDatabase.OPEN_READONLY);

            cursor = db.rawQuery("SELECT * FROM GamesLog;", null);

            TextView tvData = (TextView) findViewById(R.id.data);
            String dataStr = "";

            while (cursor.moveToNext()) {
                String playDate = cursor.getString(cursor.getColumnIndex("playDate"));
                String playTime = cursor.getString(cursor.getColumnIndex("playTime"));
                String duration = cursor.getString(cursor.getColumnIndex("duration"));
                String correctCount = cursor.getString(cursor.getColumnIndex("correctCount"));
                dataStr += "Play Date: " + playDate + "\nPlay Time: " + playTime + "\nDuration: " + duration + " second\nCorrect Count: " + correctCount + "\n\n";
            }

            tvData.setText(dataStr);

            db.close();
        } catch (SQLiteException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
