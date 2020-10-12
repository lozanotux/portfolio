package com.redhat.camel.routing;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class OrderRouterWithMulticast {
	
	public static void main(String args[]) throws Exception {
        // create CamelContext
        CamelContext context = new DefaultCamelContext();
        
        // connect to embedded ActiveMQ JMS broker
        ConnectionFactory connectionFactory = 
            new ActiveMQConnectionFactory("vm://localhost");
        context.addComponent("jms",
            JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        // add our route to the CamelContext
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                // load file orders from src/data into the JMS queue
                from("file:src/data?noop=true").to("jms:incomingOrders");
        
                // content-based router
                from("jms:incomingOrders")
                .wireTap("log:receivedMessage") // For monitoring purposes
                .choice()
                    .when(header("CamelFileName").endsWith(".xml"))
                        .to("jms:xmlOrders")  
                    .when(header("CamelFileName").regex("^.*(csv|csl)$"))
                        .to("jms:csvOrders")
                    .otherwise()
                        .to("jms:badOrders");        
                
                from("jms:xmlOrders").multicast().to("jms:accounting", "jms:production");
                
                // test that our route is working
                from("jms:accounting").process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Accounting received order: " 
                                + exchange.getIn().getHeader("CamelFileName"));   
                    }
                });                
                from("jms:production").process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Production received order: " 
                                + exchange.getIn().getHeader("CamelFileName"));   
                    }
                });
            }
        });

        // start the route and let it do its work
        context.start();
        Thread.sleep(2000);

        // stop the CamelContext
        context.stop();
    }

}
