package com.example.tyler.timerman;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class Timer extends AppCompatActivity {

    private static final String TAG = "Timer";

    private Chronometer chronometer;
    private TextView durationEntry;
    private Button startButton;
    private Button stopButton;
    private Button resetButton;

    private boolean started = false;
    private long stopTime;
    private long duration;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        this.durationEntry = (TextView) this.findViewById(R.id.second_counter);

        this.resetButton = (Button) this.findViewById(R.id.reset);
        this.resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());
                stopTime = 0;
                started = false;
            }
        });

        this.startButton = (Button) findViewById(R.id.start);
        this.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duration = Long.parseLong(durationEntry.getText().toString());
                if(!started) {
                    chronometer.setBase(getBaseTime());
                    chronometer.start();
                    started = true;
                }
            }
        });

        this.stopButton = (Button) findViewById(R.id.stop);
        this.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (started) {
                    stopTime = SystemClock.elapsedRealtime();
                    chronometer.stop();
                    started = false;
                }
            }
        });

        this.chronometer = (Chronometer) this.findViewById(R.id.chrono);

        this.chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long seconds = (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000;
                Log.i(TAG, seconds + " seconds have elapsed.");
                if (seconds != 0 && seconds % duration == 0) {
                    Toast.makeText(getApplicationContext(),
                            String.format("%s Seconds Have Elapsed", seconds),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private long getBaseTime() {
        long baseTime = SystemClock.elapsedRealtime();
        return stopTime == 0 ? baseTime : baseTime - stopTime + chronometer.getBase();
    }

}
