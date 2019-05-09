package com.example.timmy.myapplication;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ShowQuestionsLog extends Activity {
    SQLiteDatabase db;

    private String DB_PATH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_questions_log);
        final Context context = this;
        DB_PATH = context.getFilesDir().getPath() +"/db/";
        Cursor cursor = null;

        try {
            String DB_NAME = "MathGame_DB.db";
            db = SQLiteDatabase.openDatabase(DB_PATH+ DB_NAME, null, SQLiteDatabase.OPEN_READONLY);

            cursor = db.rawQuery("SELECT * FROM QuestionsLog;", null);

            TextView tvData = (TextView) findViewById(R.id.data);
            String dataStr = "";

            while (cursor.moveToNext()) {
                String question = cursor.getString(cursor.getColumnIndex("question"));
                String answer = cursor.getString(cursor.getColumnIndex("answer"));
                String yourAnswer = cursor.getString(cursor.getColumnIndex("yourAnswer"));
                dataStr += "Question: " + question + "\nAnswer: " + answer + "\nYour Answer: " + yourAnswer + "\n\n";
            }

            tvData.setText(dataStr);

            db.close();
        } catch (SQLiteException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
