package com.redhat.camel.components;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class QuartzCronExample {

    public static void main(String args[]) throws Exception {
        // create CamelContext
        CamelContext context = new DefaultCamelContext();

        // add our route to the CamelContext
        context.addRoutes(new RouteBuilder() {
            public void configure() {
            	// the cron expression is the same as on Unix like systems
            	// but replacing white spaces with plus symbol (+)
                from("quartz://report?cron=0/2+*+*+*+*+?")
                .setBody().simple("I was fired at ${header.fireTime}")
                .to("stream:out");
            }
        });

        // start the route and let it do its work
        context.start();
        Thread.sleep(10000);

        // stop the CamelContext
        context.stop();
    }
    
}