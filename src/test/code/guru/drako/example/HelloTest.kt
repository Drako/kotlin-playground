package guru.drako.example

import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HelloTest {
  @Test
  @Ignore
  fun `greeting nobody should greet everybody`() {
    assertEquals(
        expected = "Hello, world!",
        actual = greet()
    )
  }

  @ParameterizedTest(name = "{index} ==> {0} should be greeted")
  @ValueSource(strings = ["Felix", "Martin", "Tilo", "Patrick"])
  @Ignore
  fun `greeting somebody should work`(who: String) {
    assertEquals(
        expected = "Hello, $who!",
        actual = greet(who)
    )
  }
}
