FROM ubuntu:20.04
FROM openjdk:11-jre-slim

EXPOSE 8081

ADD build/libs/embedapi-0.0.1-SNAPSHOT.jar embedapi-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","embedapi-0.0.1-SNAPSHOT.jar"]