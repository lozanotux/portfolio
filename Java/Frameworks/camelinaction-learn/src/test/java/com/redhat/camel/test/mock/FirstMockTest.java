package com.redhat.camel.test.mock;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class FirstMockTest extends CamelTestSupport {
	
	@Override
	protected CamelContext createCamelContext() throws Exception {
		CamelContext context = super.createCamelContext();
		/*
		 * Replace JMS with SEDA which we can do in this case as seda is a very very basic
		 * in memory JMS broker ;). This is of course only possible to switch for a few components.
		 */
        context.addComponent("jms", context.getComponent("seda"));
        return context;
	}

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("jms:topic:quote")
					.to("mock:quote");
			}
		};
	}
	
	@Test
	public void testQuote() throws Exception {
		MockEndpoint quote = getMockEndpoint("mock:quote");
		quote.expectedMessageCount(1);
		
		template.sendBody("jms:topic:quote", "Camel rocks");
		
		quote.assertIsSatisfied();
	}
	
	@Test
	public void testSameMessageArrived() throws Exception {
		MockEndpoint quote = getMockEndpoint("mock:quote");
		
		// set expectations that the same message arrived as we send
        quote.expectedBodiesReceived("Camel rocks");

        // fire in a message to Camel
        template.sendBody("jms:topic:quote", "Camel rocks");

        // verify the result
        quote.assertIsSatisfied();
	}
	
	@Test
    public void testTwoMessagesOrdered() throws Exception {
        // get the mock endpoint
        MockEndpoint quote = getMockEndpoint("mock:quote");
        // set expectations the two messages arrives in specified order
        quote.expectedBodiesReceived("Hello Camel", "Camel rocks");

        // fire in a messages to Camel
        template.sendBody("jms:topic:quote", "Hello Camel");
        template.sendBody("jms:topic:quote", "Camel rocks");

        // verify the result
        quote.assertIsSatisfied();
    }

    @Test
    public void testContains() throws Exception {
        // get the mock endpoint
        MockEndpoint quote = getMockEndpoint("mock:quote");
        // set expectations the two messages arrives in specified order
        quote.expectedMessageCount(2);
        // all messages should contain the Camel word
        quote.allMessages().body().contains("Camel");

        // fire in a messages to Camel
        template.sendBody("jms:topic:quote", "Hello Camel");
        template.sendBody("jms:topic:quote", "Camel rocks");

        // verify the result
        quote.assertIsSatisfied();
    }
	
}