pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "alarm-service"

if (gradle.parent == null) {
    includeBuild("../core")
}
