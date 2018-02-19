package bridge;

import java.net.URL;

public class CumulocityBridge implements Bridge{
    private URL cumulocityURL;
    private String accPass;
    private String accUser;

    public CumulocityBridge(URL cumulocityURL, String accPass, String accUser){
        this.cumulocityURL = cumulocityURL;
        this.accPass = accPass;
        this.accUser = accUser;
    }

    public void getAll() {

    }

    public void publish() {

    }

    public void unpublish() {

    }

    public void update() {

    }
}
