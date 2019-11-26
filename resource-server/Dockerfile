#FROM maven AS build
#COPY src /usr/src/app/src
#COPY pom.xml /usr/src/app
#RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk
COPY target/resource-server-0.0.1-SNAPSHOT.jar resource-server-0.0.1-SNAPSHOT.jar