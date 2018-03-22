package arrowhead_cloud_integrator.iot_cloud_drivers;

import arrowhead_cloud_integrator.Service;
import c8y.IsDevice;
import com.cumulocity.model.authentication.CumulocityCredentials;
import com.cumulocity.model.idtype.GId;
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;
import com.cumulocity.sdk.client.Platform;
import com.cumulocity.sdk.client.PlatformImpl;
import com.cumulocity.sdk.client.SDKException;
import com.cumulocity.sdk.client.inventory.InventoryApi;
import com.cumulocity.sdk.client.inventory.InventoryFilter;
import com.cumulocity.sdk.client.inventory.ManagedObjectCollection;

import java.util.ArrayList;

/**
 * Driver to manage Cumulocity CRUD.
 */
public class CumulocityDriver implements IoTCloudDriver {
    /**
     * URL of account specific domain.
     */
    private String cumulocityURL;
    /**
     * Password credentials.
     */
    private String accPass;
    /**
     * Username credentials.
     */
    private String accUser;
    /**
     * Cumulocity Platform SDK for receiving APIs.
     */
    private Platform platform;
    /**
     * API to manage and register devices on Cumulocity.
     */
    private InventoryApi inventory;

    /**
     * @param cumulocityURL URL of account specific Cumulocity domain.
     * @param accPass Password for Cumulocity account
     * @param accUser Username for Cumulocity account
     */
    public CumulocityDriver(String cumulocityURL, String accPass, String accUser){
        this.cumulocityURL = cumulocityURL;
        this.accPass = accPass;
        this.accUser = accUser;

        this.platform = new PlatformImpl(this.cumulocityURL, new CumulocityCredentials(this.accUser, this.accPass));
        this.inventory = platform.getInventoryApi();
    }


    /**
     * {@inheritDoc}
     */
    public ArrayList<Service> getAll() {
        ArrayList<Service> services = new ArrayList<Service>();
        InventoryFilter filter = new InventoryFilter();
        ManagedObjectCollection moc = this.inventory.getManagedObjectsByFilter(filter);
        for (ManagedObjectRepresentation mo : moc.get().allPages()){
            if(mo.get("group") != null && mo.get("group").equals("ahf")){
                services.add(parseService(mo));
            }
        }
        return services;
    }

    /**
     * {@inheritDoc}
     */
    public boolean publish(Service service) {
        ManagedObjectRepresentation mo = new ManagedObjectRepresentation();
        mo.setName(service.getName());
        mo.setType(service.getType());
        mo.set(new IsDevice());
        mo.setProperty("port", service.getPort());
        mo.setProperty("domain", service.getDomain());
        mo.setProperty("host", service.getHost());
        mo.setProperty("group", "ahf");
        mo = inventory.create(mo);
        if(mo.getSelf() != null){
            service.setCloudID(mo.getId().getValue());
            return true;
        }
        return false;
    }


    /**
     * {@inheritDoc}
     */
    public boolean unpublish(Service service) {
        if(service.getCloudID() != null) {
            GId id = new GId(service.getCloudID());
            inventory.delete(id);
            try {
                inventory.get(id);
            }
            catch (SDKException e) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean update(Service service) {
        return false;
    }


    /**
     * Parse a Cumulocity Managed Object to a Service
     *
     * @param mo ManagedObjectRepresentation to parse
     * @return Parsed Service
     */
    private Service parseService(ManagedObjectRepresentation mo){
        Service service = new Service();
        service.setDomain(mo.get("domain").toString());
        service.setHost(mo.get("host").toString());
        service.setPort(mo.get("port").toString());
        service.setName(mo.getName());
        service.setType(mo.getType());
        service.setHasBeenPublishedToCloud(true);
        return service;
    }
}
