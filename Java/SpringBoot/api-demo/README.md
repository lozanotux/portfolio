# api-demo

This project was made to test Spring Boot with an example API of Users.

## Prerequisites

* Generate a new project with [Spring Initializr](https://start.spring.io/) and select next options:
  * **Language:** ⬤ Java
  * **Project:** ⬤ Maven
  * **Spring Boot Version:** ⬤ 3.1.5
  * **Packaging:** ⬤ Jar
  * **Java Version:** ⬤ 11
  * **Add next dependencies:**
    * Spring Web `WEB`
    * Spring Data JDBC `SQL`
* Run an instance of PostgreSQL with docker using next commmand:
    ```bash
    docker run -d --name postgresql -e POSTGRES_USER=pgsql -e POSTGRES_PASSWORD=s3cr3t -e POSTGRES_DB=example -p 5432:5432 postgres:14 
    ```
* Execute [01_CREATE-TABLE.sql](./database-scripts/01_CREATE-TABLE.sql) in your database instance.
* Configure DataSource in your Java project (in [application.properties](./src/main/resources/application.properties) file)

## Run & Test

To test this project, first run the Java application:
```bash
mvn spring-boot:run
```

And then execute next CURL command:
```bash
curl -X POST localhost:8080/api/user -H 'Content-Type: application/json' -d '{ "name": "John Doe"}'
```

You will be able to see a JSON response:
```json
{
  "id": 1,
  "name": "John Doe"
}
```