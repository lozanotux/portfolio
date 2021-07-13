package com.redhat.camel.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CXFTest extends CamelSpringTestSupport{

	@Override
    protected AbstractXmlApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("META-INF/spring/cxf-route.xml");
    }
	
	@Test
    public void testOrderOk() throws Exception {
        List<Object> params = new ArrayList<Object>();
        params.add("motor");
        params.add(1);
        params.add("honda");
        
        String reply = template.requestBody("cxf:bean:orderEndpoint", params, String.class);
        assertEquals("OK", reply);
    }
	
}
