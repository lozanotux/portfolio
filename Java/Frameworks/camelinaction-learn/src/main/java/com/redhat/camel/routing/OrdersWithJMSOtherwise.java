package com.redhat.camel.routing;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class OrdersWithJMSOtherwise {
	
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
                .choice()
                    .when(header("CamelFileName").endsWith(".xml"))
                        .to("jms:xmlOrders")  
                    .when(header("CamelFileName").regex("^.*(csv|csl)$"))
                        .to("jms:csvOrders")
                    .otherwise()
                        .to("jms:badOrders");// .stop() make the flow ends here
                // .end()
                // .to("jms:other") after choice() end, we can route the message to other destination
                
                // test that our route is working
                from("jms:xmlOrders").process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Received XML order: " 
                                + exchange.getIn().getHeader("CamelFileName"));   
                    }
                });                
                from("jms:csvOrders").process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Received CSV order: " 
                                + exchange.getIn().getHeader("CamelFileName"));   
                    }
                });
                from("jms:badOrders").process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Received bad order: " 
                                + exchange.getIn().getHeader("CamelFileName"));   
                    }
                });                
            }
        });

        // start the route and let it do its work
        context.start();
        Thread.sleep(10000);

        // stop the CamelContext
        context.stop();
    }

}
