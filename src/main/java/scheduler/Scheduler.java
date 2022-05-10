package scheduler;

import java.util.Timer;
import java.util.TimerTask;

public class Scheduler {

    private Timer timer;

    private TimerTask task;

    public Scheduler(Timer timer, TimerTask task) {
        this.timer = timer;
        this.task = task;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public TimerTask getTask() {
        return task;
    }

    public void setTask(TimerTask task) {
        this.task = task;
    }
}
