package com.example.bettertimepoc;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by billmote on 12/10/14.
 */
public class Timer {

    private static final long TIMER_DURATION = 180000;
    /*
        Bill wanted this to be a LinkedList, but he was wrong.
     */
    private transient Set<TimerListener> listeners = new HashSet<TimerListener>();
    private long completionTime;

    public void addListener(TimerListener listener) {
        listeners.add(listener);
    }

    public void removeListener(TimerListener listener) {
        listeners.remove(listener);
    }

    public void start() {
        completionTime = System.currentTimeMillis() + TIMER_DURATION;
        for (TimerListener listener : listeners) {
            listener.onTimerChanged(this);
        }
    }

    public void reset() {
        completionTime = 0;
        for (TimerListener listener : listeners) {
            listener.onTimerChanged(this);
        }
    }

    public boolean isRunning() {
        return getTimeRemaining() > 0;
    }

    public long getCompletionTime() {
        return completionTime;
    }

    public long getTimeRemaining() {
        long timeRemaining = completionTime - System.currentTimeMillis();
        return timeRemaining > 0 ? timeRemaining : 0;
    }

    public long getVisibleTimeRemaining() {
        long timeRemaining = getTimeRemaining();
        return timeRemaining <= 0 ? TIMER_DURATION : timeRemaining;
    }

    public interface TimerListener {
        public void onTimerChanged(Timer timer);
    }

}
