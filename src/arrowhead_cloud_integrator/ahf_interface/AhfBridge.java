package arrowhead_cloud_integrator.ahf_interface;


import arrowhead_cloud_integrator.Service;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Bridge to establish CRUD with Ahf.
 */
public class AhfBridge {
    /**
     * URL for authorising with Ahf
     */
    private URL authorisationUrl;
    /**
     * URL for Ahf Service Discovery
     */
    private URL serviceDiscoveryUrl;
    /**
     * Router for managing connection with Ahf
     */
    private AhfRouter router = new AhfRouter();
    /**
     * XML handler to format response and requests with Ahf.
     */
    private XMLHandler handler = new XMLHandler();

    /**
     * @param authURL Authorisation URL.
     * @param sdURL   Service Discovery URL.
     */
    public AhfBridge(URL authURL, URL sdURL) {
        this.authorisationUrl = authURL;
        this.serviceDiscoveryUrl = sdURL;
    }

    /**
     * @return list of all Ahf services, null if empty.
     */
    public ArrayList<Service> getAll() {

        try {
            return handler.dataParser(handler.strToDoc(router.httpGet(new URL(serviceDiscoveryUrl + "service"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Publish a service to AHF.
     *
     * @param s service to publish.
     */
    public void publish(Service s) {
        authAdd(s);
        serviceReg(s);
    }

    /**
     * Unpublish a service.
     *
     * @param s service to unpublish.
     */
    public void unpublish(Service s) {
        authRem(s);
        serviceRemove(s);
    }

    /**
     * Register a new service on AHF.
     *
     * @param s service to register.
     */
    private void serviceReg(Service s) {
        // Format to XML to send to AHF
        String ServiceString = "<service> \n" +
                "    <domain>" + s.getDomain() + "</domain>\n" +
                "    <host>" + s.getHost() + "</host>\n" +
                "    <name>" + s.getName() + "</name>\n" +
                "    <port>" + s.getPort() + "</port>\n" +
                "    <properties>\n" +
                "    <property>\n" +
                "       <name>version</name>\n" +
                "       <value>1.0</value>\n" +
                "    </property>\n" +
                "    <property>\n" +
                "       <name>path</name>\n" +
                "       <value>hello</value>\n" +
                "    </property>\n" +
                "    </properties>\n" +
                "    <type>" + s.getType() + "._tcp</type>\n" +
                "</service>";
        try {
            router.httpPost(new URL(serviceDiscoveryUrl + "publish"), "application/xml", ServiceString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove service from Ahf.
     *
     * @param s service to remove.
     */
    private void serviceRemove(Service s) {
        String ServiceString = "<service>\n" +
                "    <name>" + s.getName() + "</name>\n" +
                "</service>";
        try {
            router.httpPost(new URL(serviceDiscoveryUrl + "unpublish"), "application/xml", ServiceString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add authorization rule to Ahf for specific service.
     *
     * @param s service to add authorization rule for.
     */
    private void authAdd(Service s) {
        String ServiceString = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "                  xmlns:aut=\"http://arrowhead.eu/authorisation\">\n" +
                "    <soapenv:Header/>\n" +
                "    <soapenv:Body>\n" +
                "        <aut:addAuthorisationRule>\n" +
                "            <name>" + s.getName() + "</name>\n" +
                "            <type>" + s.getType() + "</type>\n" +
                "            <distinguishedNameSuffix>CN=" + s.getName() + "</distinguishedNameSuffix>\n" + //Name for now
                "        </aut:addAuthorisationRule>\n" +
                "    </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        router.httpPost(authorisationUrl, "text/xml", ServiceString);
    }

    /**
     * Remove authorization rule for specific service.
     *
     * @param s service to remove authorization rule for.
     */
    private void authRem(Service s) {
        String ServiceString = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "                  xmlns:aut=\"http://arrowhead.eu/authorisation\">\n" +
                "<soapenv:Header/>\n" +
                "    <soapenv:Body>\n" +
                "        <aut:removeAuthorisationRule>\n" +
                "             <name>" + s.getName() + "</name>\n" +
                "            <type>" + s.getType() + "</type>\n" +
                "            <distinguishedNameSuffix>CN=" + s.getName() + "</distinguishedNameSuffix>\n" + //Name for now
                "        </aut:removeAuthorisationRule>\n" +
                "    </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        router.httpPost(authorisationUrl, "text/xml", ServiceString);
    }
}