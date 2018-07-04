package soajp.client.soap;

import java.io.*;
import java.net.*;

public class TestClient {

    public static void main(String[] args) {
        SOAPClient client = new SOAPClient();

        try {
            client.getCustomer("1001");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
