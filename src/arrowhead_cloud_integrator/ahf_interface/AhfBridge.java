package arrowhead_cloud_integrator.ahf_interface;


import arrowhead_cloud_integrator.Service;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AhfBridge {
    private URL authorisationUrl;
    private URL serviceDiscoveryUrl;
    private AhfRouter router = new AhfRouter();
    private XMLHandler handler = new XMLHandler();

    public AhfBridge(URL authURL, URL sdURL){
        this.authorisationUrl = authURL;
        this.serviceDiscoveryUrl = sdURL;
    }


    public ArrayList<Service> getAll() {

        try {
            return handler.dataParser(handler.strToDoc(router.httpGet(new URL (serviceDiscoveryUrl + "service"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void publish(Service s) {
        authAdd(s);
        serviceReg(s);
    }

    public void unpublish(Service s) {
        authRem(s);
        serviceRemove(s);

    }

    public void update() {

    }

    private void authReq(){

    }

    private void serviceReg(Service s){
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
            //URL test = new URL(sdURL+"publish");
            router.httpPost( new URL(serviceDiscoveryUrl+"publish"),"application/xml", ServiceString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void serviceRemove(Service s){
        String ServiceString = "<service>\n" +
                "    <name>" + s.getName() + "</name>\n" +
                "</service>";
        try {
            router.httpPost( new URL(serviceDiscoveryUrl+"unpublish"),"application/xml", ServiceString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void authAdd(Service s){
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
        router.httpPost(authorisationUrl,"text/xml", ServiceString);

    }

    private void authRem(Service s){
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
        router.httpPost(authorisationUrl,"text/xml", ServiceString);
    }
}
