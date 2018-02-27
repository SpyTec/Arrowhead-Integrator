package arrowhead_cloud_integrator.ahf_interface;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class AhfRouter {


    public void AhfRouter(){

    }

    public void httpPost(URL endpoint, String xmlType, String xmlString){
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) endpoint.openConnection();

        connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", xmlType);
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream()
            );

            wr.writeBytes(xmlString);
            wr.close();

        System.out.println("response code: " + connection.getResponseCode());

        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append(System.lineSeparator());
        }
        rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String httpGet(URL endpoint) throws IOException{
        StringBuilder result = new StringBuilder();
        URL url = endpoint;
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }
}
