rootProject.name = "kotlin-playground"

pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
  }

  resolutionStrategy {
    eachPlugin {
      if (requested.id.id == "org.jetbrains.kotlin.plugin.serialization") {
        useModule("org.jetbrains.kotlin:kotlin-serialization:${requested.version}")
      }
    }
  }
}
