package arrowhead_cloud_integrator.iot_cloud_drivers;

import arrowhead_cloud_integrator.Service;

import java.util.ArrayList;

/**
 * Created by Hultstrand on 2018-02-21.
 */
public interface IoTCloudDriver {

    public ArrayList<Service> getAll();

    public boolean publish(Service service);

    public boolean unpublish(Service service);

    public boolean update(Service service);
}
