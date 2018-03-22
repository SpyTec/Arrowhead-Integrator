package arrowhead_cloud_integrator.ahf_interface;

import arrowhead_cloud_integrator.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * XML Handler and formatter for AHF.
 */
public class XMLHandler {

    public void XMLHandler(){

    }

    /**
     * Transform Document to string.
     *
     * @param doc Doc to be transformed.
     * @return string of the document.
     */
    public String docToString(Document doc){
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString();
            return output;
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Generate a Document from a string
     *
     * @param xmlStr string to transform to Document
     * @return Formatted Document
     */
    public Document strToDoc(String xmlStr){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) );
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Parse Document to extract a list of Service instances.
     *
     * @param doc Document to extract services from.
     * @return List of services extracted.
     */
    public ArrayList<Service> dataParser(Document doc){

        NodeList nList = doc.getElementsByTagName("service");
        ArrayList<Service>  services = new ArrayList<Service>();

        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);


            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                Service service = new Service();
                service.setDomain(eElement
                        .getElementsByTagName("domain")
                        .item(0).getTextContent());
                service.setHost(eElement
                        .getElementsByTagName("host")
                        .item(0).getTextContent());
                service.setName(eElement
                        .getElementsByTagName("name")
                        .item(0).getTextContent().replaceAll("."+eElement
                                .getElementsByTagName("type")
                                .item(0)
                                .getTextContent(),"").replaceAll(".srv.docker.ahf.",""));
                service.setPort(eElement
                        .getElementsByTagName("port")
                        .item(0).getTextContent());
                service.setType(eElement
                        .getElementsByTagName("type")
                        .item(0)
                        .getTextContent());
                service.setHasBeenPublishedToAhf(true);
                services.add(service);
            }
        }
        return services;
    }
}
