package com.example.timmy.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
//import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.*;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class MathematicGame extends Activity {
    private TextView tvQuestion, tvTimer, tvQuestionNum;
    private EditText editText;
    private MediaPlayer m;

    private String[] ques;
    private String[] ans;
    private String[][] fakeAnsArray;
    private int rec = 0, quesNum = 0;
    private SQLiteDatabase db;
    private String sql;
    private Cursor cursor = null;
    private boolean downloading = true;
    private String playDate, playTime;
    private int correctCount = 0;

    private DownloadTask task = null;

    private String DB_PATH;
    private String DB_NAME = "MathGame_DB.db";

    private class DownloadTask extends AsyncTask<String, Void, String> {
        private String url;
        @Override
        protected String doInBackground(String... values) {
            String reply = "";
            try {
                // prepare URL and execute http request
                url = "https://opentdb.com/api.php?amount=10&category=19&difficulty=medium&type=multiple";
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet(url);
                HttpResponse response = client.execute(request);

                // process http response
                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent()));
                String line = "";
                while ((line = rd.readLine()) != null) {
                    reply += line;
                }
                rd.close();


            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(reply);
            return reply;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject obj = new JSONObject(result);
                JSONArray arr = obj.getJSONArray("results");
                JSONArray fakeans = null;
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject o = arr.getJSONObject(i);
                    ques[i] = o.getString("question");
                    ans[i] = o.getString("correct_answer");
                    System.out.println(ques[i]);
                    System.out.println(ans[i]);
                    fakeans = o.getJSONArray("incorrect_answers");
                    System.out.println(fakeans.getString(0)+" "+fakeans.getString(1)+" "+fakeans.getString(2));
                    for(int j=0;j<fakeans.length();j++){
                        System.out.println("here "+j+" "+ fakeans.getString(j));
                        fakeAnsArray[i][j]= fakeans.getString(j);
                    }
                }
                downloading = false;
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mathematic_game);
        final Context context = this;
        DB_PATH = context.getFilesDir().getPath() +"/db/";
        m = MediaPlayer.create(this, R.raw.music);
        m.start();
        m.setLooping(true);
        ques = new String[10];
        ans = new String[10];
        fakeAnsArray = new String[10][3];

        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        tvTimer = (TextView) findViewById(R.id.tvTimer);
        tvQuestionNum = (TextView) findViewById(R.id.tvQuestionNum);
        editText  = (EditText) findViewById(R.id.editText);

        editText.setEnabled(false);
        ((Button) findViewById(R.id.btnAnswer)).setEnabled(false);

        if (task == null || task.getStatus().equals(AsyncTask.Status.FINISHED)) {
            tvQuestion.setText("loading...");
            task = new DownloadTask();
            task.execute();

            Timer tm = new Timer();
            tm.schedule(tmTask, 1000, 1000);
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        playDate = dateFormat.format(date);
        dateFormat = new SimpleDateFormat("HH:mm:ss");
        playTime = dateFormat.format(date);
    }
    @Override
    protected void onPause() {
        super.onPause();
        m.reset();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if(downloading) {

                    }else{
                        editText.setEnabled(true);
                        ((Button) findViewById(R.id.btnAnswer)).setEnabled(true);
                        tvQuestion.setText(ques[quesNum]);
                        ++rec;
                        tvTimer.setText("Time Spent: " + rec + " seconds");
                        tvQuestionNum.setText("Question No. " + (quesNum + 1));
                    }
                    break;
                default:
            }
        }};

    private TimerTask tmTask = new TimerTask(){
        @Override
        public void run() {
            // TODO Auto-generated method stub
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);
        }
    };

    public void onClickbtnAnswer(View v){
        if(editText.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Please enter the answer", Toast.LENGTH_LONG).show();
        }else {
            String yourAns = editText.getText().toString();

            if (yourAns.equals(ans[quesNum])) {
                Toast.makeText(getApplicationContext(), "Correct, the answer is " + ans[quesNum], Toast.LENGTH_SHORT).show();
                correctCount++;
            } else {
                Toast.makeText(getApplicationContext(), "Wrong, the answer is " + ans[quesNum], Toast.LENGTH_SHORT).show();
            }
            editText.setText("");

            insertQuestionsLog(ques[quesNum], ans[quesNum] + "", yourAns + "");

            quesNum++;

            //Game Over
            if ((quesNum <= 9) && (quesNum >= 0)) {
                tvQuestionNum.setText("Question: " + (quesNum + 1));
                tvQuestion.setText(ques[quesNum]);
            }else{
                downloading = true;
                tvQuestion.setText("Game Over");
                editText.setEnabled(false);
                ((Button) findViewById(R.id.btnAnswer)).setEnabled(false);
                insertGamesLog();
                ShowMsgDialog();
            }
        }
    }

    public void insertQuestionsLog(String question, String answer, String yourAnswer) {
        //Insert New QuestionsLog into Database
        try{
            int questionNo = 0;
            db = SQLiteDatabase.openDatabase(DB_PATH+DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);

            cursor = db.rawQuery("SELECT questionNo FROM QuestionsLog ORDER BY questionNo DESC LIMIT 1", null);

            while (cursor.moveToNext()) {
                questionNo = (cursor.getInt(cursor.getColumnIndex("questionNo"))) + 1;
            }

            db.execSQL("INSERT INTO QuestionsLog(questionNo, question, answer, yourAnswer) values"
                    + "('" + questionNo + "', '" + question + "', '" + answer + "', '" + yourAnswer + "');");

            db.close();

            //Toast.makeText(getApplicationContext(), "QuestionsLog Add", Toast.LENGTH_SHORT).show();

        } catch (SQLiteException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void insertGamesLog(){
        //Insert New GamesLog into Database
        try{
            int gameNo = 0;
            db = SQLiteDatabase.openDatabase(DB_PATH+DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);

            cursor = db.rawQuery("SELECT gameNo FROM GamesLog ORDER BY gameNo DESC LIMIT 1", null);

            while (cursor.moveToNext()) {
                gameNo = Integer.parseInt(cursor.getString(cursor.getColumnIndex("gameNo"))) + 1;
            }

            db.execSQL("INSERT INTO GamesLog(gameNo, playDate, playTime, duration, correctCount) values"
                    + "('" + gameNo + "', '" + playDate + "', '" + playTime + "', '" + rec + "', '" + correctCount + "');");

            db.close();
        } catch (SQLiteException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void ShowMsgDialog() {
        String msg = "Time Spent: " + rec + " second\nCorrect: " + correctCount;

        AlertDialog.Builder MyAlertDialog = new AlertDialog.Builder(this);
        MyAlertDialog.setTitle("Game Over");
        MyAlertDialog.setMessage(msg);
        MyAlertDialog.setCancelable(false);
        DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        };;
        MyAlertDialog.setNeutralButton("Back to Menu",OkClick );
        MyAlertDialog.show();
    }
}
