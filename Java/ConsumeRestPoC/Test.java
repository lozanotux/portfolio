package com.oracle.restpocs;

import javax.ws.rs.client.*;

public class Test {
    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        
        String name = client.target("http://samples.openweathermap.org/data/2.5/weather")
                              .queryParam("q", "London,uk")
                              .queryParam("appid","b1b15e88fa797225412429c1c50c122a1")
                              .request()
                              .get(String.class);
        System.out.println(name);
        client.close();
    }
}