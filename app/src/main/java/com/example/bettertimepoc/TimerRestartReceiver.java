package com.example.bettertimepoc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class TimerRestartReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        TimerHelper timerHelper = ((TimerApplication) context.getApplicationContext()).getTimerHelper();
        Timer timer = timerHelper.loadTimer();
        Log.v("DEBUG", "WTF before the if");
        if (timer.isRunning()) {
            Log.v("DEBUG", "WTF inside the if");
            timerHelper.onTimerChanged(timer);  // Slightly awkward
        } else {
            Log.v("DEBUG", "WTF outside the if");
            if (timer.getCompletionTime() != 0) {
                Log.v("DEBUG", "WTF inside the second if");
                long elapsedTimeSinceAlarmFinished = Math.abs(timer.getTimeRemaining());
                Toast.makeText(context, "The timer finished " + elapsedTimeSinceAlarmFinished + "ms ago", Toast.LENGTH_LONG).show();
            } else {
                Log.v("DEBUG", "WTF outside the second if");
            }
        }
    }
}
