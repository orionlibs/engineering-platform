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
include(":core", ":user-service", ":system-service")
//include(":uns-cli")

project(":core").projectDir = file("core")
project(":user-service").projectDir = file("user-service")
project(":system-service").projectDir = file("system-service")
