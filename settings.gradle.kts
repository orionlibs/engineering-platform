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
include(":core", ":user-service")
//include(":uns-cli")

project(":core").projectDir = file("core")
project(":user-service").projectDir = file("user-service")
