package guru.drako.example.playground

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doCallRealMethod
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.Test
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FizzBuzzTest {
  companion object {
    @Suppress("unused")
    @JvmStatic
    private fun divisableTestData(): Stream<Arguments> {
      return Stream.of(
          Arguments.of(1, 1, true),
          Arguments.of(1, 2, false),
          Arguments.of(2, 1, true),
          Arguments.of(2, 2, true),
          Arguments.of(2, 3, false),
          Arguments.of(3, 1, true),
          Arguments.of(3, 2, false),
          Arguments.of(3, 3, true),
          Arguments.of(5, 3, false),
          Arguments.of(5, 5, true),
          Arguments.of(6, 3, true),
          Arguments.of(10, 5, true),
          Arguments.of(15, 3, true),
          Arguments.of(15, 5, true)
      )
    }
  }

  private val instance = FizzBuzz()

  private val mockedInstance = mock<FizzBuzz>()

  @BeforeEach
  fun setup() {
    reset(mockedInstance)
  }

  @Test
  fun `Should have 100 lines`() {
    val result = instance.fizzbuzz()
    assertEquals(expected = 100, actual = result.lines().size)
  }

  @ParameterizedTest(name = "{index} ==> {0} isDivisableBy {1} should be {2}")
  @MethodSource("divisableTestData")
  fun `Check is divisable`(numerator: Int, denominator: Int, expectedResult: Boolean) {
    assertEquals(expectedResult, numerator isDivisableBy denominator)
  }

  @ParameterizedTest(name = "{index} ==> line {0} should be {1}")
  @CsvSource(
      "1, 1",
      "2, 2",
      "3, Fizz",
      "4, 4",
      "5, Buzz",
      "6, Fizz",
      "10, Buzz",
      "12, Fizz",
      "15, FizzBuzz",
      "30, FizzBuzz",
      "100, Buzz"
  )
  fun `Test getLineOutput`(line: Int, result: String) {
    val actual = instance.getLineOutput(line)
    assertEquals(actual = actual, expected = result)
  }

  @Test
  fun `fizzbuzz is working`() {
    doAnswer { invocation ->
      "${invocation.arguments[0]}"
    }.whenever(mockedInstance).getLineOutput(any())
    doCallRealMethod().whenever(mockedInstance).fizzbuzz()

    val actual = mockedInstance.fizzbuzz()
    val expected = (1..100).joinToString(separator = "\n")
    assertEquals(expected, actual)
    verify(mockedInstance, times(100)).getLineOutput(any())
  }
}
