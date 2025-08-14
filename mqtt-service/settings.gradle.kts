pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "mqtt-service"

if (gradle.parent == null) {
    includeBuild("../core")
}
