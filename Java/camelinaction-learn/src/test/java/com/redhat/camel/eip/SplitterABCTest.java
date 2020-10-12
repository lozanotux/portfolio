package com.redhat.camel.eip;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class SplitterABCTest extends CamelTestSupport {

	@Test
	public void testSplitABC() throws Exception {
		MockEndpoint mock = getMockEndpoint("mock:split");
		mock.expectedBodiesReceived("A", "B", "C");
		
		List<String> body = new ArrayList<String>();
		body.add("A");
		body.add("B");
		body.add("C");
		
		template.sendBody("direct:start", body);
		
		assertMockEndpointsSatisfied();
	}
	
	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		return new RouteBuilder() {
			public void configure() throws Exception {
				from("direct:start")
				.split(body())
					.log("Splited line ${body}")
					.to("mock:split");
			}
		};
	}
	
}
