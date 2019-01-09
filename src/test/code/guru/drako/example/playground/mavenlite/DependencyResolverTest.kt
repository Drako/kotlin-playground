package guru.drako.example.playground.mavenlite

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DependencyResolverTest {
  private val mavenCentral = mock<Repository>()
  private val jcenter = mock<Repository>()
  private val resolver = DependencyResolver(setOf(mavenCentral, jcenter))

  @BeforeEach
  fun setup() {
    reset(mavenCentral, jcenter)
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
