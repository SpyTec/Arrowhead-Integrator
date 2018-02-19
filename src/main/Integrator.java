package main;

import model.Clock;
import model.Comparator;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;

public class Integrator implements Observer{
    private static Clock clk;
    private static Comparator comp;
    private static int intervall = 10000; // 10 seconds
    private static URL sdURL;
    private static URL authURL;
    private static URL cumulocityURL;
    private static String accPass;
    private static String accUSER;

    public static void main(String[] args) {
        Integrator integrator = new Integrator();
    }

    public Integrator(){
        clk = new Clock(intervall, this);
        comp = new Comparator(sdURL, authURL, cumulocityURL, accPass, accUSER);
    }

    public void update(Observable observable, Object o) {
        comp.updateServices();
        comp.compareCumulocity();
    }
}
