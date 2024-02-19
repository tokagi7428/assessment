# Use an appropriate base image
FROM openjdk:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the Spring Boot application JAR file into the container
COPY build/libs/posttest-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application runs on
EXPOSE 8888

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
