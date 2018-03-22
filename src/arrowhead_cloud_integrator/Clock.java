package arrowhead_cloud_integrator;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Clock to manage time to call integrator
 */
public class Clock extends Observable {
    /**
     * Intervall for the clock.
     */
    private int intervall;
    /**
     * Timer to execute with a set interval.
     */
    private Timer timer;

    /**
     * @param intervall  to execute clock.
     * @param integrator to call with each clock cycle.
     */
    public Clock(int intervall, Integrator integrator) {
        this.addObserver(integrator);
        this.intervall = intervall;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                setChanged();
                notifyObservers();
            }
        }, intervall, intervall);
    }
}
