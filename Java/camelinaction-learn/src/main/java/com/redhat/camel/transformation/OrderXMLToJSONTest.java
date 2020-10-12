package com.redhat.camel.transformation;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class OrderXMLToJSONTest {

	public static void main(String args[]) throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			public void configure() {
				from("file:data/inbox?noop=true")
				.marshal("xmljson")
				.process(new Processor() {
					public void process(Exchange exchange) {
						System.out.println("JSON marshalled: " + exchange.getIn().getBody(String.class));
					}
				})
				.end();
			}
		});
		
		context.start();
		Thread.sleep(10000);
		context.stop();
	}
	
}
