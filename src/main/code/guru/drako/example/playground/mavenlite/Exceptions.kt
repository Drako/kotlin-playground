package guru.drako.example.playground.mavenlite

import java.lang.RuntimeException

class ArtifactNotFoundException(val artifact: String) : RuntimeException("$artifact could not be resolved on any of the repositories.")
