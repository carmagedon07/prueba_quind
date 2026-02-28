# Multi-stage Dockerfile for building and running the Spring Boot application
# Builder stage: uses Maven with JDK17 to build the application
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app
# Copy only what is needed for dependency resolution first to leverage Docker cache
COPY pom.xml ./
COPY src ./src

# Build the application (skip tests to speed up build; remove -DskipTests to run tests)
RUN mvn -B -DskipTests package

# Run stage: smaller runtime image with JRE17
FROM eclipse-temurin:17-jre
LABEL maintainer="notification-service"

# Set a non-root user (optional) - use root by default for maximum compatibility
# Create app directory
WORKDIR /app

# Copy jar from builder stage
COPY --from=builder /app/target/*.jar /app/app.jar

# Expose the port the application listens on (matches application.properties)
EXPOSE 8086

# Use a small set of sensible JVM defaults via JAVA_TOOL_OPTIONS (can be overridden at runtime)
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -Xms256m -Xmx512m"

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

