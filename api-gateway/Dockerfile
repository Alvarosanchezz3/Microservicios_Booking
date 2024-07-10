FROM amazoncorretto:21
EXPOSE 8086
COPY /target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]