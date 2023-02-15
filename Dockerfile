FROM openjdk:19-alpine

COPY target/*.jar /kameleoon.jar

ENTRYPOINT ["java", "-jar", "/kameleoon.jar"]