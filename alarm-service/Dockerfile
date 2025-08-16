FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app
COPY gradlew .
COPY gradle ./gradle
RUN chmod +x gradlew
COPY settings.gradle.kts build.gradle.kts ./
COPY core core
COPY alarm-service ./alarm-service
RUN ./gradlew :alarm-service:bootJar --no-daemon
FROM eclipse-temurin:21-jre-jammy
EXPOSE 8080
COPY --from=build /app/alarm-service/build/libs/app.jar /app.jar
ARG ALARM_DATABASE_URL
ARG ALARM_DATABASE_USERNAME
ARG ALARM_DATABASE_PASSWORD
ENTRYPOINT java -jar "/app.jar"
