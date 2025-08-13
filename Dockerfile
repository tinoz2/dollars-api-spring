# Etapa 1: Build
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos archivos de Maven
COPY pom.xml .
COPY src ./src

# Compilamos el proyecto y empaquetamos el jar
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copiamos el jar desde la etapa de build
COPY --from=build /app/target/minty-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]