package com.example.bettertimepoc;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by billmote on 12/10/14.
 */
public class TimerHelper implements Timer.TimerListener {

    private static final String KEY_PREFS_TIMER = "timer_state";
    private static final String KEY_PREFERENCE_FILENAME = "timer";
    private final Context context;

    public TimerHelper(Context context) {
        this.context = context;
    }

    @Override
    public void onTimerChanged(Timer timer) {
        saveTimer(timer);
        updateNotification(timer);
    }

    private void updateNotification(Timer timer) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent("com.timer.changed");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,1,intent,0);
        Log.v("DEBUG", "in updateNotification");
        if (timer.isRunning()) {
            Log.v("DEBUG", "scheduling an alarm for " + timer.getTimeRemaining() + "ms from now");
            if (Build.VERSION.SDK_INT >= 19) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, timer.getCompletionTime(), pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, timer.getCompletionTime(), pendingIntent);
            }
        } else {
            Log.v("DEBUG", "timer thinks its not running...wtf");
            alarmManager.cancel(pendingIntent);
        }
    }

    private void saveTimer(Timer timer) {
        String timerAsString = new Gson().toJson(timer);
        getSharedPreferencesEditor().putString(KEY_PREFS_TIMER, timerAsString).apply();
    }

    public Timer loadTimer() {
        String timerAsString = getSharedPreferences().getString(KEY_PREFS_TIMER, "");
        final Timer timer;
        if (TextUtils.isEmpty(timerAsString)) {
            timer = new Timer();
        } else {
            timer = new Gson().fromJson(timerAsString, Timer.class);
        }

        timer.addListener(this);
        return timer;
    }

    private SharedPreferences.Editor getSharedPreferencesEditor() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.edit();
    }

    private SharedPreferences getSharedPreferences() {
        return context.getApplicationContext().getSharedPreferences(KEY_PREFERENCE_FILENAME, Context.MODE_PRIVATE);
    }
}
