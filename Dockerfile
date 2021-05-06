FROM openjdk:8-jdk-alpine
COPY target/addresses-api-*.jar /app/addresses-api.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app/addresses-api.jar"]