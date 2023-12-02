FROM maven:3.9-eclipse-temurin-19-alpine AS builder
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn clean package -DskipTests

FROM openjdk:19
WORKDIR "/app"
COPY --from=builder /target/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]