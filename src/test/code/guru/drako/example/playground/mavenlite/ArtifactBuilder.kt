package guru.drako.example.playground.mavenlite

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever

@DslMarker
annotation class ArtifactDsl

@ArtifactDsl
interface ArtifactConfigurator {
  var id: String
  var version: String

  operator fun String.unaryPlus()
}

private class ArtifactBuilder : ArtifactConfigurator {
  override lateinit var id: String
  override lateinit var version: String

  private val dependencies = mutableSetOf<Dependency>()

  override fun String.unaryPlus() {
    dependencies.add(Dependency.of(this))
  }

  fun build() = Artifact(id, version, dependencies)
}

fun artifact(
  artifactId: String? = null,
  version: String? = null,
  configure: ArtifactConfigurator.() -> Unit = {}
): Artifact {
  return ArtifactBuilder()
      .apply {
        artifactId?.let { id = it }
        version?.let { this.version = it }
      }
      .apply(configure).build()
}

suspend fun artifact(
  groupId: String,
  artifactId: String? = null,
  version: String? = null,
  repository: Repository,
  configure: ArtifactConfigurator.() -> Unit = {}
): Artifact {
  return artifact(
      artifactId = artifactId,
      version = version,
      configure = configure
  ).also {
    doReturn(it).whenever(repository).queryArtifact(groupId, it.id, it.version)
  }
}
