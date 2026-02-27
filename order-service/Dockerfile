WORKDIR /app
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución (Runtime)
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copiamos solo el JAR generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]