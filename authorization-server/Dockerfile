#FROM maven AS build
#COPY /src /usr/src/app/src
#COPY pom.xml /usr/src/app
#RUN mvn -f /usr/src/app/pom.xml clean install -Dmaven.install.skip=true

FROM openjdk
COPY target/authorization-server-0.0.1-SNAPSHOT.jar authorization-server-0.0.1-SNAPSHOT.jarạ
#EXPOSE 8901
#ENTRYPOINT ["./wait-for-it.sh", "mysql-master:3308","java","-jar","/authorization-server-0.0.1-SNAPSHOT.jar"]