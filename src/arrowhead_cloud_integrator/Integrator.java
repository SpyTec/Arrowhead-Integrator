package arrowhead_cloud_integrator;

import arrowhead_cloud_integrator.ahf_interface.AhfBridge;
import arrowhead_cloud_integrator.iot_cloud_drivers.CumulocityDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

public class Integrator implements Observer{
    private Clock clk;
    private ServicesModel servicesModel;
    private CumulocityDriver cloudDriver;
    private AhfBridge ahfBridge;
    private int intervall = 10000; // 10 seconds
    private URL serviceDiscoveryUrl;
    private URL authorisationUrl;
    private String cumulocityDomain;
    private String cumulocityUsername;
    private String cumulocityPassword;
    public Properties defaultProps;

    public static void main(String[] args) {
        try {
            Integrator integrator = new Integrator();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integrator() throws IOException{

        defaultProps = new Properties();
        FileInputStream in = new FileInputStream("integrator.properties");
        defaultProps.load(in);
        in.close();

        cumulocityDomain = String.format("https://%s.cumulocity.com", defaultProps.getProperty("cumulocity_domain"));
        cumulocityUsername = defaultProps.getProperty("cumulocity_username");
        cumulocityPassword = defaultProps.getProperty("cumulocity_password");

        authorisationUrl = new URL(defaultProps.getProperty("authorisation_url"));
        serviceDiscoveryUrl = new URL(defaultProps.getProperty("service_discovery_url"));

        cloudDriver = new CumulocityDriver(cumulocityDomain, cumulocityPassword, cumulocityUsername);
        ahfBridge = new AhfBridge(authorisationUrl, serviceDiscoveryUrl);
        clk = new Clock(intervall, this);
        servicesModel = new ServicesModel(cloudDriver, ahfBridge);
    }

    public void update(Observable observable, Object o) {
        servicesModel.updateServices();
        servicesModel.compareCloudServices();
    }
}
