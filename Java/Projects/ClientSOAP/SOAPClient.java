package soajp.client.soap;

import com.sun.org.apache.xml.internal.serialize.*;
import java.io.*;
import java.net.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class SOAPClient {
    
    private Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    //format the XML in your String
    public String formatXML(String unformattedXml) {
        try {
            Document document = parseXmlFile(unformattedXml);
            OutputFormat format;
            format = new OutputFormat(document);
            format.setIndenting(true);
            format.setIndent(3);
            format.setOmitXMLDeclaration(true);
            Writer out = new StringWriter();
            XMLSerializer serializer;
            serializer = new XMLSerializer(out, format);
            serializer.serialize(document);
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public String getCustomer(String customerID) throws MalformedURLException, IOException {
        //Code to make a webservice HTTP request
        String responseString = "";
        String outputString = "";
        String wsURL = "http://www.<host>:<port>/crm/CustomerService";
        URL url = new URL(wsURL);
        URLConnection connection = url.openConnection();
        HttpURLConnection httpConn = (HttpURLConnection)connection;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        String xmlInput =
        "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"http://www.<host>/wsdl/crm/CRMService/1/\">\n" +
        "    <soapenv:Header/>\n" +
        "    <soapenv:Body>\n" +
        "        <ns:get>\n" +
        "            <id>" + customerID + "</id>\n" +
        "        </ns:get>\n" +
        "    </soapenv:Body>\n" +
        "</soapenv:Envelope>";

        byte[] buffer;
        buffer = new byte[xmlInput.length()];
        buffer = xmlInput.getBytes();
        bout.write(buffer);
        byte[] b = bout.toByteArray();
        String SOAPAction = "get_action";
        
        // Set the appropriate HTTP parameters.
        httpConn.setRequestProperty("Content-Length",
        String.valueOf(b.length));
        httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        httpConn.setRequestProperty("SOAPAction", SOAPAction);
        httpConn.setRequestMethod("POST");
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
		
        //Write the content of the request to the outputstream of the HTTP Connection.
        try (OutputStream out = httpConn.getOutputStream()) {
            //Write the content of the request to the outputstream of the HTTP Connection.
            out.write(b);
            //Ready with sending the request.
        }

        //Read the response.
        InputStreamReader isr =
        new InputStreamReader(httpConn.getInputStream());
        BufferedReader in = new BufferedReader(isr);

        //Write the SOAP message response to a String.
        while ((responseString = in.readLine()) != null) {
        outputString = outputString + responseString;
        }
        
        //Parse the String output to a org.w3c.dom.Document and be able to reach every node with the org.w3c.dom API.
        Document document = parseXmlFile(outputString);
        NodeList nodeLst = document.getElementsByTagName("firstName");
        String customerName = nodeLst.item(0).getTextContent();
        System.out.println("Customer Name: " + customerName);

        //Write the SOAP message formatted to the console.
        String formattedSOAPResponse = formatXML(outputString);
        System.out.println(formattedSOAPResponse);
        return customerName;
    }
}
