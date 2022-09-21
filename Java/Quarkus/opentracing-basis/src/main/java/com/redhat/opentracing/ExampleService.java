package com.redhat.opentracing;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.opentracing.Traced;

@Traced
@ApplicationScoped
public class ExampleService {

    public String bonjour() {
        return "bonjour";
    }

}
