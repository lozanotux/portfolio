package com.redhat.camel.test;

import org.apache.camel.CamelContext;
import org.apache.camel.component.properties.PropertiesComponent;

public class UnitDevTest extends UnitProdTest {

	@Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext context = super.createCamelContext();

        // setup the properties component to use the test file
        PropertiesComponent prop = context.getComponent("properties", PropertiesComponent.class);
        prop.setLocation("classpath:rider-test.properties");

        return context;
    }
	
}
