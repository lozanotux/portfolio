package com.redhat.camel.components;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class OrderRouterWithSeda {

	public static void main(String[] args) throws Exception {
		// 1st - Create a Camel runtime
		CamelContext context = new DefaultCamelContext();
		
		// 2nd - Connect to an embedded ActiveMQ JMS broker
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
		context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		
		// 3rd - Add a route to camel
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				// load file orders from src/data into the SEDA queue
				from("file:src/data?noop=true").to("seda:incomingOrders");
				
				// content-based route (EIP)
				from("seda:incomingOrders")
				.choice()
					.when(header("CamelFileName").endsWith(".xml"))
						.to("seda:xmlOrders")
					.when(header("CamelFileName").endsWith(".csv"))
						.to("seda:csvOrders");
				
				from("seda:xmlOrders?multipleConsumers=true").to("jms:accounting");
                from("seda:xmlOrders?multipleConsumers=true").to("jms:production");
                
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
        Thread.sleep(10000);

        // stop the CamelContext
        context.stop();
	}
	
}
