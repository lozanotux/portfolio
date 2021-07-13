package com.redhat.camel.beans;

public class HelloBean {
	
	public String hello(String name) {
        System.out.println("Invoking HelloBean with " + name);
        return "Hello " + name;
    }

}
