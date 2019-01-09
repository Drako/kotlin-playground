package guru.drako.example.playground

import java.lang.IllegalArgumentException
import java.lang.NumberFormatException

class RomanNumeralsConverter {
  enum class RomanDigits(
    val value: Int,
    val subtractor: RomanDigits? = null
  ) {
    I(1),
    V(5, I),
    X(10, I),
    L(50, X),
    C(100, X),
    D(500, C),
    M(1000, C);

    val char: Char
      get() = name[0]
  }

  companion object {
    private val DIGITS = RomanDigits
        .values()
        .asSequence()
        .sortedByDescending { it.value }
        .toList()

    private val NUMERAL_PATTERN = Regex(DIGITS.fold("^") { acc, digit ->
      "$acc$digit{0,3}" + (digit.subtractor?.let { subtractor ->
        "(?:$subtractor$digit)?"
      } ?: "")
    } + "$")
  }

  /**
   * Converts a roman numeral [String] into a normal [Int].
   *
   * @param roman A [String] containing a valid roman numeral.
   * @return The numeric value of the given roman numeral.
   *
   * @throws NumberFormatException if the parameter is not a valid roman numeral.
   */
  fun roman2arabic(roman: String): Int {
    if (!NUMERAL_PATTERN.matches(roman)) {
      throw NumberFormatException("$roman is not a valid roman numeral")
    }

    var index = 0
    return DIGITS.fold(0) { acc, digit ->
      if (index >= roman.length) return@fold acc

      var sum = 0
      while (index < roman.length && roman[index] == digit.char) {
        sum += digit.value
        ++index
      }

      if (
          (index + 1 < roman.length) &&
          (roman[index] == digit.subtractor?.char) &&
          (roman[index + 1] == digit.char)
      ) {
        sum += digit.value - digit.subtractor.value
        index += 2
      }

      acc + sum
    }
  }

  /**
   * Converts a normal [Int] into a [String] containing a roman numeral.
   *
   * @param arabic The number to be converted.
   * @return The number as a roman numeral.
   *
   * @throws IllegalArgumentException if the parameter is not in the range 1..3999.
   */
  fun arabic2roman(arabic: Int): String {
    if (arabic !in 1..3999) {
      throw IllegalArgumentException("$arabic is not in range 1..3999")
    }

    var remaining = arabic
    return DIGITS.fold("") { acc, digit ->
      val part = digit.name.repeat(remaining / digit.value)
      remaining %= digit.value
      if (digit.subtractor != null) {
        val value = digit.value - digit.subtractor.value
        if (remaining >= value) {
          remaining -= value
          return@fold acc + part + digit.subtractor.char + digit.char
        }
      }
      acc + part
    }
  }
}
