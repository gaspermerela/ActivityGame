package com.example.gaper.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import android.content.Intent;
import android.net.Uri;

public class SecondActivity extends MainActivity {

    private TextView time;
    private TextView share;
    private Button word;
    private Button start;
    private Button stop;
    private Button up;
    private Button down;
    private Button newWord;
    private Button end;
    private CountDownTimer countDownTimer;

    private String[] words;
    private String[] presentation = {"speaking", "drawing", "pantomime"};
    private String a = "";

    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.start:
                    start(90);
                    enableButtons(true, 4);
                    break;
                case R.id.stop:
                    cancel();
                    enableButtons(true, 2);
                    break;
                case R.id.newWord:
                    setRandomWord();
                    enableButtons(false, 1);
                    break;
                case R.id.up:
                    enableButtons(true, 3);
                    break;
                case R.id.down:
                    enableButtons(true, 3);
                    break;
                case R.id.word:
                    goTo(v);
                    break;
                case R.id.end:
                    theEnd();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(btnClickListener);
        stop = (Button) findViewById(R.id.stop);
        stop.setOnClickListener(btnClickListener);
        time = (TextView) findViewById(R.id.time);
        up =  (Button)findViewById(R.id.up);
        up.setOnClickListener(btnClickListener);
        down = (Button)findViewById(R.id.down);
        down.setOnClickListener(btnClickListener);
        newWord = (Button)findViewById(R.id.newWord);
        newWord.setOnClickListener(btnClickListener);
        word = (Button) findViewById(R.id.word);
        word.setOnClickListener(btnClickListener);
        share = (TextView) findViewById(R.id.share);
        end = (Button) findViewById(R.id.end);
        end.setOnClickListener(btnClickListener);

        createWordsList();
        //setRandomWord();
        word.setText(null);
        share.setText(null);
    }

    public void start(int timeS) {
        time.setText(null);

        countDownTimer = new CountDownTimer(timeS * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time.setText("" + millisUntilFinished / 1000);

                int sek = (int)millisUntilFinished / 1000;
                int minute = sek / 60;
                int preoStaleSekunde = sek - minute * 60;
                time.setText("" + minute + " : " + preoStaleSekunde);
            }
            @Override
            public void onFinish() {
                time.setText("Time's up!");
            }
        };

        countDownTimer.start();
    }

    public void cancel() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
            time.setText(null);
        }
    }

    private void enableButtons(boolean enable, int a){
        if (enable) {
            switch (a) {
                case 1:
                    up.setEnabled(true);
                    down.setEnabled(true);
                    newWord.setEnabled(true);
                    break;
                case 2:
                    up.setEnabled(true);
                    down.setEnabled(true);
                    stop.setEnabled(false);
                    break;
                case 3:
                    newWord.setEnabled(true);
                    up.setEnabled(false);
                    down.setEnabled(false);
                    break;
                case 4:
                    start.setEnabled(false);
                    stop.setEnabled(true);
                    word.setEnabled(false);
                    break;
            }
        }
        else {
            up.setEnabled(false);
            down.setEnabled(false);
            newWord.setEnabled(false);
            start.setEnabled(true);
            word.setEnabled(true);
        }
    }

    private void createWordsList() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(getAssets().open("asset/random_words.txt")));
            String line = in.readLine();
            words = line.split(" ");
        } catch (IOException e) {
            System.out.print("Napaka pri branju datoteke: " + e.getMessage());
        }
    }

    private void setRandomWord () {
        a = words[(int)(Math.random() * words.length-1)];
        word.setText(a);
        share.setText(presentation[(int)(Math.random() * presentation.length)]);
    }

    public void goTo (View view) {
        goToUrl ( "http://www.dictionary.com/browse/"+ a +"?s=t");
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    private void theEnd (){
        Intent j = new Intent(SecondActivity.this, EndActivity.class);
        startActivity(j);
    }
}