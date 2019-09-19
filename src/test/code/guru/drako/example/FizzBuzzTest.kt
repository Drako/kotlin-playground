package guru.drako.example

import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FizzBuzzTest {
  private val INSTANCE = FizzBuzz()

  @ParameterizedTest(name = "{index} ==> {0} should be turned into {1}")
  @CsvSource(
      "1, 1", "2, 2", "4, 4", "7, 7", "97, 97", "98, 98",
      "3, Fizz", "6, Fizz", "12, Fizz", "96, Fizz", "99, Fizz",
      "5, Buzz", "10, Buzz","95, Buzz","100, Buzz",
      "15, FizzBuzz", "30, FizzBuzz", "45, FizzBuzz", "90, FizzBuzz"
  )
  fun `Rules of FizzBuzz should be obeyed`(n: Int, expectedResult: String) {
    assertEquals(
        expected = expectedResult,
        actual = INSTANCE.fizzBuzz(n)
    )
  }
}