FROM amazoncorretto:21
EXPOSE 8085
COPY /target/email-service-0.0.1-SNAPSHOT.jar email-service.jar
ENTRYPOINT ["java", "-jar", "email-service.jar"]