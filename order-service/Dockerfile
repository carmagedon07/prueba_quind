<<<<<<< HEAD
WORKDIR /app
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución (Runtime)
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copiamos solo el JAR generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
=======
# Usamos una imagen de Java ligera
FROM eclipse-temurin:21-jre-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el JAR generado por Maven al contenedor
COPY target/*.jar app.jar

# Comando para iniciar la aplicación
>>>>>>> develop_checkout_2
ENTRYPOINT ["java", "-jar", "app.jar"]