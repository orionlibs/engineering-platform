pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
  }
}

dependencyResolutionManagement {
  repositories {
    mavenLocal()
    mavenCentral()
  }
}

rootProject.name = "engineering-platform"
include(":core", ":mqtt-service", ":user-service", ":system-service", ":database-service", ":device-service", ":alarm-service")
//include(":uns-cli")

project(":core").projectDir = file("core")
project(":mqtt-service").projectDir = file("mqtt-service")
project(":user-service").projectDir = file("user-service")
project(":system-service").projectDir = file("system-service")
project(":database-service").projectDir = file("database-service")
project(":device-service").projectDir = file("device-service")
project(":alarm-service").projectDir = file("alarm-service")
