package com.example.bettertimepoc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TimerReceiver extends BroadcastReceiver {
    public TimerReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Finished!", Toast.LENGTH_SHORT).show();
    }
}
