FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app
COPY gradlew .
COPY gradle ./gradle
RUN chmod +x gradlew
COPY settings.gradle.kts build.gradle.kts ./
COPY core core
COPY mqtt-service ./mqtt-service
RUN ./gradlew :mqtt-service:bootJar --no-daemon
FROM eclipse-temurin:21-jre-jammy
EXPOSE 8080
COPY --from=build /app/mqtt-service/build/libs/app.jar /app.jar
ENTRYPOINT java -jar "/app.jar"
