package com.example.etash.averager;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    TextView firstTime, secondTime, thirdTime, fourthTime, fifthTime, bestAverage, worstAverage,
            finalAverage, timerTextView;
    Button timer;
    long [] times = new long[5];
    long [] tempTimes = new long[4];
    int solveNumber = 0;
    boolean isTimerOn = false;
    long startTime = 0;
    long timeOfSolve;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            timeOfSolve = millis;
            timerTextView.setText("Time: " + convertMillisToString(millis));
            timerHandler.postDelayed(this, 10);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstTime = findViewById(R.id.FirstTime);
        secondTime = findViewById(R.id.SecondTime);
        thirdTime = findViewById(R.id.ThirdTime);
        fourthTime = findViewById(R.id.FourthTime);
        fifthTime = findViewById(R.id.FifthTime);
        bestAverage = findViewById(R.id.BestAverage);
        worstAverage = findViewById(R.id.WorstAverage);
        finalAverage = findViewById(R.id.FinalAverage);
        timerTextView = findViewById(R.id.TimerTextView);
        timer = findViewById(R.id.timer);
        timerTextView.setText("Time");
        timer.setText("Start");
        timer.setBackgroundColor(Color.GREEN);
        hide();
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(solveNumber == 0) {
                    hide();
                }
                updateTimer();
            }
        });
    }


    public long calculateBestAverage(){
        for(int i = 0; i<4; i++){
            tempTimes[i] = times[i];
        }
        Arrays.sort(tempTimes);
        return (tempTimes[0] + tempTimes[1] + tempTimes[2])/((long)(3.0));
    }

    public long calculateWorstAverage(){
        for(int i = 0; i<4; i++){
            tempTimes[i] = times[i];
        }
        Arrays.sort(tempTimes);
        return (tempTimes[1] + tempTimes[2] + tempTimes[3])/((long)(3.0));
    }

    public long calculateFinalAverage(){
        Arrays.sort(times);
        return (tempTimes[1] + tempTimes[2] + tempTimes[3])/((long)(3.0));
    }

    public void hide() {
        firstTime.setVisibility(View.INVISIBLE);
        secondTime.setVisibility(View.INVISIBLE);
        thirdTime.setVisibility(View.INVISIBLE);
        fourthTime.setVisibility(View.INVISIBLE);
        fifthTime.setVisibility(View.INVISIBLE);
        bestAverage.setVisibility(View.INVISIBLE);
        worstAverage.setVisibility(View.INVISIBLE);
        finalAverage.setVisibility(View.INVISIBLE);
    }

    public void updateTimer(){
        if(isTimerOn){
            timerHandler.removeCallbacks(timerRunnable);
            isTimerOn = false;
            timer.setText("Start");
            updateDisplayAndTimes();
            timer.setBackgroundColor(Color.GREEN);
        } else {
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable,0);
            isTimerOn = true;
            timer.setText("Stop");
            timer.setBackgroundColor(Color.RED);
        }
    }

    public void updateDisplayAndTimes(){
        if(solveNumber == 0){
            firstTime.setText("First " + timerTextView.getText());
            firstTime.setVisibility(View.VISIBLE);
            times[0] = timeOfSolve;
        } else if(solveNumber == 1){
            secondTime.setText("Second " + timerTextView.getText());
            secondTime.setVisibility(View.VISIBLE);
            times[1] = timeOfSolve;
        } else if(solveNumber == 2){
            thirdTime.setText("Third " + timerTextView.getText());
            thirdTime.setVisibility(View.VISIBLE);
            times[2] = timeOfSolve;
        } else if(solveNumber == 3){
            fourthTime.setText("Fourth " + timerTextView.getText());
            fourthTime.setVisibility(View.VISIBLE);
            times[3] = timeOfSolve;
            bestAverage.setText("Best Possible Average: " + convertMillisToString(calculateBestAverage()));
            worstAverage.setText("Worst Possible Average: " + convertMillisToString(calculateWorstAverage()));
            bestAverage.setVisibility(View.VISIBLE);
            worstAverage.setVisibility(View.VISIBLE);
        } else if(solveNumber == 4) {
            fifthTime.setText("Fifth " + timerTextView.getText());
            fifthTime.setVisibility(View.VISIBLE);
            finalAverage.setText("Final Average: " + convertMillisToString(calculateFinalAverage()));
            finalAverage.setVisibility(View.VISIBLE);
        }
        solveNumber++;
        solveNumber %= 5;
    }

    public String convertMillisToString(long millis){
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        String str = String.format("%d:%02d:%03d", minutes, seconds, (millis%1000));
        return str;
    }
}
