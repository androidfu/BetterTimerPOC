package com.example.bettertimepoc;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity implements Timer.TimerListener {

    private Timer timer;
    private TextView timeRemaining;

    @Override
    protected void onPause() {
        timeRemaining.removeCallbacks(displayTimeRemainingRunnable);
        timer.removeListener(this);
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Keep our screen on if the user has opted to do so in settings
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        timer.addListener(this);
        displayTimeRemaining();
        SharedPreferences sharedPreferences = getSharedPreferences("some", MODE_PRIVATE);
        sharedPreferences.reg
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TimerHelper timerHelper = ((TimerApplication) getApplication()).getTimerHelper();
        timer = timerHelper.loadTimer();

        Button start = (Button) findViewById(R.id.button);
        Button reset = (Button) findViewById(R.id.button2);
        timeRemaining = (TextView) findViewById(R.id.textView2);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.reset();
            }
        });
    }

    private final Runnable displayTimeRemainingRunnable = new Runnable() {
        @Override
        public void run() {
            displayTimeRemaining();
            //timeRemaining.invalidate();
        }
    };

    private void displayTimeRemaining() {
        timeRemaining.setText("" + timer.getVisibleTimeRemaining());
        timeRemaining.removeCallbacks(displayTimeRemainingRunnable);
        if (timer.isRunning()) {
            timeRemaining.postDelayed(displayTimeRemainingRunnable, 100);
        }
    }

    @Override
    public void onTimerChanged(Timer timer) {
        displayTimeRemaining();
    }
}
