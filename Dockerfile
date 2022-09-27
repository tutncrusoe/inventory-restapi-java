#Start with a base image containing Java runtime
FROM openjdk:11-slim as build

#Information around who maintains the image
MAINTAINER nqlam.com

# Add the application's jar to the container
COPY target/inventory-0.0.1-SNAPSHOT.jar inventory-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java","-jar","/inventory-0.0.1-SNAPSHOT.jar"]