package arrowhead_cloud_integrator;

import arrowhead_cloud_integrator.ahf_interface.AHFBridge;
import arrowhead_cloud_integrator.iot_cloud_drivers.CumulocityDriver;

import java.net.URL;

public class ServicesModel {
    private AHFBridge ahfBridge;
    private CumulocityDriver cumulocityDriver;
    private Service[] ahfServices;
    private int[] cumulocityDevices; //should take device type (NOT INT)
    private URL sdURL;
    private URL authURL;
    private URL cumulocityURL;
    private String accPass;
    private String accUSER;


    public ServicesModel(URL sdURL, URL authURL, URL cumulocityURL, String accPass, String accUSER) {
        this.sdURL = sdURL;
        this.authURL = authURL;
        this.cumulocityURL = cumulocityURL;
        this.accPass = accPass;
        this.accUSER = accUSER;
        this.ahfBridge = new AHFBridge(this.authURL, this.sdURL);
        this.cumulocityDriver = new CumulocityDriver(this.cumulocityURL, this.accPass, this.accUSER);
    }

    private Service[] getAHFServices() {
        ahfBridge.getAll();
        return null;
    }

    private void getCumulocityServices() {
        cumulocityDriver.getAll();
    }

    public void updateServices() {
        Service[] temp = getAHFServices();
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < ahfServices.length; j++) {
                if (temp[i].equals(ahfServices[j])) {
                    break;
                }
            }
            updateService(temp[i]); //ADDDDDD TOOO LOCAL (CUMULOCUTY)
        }

        for (int i = 0; i < ahfServices.length; i++) {
            for (int j = 0; j < temp.length; j++) {
                if (ahfServices[i].equals(temp[j])) {
                    break;
                }
            }
            updateService(ahfServices[i]); //REEEEMOVE FROOOM LOCAL (CUMULOCUTY)
        }
    }

    public void compareCumulocity() {
        getCumulocityServices();
    }

    /* Should take a service as argument (NOT STRING) */
    private void updateService(Service serviceData) {

    }

    /* Should take a device as argument (NOT INT) */
    private void updateService(int deviceData) {

    }
}
