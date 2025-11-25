# Etapa 1 — Construir la app con Gradle
FROM gradle:8.5-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# Etapa 2 — Imagen final, ligera
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiar el JAR generado
COPY --from=builder /app/build/libs/*.jar app.jar

# Puerto que usa Spring Boot
EXPOSE 8080

# Permitir que Render defina el puerto por variable de entorno
ENV PORT=8080

# Ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
