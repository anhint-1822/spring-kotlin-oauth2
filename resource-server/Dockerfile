FROM maven AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk
COPY --from=build /usr/src/app/target/resource-server-0.0.1-SNAPSHOT.jar /usr/app/resource-server-0.0.1-SNAPSHOT.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","/usr/app/resource-server-0.0.1-SNAPSHOT.jar"]