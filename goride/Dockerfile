FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/goride-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 to the host
EXPOSE 8080

# Run the JAR file
CMD ["java", "-jar", "app.jar"]
