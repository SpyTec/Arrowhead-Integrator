package arrowhead_cloud_integrator;

import arrowhead_cloud_integrator.ahf_interface.AhfBridge;
import arrowhead_cloud_integrator.iot_cloud_drivers.CumulocityDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

/**
 * Integrator for AHF and IoT Cloud that manages CRUD synchronization.
 */
public class Integrator implements Observer{
    /**
     * Internal clock for polling continuously
     */
    private Clock clk;
    /**
     * Model for storing and mapping services between IoT cloud and AHF
     */
    private ServicesModel servicesModel;
    /**
     * The specific IoTCloudDriver used to integrate with
     */
    private CumulocityDriver cloudDriver;
    /**
     * CRUD bridge with AHF
     */
    private AhfBridge ahfBridge;
    /**
     * Polling interval for clock
     */
    private int interval = 20000; // 20 seconds
    /**
     * URL for AHF Service Discovery
     */
    private URL serviceDiscoveryUrl;
    /**
     * URL for AHF Authorization URL
     */
    private URL authorisationUrl;
    /**
     * Domain for Cumulocity.
     * Ex: "ahf"
     */
    private String cumulocityDomain;
    /**
     * Cumulocity username
     */
    private String cumulocityUsername;
    /**
     * Cumulocity Password
     */
    private String cumulocityPassword;
    /**
     * Properties of passwords and keys
     */
    public Properties defaultProps;

    /**
     * Creates a new instance of Integrator.
     * @param args
     */
    public static void main(String[] args) {
        try {
            Integrator integrator = new Integrator();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Integrator to create relevant instances of AhfBridge, IoTCloudDriver, and ServicesModel.
     * If integrator.properties file is not found make a copy of integrator.properties.example to integrator.properties
     * with the correct credentials.
     * @throws IOException for errors with integrator.properties file.
     */
    public Integrator() throws IOException{

        // Load in passwords and keys from integrator.properties.
        defaultProps = new Properties();
        FileInputStream in = new FileInputStream("integrator.properties");
        defaultProps.load(in);
        in.close();

        cumulocityDomain = String.format("https://%s.cumulocity.com", defaultProps.getProperty("cumulocity_domain"));
        cumulocityUsername = defaultProps.getProperty("cumulocity_username");
        cumulocityPassword = defaultProps.getProperty("cumulocity_password");

        authorisationUrl = new URL(defaultProps.getProperty("authorisation_url"));
        serviceDiscoveryUrl = new URL(defaultProps.getProperty("service_discovery_url"));

        // Main application instances
        cloudDriver = new CumulocityDriver(cumulocityDomain, cumulocityPassword, cumulocityUsername);
        ahfBridge = new AhfBridge(authorisationUrl, serviceDiscoveryUrl);
        // Setup polling with interval. Clock will call update().
        clk = new Clock(interval, this);
        servicesModel = new ServicesModel(cloudDriver, ahfBridge);
    }

    public void update(Observable observable, Object o) {
        System.out.println("----------Clock-Cycle----------");
        // Synchronize AHF and IoT Cloud
        servicesModel.updateServices();
    }
}
