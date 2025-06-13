# Backend Dockerfile
FROM maven:3.9.10-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies (for better caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-jammy

# Create app directory
WORKDIR /app

# Create data directory for H2 database
RUN mkdir -p /app/data && chmod 755 /app/data

# Copy the built jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Set JVM options for Railway
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Set Spring profile to production
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application
CMD java $JAVA_OPTS -jar app.jar