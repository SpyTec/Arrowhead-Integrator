package bridge;


import java.net.URL;

public class AHFBridge implements Bridge{
    private URL authURL;
    private URL sdURL;

    public AHFBridge(URL authURL, URL sdURL){
        this.authURL = authURL;
        this.sdURL = sdURL;
    }

    public void getAll() {

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
