package com.redhat.camel.transformation;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class OrderToCsvProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		String customInMessage = exchange.getIn().getBody(String.class);
		
		String id = customInMessage.substring(0, 9);
		String customerId = customInMessage.substring(10, 19);
		String date = customInMessage.substring(20, 29);
		String items = customInMessage.substring(30);
		
		String[] itemIds = items.split("@");
		
		// Map to CSV
		StringBuilder csv = new StringBuilder();
		csv.append(id.trim());
		csv.append(",").append(date.trim());
		csv.append(",").append(customerId.trim());
		for (String item : itemIds) {
			csv.append(",").append(item.trim());
		}
		
		exchange.getIn().setBody(csv.toString());
	}
	
}
