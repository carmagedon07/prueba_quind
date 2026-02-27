# Usamos una imagen de Java ligera
FROM eclipse-temurin:21-jre-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el JAR generado por Maven al contenedor
COPY target/*.jar app.jar

# Comando para iniciar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]