package com.redhat.camel.transformation;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class ReverseDataFormatTest extends CamelTestSupport {

	@Test
    public void testMarshal() throws Exception {
        String out = template.requestBody("direct:marshal", "Camel rocks", String.class);
        assertEquals("skcor lemaC", out);
    }

    @Test
    public void testUnmarshal() throws Exception {
        String out = template.requestBody("direct:unmarshal", "skcor lemaC", String.class);
        assertEquals("Camel rocks", out);
    }
	
	@Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
            	
            	ReverseDataFormat reverse = new ReverseDataFormat();
            	
                from("direct:marshal")
                    .marshal(reverse)
                    .log("log:marshal");
                
                from("direct:unmarshal")
                	.unmarshal(reverse)
                	.to("log:unmarshal");
            }
        };
    }
	
}
