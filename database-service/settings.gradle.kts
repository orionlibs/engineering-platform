pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "database-service"

if (gradle.parent == null) {
    includeBuild("../core")
}
