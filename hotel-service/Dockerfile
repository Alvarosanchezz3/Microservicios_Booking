FROM amazoncorretto:21
EXPOSE 8085
RUN mvn package -DskipTests
COPY /target/hotel-service-0.0.1-SNAPSHOT.jar hotel-service.jar
ENTRYPOINT ["java", "-jar", "hotel-service.jar"]