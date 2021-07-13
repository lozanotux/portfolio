package com.redhat.camel.components;

import static org.apache.camel.component.jms.JmsComponent.jmsComponentClientAcknowledge;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class MinaTCPTest extends CamelTestSupport {
	
	private static final String STATUS_GOOD = "MachineID=2371748;Status=Good";

	@Override
	protected CamelContext createCamelContext() throws Exception {
		CamelContext context = super.createCamelContext();
		
		// Need to add a JMS component for ActiveMQ
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
		context.addComponent("ajms", jmsComponentClientAcknowledge(connectionFactory));
		
		return context;
	}
	
	// So test a Route
	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("mina:tcp://localhost:8999?textline=true&sync=false")
					.to("ajms:operations");
                
                from("ajms:operations")
                	.to("mock:end");
			}
		};
	}
	
	@Test
	public void testSendToTCP() throws Exception {
		MockEndpoint mock = getMockEndpoint("mock:end");
		// Set the expectations
		mock.expectedBodiesReceived(STATUS_GOOD);
		
		// Trigger the route
		template.sendBody("mina:tcp://localhost:8999?textline=true&sync=false", STATUS_GOOD);
		
		mock.assertIsSatisfied();
	}
	
}