package arrowhead_cloud_integrator;

import arrowhead_cloud_integrator.ahf_interface.AhfBridge;
import arrowhead_cloud_integrator.iot_cloud_drivers.CumulocityDriver;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;

public class Integrator implements Observer{
    private Clock clk;
    private ServicesModel servicesModel;
    private CumulocityDriver cloudDriver;
    private AhfBridge ahfBridge;
    private int intervall = 10000; // 10 seconds
    private URL sdURL;
    private URL authURL;
    private String cumulocityURL;
    private String accPass;
    private String accUSER;

    public static void main(String[] args) {
        Integrator integrator = new Integrator();
    }

    public Integrator(){
        cloudDriver = new CumulocityDriver(cumulocityURL, accPass, accUSER);
        ahfBridge = new AhfBridge(authURL, sdURL);
        clk = new Clock(intervall, this);
        servicesModel = new ServicesModel(cloudDriver, ahfBridge);
    }

    public void update(Observable observable, Object o) {
        servicesModel.updateServices();
        servicesModel.compareCloudServices();
    }
}
