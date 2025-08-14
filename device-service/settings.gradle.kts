pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "device-service"

if (gradle.parent == null) {
    includeBuild("../core")
}
