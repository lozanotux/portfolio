package com.redhat.camel.pipeline;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class FileToLogExample {
	
	public static void main(String args[]) throws Exception {
        
		// create CamelContext
        CamelContext context = new DefaultCamelContext();
        
        // add our route to the CamelContext
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from("file:/tmp/in")
                .process(new CustomProcessor())
            	.to("log:incomingOrders");
            }
        });

        // start the route and let it do its work
        context.start();
        Thread.sleep(10000);

        // stop the CamelContext
        context.stop();
    }

}
