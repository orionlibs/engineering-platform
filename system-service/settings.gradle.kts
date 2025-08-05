pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "system-service"

if (gradle.parent == null) {
    includeBuild("../core")
}
