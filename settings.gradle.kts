rootProject.name = "kotlin-playground"

pluginManagement {
  repositories {
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
  }

  resolutionStrategy {
    eachPlugin {
      if (requested.id.id == "org.jetbrains.kotlin.plugin.serialization") {
        useModule("org.jetbrains.kotlin:kotlin-serialization:${requested.version}")
      }
    }
  }
}
