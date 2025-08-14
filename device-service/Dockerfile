FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app
COPY gradlew .
COPY gradle ./gradle
RUN chmod +x gradlew
COPY settings.gradle.kts build.gradle.kts ./
COPY core core
COPY device-service ./device-service
RUN ./gradlew :device-service:bootJar --no-daemon
FROM eclipse-temurin:21-jre-jammy
EXPOSE 8080
COPY --from=build /app/device-service/build/libs/app.jar /app.jar
ARG DEVICE_DATABASE_URL
ARG DEVICE_DATABASE_USERNAME
ARG DEVICE_DATABASE_PASSWORD
ENTRYPOINT java -jar "/app.jar"
