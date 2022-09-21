# opentracing-basis

This guide explains how your Quarkus application can utilize OpenTracing to provide distributed tracing for interactive web applications.

## Initialize Project

To initialize this project from the beginning, you can run the next `maven` command:
```bash
$ mvn io.quarkus.platform:quarkus-maven-plugin:2.12.2.Final:create -DprojectGroupId=com.redhat -DprojectArtifactId=opentracing-basis -Dextensions="resteasy-reactive,quarkus-smallrye-opentracing" -DnoCode
```

Otherwise, if you have an existing project you can add only the required extension for `opentracing` running the next command:
```bash
$ mvn quarkus:add-extension -Dextensions="smallrye-opentracing"
```

## How Opentracing works?

There are two ways of implement a Jaeger integration:

1. Using certain properties inside [application.properties](./src/main/resources/application.properties):
```properties
quarkus.jaeger.service-name=my-service
quarkus.jaeger.sampler-type=const
quarkus.jaeger.sampler-param=1
quarkus.log.console.format=%d{HH:mm:ss} %-5p traceId=%X{traceId}, parentId=%X{parentId}, spanId=%X{spanId}, sampled=%X{sampled} [%c{2.}] (%t) %s%e%n
```
2. Using environment variables at runtime:
```bash
$ mvn quarkus:dev -Djvm.args="-DJAEGER_SERVICE_NAME=my-service -DJAEGER_SAMPLER_TYPE=const -DJAEGER_SAMPLER_PARAM=1"
```

# Test this project

1. To test this project you need a running instance of Jaeger. For this, you need to run the next command:
```bash
$ docker run -d --name jaeger -e COLLECTOR_ZIPKIN_HTTP_PORT=9411 -p 5775:5775/udp -p 6831:6831/udp -p 6832:6832/udp -p 5778:5778 -p 16686:16686 -p 14268:14268 -p 14250:14250 -p 9411:9411 jaegertracing/all-in-one:latest
```

2. Then, execute and test this `Quarkus Application` executing the next commands in separate terminal windows:
```bash
$ mvn quarkus:dev
$ curl -vv localhost:8080/hello
```

> The above commands make a request to the REST API and return a `hello` message.

3. To see the tracing inside `Jaeger` web console, open the URL [http://localhost:16686/search](http://localhost:16686/search) and select the service `my-service` in Service dropdown menu.
