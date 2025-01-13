# Stage 1: Build the JAR file using Maven
FROM maven:3.9.4-amazoncorretto-21 AS build


# Set the working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Run Maven to build the application
RUN mvn clean install

# Stage 2: Run the application
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/urlShortner-0.0.1-SNAPSHOT.jar app.jar

# Expose the application's port
EXPOSE 8090

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
