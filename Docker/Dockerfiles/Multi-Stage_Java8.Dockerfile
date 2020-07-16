# alpine versions are an alternative of more smaller images
FROM maven:3.6-jdk-8-alpine AS builder
WORKDIR /app
COPY pom.xml .
# this line help us to check if dependencies are correctly or not
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn -e -B package

# In this case, we use a multi-stage builds in Docker
# If we not specify the version, java version can change
# in the future and our app will can not work.
FROM openjdk:8-jre-alpine
COPY --from=builder /app/target/app-0.0.1.jar .
CMD ["java", "-jar", "/app-0.0.1.jar"]