package arrowhead_cloud_integrator;

import arrowhead_cloud_integrator.ahf_interface.AhfBridge;
import arrowhead_cloud_integrator.iot_cloud_drivers.IoTCloudDriver;

import java.util.ArrayList;

/**
 * Services model to synchronize services between Ahf and IoT Cloud.
 */
public class ServicesModel {
    /**
     * Ahf bridge to manage CRUD with Ahf
     */
    private AhfBridge ahfBridge;
    /**
     * CRUD for IoT cloud driver
     */
    private IoTCloudDriver cloudDriver;
    /**
     * List of services synchronized with both AHF and IoT cloud.
     */
    private ArrayList<Service> services = new ArrayList<Service>();

    /**
     * @param cloudDriver Cloud driver to synchronize to.
     * @param ahfBridge   Ahf bridge to synchronize to.
     */
    public ServicesModel(IoTCloudDriver cloudDriver, AhfBridge ahfBridge) {
        this.ahfBridge = ahfBridge;
        this.cloudDriver = cloudDriver;
    }

    /**
     * @return All services available on Ahf.
     */
    private ArrayList<Service> getAHFServices() {
        return ahfBridge.getAll();
    }

    /**
     * @return All services available on IoT cloud.
     */
    private ArrayList<Service> getCloudServices() {
        return cloudDriver.getAll();
    }

    /**
     * Synchronize the services with Ahf and IoT cloud.
     */
    public void updateServices() {
        ArrayList<Service> ahfServices = getAHFServices();
        ArrayList<Service> cloudServices = getCloudServices();

        // Iterate through IoT cloud services to be added to Ahf
        for (Service cloudService : cloudServices) {
            boolean exists = false;
            for (Service ahfService : ahfServices) {
                if (cloudService.getName().equals(ahfService.getName())) {
                    if (!services.contains(cloudService)) {
                        exists = true;
                        break;
                    }
                }
            }
            if (!exists) {
                System.out.println("Adding to AHF");
                ahfBridge.publish(cloudService);
                services.add(cloudService);
            }
        }
        // Iterate through Ahf services to be added to IoT cloud
        for (Service ahfService : ahfServices) {
            boolean exists = false;
            for (Service cloudService : cloudServices) {
                if (cloudService.getName().equals(ahfService.getName())) {
                    if (!services.contains(ahfService)) {
                        exists = true;
                        break;
                    }
                }
            }
            if (!exists) {
                System.out.println("Adding to Cloud");
                cloudDriver.publish(ahfService);
                services.add(ahfService);
            }
        }
    }
}
