package arrowhead_cloud_integrator;

import arrowhead_cloud_integrator.ahf_interface.AHFBridge;
import arrowhead_cloud_integrator.iot_cloud_drivers.IoTCloudDriver;

import java.net.URL;

public class ServicesModel {
    private AHFBridge ahfBridge;
    private IoTCloudDriver cloudDriver;
    private Service[] ahfServices;
    private Service[] iotCloudServices;


    public ServicesModel(IoTCloudDriver cloudDriver, AHFBridge ahfBridge) {
        this.ahfBridge = ahfBridge;
        this.cloudDriver = cloudDriver;
    }

    private Service[] getAHFServices() {
        ahfBridge.getAll();
        return null;
    }

    private void getCumulocityServices() {
        cloudDriver.getAll();
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
