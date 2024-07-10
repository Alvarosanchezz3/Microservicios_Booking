FROM amazoncorretto:21
EXPOSE 8888
COPY /target/config-server-0.0.1-SNAPSHOT.jar server-config.jar
ENTRYPOINT ["java", "-jar", "server-config.jar"]