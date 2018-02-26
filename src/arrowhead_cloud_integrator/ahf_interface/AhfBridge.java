package arrowhead_cloud_integrator.ahf_interface;


import arrowhead_cloud_integrator.Service;

import java.net.URL;
import java.util.ArrayList;

public class AhfBridge {
    private URL authURL;
    private URL sdURL;

    public AhfBridge(URL authURL, URL sdURL){
        this.authURL = authURL;
        this.sdURL = sdURL;
    }

    public ArrayList<Service> getAll() {
        return null;
    }

    public void publish() {

    }

    public void unpublish() {

    }

    public void update() {

    }

    private void authReg(){

    }

    private void serviceReg(){

    }

    private void serviceRemove(){

    }

    private void authAdd(){

    }

    private void authRem(){

    }
}
