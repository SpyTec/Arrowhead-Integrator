package arrowhead_cloud_integrator;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable {
    private int intervall;
    private Timer timer;

    public Clock(int intervall, Integrator integrator){
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
