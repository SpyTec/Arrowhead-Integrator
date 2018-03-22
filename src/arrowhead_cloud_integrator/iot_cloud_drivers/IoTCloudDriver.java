package arrowhead_cloud_integrator.iot_cloud_drivers;

import arrowhead_cloud_integrator.Service;

import java.util.ArrayList;

/**
 * Interface to manage CRUD with a IoT cloud provider.
 */
public interface IoTCloudDriver {

    /**
     * Get all the registered services to be synchronized with Ahf.
     *
     * @return List of registered Services.
     */
    public ArrayList<Service> getAll();

    /**
     * Publish a Service to IoT cloud.
     *
     * @param service to be published.
     * @return true if successful, false if not.
     */
    public boolean publish(Service service);

    /**
     * Unpublish a service from IoT cloud.
     *
     * @param service to be unpublished.
     * @return true if unpublished, false if not.
     */
    public boolean unpublish(Service service);

    /**
     * Update specific service with changed properties of Service instance.
     *
     * @param service that has changed.
     * @return true if changed, false if not.
     */
    public boolean update(Service service);
}
