FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app
COPY gradlew .
COPY gradle ./gradle
RUN chmod +x gradlew
COPY settings.gradle.kts build.gradle.kts ./
COPY core core
COPY system-service ./system-service
RUN ./gradlew :system-service:bootJar --no-daemon
FROM eclipse-temurin:21-jre-jammy
EXPOSE 8080
COPY --from=build /app/system-service/build/libs/app.jar /app.jar
ARG SYSTEM_DATABASE_URL
ARG SYSTEM_DATABASE_USERNAME
ARG SYSTEM_DATABASE_PASSWORD
ENTRYPOINT java -jar "/app.jar"
