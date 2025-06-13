# 1) Build stage
FROM eclipse-temurin:21-jre-jammy AS builder
WORKDIR /app
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN mvn dependency:go-offline -B
COPY src src
RUN mvn package -DskipTests -B

# 2) Run stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/backend-0.0.1-SNAPSHOT.jar app.jar
ENV PORT=8080
ENTRYPOINT ["java","-jar","app.jar"]
