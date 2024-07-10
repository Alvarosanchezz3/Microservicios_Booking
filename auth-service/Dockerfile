FROM amazoncorretto:21
EXPOSE 8084
COPY /target/auth-service-0.0.1-SNAPSHOT.jar auth-service.jar
ENTRYPOINT ["java", "-jar", "auth-service.jar"]