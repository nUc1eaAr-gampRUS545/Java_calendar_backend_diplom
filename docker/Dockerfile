# Используем базовый образ с Java
FROM openjdk:23-jdk-alpine
COPY ../target/.*jar app.jar

EXPOSE 8080

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]