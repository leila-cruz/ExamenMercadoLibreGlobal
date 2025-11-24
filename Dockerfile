# Etapa 1: Build
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
# -x test es clave para deploy rápido en la nube
RUN ./gradlew bootJar -x test --no-daemon

# Etapa 2: Runtime
FROM eclipse-temurin:21-jre-alpine
EXPOSE 8080
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]