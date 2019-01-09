package guru.drako.example.playground.mavenlite

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DependencyResolverTest {
  private val mavenCentral = mock<Repository>()
  private val jcenter = mock<Repository>()
  private val resolver = DependencyResolver(listOf(mavenCentral, jcenter))

  @BeforeEach
  fun setup() {
    reset(mavenCentral, jcenter)
  }

  @Test
  fun `Collecting dependencies for artifact with no dependencies should work`() {
    runBlocking {
      resolver.collectDependenciesOf(artifact("dummy", "0.1.0"))
    }
  }

  private fun checkSingleDependencyFoundInRepository(repository: Repository) {
    runBlocking {
      val dummyArtifact = artifact("guru.drako.example", "dummy", "0.1.0", repository = repository)

      val myArtifact = artifact(artifactId = "my", version = "0.1.0") {
        +"guru.drako.example:dummy:0.1.0"
      }

      resolver.collectDependenciesOf(myArtifact)
      with(dummyArtifact) {
        verify(repository).queryArtifact(
            "guru.drako.example",
            id,
            version
        )
      }
    }
  }

  @Test
  fun `Collecting dependencies should work if dependency is in first repo`() {
    checkSingleDependencyFoundInRepository(mavenCentral)
  }

  @Test
  fun `Collecting dependencies should work if dependency is in second repo`() {
    checkSingleDependencyFoundInRepository(jcenter)
  }

  @Test
  fun `Collecting dependencies should fail if dependency is in neither repo`() {
    val myArtifact = artifact(artifactId = "my", version = "0.1.0") {
      +"guru.drako.example:dummy:0.1.0"
    }

    val ex = assertThrows<ArtifactNotFoundException> {
      runBlocking {
        resolver.collectDependenciesOf(myArtifact)
      }
    }
    assertEquals(expected = "guru.drako.example:dummy:0.1.0", actual = ex.artifact)
  }

  @Test
  fun `Collecting from different repos works`() {
    runBlocking {
      artifact(groupId = "guru.drako.example", artifactId = "a", version = "0.1.0", repository = mavenCentral)
      artifact(groupId = "guru.drako.example", artifactId = "b", version = "0.1.0", repository = jcenter)
      val myArtifact = artifact(artifactId = "test", version = "0.1.0") {
        +"guru.drako.example:a:0.1.0"
        +"guru.drako.example:b:0.1.0"
      }
      resolver.collectDependenciesOf(myArtifact)
      verify(mavenCentral, times(2)).queryArtifact(any(), any(), any())
      verify(jcenter, times(1)).queryArtifact(any(), any(), any())
    }
  }

  @Test
  fun `Collecting recursively works`() {
    runBlocking {
      artifact(groupId = "guru.drako.example", artifactId = "a", version = "0.1.0", repository = mavenCentral) {
        +"guru.drako.example:b:0.1.0"
      }
      artifact(groupId = "guru.drako.example", artifactId = "b", version = "0.1.0", repository = jcenter)
      val myArtifact = artifact(artifactId = "test", version = "0.1.0") {
        +"guru.drako.example:a:0.1.0"
      }
      resolver.collectDependenciesOf(myArtifact)
      verify(mavenCentral, times(2)).queryArtifact(any(), any(), any())
      verify(jcenter, times(1)).queryArtifact(any(), any(), any())
    }
  }

  @Test
  fun `Collecting circular dependencies recursively works`() {
    runBlocking {
      artifact(groupId = "guru.drako.example", artifactId = "a", version = "0.1.0", repository = mavenCentral) {
        +"guru.drako.example:b:0.1.0"
      }
      artifact(groupId = "guru.drako.example", artifactId = "b", version = "0.1.0", repository = jcenter) {
        +"guru.drako.example:b:0.1.0"
      }
      val myArtifact = artifact(artifactId = "test", version = "0.1.0") {
        +"guru.drako.example:a:0.1.0"
      }
      resolver.collectDependenciesOf(myArtifact)
      verify(mavenCentral, times(2)).queryArtifact(any(), any(), any())
      verify(jcenter, times(1)).queryArtifact(any(), any(), any())
    }
  }

  @Ignore("These are just here to make that the mocking with coroutines works as expected, they don't need to be run all the time.")
  @Nested
  inner class CheckTestEvironment {
    @Test
    fun `Not adjusting the mocks should not crash badly`() = runBlocking {
      assertNull(mavenCentral.queryArtifact("guru.drako.example", "mavenlite", "0.1.0"))
      assertNull(jcenter.queryArtifact("guru.drako.example", "mavenlite", "0.1.0"))
    }

    @Test
    fun `Mocking coroutines should work as expected`() {
      val dummyArtifact = Artifact(
          id = "dummy",
          version = "0.1.0"
      )

      runBlocking {
        doReturn(dummyArtifact).whenever(mavenCentral).queryArtifact(any(), eq("dummy"), eq("0.1.0"))

        assertNotNull(mavenCentral.queryArtifact("guru.drako.example", "dummy", "0.1.0"))
        assertNull(mavenCentral.queryArtifact("guru.drako.example", "dummy", "0.0.1"))
        assertNull(mavenCentral.queryArtifact("guru.drako.example", "foo", "0.1.0"))
        assertNotNull(mavenCentral.queryArtifact("com.example", "dummy", "0.1.0"))
      }
    }
  }
}
