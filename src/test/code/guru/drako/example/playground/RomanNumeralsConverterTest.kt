package guru.drako.example.playground

import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RomanNumeralsConverterTest {
  private val converter = RomanNumeralsConverter()

  companion object {
    @JvmStatic
    private fun testValues() = Stream.of(
        Arguments.of(1, "I"),
        Arguments.of(2, "II"),
        Arguments.of(5, "V"),
        Arguments.of(6, "VI"),
        Arguments.of(4, "IV"),
        Arguments.of(10, "X"),
        Arguments.of(9, "IX"),
        Arguments.of(11, "XI"),
        Arguments.of(12, "XII"),
        Arguments.of(14, "XIV"),
        Arguments.of(15, "XV"),
        Arguments.of(16, "XVI"),
        Arguments.of(23, "XXIII"),
        Arguments.of(49, "XLIX"),
        Arguments.of(50, "L"),
        Arguments.of(51, "LI"),
        Arguments.of(99, "XCIX"),
        Arguments.of(100, "C"),
        Arguments.of(101, "CI"),
        Arguments.of(149, "CXLIX"),
        Arguments.of(500, "D"),
        Arguments.of(999, "CMXCIX"),
        Arguments.of(1000, "M"),
        Arguments.of(1001, "MI"),
        Arguments.of(1500, "MD"),
        Arguments.of(1400, "MCD"),
        Arguments.of(1990, "MCMXC"),
        Arguments.of(3999, "MMMCMXCIX")
    )
  }

  @ParameterizedTest(name = "{index} ==> {1} should be {0}")
  @MethodSource("testValues")
  fun `Conversion Roman to Arabic`(arabic: Int, roman: String) {
    assertEquals(expected = arabic, actual = converter.roman2arabic(roman))
  }

  @ParameterizedTest(name = "{index} ==> {0} should throw")
  @ValueSource(strings = ["N", "ABC", "IL"])
  fun `Roman to Arabic should fail if number is not a roman numeral`(n: String) {
    assertThrows<IllegalArgumentException> {
      converter.roman2arabic(n)
    }
  }

  @ParameterizedTest(name = "{index} ==> {0} should be {1}")
  @MethodSource("testValues")
  fun `Conversion Arabic to Roman`(arabic: Int, roman: String) {
    assertEquals(expected = roman, actual = converter.arabic2roman(arabic))
  }

  @ParameterizedTest(name = "{index} ==> {0} should throw")
  @ValueSource(ints = [0, 4000])
  fun `Arabic to Roman should fail if number out of bounds`(n: Int) {
    assertThrows<IllegalArgumentException> {
      converter.arabic2roman(n)
    }
  }
}
