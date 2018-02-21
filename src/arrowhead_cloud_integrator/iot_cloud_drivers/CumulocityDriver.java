package arrowhead_cloud_integrator.iot_cloud_drivers;

import arrowhead_cloud_integrator.Service;

import java.net.URL;

public class CumulocityDriver implements IoTCloudDriver {
    private URL cumulocityURL;
    private String accPass;
    private String accUser;

    public CumulocityDriver(URL cumulocityURL, String accPass, String accUser){
        this.cumulocityURL = cumulocityURL;
        this.accPass = accPass;
        this.accUser = accUser;
    }

    @Override
    public Service[] getAll() {
        return null;
    }

    @Override
    public Service publish(Service service) {
        return null;
    }

    @Override
    public boolean unpublish(Service service) {
        return false;
    }

    @Override
    public boolean update(Service service) {
        return false;
    }
}
