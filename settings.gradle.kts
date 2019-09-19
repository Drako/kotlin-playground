rootProject.name = "kotlin-playground"

val knownPlugins = mapOf(
    "org.jetbrains.kotlin.plugin.serialization" to "org.jetbrains.kotlin:kotlin-serialization"
)

pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
  }

  resolutionStrategy {
    eachPlugin {
      knownPlugins[requested.id.id]?.let { groupAndArtifact ->
        useModule("$groupAndArtifact:${requested.version}")
      }
    }
  }
}
