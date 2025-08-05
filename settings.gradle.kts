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
include(":core", ":system-service", ":user-service")
//include(":uns-cli")

project(":core").projectDir = file("core")
project(":system-service").projectDir = file("system-service")
project(":user-service").projectDir = file("user-service")
