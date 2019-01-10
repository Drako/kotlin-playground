package guru.drako.example.playground.mavenlite

/**
 * @property repositories The repositories to search for dependencies.
 */
class DependencyResolver(val repositories: List<Repository>) {
  /**
   * Check the known [repositories] for the dependencies of the given [artifact].
   * Dependencies are resolved recursively.
   *
   * @param artifact The locally specified [Artifact] describing the current project.
   *
   * @throws ArtifactNotFoundException if the artifact could not be found anywhere.
   */
  suspend fun collectDependenciesOf(
    artifact: Artifact,
    seen: MutableSet<Dependency> = mutableSetOf()
  ) {
    dependencyLoop@ for (dep in (artifact.dependencies - seen)) {
      seen += dep
      for (repo in repositories) {
        val resolved = repo.queryArtifact(dep.groupId, dep.artifactId, dep.version)
        if (resolved != null) {
          collectDependenciesOf(resolved, seen)
          continue@dependencyLoop
        }
      }
      throw ArtifactNotFoundException("$dep")
    }
  }
}
