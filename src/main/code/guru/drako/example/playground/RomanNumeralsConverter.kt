package guru.drako.example.playground

class RomanNumeralsConverter {
  /**
   * Converts a roman numeral [String] into a normal [Int].
   *
   * @param roman A [String] containing a valid roman numeral.
   * @return The numeric value of the given roman numeral.
   *
   * @throws NumberFormatException if the parameter is not a valid roman numeral.
   */
  fun roman2arabic(roman: String): Int = TODO("Convert \"$roman\" to an Int")

  /**
   * Converts a normal [Int] into a [String] containing a roman numeral.
   *
   * @param arabic The number to be converted.
   * @return The number as a roman numeral.
   *
   * @throws IllegalArgumentException if the parameter is not in the range 1..3999.
   */
  fun arabic2roman(arabic: Int): String = TODO("Convert $arabic to a roman numeral")
}
