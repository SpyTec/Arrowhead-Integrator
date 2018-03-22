package arrowhead_cloud_integrator;

/**
 * Model to represent devices and services within AHF and IoT cloud.
 */
public class Service {
    /**
     * Domain the service belongs to.
     */
    private String domain;
    /**
     * Host name.
     */
    private String host;
    /**
     * Name of the service.
     */
    private String name;
    /**
     * Port on AHF.
     */
    private String port;
    /**
     * Type of service.
     */
    private String type;
    /**
     * Cloud specific ID for accessibility.
     */
    private String cloudID;
    /**
     * Toggled if service has been published to AHF
     */
    private boolean hasBeenPublishedToAhf;
    /**
     * Toggled if service has been published to IoT cloud
     */
    private boolean hasBeenPublishedToCloud;

    /**
     * @return Unique identifier with relevant information
     */
    @Override
    public String toString() {
        return domain + host + name + port + type;
    }

    /**
     * @return Domain used.
     */
    public String getDomain() {
        return this.domain;
    }

    /**
     * @return Host name used.
     */
    public String getHost() {
        return this.host;
    }

    /**
     * @return Name of the service.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return Port used for service within AHF
     */
    public String getPort() {
        return this.port;
    }

    /**
     * @return Type of service
     */
    public String getType() {
        return this.type;
    }

    /**
     * @param domain Set the domain of the service.
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * @param host Set the host of the service.
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @param name Set the name of the service.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param port Set the AHF port of the service
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @param type Set the type of service
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return Get the Cloud ID identifier.
     */
    public String getCloudID() {
        return cloudID;
    }

    /**
     * @param cloudID Set the Cloud ID identifier.
     */
    public void setCloudID(String cloudID) {
        this.cloudID = cloudID;
    }

    /**
     * @return Whether or not the service has been published to Ahf
     */
    public boolean hasBeenPublishedToAhf() {
        return hasBeenPublishedToAhf;
    }

    /**
     * @param hasBeenPublishedToAhf Whether or not the service has been published to Ahf
     */
    public void setHasBeenPublishedToAhf(boolean hasBeenPublishedToAhf) {
        this.hasBeenPublishedToAhf = hasBeenPublishedToAhf;
    }

    /**
     * @return Whether or not the service has been published to IoT Cloud
     */
    public boolean hasBeenPublishedToCloud() {
        return hasBeenPublishedToCloud;
    }

    /**
     * @param hasBeenPublishedToCloud Whether or not the service has been published to IoT Cloud
     */
    public void setHasBeenPublishedToCloud(boolean hasBeenPublishedToCloud) {
        this.hasBeenPublishedToCloud = hasBeenPublishedToCloud;
    }
}
