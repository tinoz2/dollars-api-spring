FROM openjdk:21-jdk-slim
WORKDIR /app

# Copiamos el .jar con el nombre exacto
COPY target/minty-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Ejecutamos el jar dentro de /app
ENTRYPOINT ["java","-jar","/app/app.jar"]