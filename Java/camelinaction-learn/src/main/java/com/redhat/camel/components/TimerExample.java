package com.redhat.camel.components;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class TimerExample {

    public static void main(String args[]) throws Exception {
        // create CamelContext
        CamelContext context = new DefaultCamelContext();

        // add our route to the CamelContext
        context.addRoutes(new RouteBuilder() {
            public void configure() {
            	// the period can be used in milliseconds or using keywords:
            	// h for hours, m for minutes and s for seconds
            	// for example: 1m20s (90000 milliseconds) or 2s (2000 milliseconds)
                from("timer://myTimer?period=2s")
                .setBody().simple("Current time is ${header.firedTime}")
                .to("stream:out");
            }
        });

        // start the route and let it do its work
        context.start();
        Thread.sleep(5000);

        // stop the CamelContext
        context.stop();
    }
    
}