package guru.drako.example.playground.mavenlite

data class Dependency(
  val groupId: String,
  val artifactId: String,
  val version: String
) {
  companion object {
    fun of(string: String) = string
        .split(":")
        .let { parts -> Dependency(parts[0], parts[1], parts[2]) }
  }

  override fun toString() = "$groupId:$artifactId:$version"
}
