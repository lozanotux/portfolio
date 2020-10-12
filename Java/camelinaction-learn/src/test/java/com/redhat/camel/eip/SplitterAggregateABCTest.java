package com.redhat.camel.eip;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class SplitterAggregateABCTest extends CamelTestSupport {

	@Test
	public void testSplitAggregateABC() throws Exception {
		MockEndpoint split = getMockEndpoint("mock:split");
        // we expect 3 messages to be split and translated into a quote
        split.expectedBodiesReceived("Camel rocks", "Hi mom", "Yes it works");

        MockEndpoint result = getMockEndpoint("mock:result");
        // and one combined aggregated message as output with all the quotes together
        result.expectedBodiesReceived("Camel rocks+Hi mom+Yes it works");

        template.sendBody("direct:start", "A,B,C");

        assertMockEndpointsSatisfied();
	}
	
	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		return new RouteBuilder() {
			public void configure() throws Exception {
				from("direct:start")
					// tell Splitter to use the aggregation strategy
					.split(body(), new MySplitterStrategy())
						// log each splitted message
						.log("Splited line: ${body}")
						// and have them translated into a quote
						.bean(WordTranslateBean.class)
						// and send it to a mock
	                    .to("mock:split")
	                .end()
					// log the outgoing aggregated message
	                .log("Aggregated ${body}")
	                // and send it to a mock as well
	                .to("mock:result");
			}
		};
	}
	
}
