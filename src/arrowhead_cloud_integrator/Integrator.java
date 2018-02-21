package arrowhead_cloud_integrator;

import arrowhead_cloud_integrator.ahf_interface.AhfBridge;
import arrowhead_cloud_integrator.iot_cloud_drivers.CumulocityDriver;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;

public class Integrator implements Observer{
    private static Clock clk;
    private static ServicesModel servicesModel;
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
        CumulocityDriver cloudDriver = new CumulocityDriver(cumulocityURL, accPass, accUSER);
        AhfBridge ahfBridge = new AhfBridge(authURL, sdURL);
        clk = new Clock(intervall, this);
        servicesModel = new ServicesModel(cloudDriver, ahfBridge);
    }

    public void update(Observable observable, Object o) {
        servicesModel.updateServices();
        servicesModel.compareCumulocity();
    }
}
