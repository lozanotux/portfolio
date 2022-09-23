# quarkus-fault-tolerance

This project demonstrate the usage of MicroProfile Fault Tolerance annotations such as @Timeout, @Fallback, @Retry and @CircuitBreaker.

## Prerequisites

* JDK 11+ installed with JAVA_HOME configured appropiately
* Maven 3.8.1+ 

## How to initialize this project?

To create this project from scratch run the next command:
```bash
$ mvn io.quarkus.platform:quarkus-maven-plugin:2.12.3.Final:create -DprojectGroupId=com.redhat -DprojectArtifactId=quarkus-fault-tolerance -Dextensions="smallrye-fault-tolerance,resteasy-reactive-jackson" -DnoCode
```

Or, if you have an existing project. You can add the required extension executing the next command:
```bash
$ mvn quarkus:add-extension -Dextensions="smallrye-fault-tolerance"
```

## Run this project

To execute and test this project, run the next command:
```bash
$ mvn quarkus:dev
```

## How to test the API?

To test the API, you can make the next requests:

```bash
$ curl localhost:8080/coffee
```

What's to expect to see?
```json
[
  {
    "id": 1,
    "name": "Fernandez Espresso",
    "countryOfOrigin": "Colombia",
    "price": 23
  },
  {
    "id": 2,
    "name": "La Scala Whole Beans",
    "countryOfOrigin": "Bolivia",
    "price": 18
  },
  {
    "id": 3,
    "name": "Dak Lak Filter",
    "countryOfOrigin": "Vietnam",
    "price": 25
  }
]
```

```bash
$ curl localhost:8080/coffee/2/recommendations
```

What's to expect to see?
```json
[
  {
    "id": 1,
    "name": "Fernandez Espresso",
    "countryOfOrigin": "Colombia",
    "price": 23
  },
  {
    "id": 3,
    "name": "Dak Lak Filter",
    "countryOfOrigin": "Vietnam",
    "price": 25
  }
]
```

```bash
$ curl localhost:8080/coffee/2/availability
```

What's to expect to see?
```json
12
```

> **Note:** this last one is particular, this method return a random Integer or a RuntimeException to see CircuitBreaker in action. It's recommended to open this request in a web browsers to see more simplified response.
