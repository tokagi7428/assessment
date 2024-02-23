# Use an appropriate base image
FROM openjdk:17-jdk-alpine

# Set working directory
WORKDIR /app

ADD . .

# install gradle and build .jar
RUN chmod +x gradlew
RUN ./gradlew wrapper
RUN ["./gradlew","bootJar"]

# Expose the port your application runs on
EXPOSE 8888

# Run the application
ENTRYPOINT ["java", "-jar", "build/libs/posttest-0.0.1-SNAPSHOT.jar"]
