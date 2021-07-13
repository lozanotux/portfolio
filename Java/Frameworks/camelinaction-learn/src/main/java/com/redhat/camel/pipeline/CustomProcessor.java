package com.redhat.camel.pipeline;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class CustomProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		System.out.println("We just taked the file: " + exchange.getIn().getHeader("CamelFileName"));
	}

}
