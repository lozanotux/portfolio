package com.redhat.greeting;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("/hello")
public class GreetingResource {

    @ConfigProperty(name = "github.api.key.token")
    String githubToken;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }

    @GET
    @Path("/secret")
    @Produces(MediaType.TEXT_PLAIN)
    public String ghToken() {
        return githubToken;
    }

    @GET
    @Path("/file")
    @Produces(MediaType.TEXT_PLAIN)
    public String ghTokenFile() throws IOException {
        final byte[] encodedToken = Files.readAllBytes(
                Paths.get("/deployment/github/github.api.key.token")
        );

        return new String(encodedToken);
    }
}