package com.redhat.camel.test.mock;

public class OrderService {

	public boolean validateOrder(String order) {
        // validate order
        return order.startsWith("123");
    }

    public String processOrder(String order) {
        // to simulate exception being thrown
        if (order.endsWith("9999")) {
            throw new IllegalArgumentException("Forced error");
        }

        // simulate processing order
        return "OK," + order;
    }
	
}