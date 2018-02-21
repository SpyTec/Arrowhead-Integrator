package arrowhead_cloud_integrator.iot_cloud_drivers;

import arrowhead_cloud_integrator.Service;

/**
 * Created by Hultstrand on 2018-02-21.
 */
public interface IoTCloudDriver {

    public Service[] getAll();

    public Service publish(Service service);

    public boolean unpublish(Service service);

    public boolean update(Service service);
}
