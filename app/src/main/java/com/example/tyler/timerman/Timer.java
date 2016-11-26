package com.example.tyler.timerman;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

public class Timer extends AppCompatActivity {

    private static final String TAG = "Timer";

    private Chronometer chronometer;
    private boolean started = false;
    private long startTime;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        this.chronometer = (Chronometer) this.findViewById(R.id.chrono);
        this.chronometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (started) {
                    chronometer.stop();
                    startTime  = SystemClock.elapsedRealtime();
                    started = false;
                } else {
                    if(startTime == 0) {
                        startTime = SystemClock.elapsedRealtime();
                    }
                    chronometer.setBase(startTime);
                    chronometer.start();
                    started = true;
                }
            }
        });

        this.chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long seconds = (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000;
                Log.i(TAG, seconds + " seconds have elapsed.");
                if (seconds != 0 && seconds % 5 == 0) {
                    Toast.makeText(getApplicationContext(),
                            String.format("%s Seconds Have Elapsed", seconds),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
