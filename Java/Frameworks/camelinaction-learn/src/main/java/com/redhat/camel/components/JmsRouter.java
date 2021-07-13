package com.redhat.camel.components;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class JmsRouter extends RouteBuilder {

	@Override
    public void configure() {
        // load file orders from src/data into the JMS queue
        from("file:src/data?noop=true").to("jms:incomingOrders");

        // content-based router
        from("jms:incomingOrders")
        .choice()
            .when(header("CamelFileName").endsWith(".xml"))
                .to("jms:topic:xmlOrders")  
            .when(header("CamelFileName").endsWith(".csv"))
                .to("jms:topic:csvOrders");

        from("jms:topic:xmlOrders").to("jms:accounting");  
        from("jms:topic:xmlOrders").to("jms:production");  
        
        // test that our route is working
        from("jms:accounting").process(new Processor() {
            public void process(Exchange exchange) throws Exception {
                System.out.println("Accounting received order: "
                        + exchange.getIn().getHeader("CamelFileName"));  
            }
        })
        .to("mock:accounting");
        from("jms:production").process(new Processor() {
            public void process(Exchange exchange) throws Exception {
                System.out.println("Production received order: "
                        + exchange.getIn().getHeader("CamelFileName"));  
            }
        })
        .to("mock:production");
    }
	
}