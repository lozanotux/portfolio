package com.redhat.camel.components;

import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RequestReplyJmsTest extends CamelSpringTestSupport {
	
	@Override
    protected AbstractXmlApplicationContext createApplicationContext() {
        // load the spring XML file from this classpath
        return new ClassPathXmlApplicationContext("jms-camelcontext.xml");
    }
	
	@Test
	public void testPublishSubscribeModel() throws Exception {
		context.setTracing(true);
		
		MockEndpoint accounting = getMockEndpoint("mock:accounting");
		MockEndpoint production = getMockEndpoint("mock:production");
		
		accounting.expectedMessageCount(1);
		production.expectedMessageCount(1);
		
		Thread.sleep(2000);
		
		accounting.assertIsSatisfied();
		production.assertIsSatisfied();
	}

}
