package com.demo.testjersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserClient {
    
    @GET
    @Path("/client")
    @Produces(MediaType.APPLICATION_JSON)
    public String getName(@QueryParam("name") String name) {
        
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/testJersey/webapi/user/getName?name=" + name);
        
        Response response = target.request().get();
        String data = response.readEntity(String.class);
        
        
        //return "[CLIENT REQUEST: Method=@GET, Status=" + response.getStatus() + ", Data=" + data + "]";
        return "{"
                + "\"method\": \"@GET\","
                + "\"http_status\": " + response.getStatus() + ","
                + "\"data\": \"" + data + "\""
                + "}";
    }
    
}
