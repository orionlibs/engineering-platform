import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    application
    jacoco
    `base`
    id("maven-publish")
    id("com.github.ben-manes.versions") version "0.52.0"
    id("com.vanniktech.dependency.graph.generator") version "0.7.0"
}

group = "io.github.orionlibs"
version = "0.0.1"

repositories {
    mavenLocal()
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("io.github.orionlibs.mqtt.Application")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "io.github.orionlibs"
            artifactId = "mqtt-service"
            version = "0.0.1"
        }
    }
    
    repositories {
        mavenLocal()
    }
}

val isMonorepoContext = gradle.parent != null

tasks.withType<JavaCompile> {
    finalizedBy("publishToMavenLocal")
    
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xlint:unchecked", "-Werror"))
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
    }
}

dependencies {
    implementation("com.hivemq:hivemq-community-edition-embedded:2025.4")
    implementation("com.hivemq:hivemq-extension-sdk:4.42.0")
    implementation("com.hivemq:hivemq-mqtt-client:1.3.7")

    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    testImplementation(platform("org.junit:junit-bom:5.13.3"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.named("build") {
    finalizedBy("publishToMavenLocal")
}
