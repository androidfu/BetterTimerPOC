package com.example.bettertimepoc;

import android.app.Application;

/**
 * Created by billmote on 12/10/14.
 */
public class TimerApplication extends Application {
    private TimerHelper timerHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        timerHelper = new TimerHelper(this);
    }

    public TimerHelper getTimerHelper() {
        return timerHelper;
    }
}
