package model;

import bridge.AHFBridge;
import bridge.CumulocityBridge;
import comm.Service;

import java.net.URL;

public class Comparator {
    private AHFBridge ahfBridge;
    private CumulocityBridge cumulocityBridge;
    private Service[] ahfServices;
    private int[] cumulocityDevices; //should take device type (NOT INT)
    private URL sdURL;
    private URL authURL;
    private URL cumulocityURL;
    private String accPass;
    private String accUSER;


    public Comparator(URL sdURL, URL authURL, URL cumulocityURL, String accPass, String accUSER) {
        this.sdURL = sdURL;
        this.authURL = authURL;
        this.cumulocityURL = cumulocityURL;
        this.accPass = accPass;
        this.accUSER = accUSER;
        this.ahfBridge = new AHFBridge(this.authURL, this.sdURL);
        this.cumulocityBridge = new CumulocityBridge(this.cumulocityURL, this.accPass, this.accUSER);
    }

    private Service[] getAHFServices() {
        ahfBridge.getAll();
    }

    private void getCumulocityServices() {
        cumulocityBridge.getAll();
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
