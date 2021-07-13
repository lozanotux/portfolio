package com.redhat.camel.beans;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

import junit.framework.TestCase;

public class SimpleRegistryTest extends TestCase {

	private CamelContext context;
	private ProducerTemplate template;
	
	protected void setUp() throws Exception {
		SimpleRegistry registry = new SimpleRegistry();
		registry.put("helloBean", HelloBean.class);
		
		context = new DefaultCamelContext(registry);
		template = context.createProducerTemplate();
		
		context.addRoutes(new RouteBuilder() {
			public void configure() throws Exception {
				from("direct:hello")
				.beanRef("helloBean");
			}
		});
		
		context.start();
	}
	
	protected void tearDown() throws Exception {
		template.stop();
		context.stop();
	}
	
	public void test_hello_route_with_world() throws Exception {
		Object reply = template.requestBody("direct:hello", "World");
		assertEquals("Hello World", reply);
	}
	
}
