package com.cyphermoon.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private int seconds;
    private boolean running;
    private boolean wasRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }

        runTimer();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
    }

    @Override
    protected void onStop() {
        super.onStop();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(wasRunning) running = true;
    }

    public void startWatch(View view){
        running = true;
    }

    public void stopWatch(View view){
        running = false;
    }

    public void resetWatch(View view){
        running = false;
        seconds = 0;
    }

    public void runTimer(){
        final TextView timeView = (TextView) findViewById(R.id.time_view);

        Handler handler = new Handler();
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        int hours = seconds / 3600 ;
                        int minutes =(seconds % 3600) / 60 ;
                        int sec = seconds % 60;

                        String timeDisplay = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, sec);
                        timeView.setText(timeDisplay);

                        if(running) seconds++;

                        handler.postDelayed(this, 1000);
                    }
                }
        );

    }


}