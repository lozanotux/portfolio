package com.redhat.camel.eip;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class AggregateABCTest extends CamelTestSupport {
	
	@Test
	public void testABC() throws Exception {
		MockEndpoint mock = getMockEndpoint("mock:result");
		
		// we expect ABC in the published message
        // notice: Only 1 message is expected
        mock.expectedBodiesReceived("ABC");
        
        // send the first message
        template.sendBodyAndHeader("direct:start", "A", "myId", 1);
        // send the 2nd message with the same correlation key
        template.sendBodyAndHeader("direct:start", "B", "myId", 1);
        // the F message has another correlation key
        template.sendBodyAndHeader("direct:start", "F", "myId", 2);
        // now we have 3 messages with the same correlation key
        // and the Aggregator should publish the message
        template.sendBodyAndHeader("direct:start", "C", "myId", 1);

        assertMockEndpointsSatisfied();
	}

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("direct:start")
				// do a little logging
				.log("Sending ${body} with correlation key ${header.myId}")
				// aggregate based on header correlation key
				// use class MyAggregationStrategy for aggregation
				// and complete when we have aggregated 3 messages
				.aggregate(header("myId"), new MyAggregationStrategy()).completionSize(3)
					// do a little logging
					.log("Sending out ${body}")
					// and send it to the mock
					.to("mock:result");
			}
		};
	}
	
}
