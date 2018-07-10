package com.demo.testjersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/user")
public class UserResource {
    
    @GET
    @Path("/getName")
    @Produces(MediaType.TEXT_PLAIN)
    public String getName(@QueryParam("name") String name) {
        return "Bienvenido " + name;
    }
    
}
