package guru.drako.example.playground.mavenlite

interface Repository {
  /**
   * Tries to find the artifact in itself and returns it if found.
   *
   * @param groupId The id of the group containing the artifact.
   * @param artifactId The id of the artifact.
   * @param version The version of the artifact.
   *
   * @return The artifact or null if not found.
   */
  suspend fun queryArtifact(groupId: String, artifactId: String, version: String): Artifact?
}
