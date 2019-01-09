package guru.drako.example.playground.mavenlite

data class Artifact(
  val id: String,
  val version: String,
  val dependencies: Set<Dependency> = setOf()
)
