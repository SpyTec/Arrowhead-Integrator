package arrowhead_cloud_integrator;

import arrowhead_cloud_integrator.ahf_interface.AhfBridge;
import arrowhead_cloud_integrator.iot_cloud_drivers.IoTCloudDriver;

public class ServicesModel {
    private AhfBridge ahfBridge;
    private IoTCloudDriver cloudDriver;
    private Service[] ahfServices;
    private Service[] iotCloudServices;


    public ServicesModel(IoTCloudDriver cloudDriver, AhfBridge ahfBridge) {
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
        for (Service aTemp : temp) {
            for (Service ahfService : ahfServices) {
                if (aTemp.equals(ahfService)) {
                    break;
                }
            }
            updateService(aTemp); //ADDDDDD TOOO LOCAL (CUMULOCUTY)
        }

        for (Service ahfService : ahfServices) {
            for (Service aTemp : temp) {
                if (ahfService.equals(aTemp)) {
                    break;
                }
            }
            updateService(ahfService); //REEEEMOVE FROOOM LOCAL (CUMULOCUTY)
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
