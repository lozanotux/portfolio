package com.redhat.camel.eip;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class MyAggregationStrategy implements AggregationStrategy {

	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		// the first time there are no existing message and therefore
        // the oldExchange is null. In these cases we just return
        // the newExchange
		if (oldExchange == null) {
			return newExchange;
		}
		
		// now we have both an existing message (oldExchange)
        // and a incoming message (newExchange)
        // we want to merge together.

        // in this example we add their bodies
		String oldBody = oldExchange.getIn().getBody(String.class);
		String newBody = newExchange.getIn().getBody(String.class);
		
		// the body should be the two bodies added together
        String body = oldBody + newBody;

        // update the existing message with the added body
        oldExchange.getIn().setBody(body);
        // and return it
        return oldExchange;
	}
	
}
