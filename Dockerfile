FROM openjdk:8
COPY ./target/spring-kotlin-crud-0.0.1-SNAPSHOT.jar spring-kotlin-crud-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","spring-kotlin-crud-0.0.1-SNAPSHOT.jar"]