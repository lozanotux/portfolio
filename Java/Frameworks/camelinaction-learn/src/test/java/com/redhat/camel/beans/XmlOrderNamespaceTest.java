package com.redhat.camel.beans;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.w3c.dom.Document;

public class XmlOrderNamespaceTest extends CamelTestSupport {

	@Override
	protected JndiRegistry createRegistry() throws Exception {
		JndiRegistry jndi = super.createRegistry();
        jndi.bind("guid", new GuidGenerator());
        jndi.bind("xmlOrderService", new XmlOrderNamespaceService());
        return jndi;
	}
	
	@Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
				from("file://target/order")
				.to("log:before")
				.bean("xmlOrderService")
				.to("log:after")
				.to("mock:queue:order");
			}
		};
    }
	
	@Test
    public void test_send_incomingOrder() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:queue:order");
        mock.expectedMessageCount(1);
        context.setTracing(true);

        // prepare a XML document from a String which is converted to a DOM
        String body = "<order xmlns=\"http://camelinaction.com/order\" customerId=\"4444\"><item>Camel in action</item></order>";
        Document xml = context.getTypeConverter().convertTo(Document.class, body);
        
        // store the order as a file which is picked up by the route
        template.sendBodyAndHeader("file://target/order", xml, Exchange.FILE_NAME, "order.xml");

        mock.assertIsSatisfied();
    }
	
}
