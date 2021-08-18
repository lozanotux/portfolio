# Greeting Quarkus

This project uses Quarkus, the Supersonic Subatomic Java Framework. The application `greeting` only take a secret from an OpenShift cluster an show its content.

## How did I scaffold the project?

You need **Maven** as dependency (no matter if we want to use **mvn** itself or **gralde**). For gradle, you can run the next command from a command line:
```bash
mvn io.quarkus:quarkus-maven-plugin:1.4.1.Final:create -DprojectGroupId=com.redhat -DprojectArtifactId=greeting -DclassName="com.redhat.greeting.GreetingResource" -Dpath="/hello" -DbuildTool=gradle
```

## How to deploy the app into OpenShift 4.X?

First you need to build the **JAR** file, and then the docker image. The next commands will generate the openshift artifacts (deployment and service objects). This particular project also needs a secret with a specific content to show that through the `API` of the app.

So, the next commands are necessary to deploy the app from the beginning (standing in the root folder of the project):

1. Build the application. This command will generate the **JAR** file and **OpenShift** artifacts:
```bash
$ ./gradlew clean build -x test
```
2. Then, build the docker image using the **JAR** file previously generated:
```bash
$ docker build -f src/main/docker/Dockerfile.jvm -t lozanotux/greeting:1.0 .
```
3. Push the docker image to our registry (hub.docker.com in this case):
```bash
$ docker push lozanotux/greeting:1.0
```
4. Create the secret that the app will read to show through the `API`:
```bash
$ oc create secret generic greeting-security --from-literal=github.api.key.token=eyJhbGci0NFtZSI6IpMeQssw5c
```
5. Create the **OpenShift** artifacts inside the cluster with the `OC` command line utiliy:
```bash
$ oc apply -f build/kubernetes/kubernetes.yml
```
6. Finally, create the **route** for the app exposing the `service` object:
```bash
$ oc expose svc greeting
```


